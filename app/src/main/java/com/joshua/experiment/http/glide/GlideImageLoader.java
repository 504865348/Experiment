package com.joshua.experiment.http.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.joshua.experiment.R;
import com.youth.banner.loader.ImageLoader;

import static android.R.attr.data;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/5/27 13:49
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);

    }
}
