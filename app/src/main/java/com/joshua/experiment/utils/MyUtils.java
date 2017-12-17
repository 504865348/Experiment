package com.joshua.experiment.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.attr.path;
import static com.joshua.experiment.R.drawable.p;


/**
 * ============================================================
 * <p/>
 * 版 权 ： 吴奇俊  (c) 2016
 * <p/>
 * 作 者 : 吴奇俊
 * <p/>
 * 版 本 ： 1.0
 * <p/>
 * 创建日期 ： 2016/8/2 17:29
 * <p/>
 * 描 述 ：个人工具类
 * <p/>
 * ============================================================
 **/
public class MyUtils extends Activity {
    public static String TAG = "com.joshua.log";
    private static int SD_TOTAL_MEMORY = 0;//获取SD卡全部内存
    private static int SD_REMAIN_MEMORY = 1;//获取SD卡剩余内存

    /**
     * 发送短信
     * <uses-permission android:name="android.permission.SEND_SMS"/>
     *
     * @param address 地址
     * @param content 内容
     */
    public static void sendSms(String address, String content) {
        SmsManager manager = SmsManager.getDefault();
        ArrayList<String> smsList = manager.divideMessage(content);
        for (String sms : smsList) {
            manager.sendTextMessage(address, null, sms, null, null);
        }
    }

    /**
     * 拨打电话
     * <uses-permission android:name="android.permission.CALL_PHONE"/>
     *
     * @param number 号码
     * @return 用于调用拨号界面的intent
     */
    public static Intent callNumber(String number) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        return intent;
    }

    /**
     * 判断SD卡片是否挂载
     *
     * @return boolean
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡容量
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
     *
     * @param activity 上下文
     * @param choice   SD_TOTAL_MEMORY=0：全部容量
     *                 SD_REMAIN_MEMORY=1：剩余容量
     * @return 容量
     */
    public static String getSDcardMemory(Activity activity, int choice) {
        File sdCard = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(sdCard.getPath());
        long blockSize;
        long totalBlocks;
        long availableBlocks;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = statFs.getBlockSizeLong();
            availableBlocks = statFs.getAvailableBlocksLong();
            totalBlocks = statFs.getBlockCountLong();
        } else {
            blockSize = statFs.getBlockSize();
            totalBlocks = statFs.getBlockCount();
            availableBlocks = statFs.getAvailableBlocks();
        }
        if (choice == SD_TOTAL_MEMORY) {
            return Formatter.formatFileSize(activity, totalBlocks * blockSize);

        } else if (choice == SD_REMAIN_MEMORY) {
            return Formatter.formatFileSize(activity, availableBlocks * blockSize);
        }
        return "获取SD卡容量失败";
    }

    /**
     * 将String写入文件中
     *
     * @param string string内容
     * @param file   目标文件
     * @throws IOException
     */
    public static void String2File(String string, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(string.getBytes());
    }

    /**
     * 从url连接中获取文件名称
     *
     * @param url 网络地址
     * @return 文件名称
     */
    public static String getFileNameFromURL(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is     输入流
     * @param decode 编码方式
     * @return 字符串
     */
    public static String Stream2String(InputStream is, String decode) {
        byte[] b = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            while ((len = is.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            String text = new String(bos.toByteArray(), decode);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否是手机号码
     *
     * @param mobiles 手机号码
     * @return true/false
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobiles.isEmpty()) {
            return false;
        }
        Pattern MB = Pattern.compile("^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$");
        /**
         * 中国移动：China Mobile
         * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
         */
        Pattern CM = Pattern.compile("^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\\\\d{8}$");
        /**
         * 中国联通：China Unicom
         * 130,131,132,145,155,156,170,171,175,176,185,186
         */
        Pattern CU = Pattern.compile("^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\\\\d{8}$");
        /**
         * 中国电信：China Telecom
         * 133,149,153,170,173,177,180,181,189
         */
        Pattern CT = Pattern.compile("^1(3[3]|4[9]|53|7[037]|8[019])\\\\d{8}$");


        return MB.matcher(mobiles).matches() ||CM.matcher(mobiles).matches() || CU.matcher(mobiles).matches() || CT.matcher(mobiles).matches();
    }

    /**
     * 判断是否是邮箱地址
     *
     * @param emails 邮箱地址
     * @return true/false
     */
    public static boolean isEmailAdress(String emails) {
        if (emails.isEmpty()) {
            return false;
        }

        Pattern p = Pattern.compile("/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/");

        Matcher m = p.matcher(emails);

        return m.matches();

    }

    /**
     * 通过图片的uri转换为bitmap
     *
     * @param context 上下文
     * @param uri     图片地址
     * @return bitmap
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将bitmap保存为图片文件
     *
     * @param bitmap  bitmap
     * @param picName 文件名称
     */
    public static void saveBitmap(Bitmap bitmap, String user, String picName) {
        //sd卡保存路径
        String absPath = Environment.getExternalStorageDirectory() + "/craftsman/" + user;
        try {
            File category = new File(absPath);
            File file = new File(absPath, picName + ".JPEG");
            if (file.exists()) {
                file.delete();
            } else {
                category.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态计算listView的高度以便于和scrollView结合
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 把drawable转化为bitmap
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }

    /**
     * 生成验证码
     *
     * @return
     */
    public static String generifyCode() {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int itmp = random.nextInt(9);
            String stmp = itmp + "";
            sRand.append(stmp);
        }
        return sRand.toString();
    }

}

