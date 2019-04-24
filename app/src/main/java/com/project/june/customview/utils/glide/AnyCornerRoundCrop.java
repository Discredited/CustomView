package com.project.june.customview.utils.glide;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * @author <a href="mailto:xujun@snqu.com">徐俊</a>
 * @version 1.0.0
 * @description 任意圆角
 * @time 2018/6/5 19:39
 */

public class AnyCornerRoundCrop extends BitmapTransformation {

    private static final int VERSION = 1;
    private static final String ID = "com.snqu.cospa.utils.glide.AnyCornerRoundCrop" + VERSION;
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private int radius;
    private int cropType;

    public AnyCornerRoundCrop(int cropType, int radius) {
        this.radius = radius;
        this.cropType = cropType;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return TransformationUtils.anyCornerRoundedCorners(pool, toTransform, radius, cropType);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
