package demo.lee.com.compressimagelibrary;

import android.os.Environment;

public class Constants {
    public static final int CAMERA_CODE = 1001;
    public static final int ALBUM_CODE = 1002;
    public static final String BASE_CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String COMPRESS_CACHE = "compress_cache";

    public Constants() {
    }
}
