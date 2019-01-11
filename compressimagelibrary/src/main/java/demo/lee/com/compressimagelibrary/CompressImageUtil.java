package demo.lee.com.compressimagelibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CompressImageUtil {
    private CompressConfig config;
    private Context context;
    private Handler mhHandler = new Handler();

    public CompressImageUtil(Context context, CompressConfig config) {
        this.context = context;
        this.config = config == null ? CompressConfig.getDefaultConfig() : config;
    }

    public void compress(String imgPath, CompressResultListener listener) {
        if (this.config.isEnablePixelCompress()) {
            try {
                this.compressImageByPixel(imgPath, listener);
            } catch (FileNotFoundException var4) {
                listener.onCompressFailed(imgPath, String.format("图片压缩失败,%s", var4.toString()));
                var4.printStackTrace();
            }
        } else {
            this.compressImageByQuality(BitmapFactory.decodeFile(imgPath), imgPath, listener);
        }

    }

    private void compressImageByQuality(Bitmap bitmap, String imgPath, CompressResultListener listener) {
        if (bitmap == null) {
            this.sendMsg(false, imgPath, "像素压缩失败，bitmap为空", listener);
        } else {
            (new Thread(() -> {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);

                while(baos.toByteArray().length > this.config.getMaxSize()) {
                    baos.reset();
                    options -= 5;
                    if (options <= 5) {
                        options = 5;
                    }

                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                    if (options == 5) {
                        break;
                    }
                }

                try {
                    File thumbnailFile = this.getThumbnailFile(new File(imgPath));
                    FileOutputStream fos = new FileOutputStream(thumbnailFile);
                    fos.write(baos.toByteArray());
                    fos.flush();
                    fos.close();
                    baos.flush();
                    baos.close();
                    bitmap.recycle();
                    this.sendMsg(true, thumbnailFile.getPath(), (String)null, listener);
                } catch (Exception var8) {
                    this.sendMsg(false, imgPath, "质量压缩失败", listener);
                    var8.printStackTrace();
                }

            })).start();
        }
    }

    private void compressImageByPixel(String imgPath, CompressResultListener listener) throws FileNotFoundException {
        if (imgPath == null) {
            this.sendMsg(false, (String)null, "要压缩的文件不存在", listener);
        } else {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(imgPath, newOpts);
            newOpts.inJustDecodeBounds = false;
            int width = newOpts.outWidth;
            int height = newOpts.outHeight;
            float maxSize = (float)this.config.getMaxPixel();
            int be = 1;
            if (width >= height && (float)width > maxSize) {
                be = (int)((float)newOpts.outWidth / maxSize);
                ++be;
            } else if (width < height && (float)height > maxSize) {
                be = (int)((float)newOpts.outHeight / maxSize);
                ++be;
            }

            if (width <= this.config.getUnCompressNormalPixel() || height <= this.config.getUnCompressNormalPixel()) {
                be = 2;
                if (width <= this.config.getUnCompressMinPixel() || height <= this.config.getUnCompressMinPixel()) {
                    be = 1;
                }
            }

            newOpts.inSampleSize = be;
            newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            newOpts.inPurgeable = true;
            newOpts.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
            if (this.config.isEnableQualityCompress()) {
                this.compressImageByQuality(bitmap, imgPath, listener);
            } else {
                File thumbnailFile = this.getThumbnailFile(new File(imgPath));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(thumbnailFile));
                listener.onCompressSuccess(thumbnailFile.getPath());
            }

        }
    }

    private File getThumbnailFile(File file) {
        return file != null && file.exists() ? this.getPhotoCacheDir(file) : file;
    }

    private File getPhotoCacheDir(File file) {
        if (TextUtils.isEmpty(this.config.getCacheDir())) {
            this.config.setCacheDir("compress_cache");
        }

        File mCacheDir = new File(this.config.getCacheDir());
        return mCacheDir.mkdirs() || mCacheDir.exists() && mCacheDir.isDirectory() ? new File(mCacheDir, "compress_" + file.getName()) : file;
    }

    private void sendMsg(boolean isSuccess, String imagePath, String message, CompressResultListener listener) {
        this.mhHandler.post(() -> {
            if (isSuccess) {
                listener.onCompressSuccess(imagePath);
            } else {
                listener.onCompressFailed(imagePath, message);
            }

        });
    }

}
