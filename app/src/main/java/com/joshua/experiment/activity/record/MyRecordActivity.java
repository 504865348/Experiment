package com.joshua.experiment.activity.record;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.joshua.experiment.R;
import com.joshua.experiment.adapter.MyRecordAdapter;
import com.joshua.experiment.entity.MyRecording;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

public class MyRecordActivity extends AppCompatActivity {

    private List<MyRecording> mMyRecordings = new ArrayList<>();
    private MyRecordAdapter mAdapter;
    private boolean isEmpty = false;
    @BindView(R.id.my_records_lv)
    ListView records_list;
    @BindView(R.id.empty_layout)
    FrameLayout empty_layout;
    @BindView(R.id.my_records_tool_bar)
    Toolbar my_records_tool_bar;
    @BindView(R.id.iv_add)
    ImageView iv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_records);
        ButterKnife.bind(this);

        my_records_tool_bar.setTitle("");
        setSupportActionBar(my_records_tool_bar);
        GetVideoFiles(); // 获取所有文件，转化为 MyRecording 对象
        if (!isEmpty) {
            records_list.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
            mAdapter = new MyRecordAdapter(MyRecordActivity.this, mMyRecordings);
            records_list.setAdapter(mAdapter);
        } else {
            records_list.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        }
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
    }

    /**
     * 监听返回按钮
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * 获取文件列表
     */
    public void GetVideoFiles() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 获取该文件夹下的所有文件
             */
            mMyRecordings.clear();
            String path = Environment.getExternalStorageDirectory().getPath();
            File mediaStorageDir = new File(path + "/crafts_videos");
            File[] files = mediaStorageDir.listFiles();
            if (files == null) {
                isEmpty = true;
            } else {
                for (File file : files) {
                    Log.e("TAG", file.getName());
                    MyRecording myRecording = new MyRecording();
                    myRecording.setName(file.getName());
                    String publish_time = new SimpleDateFormat("yyyy年MM月dd日").format(file.lastModified());
                    myRecording.setTime(publish_time);
                    myRecording.setStorageUrl(file.getAbsolutePath());
                    mMyRecordings.add(myRecording);
                }
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
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
                        GetVideoFiles();
                        mAdapter.notifyDataSetChanged();
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
