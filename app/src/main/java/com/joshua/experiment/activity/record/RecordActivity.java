package com.joshua.experiment.activity.record;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.experiment.R;
import com.joshua.experiment.activity.core.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class RecordActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = "MainActivity";
    private SurfaceView mSurfaceview;
    private Button mBtnStartStop;
    private Button mBtnPlay, mSaveVideo;
    private boolean mStartedFlg = false;//是否正在录像
    private boolean mIsPlay = false;//是否正在播放录像
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private ImageView mImageView;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;
    private TextView textView;
    private int text = 0;
    private TextView tv_change;
    private boolean isFront=true;

    private android.os.Handler handler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            text++;
            textView.setText(text + "");
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);

        mImageView = (ImageView) findViewById(R.id.iv_background);
        mSurfaceview = (SurfaceView) findViewById(R.id.surfaceview);
        mBtnStartStop = (Button) findViewById(R.id.btnStartStop);
        mBtnPlay = (Button) findViewById(R.id.btnPlayVideo);
        mSaveVideo= (Button) findViewById(R.id.btnDone);
        textView = (TextView) findViewById(R.id.text);
        tv_change= (TextView) findViewById(R.id.tv_change);
        tv_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFront=!isFront;
                if(isFront){
                    tv_change.setText("点击改变：当前使用前置摄像头");
                }else{
                    tv_change.setText("点击改变：当前使用后置摄像头");
                }

            }
        });
        mBtnStartStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv_change.setVisibility(View.GONE);

                if (mIsPlay) {
                    if (mediaPlayer != null) {
                        mIsPlay = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
                if (!mStartedFlg) {
                    //之前录制过，取消， 删除文件
                    if (path != null) {
                        File file = new File(path);
                        file.delete();
                        text = 0;
                    }
                    handler.postDelayed(runnable, 1000);
                    mImageView.setVisibility(View.GONE);
                    if (mRecorder == null) {
                        mRecorder = new MediaRecorder();
                    }
                    if(isFront){
                        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                        if (camera != null) {
                            camera.setDisplayOrientation(90);
                            camera.unlock();
                            mRecorder.setCamera(camera);
                        }
                    }else {
                        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                        if (camera != null) {
                            camera.setDisplayOrientation(90);
                            camera.unlock();
                            mRecorder.setCamera(camera);
                        }
                    }


                    try {
                        // 这两项需要放在setOutputFormat之前
                        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

                        // Set output file format
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

                        // 这两项需要放在setOutputFormat之后
                        //.设置音频的编码格式
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                        //设置图像的编码格式
                        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

                        mRecorder.setVideoSize(640, 480);
                        mRecorder.setVideoFrameRate(128);
                        mRecorder.setVideoEncodingBitRate(1024 * 1024);
                        mRecorder.setOrientationHint(270);
                        //设置记录会话的最大持续时间（毫秒）
                        mRecorder.setMaxDuration(10000000);
                        mRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());

                        File dir = createMediaFile();
                        path = dir.getAbsolutePath();
                        mRecorder.setOutputFile(path);
                        mRecorder.prepare();
                        mRecorder.start();
                        mStartedFlg = true;
                        mBtnStartStop.setText("完成");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //stop
                    if (mStartedFlg) {
                        try {
                            handler.removeCallbacks(runnable);
                            mRecorder.stop();
                            mRecorder.reset();
                            mRecorder.release();
                            mRecorder = null;
                            mBtnStartStop.setText("重录");
                            if (camera != null) {
                                camera.release();
                                camera = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            mStartedFlg = false;
                        }
                    }

                }
            }
        });

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (path == null) {
                    Toast.makeText(mBaseActivity, "请先录制视频", Toast.LENGTH_SHORT).show();
                    return;
                }
                mIsPlay = true;
                mImageView.setVisibility(View.GONE);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.reset();
                Uri uri = Uri.parse(path);
                mediaPlayer = MediaPlayer.create(mBaseActivity, uri);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDisplay(mSurfaceHolder);
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });


        mSaveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(800, 480);
        // setType必须设置，要不出错.
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mStartedFlg) {
            mImageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);         // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);       // 分
        int hour = ca.get(Calendar.HOUR);           // 小时
        int second = ca.get(Calendar.SECOND);       // 秒

        String date = "" + year + (month + 1) + day + hour + minute + second;
        Log.d(TAG, "date:" + date);

        return date;
    }

    /**
     * 获取SD path
     *
     * @return
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
            return sdDir.toString();
        }

        return null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // 将holder，这个holder为开始在onCreate里面取得的holder，将它赋给mSurfaceHolder
        mSurfaceHolder = surfaceHolder;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        handler.removeCallbacks(runnable);
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
            Log.d(TAG, "surfaceDestroyed release mRecorder");
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
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

}

