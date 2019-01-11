package demo.lee.com.compressimagelibrary;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class CompressImageManager implements CompressImage{
    private CompressImageUtil compressImageUtil;
    private ArrayList<Photo> images;
    private CompressListener listener;
    private CompressConfig config;

    public static CompressImage build(Context context, CompressConfig config, ArrayList<Photo> images, CompressListener listener) {
        return new CompressImageManager(context, config, images, listener);
    }

    private CompressImageManager(Context context, CompressConfig config, ArrayList<Photo> images, CompressListener listener) {
        this.compressImageUtil = new CompressImageUtil(context, config);
        this.config = config;
        this.images = images;
        this.listener = listener;
    }

    @Override
    public void compress() {
        if (this.images != null && !this.images.isEmpty()) {
            Iterator var1 = this.images.iterator();

            Photo image;
            do {
                if (!var1.hasNext()) {
                    this.compress((Photo)this.images.get(0));
                    return;
                }

                image = (Photo)var1.next();
            } while(image != null);

            this.listener.onCompressFailed(this.images, "压缩时发现图片集合中有空图片对象");
        } else {
            this.listener.onCompressFailed(this.images, "图片集合为空");
        }
    }

    private void compress(final Photo image) {
        if (TextUtils.isEmpty(image.getOriginalPath())) {
            this.continueCompress(image, false);
        } else {
            File file = new File(image.getOriginalPath());
            if (file.exists() && file.isFile()) {
                if (file.length() < (long)this.config.getMaxSize()) {
                    this.continueCompress(image, true);
                } else {
                    this.compressImageUtil.compress(image.getOriginalPath(), new CompressResultListener() {
                        @Override
                        public void onCompressSuccess(String imgPath) {
                            image.setCompressPath(imgPath);
                            CompressImageManager.this.continueCompress(image, true);
                        }

                        @Override
                        public void onCompressFailed(String imgPath, String msg) {
                            CompressImageManager.this.continueCompress(image, false, msg);
                        }
                    });
                }
            } else {
                this.continueCompress(image, false);
            }
        }
    }

    private void continueCompress(Photo image, boolean preSuccess, String... message) {
        image.setCompressed(preSuccess);
        int index = this.images.indexOf(image);
        if (index == this.images.size() - 1) {
            this.handleCompressCallBack(message);
        } else {
            this.compress((Photo)this.images.get(index + 1));
        }

    }

    private void handleCompressCallBack(String... message) {
        if (message.length > 0) {
            this.listener.onCompressFailed(this.images, message[0]);
        } else {
            Iterator var2 = this.images.iterator();

            Photo image;
            do {
                if (!var2.hasNext()) {
                    this.listener.onCompressSuccess(this.images);
                    return;
                }

                image = (Photo)var2.next();
            } while(image.isCompressed());

            this.listener.onCompressFailed(this.images, image.getCompressPath() + "压缩失败，原始图片路径为空或者不存在");
        }
    }
}
