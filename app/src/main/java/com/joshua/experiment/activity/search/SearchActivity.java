package com.joshua.experiment.activity.search;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;
import com.joshua.experiment.adapter.HotCraftsAdapter;
import com.joshua.experiment.adapter.HotSkillsAdapter;
import com.joshua.experiment.adapter.QuesAnsClassifyAdapter;
import com.joshua.experiment.entity.HotCraftsman;
import com.joshua.experiment.entity.HotSkills;
import com.joshua.experiment.entity.joshua.QuesAnsClassify;
import com.joshua.experiment.entity.Server;
import com.joshua.experiment.http.HttpCommonCallback;
import com.joshua.experiment.http.HttpCookieJar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class SearchActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.search_history_back)
    ImageView img_back;
    @BindView(R.id.search_interface_search)
    TextView text_search;
    @BindView(R.id.search_history_edit)
    EditText mSearchHistoryEdit;
    @BindView(R.id.rv_album)
    RecyclerView rv_album;
    @BindView(R.id.tv_album)
    TextView tv_album;
    @BindView(R.id.rv_craftsman)
    RecyclerView rv_craftsman;
    @BindView(R.id.tv_craftsman)
    TextView tv_craftsman;
    @BindView(R.id.rv_question)
    RecyclerView rv_question;
    @BindView(R.id.tv_question)
    TextView tv_question;

    //访问网络
    private OkHttpClient mClient;
    private List<QuesAnsClassify> list_question = new ArrayList<>();
    private List<HotSkills> list_album = new ArrayList<>();
    private List<HotCraftsman> list_craftsman = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        ButterKnife.bind(this);
        setListener();
    }

    /**
     * 设置点击监听器
     */
    private void setListener() {
        img_back.setOnClickListener(this);
        text_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_history_back:
                finish();
                break;
            case R.id.search_interface_search:
                // 保存关键字
                String keyWord = mSearchHistoryEdit.getText().toString();
                if (keyWord.length() <= 10 && keyWord.length() > 0) {
                    searchFromServer(keyWord);
                } else {
                    Toast.makeText(this, "关键字需要在1-10之间", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 搜索
     */
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private void searchFromServer(String keyWord) {
        mClient = new OkHttpClient.Builder()
                .cookieJar(new HttpCookieJar(mBaseActivity))
                .build();
        String requestStr = "method=" + Server.SERVER_SEARCH + "&keyWord=" + keyWord;

        RequestBody params = RequestBody.create(MEDIA_TYPE_MARKDOWN, requestStr);
        final Request request = new Request.Builder()
                .url(Server.SERVER_REMOTE)
                .post(params)
                .build();
        Call call = mClient.newCall(request);
        call.enqueue(new HttpCommonCallback(mBaseActivity) {
            @Override
            protected void success(String result) {
                parseData(result);
            }

            @Override
            protected void error() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mBaseActivity, "网络错误，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void parseData(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.d(TAG, "parseData: " + jsonObject);
            String question = jsonObject.getString("question");
            Log.d(TAG, "question: " + question);
            String album = jsonObject.getString("album");
            Log.d(TAG, "album: " + album);
            String craftsman = jsonObject.getString("craftsman");
            Log.d(TAG, "craftsman: " + craftsman);
            Gson gson = new Gson();
            //解析question
            list_question = gson.fromJson(question, new TypeToken<List<QuesAnsClassify>>() {
            }.getType());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initRecycleQuestion();
                    if (list_question.size() > 0) {
                        tv_question.setVisibility(View.GONE);
                    } else {
                        tv_question.setVisibility(View.VISIBLE);
                    }
                }
            });
            //解析album
            list_album = gson.fromJson(album, new TypeToken<List<HotSkills>>() {
            }.getType());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initRecycleAlbum();
                    if (list_album.size() > 0) {
                        tv_album.setVisibility(View.GONE);
                    } else {
                        tv_album.setVisibility(View.VISIBLE);
                    }
                }
            });
            //解析craftsman
            list_craftsman = gson.fromJson(craftsman, new TypeToken<List<HotCraftsman>>() {
            }.getType());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    initRecycleCraftsman();
                    if (list_craftsman.size() > 0) {
                        tv_craftsman.setVisibility(View.GONE);
                    } else {
                        tv_craftsman.setVisibility(View.VISIBLE);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initRecycleCraftsman() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_craftsman.setLayoutManager(linearLayoutManager);
        rv_craftsman.setAdapter(new HotCraftsAdapter(this, list_craftsman));
    }

    private void initRecycleAlbum() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        rv_album.setLayoutManager(linearLayoutManager);
        rv_album.setHasFixedSize(true);
        rv_album.setNestedScrollingEnabled(false);
        HotSkillsAdapter albumAdapter = new HotSkillsAdapter(this, list_album);
        rv_album.setAdapter(albumAdapter);
    }

    private void initRecycleQuestion() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_question.setLayoutManager(linearLayoutManager);
        rv_question.setHasFixedSize(true);
        rv_question.setNestedScrollingEnabled(false);
        rv_question.setAdapter(new QuesAnsClassifyAdapter(this, list_question));
    }
}
