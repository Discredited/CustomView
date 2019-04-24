package com.project.june.customview.utils.glide;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;
import com.bumptech.glide.util.Synthetic;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class TransformationUtils {
    private static final String TAG = "TransformationUtils";
    public static final int PAINT_FLAGS = Paint.DITHER_FLAG | Paint.FILTER_BITMAP_FLAG;
    private static final Paint DEFAULT_PAINT = new Paint(PAINT_FLAGS);
    private static final int CIRCLE_CROP_PAINT_FLAGS = PAINT_FLAGS | Paint.ANTI_ALIAS_FLAG;
    private static final Paint CIRCLE_CROP_SHAPE_PAINT = new Paint(CIRCLE_CROP_PAINT_FLAGS);
    private static final Paint CIRCLE_CROP_BITMAP_PAINT;

    public final static int ALL = 1;
    public final static int TOP_LEFT = 2;
    public final static int TOP_RIGHT = 3;
    public final static int BOTTOM_LEFT = 4;
    public final static int BOTTOM_RIGHT = 5;
    public final static int TOP = 6;
    public final static int BOTTOM = 7;
    public final static int LEFT = 8;
    public final static int RIGHT = 9;

    // See #738.
    private static final Set<String> MODELS_REQUIRING_BITMAP_LOCK =
            new HashSet<>(
                    Arrays.asList(
                            // Moto X gen 2
                            "XT1085",
                            "XT1092",
                            "XT1093",
                            "XT1094",
                            "XT1095",
                            "XT1096",
                            "XT1097",
                            "XT1098",
                            // Moto G gen 1
                            "XT1031",
                            "XT1028",
                            "XT937C",
                            "XT1032",
                            "XT1008",
                            "XT1033",
                            "XT1035",
                            "XT1034",
                            "XT939G",
                            "XT1039",
                            "XT1040",
                            "XT1042",
                            "XT1045",
                            // Moto G gen 2
                            "XT1063",
                            "XT1064",
                            "XT1068",
                            "XT1069",
                            "XT1072",
                            "XT1077",
                            "XT1078",
                            "XT1079"
                    )
            );

    /**
     * https://github.com/bumptech/glide/issues/738 On some devices, bitmap drawing is not thread
     * safe.
     * This lock only locks for these specific devices. For other types of devices the lock is always
     * available and therefore does not impact performance
     */
    private static final Lock BITMAP_DRAWABLE_LOCK =
            MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL)
                    ? new ReentrantLock() : new NoLock();

    static {
        ROUND_CROP_BACKGROUND_BITMAP_PAINT = new Paint();

        CIRCLE_CROP_BITMAP_PAINT = new Paint(CIRCLE_CROP_PAINT_FLAGS);
        CIRCLE_CROP_BITMAP_PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
    }

    private static Paint ROUND_CROP_BACKGROUND_BITMAP_PAINT;

    private TransformationUtils() {
        // Utility class.
    }


    public static Lock getBitmapDrawableLock() {
        return BITMAP_DRAWABLE_LOCK;
    }


    /**
     * Crop the image to a circle and resize to the specified width/height.  The circle crop will
     * have the same width and height equal to the min-edge of the result image.
     *
     * @param pool       The BitmapPool obtain a bitmap from.
     * @param inBitmap   The Bitmap to resize.
     * @param destWidth  The width in pixels of the final Bitmap.
     * @param destHeight The height in pixels of the final Bitmap.
     * @return The resized Bitmap (will be recycled if recycled is not null).
     */
    public static Bitmap circleCrop(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap,
                                    int destWidth, int destHeight,
                                    int borderWidth, int borderColor) {

        int destMinEdge = Math.min(destWidth, destHeight);
        float radius = destMinEdge / 2f;

        // Alpha is required for this transformation.
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);

        Config outConfig = getAlphaSafeConfig(inBitmap);
        Bitmap result = pool.get(destMinEdge, destMinEdge, outConfig);
        result.setHasAlpha(true);

        BITMAP_DRAWABLE_LOCK.lock();
        CIRCLE_CROP_SHAPE_PAINT.setColor(borderColor);
        CIRCLE_CROP_SHAPE_PAINT.setStyle(Paint.Style.STROKE);
        CIRCLE_CROP_SHAPE_PAINT.setStrokeWidth(borderWidth);
        CIRCLE_CROP_BITMAP_PAINT.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        try {
            Canvas canvas = new Canvas(result);
            // Draw a circle
            canvas.drawCircle(radius, radius, radius - borderWidth / 2, CIRCLE_CROP_SHAPE_PAINT);
            // Draw the bitmap in the circle
            canvas.drawCircle(radius, radius, radius - borderWidth, CIRCLE_CROP_BITMAP_PAINT);
            clear(canvas);
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    private static Bitmap getAlphaSafeBitmap(
            @NonNull BitmapPool pool, @NonNull Bitmap maybeAlphaSafe) {
        Config safeConfig = getAlphaSafeConfig(maybeAlphaSafe);
        if (safeConfig.equals(maybeAlphaSafe.getConfig())) {
            return maybeAlphaSafe;
        }

        Bitmap argbBitmap =
                pool.get(maybeAlphaSafe.getWidth(), maybeAlphaSafe.getHeight(), safeConfig);
        new Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0 /*left*/, 0 /*top*/, null /*paint*/);

        // We now own this Bitmap. It's our responsibility to replace it in the pool outside this method
        // when we're finished with it.
        return argbBitmap;
    }

    @NonNull
    private static Config getAlphaSafeConfig(@NonNull Bitmap inBitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Avoid short circuiting the sdk check.
            if (Config.RGBA_F16.equals(inBitmap.getConfig())) { // NOPMD
                return Config.RGBA_F16;
            }
        }

        return Config.ARGB_8888;
    }

    /**
     * Creates a bitmap from a source bitmap and rounds the corners.
     * <p>
     * <p>This method does <em>NOT</em> resize the given {@link Bitmap}, it only rounds it's corners.
     * To both resize and round the corners of an image, consider
     * {@link com.bumptech.glide.request.RequestOptions#transforms(Transformation[])} and/or
     * {@link com.bumptech.glide.load.MultiTransformation}.
     *
     * @param inBitmap       the source bitmap to use as a basis for the created bitmap.
     * @param roundingRadius the corner radius to be applied (in device-specific pixels).
     * @return a {@link Bitmap} similar to inBitmap but with rounded corners.
     * @throws IllegalArgumentException if roundingRadius, width or height is 0 or less.
     */
    public static Bitmap roundedCorners(
            @NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int roundingRadius,
            int borderWidth, int borderColor) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");

        // Alpha is required for this transformation.
        Config safeConfig = getAlphaSafeConfig(inBitmap);
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), safeConfig);

        result.setHasAlpha(true);

        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        ROUND_CROP_BACKGROUND_BITMAP_PAINT.setAntiAlias(true);
        ROUND_CROP_BACKGROUND_BITMAP_PAINT.setColor(borderColor);
        //带边框
        RectF bgRect = new RectF(0, 0, result.getWidth(), result.getHeight());
        RectF rect = new RectF(borderWidth, borderWidth, result.getWidth() - borderWidth, result.getHeight() - borderWidth);
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            canvas.drawColor(borderColor, PorterDuff.Mode.CLEAR);
            canvas.drawRoundRect(bgRect, roundingRadius, roundingRadius, ROUND_CROP_BACKGROUND_BITMAP_PAINT);
            canvas.drawRoundRect(rect, roundingRadius, roundingRadius, paint);
            clear(canvas);
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    public static Bitmap anyCornerRoundedCorners(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int roundingRadius, int cornerType) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");

        // Alpha is required for this transformation.
        Config safeConfig = getAlphaSafeConfig(inBitmap);
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), safeConfig);

        result.setHasAlpha(true);

        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);

        //带边框
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            drawRoundRect(canvas, paint, cornerType, roundingRadius, result.getWidth(), result.getHeight());
            clear(canvas);
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock();
        }

        if (!toTransform.equals(inBitmap)) {
            pool.put(toTransform);
        }

        return result;
    }

    private static void drawRoundRect(Canvas canvas, Paint paint, int cornerType, int radius, float right, float bottom) {
        switch (cornerType) {
            case ALL:
                canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
                break;
            case TOP_LEFT:
                drawTopLeftRoundRect(canvas, paint, radius, right, bottom);
                break;
            case TOP_RIGHT:
                drawTopRightRoundRect(canvas, paint, radius, right, bottom);
                break;
            case BOTTOM_LEFT:
                drawBottomLeftRoundRect(canvas, paint, radius, right, bottom);
                break;
            case BOTTOM_RIGHT:
                drawBottomRightRoundRect(canvas, paint, radius, right, bottom);
                break;
            case TOP:
                drawTopRoundRect(canvas, paint, radius, right, bottom);
                break;
            case BOTTOM:
                drawBottomRoundRect(canvas, paint, radius, right, bottom);
                break;
            case LEFT:
                drawLeftRoundRect(canvas, paint, radius, right, bottom);
                break;
            case RIGHT:
                drawRightRoundRect(canvas, paint, radius, right, bottom);
                break;
            default:
                canvas.drawRoundRect(new RectF(0, 0, right, bottom), radius, radius, paint);
                break;
        }
    }

    private static void drawTopLeftRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, radius * 2, radius * 2),
                radius, radius, paint);
        canvas.drawRect(new RectF(0, radius, radius, bottom), paint);
        canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
    }

    private static void drawTopRightRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - radius * 2, 0, right, radius * 2), radius,
                radius, paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
        canvas.drawRect(new RectF(right - radius, radius, right, bottom), paint);
    }

    private static void drawBottomLeftRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, bottom - radius * 2, radius * 2, bottom),
                radius, radius, paint);
        canvas.drawRect(new RectF(0, 0, radius * 2, bottom - radius), paint);
        canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
    }

    private static void drawBottomRightRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - radius * 2, bottom - radius * 2, right, bottom), radius,
                radius, paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
        canvas.drawRect(new RectF(right - radius, 0, right, bottom - radius), paint);
    }

    private static void drawTopRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, right, radius * 2), radius, radius,
                paint);
        canvas.drawRect(new RectF(0, radius, right, bottom), paint);
    }

    private static void drawBottomRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, bottom - radius * 2, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(0, 0, right, bottom - radius), paint);
    }

    private static void drawLeftRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(0, 0, radius * 2, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(radius, 0, right, bottom), paint);
    }

    private static void drawRightRoundRect(Canvas canvas, Paint paint, int radius, float right, float bottom) {
        canvas.drawRoundRect(new RectF(right - radius * 2, 0, right, bottom), radius, radius,
                paint);
        canvas.drawRect(new RectF(0, 0, right - radius, bottom), paint);
    }

    // Avoids warnings in M+.
    private static void clear(Canvas canvas) {
        canvas.setBitmap(null);
    }

    private static final class NoLock implements Lock {

        @Synthetic
        NoLock() {
        }

        @Override
        public void lock() {
            // do nothing
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            // do nothing
        }

        @Override
        public boolean tryLock() {
            return true;
        }

        @Override
        public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
            return true;
        }

        @Override
        public void unlock() {
            // do nothing
        }

        @NonNull
        @Override
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}