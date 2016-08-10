package com.lz.easyui.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Nick create at 2011-3-17
 */
public class ImageUtil {

    private static String TAG = ImageUtil.class.getSimpleName();


    public static Bitmap createVideoThumbnail(String filePath) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            bitmap = android.media.ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        } catch (IllegalArgumentException ex) {
        } catch (RuntimeException ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }

        return bitmap;
    }

    public static Bitmap scaleAndCutThumbnail(String filename, int resizeWidth, int resizeHeight) {
        Bitmap bitmap = null;
        Bitmap resBitmap = null;
        try {
            resBitmap = ImageUtil.extractPicture(filename);
            if (resBitmap != null) {
                bitmap = ImageUtil.cutImage(resBitmap, resizeWidth, resizeHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resBitmap != null && !resBitmap.equals(bitmap) && !resBitmap.isRecycled())
                resBitmap.recycle();
        }
        return bitmap;
    }

    public static Bitmap scaleThumbnail(String filename, int resizeWidth, int resizeHeight) throws Exception {
        Bitmap bmp = null;
        InputStream input = null;
        try {
            input = StorageUtil.openInputStream(filename);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, opts);

            if (opts.outHeight > resizeHeight || opts.outWidth > resizeWidth) {
                float scaleHeight = ((float) opts.outHeight) / (float) resizeHeight;
                float scaleWidth = ((float) opts.outWidth) / (float) resizeWidth;

                int initialSize = (int) scaleHeight;
                if (scaleWidth > scaleHeight) {
                    initialSize = (int) scaleWidth;
                }

                int roundedSize = 1;
                if (initialSize <= 8) {
                    roundedSize = 1;
                    while (roundedSize < initialSize) {
                        roundedSize <<= 1;
                    }
                } else {
                    roundedSize = (initialSize + 7) / 8 * 8;
                }
                IOUtils.closeQuietly(input);
                input = StorageUtil.openInputStream(filename);

                opts.inSampleSize = roundedSize;
                opts.inJustDecodeBounds = false;
                bmp = BitmapFactory.decodeStream(input, null, opts);
            }
        } finally {
            IOUtils.closeQuietly(input);
        }
        return bmp;
    }

    public static Bitmap cutImage(Bitmap bitmap, int resizeWidth, int resizeHeight) {
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width == resizeWidth && height == resizeHeight) {
            return bitmap;
        } else {

            float scaleHeight = ((float) height) / (float) resizeHeight;
            float scaleWidth = ((float) width) / (float) resizeWidth;

            float scale = scaleHeight > scaleWidth ? scaleWidth : scaleHeight;

            int newWidth = (int) (resizeWidth * scale);
            int newHeight = (int) (resizeHeight * scale);

//            Log.d("cutImage", "width:" + width + " height:" + height + " newWidth:" + newWidth + " newHeight:" + newHeight + " scale:" + scale);

            int x = (width - newWidth) / 2;
            int y = (height - newHeight) / 2;

            return Bitmap.createBitmap(bitmap, x, y, newWidth, newHeight);
        }
    }

    public static Bitmap extractThumbnail(String filename, int sideLength) throws IOException {
        Bitmap bmp = null;
        InputStream input = null;
        try {
            input = StorageUtil.openInputStream(filename);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;

            BitmapFactory.decodeStream(input, null, opts);

            int max = Math.max(opts.outWidth, opts.outHeight);

            int initialSize = max / sideLength;
            int roundedSize = 1;
            if (initialSize <= 8) {
                roundedSize = 1;
                while (roundedSize < initialSize) {
                    roundedSize <<= 1;
                }
            } else {
                roundedSize = (initialSize + 7) / 8 * 8;
            }

            IOUtils.closeQuietly(input);
            input = StorageUtil.openInputStream(filename);

            opts.inSampleSize = roundedSize;
            opts.inJustDecodeBounds = false;

            bmp = BitmapFactory.decodeStream(input, null, opts);
//		} catch (Exception ex) {
//			Log.e(TAG, ex.toString());
        } finally {
            IOUtils.closeQuietly(input);
        }

        return bmp;
    }

    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

    public static void saveBitmap(Bitmap bitmap, String path) {
        FileOutputStream outputStream = null;
        try {
            File imageFile = new File(path);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            if (!imageFile.getParentFile().exists()) {
                imageFile.getParentFile().mkdirs();
            }

            if (imageFile.createNewFile()) {
                outputStream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static Bitmap extractPicture(String filename) {
        if (filename == null) {
            return null;
        }

        Bitmap bmp = null;
        InputStream input = null;


        try {
            input = StorageUtil.openInputStream(filename);

            BitmapFactory.Options opts = new BitmapFactory.Options();

            opts.inJustDecodeBounds = false;

            bmp = BitmapFactory.decodeStream(input, null, opts);
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        } finally {
            IOUtils.closeQuietly(input);
        }

        return bmp;
    }

    public static Bitmap convertBitmap(Bitmap _bitmap, float maxSize) {
        int width_tmp = _bitmap.getWidth(), height_tmp = _bitmap.getHeight();
        float scale = 1;

        if (width_tmp > maxSize || height_tmp > maxSize) {
            if (width_tmp >= height_tmp)
                scale = width_tmp / maxSize;
            else
                scale = height_tmp / maxSize;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(_bitmap, 0, 0, _bitmap.getWidth(), _bitmap.getHeight(), matrix, true);
        return resizeBmp;
    }

    public static Bitmap convertBitmap(final byte[] data, float maxSize) {
        Bitmap bitmap = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, o);
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;

        if (width_tmp > maxSize || height_tmp > maxSize) {
            if (width_tmp >= height_tmp)
                scale = NumberUtil.convertFloatToInt(width_tmp / maxSize);
            else
                scale = NumberUtil.convertFloatToInt(height_tmp / maxSize);
        }

        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = scale;
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, op);
        return bitmap;
    }


    public static Bitmap convertBitmap(String file, float maxSize) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            if (width_tmp > maxSize || height_tmp > maxSize) {
                if (width_tmp >= height_tmp)
                    scale = NumberUtil.convertFloatToInt(width_tmp / maxSize);
                else
                    scale = NumberUtil.convertFloatToInt(height_tmp / maxSize);
            }

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = scale;
            fis = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(fis, null, op);
            fis.close();

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null)
            return null;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }
        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }


    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, final float roundPx) {
        if (bitmap == null)
            return null;
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(Color.BLACK);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
        }
    }

    public static Bitmap getBitMap(Drawable drawable){
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();


        // 取 drawable 的颜色格式
//        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap.Config config = Bitmap.Config.ARGB_8888;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

}
