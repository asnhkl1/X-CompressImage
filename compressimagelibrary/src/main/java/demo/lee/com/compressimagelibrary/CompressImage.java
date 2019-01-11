package demo.lee.com.compressimagelibrary;

import java.util.ArrayList;

public interface CompressImage {
    void compress();

    public interface CompressListener {
        void onCompressSuccess(ArrayList<Photo> var1);

        void onCompressFailed(ArrayList<Photo> var1, String var2);
    }
}
