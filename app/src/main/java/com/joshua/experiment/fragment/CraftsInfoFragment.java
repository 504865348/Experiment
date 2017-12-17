package com.joshua.experiment.fragment;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.account.MoneyActivity;
import com.joshua.experiment.activity.answer.MyAskAnswerActivity;
import com.joshua.experiment.activity.feedback.FeedbackActivity;
import com.joshua.experiment.activity.info.EditInfoActivity;
import com.joshua.experiment.activity.my.MyAlbumActivity;
import com.joshua.experiment.activity.my.MyBillboardHotActivity;
import com.joshua.experiment.activity.order.MyOrderActivity;
import com.joshua.experiment.activity.other.MyBuyActivity;
import com.joshua.experiment.activity.other.MyCollectActivity;
import com.joshua.experiment.activity.other.MySubscribeActivity;
import com.joshua.experiment.activity.other.VideoBuyTimesActivity;
import com.joshua.experiment.activity.record.MyRecordActivity;
import com.joshua.experiment.activity.record.PostRecordActivity;
import com.joshua.experiment.activity.record.RecordActivity;
import com.joshua.experiment.activity.set.SetActivity;
import com.joshua.experiment.utils.PrefUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.joshua.experiment.R.id.my_info_my_coins;

public class CraftsInfoFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.my_info_crafts_record)
    RelativeLayout btn_record;
    @BindView(R.id.my_info_my_records)
    LinearLayout btn_my_record;
    @BindView(R.id.my_info_my_q_a)
    LinearLayout my_info_my_q_a;
    @BindView(R.id.my_info_picture)
    ImageView mMyInfoPicture;
    @BindView(R.id.my_info_user_name)
    TextView mMyInfoUserName;
    @BindView(R.id.next)
    ImageButton mNext;
    @BindView(R.id.my_info_more)
    RelativeLayout mMyInfoMore;
    @BindView(R.id.my_info_subscribe)
    LinearLayout mMyInfoSubscribe;
    @BindView(R.id.my_info_collection)
    LinearLayout mMyInfoCollection;
    @BindView(R.id.text_start_record)
    TextView mTextStartRecord;
    @BindView(R.id.text_start_upload)
    TextView mTextStartUpload;
    @BindView(R.id.my_info_crafts_upload)
    RelativeLayout mMyInfoCraftsUpload;
    @BindView(R.id.orderform)
    ImageButton mOrderform;
    @BindView(R.id.my_info_my_orders)
    LinearLayout mMyInfoMyOrders;
    @BindView(R.id.my_info_money)
    TextView mMyInfoMoney;
    @BindView(R.id.money)
    ImageButton mMoney;
    @BindView(R.id.my_info_my_coins)
    LinearLayout mMyInfoMyCoins;
    @BindView(R.id.my_info_q_a_one)
    ImageView mMyInfoQAOne;
    @BindView(R.id.question)
    ImageButton mQuestion;
    @BindView(R.id.album)
    ImageButton mAlbum;
    @BindView(R.id.my_info_my_albums)
    LinearLayout mMyInfoMyAlbums;
    @BindView(R.id.list)
    ImageButton mList;
    @BindView(R.id.my_info_my_billboard)
    LinearLayout mMyInfoMyBillboard;
    @BindView(R.id.recorder)
    ImageButton mRecorder;
    @BindView(R.id.opinion)
    ImageButton mOpinion;
    @BindView(R.id.my_info_feedback)
    LinearLayout mMyInfoFeedback;
    @BindView(R.id.set)
    ImageButton mSet;
    @BindView(R.id.my_info_sets)
    LinearLayout mMyInfoSets;
    @BindView(R.id.my_info_user_account)
    TextView myInfoUserAccount;
    @BindView(R.id.my_info_my_buys)
    LinearLayout mMyInfoBuy;
    @BindView(R.id.my_info_crafts_upload_local)
    RelativeLayout my_info_crafts_upload_local;

    private View view;
    private Uri fileUri;
    private int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 1;
    private SharedPreferences sp;
    private String userClass;

    @Override
    public View initView() {
        view = View.inflate(mContext, R.layout.my_info_crafts, null);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        showUserInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
        showUserInfo();
    }

    @Override
    public void initData() {
        super.initData();
        mMyInfoSubscribe.setOnClickListener(this);
        mMyInfoCollection.setOnClickListener(this);
        mMyInfoMyAlbums.setOnClickListener(this);
        mMyInfoMyBillboard.setOnClickListener(this);
        mMyInfoMore.setOnClickListener(this);
        mMyInfoFeedback.setOnClickListener(this);
        mMyInfoSets.setOnClickListener(this);
        mMyInfoCraftsUpload.setOnClickListener(this);
        mMyInfoBuy.setOnClickListener(this);
        my_info_crafts_upload_local.setOnClickListener(this);
        userClass = PrefUtils.getString(getActivity(), "phone", "");
        showUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 如果是小米或者OPPO，则打开自定义摄像头
     * 否则打开原生摄像头
     */
    @OnClick(R.id.my_info_crafts_record)
    public void startRecord() {
        String model = android.os.Build.MODEL;
        String type = model.substring(0, 2);
        Log.d("手机类型", "startRecord: " + model);
        if (type.equals("OP") || type.equals("MI")) {
            Intent intent1 = new Intent(getActivity(), RecordActivity.class);
            startActivity(intent1);
        } else {
            try {
                fileUri = Uri.fromFile(createMediaFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            Intent intent = new Intent();
            intent.setAction("android.media.action.VIDEO_CAPTURE");
            intent.addCategory("android.intent.category.DEFAULT");

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // 设置视频文件的名字
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0); // 设置视频质量为高
            startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

        }


    }

    @OnClick(R.id.my_info_my_orders)
    public void myOrders() {
        startActivity(new Intent(getActivity(), MyOrderActivity.class));
    }

    @OnClick(R.id.my_info_my_records)
    public void myRecord() {
        startActivity(new Intent(getActivity(), VideoBuyTimesActivity.class));
    }

    @OnClick(R.id.my_info_my_q_a)
    public void my_q_a() {
        startActivity(new Intent(getActivity(), MyAskAnswerActivity.class));
    }

    @OnClick(my_info_my_coins)
    public void my_coins() {
        startActivity(new Intent(getActivity(), MoneyActivity.class));
    }


    /**
     * 创建一个媒体对象
     */
    private File createMediaFile() throws IOException {
        /**
         * 如果 SD 卡存在，则在外部存储建立一个文件夹用于存放视频
         */
        if ((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
            /**
             * 选择自己的文件夹
             */
            String path = Environment.getExternalStorageDirectory().getPath();
            File mediaStorageDir = new File(path + "/crafts_videos");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                    return null;
                }
            }
            /**
             * 文件根据当前的毫秒数给自己命名
             */
            String timeStamp = String.valueOf(System.currentTimeMillis());
            timeStamp = timeStamp.substring(7);
            String imageFileName = "V" + timeStamp;
            String suffix = ".mp4";
            File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
            return mediaFile;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info_subscribe:
                startActivity(new Intent(getActivity(), MySubscribeActivity.class));
                break;
            case R.id.my_info_collection:
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.my_info_my_albums:
                startActivity(new Intent(getActivity(), MyAlbumActivity.class));
                break;
            case R.id.my_info_my_billboard:
//                startActivity(new Intent(getActivity(), MyBillboardActivity.class));
                startActivity(new Intent(getActivity(), MyBillboardHotActivity.class));
                break;
            case R.id.my_info_more:
                startActivity(new Intent(getActivity(), EditInfoActivity.class));
                break;
            case R.id.my_info_feedback:
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
                break;
            case R.id.my_info_sets:
                startActivity(new Intent(getActivity(), SetActivity.class));
                break;
            case R.id.my_info_crafts_upload:
                startActivity(new Intent(getActivity(), MyRecordActivity.class));
                break;
            case R.id.my_info_my_buys:
                startActivity(new Intent(getActivity(), MyBuyActivity.class));
                break;
            case R.id.my_info_crafts_upload_local:
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
                break;

        }
    }


    private void showUserInfo() {
        sp = getActivity().getSharedPreferences(userClass, Context.MODE_PRIVATE);
        if (sp.getString("nickName", "").isEmpty())
            mMyInfoUserName.setText(userClass);
        else
            mMyInfoUserName.setText(sp.getString("nickName", ""));
        myInfoUserAccount.setText(userClass);
        mMyInfoPicture.setImageURI(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/craftsman/" + userClass + "/headImage.JPEG")));


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                /** 数据库查询操作。
                 * 第一个参数 uri：为要查询的数据库+表的名称。
                 * 第二个参数 projection ： 要查询的列。
                 * 第三个参数 selection ： 查询的条件，相当于SQL where。
                 * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
                 * 第四个参数 sortOrder ： 结果排序。
                 */


                Cursor cursor = cr.query(uri, null, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        // 视频ID:MediaStore.Audio.Media._ID
                        int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                        // 视频名称：MediaStore.Audio.Media.TITLE
                        String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                        // 视频路径：MediaStore.Audio.Media.DATA
                        String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        // 视频时长：MediaStore.Audio.Media.DURATION
                        int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                        // 视频大小：MediaStore.Audio.Media.SIZE
                        long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                        // 视频缩略图路径：MediaStore.Images.Media.DATA
                        String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                        // 缩略图ID:MediaStore.Audio.Media._ID
                        int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                        // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                        // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
                        Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

                        // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                        // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                        Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                        // 如果追求更好的话可以利用 ThumbnailUtils.extractThumbnail 把缩略图转化为的制定大小
//                        ThumbnailUtils.extractThumbnail(bitmap, width,height ,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

                        copyFile(imagePath,Environment.getExternalStorageDirectory().getPath() + "/crafts_videos/" + title);

                        Intent intent = new Intent(this.getActivity(), PostRecordActivity.class);
                        intent.putExtra("videoPath", videoPath);
                        startActivity(intent);

                    }
                    cursor.close();
                }
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        }
        catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }



}
