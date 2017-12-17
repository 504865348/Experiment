package com.joshua.experiment.adapter.qafragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.joshua.experiment.activity.ask.AskQuestionActivity;
import com.joshua.experiment.entity.ClassQuesAns;
import com.joshua.experiment.utils.AudioRecoderUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nzz on 2017/7/23.
 * 问答主界面--分类--问答
 */

public class ClassQuesAnsAdapter extends android.support.v7.widget.RecyclerView.Adapter<ClassQuesAnsAdapter.MyViewHolder> implements MediaPlayer.OnCompletionListener {
    private LayoutInflater mInflater;
    private Activity mContext;
    private List<ClassQuesAns> data = new ArrayList<>();
    private OkHttpClient mOkHttpClient;
    private ProgressDialog mDialog;
    private Call mCall;
    private static MediaPlayer music;
    private File mFile;
    private boolean isFinishPlaying = true;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress=msg.arg1;
            mDialog.setProgress(progress);
        }
    };

    public ClassQuesAnsAdapter(Activity context, List<ClassQuesAns> data) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
        mOkHttpClient = new OkHttpClient();
        music = new MediaPlayer();
        music.setOnCompletionListener(this);
        mDialog = new ProgressDialog(mContext);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setMax(100);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.question_answer_class_ques_item, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv_craftsName.setText(data.get(position).getCraftsName());
        holder.tv_introduction.setText(data.get(position).getIntroduction());
        holder.tv_content.setText(data.get(position).getContent());
        if (data.get(position).getListenrNumber().equals("null")) {
            holder.tv_listenrNumber.setText("0人听过");
        }
        else {
            holder.tv_listenrNumber.setText(data.get(position).getListenrNumber() + "人听过");
        }
        holder.tv_time.setText(data.get(position).getTime());
        Glide.with(mContext).load(data.get(position).getCraftsImage()).placeholder(R.drawable.load_error).into(holder.iv_craftsImage);
        holder.btn_q_a_item_go_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AskQuestionActivity.class);
                intent.putExtra("answer", data.get(position).getCraftsName());
                intent.putExtra("craftsAccount", data.get(position).getCraftsName());
                mContext.startActivity(intent);
            }
        });
        holder.rl_play_sound.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String id = data.get(position).getId();
                String url=data.get(position).getDownloadUrl();
                //首先判断是否已经下载
                if (checkLocal(id)) {
                    //录音的播放与暂停
                    playRecord();
                } else {

                    mDialog.setMessage("音频下载中，请稍后");
                    mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mCall.cancel();
                            mFile.delete();
                        }
                    });
                    mDialog.show();
                    getSoundFromServer(id,url);
                }
            }
        });
        holder.itemView.setTag(position + "");
    }

    /**
     * 判断音频文件是否存在
     */
    private boolean checkLocal(String id) {
        mFile = new File(AudioRecoderUtils.RECODE_PATH, id + ".aac");
        return mFile.exists();
    }

    /**
     * 录音播放
     */
    private void playRecord() {
        if (mFile.exists()) {
            if (music.isPlaying()) {
                music.pause();
            } else {
                try {
                    if (isFinishPlaying) {
                        music = new MediaPlayer();
                        music.setDataSource(mFile.getAbsolutePath());
                        music.prepare();
                        isFinishPlaying = false;
                    }
                    music.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Toast.makeText(mContext, "出现异常，请反馈", Toast.LENGTH_SHORT).show();
        }
    }

    //访问网络，获取音频流，下载，准备播放
    private void getSoundFromServer(final String id,final String url) {
//        RequestBody params = new FormBody.Builder()
//                .add("method", Server.QUERY_QUESTION)
//                .add("Id", id)
//                .build();
        Request request = new Request.Builder()
//                .post(params)
                .url(url)
                .build();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                        mFile.delete();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(AudioRecoderUtils.RECODE_PATH, id + ".acc");
                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        int progress = (int) (sum * 1.0f / total * 100);
                        Log.d("h_bl", "progress=" + progress);
                        Message msg = mHandler.obtainMessage();
                        msg.what = 1;
                        msg.arg1 = progress;
                        mHandler.sendMessage(msg);
                    }
                    fos.flush();
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();
                        }
                    });

                } catch (Exception e) {
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                            mFile.delete();
                            mDialog.dismiss();
                        }
                    });

                } finally {
                    if (is != null)
                        is.close();
                    if (fos != null)
                        fos.close();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        isFinishPlaying = true;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_craftsImage;
        TextView tv_craftsName;
        TextView tv_introduction;
        TextView tv_content;
        TextView tv_listenrNumber;
        TextView tv_time;

        Button btn_q_a_item_go_ask;
        RelativeLayout rl_play_sound;

        MyViewHolder(View itemView) {
            super(itemView);
            iv_craftsImage = (ImageView) itemView.findViewById(R.id.question_answer_ques_img);
            tv_craftsName = (TextView) itemView.findViewById(R.id.question_answer_ques_crafts_name);
            tv_introduction = (TextView) itemView.findViewById(R.id.question_answer_ques_crafts_intro);
            tv_content = (TextView) itemView.findViewById(R.id.question_answer_ques_content);
            tv_listenrNumber = (TextView) itemView.findViewById(R.id.question_answer_ques_people_content);
            tv_time = (TextView) itemView.findViewById(R.id.question_answer_ques_duration);
            btn_q_a_item_go_ask = (Button) itemView.findViewById(R.id.question_answer_ques_go_ask);
            rl_play_sound = (RelativeLayout) itemView.findViewById(R.id.question_answer_play_sound);

        }
    }

}
