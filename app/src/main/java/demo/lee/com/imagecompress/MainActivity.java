package demo.lee.com.imagecompress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import demo.lee.com.compressimagelibrary.CompressConfig;
import demo.lee.com.compressimagelibrary.CompressImage;
import demo.lee.com.compressimagelibrary.CompressImageManager;
import demo.lee.com.compressimagelibrary.Photo;

public class MainActivity extends AppCompatActivity {
    private CompressConfig compressConfig; // 压缩配置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Photo> photos = new ArrayList<>();
        String path =Environment.getExternalStorageDirectory()+"/bc4.jpg";
        File file = new File(path);
        if(file.exists()){
            Photo photo = new Photo(file.getPath());
            photo.setOriginalPath(path);
            photos.add(photo);
            compressConfig = CompressConfig.builder()
                    .setUnCompressMinPixel(1000) // 最小像素不压缩，默认值：1000
                    .setUnCompressNormalPixel(2000) // 标准像素不压缩，默认值：2000
                    .setMaxPixel(1200) // 长或宽不超过的最大像素 (单位px)，默认值：1200
                    .setMaxSize(200 * 1024) // 压缩到的最大大小 (单位B)，默认值：200 * 1024 = 200KB
                    .enablePixelCompress(true) // 是否启用像素压缩，默认值：true
                    .enableQualityCompress(true) // 是否启用质量压缩，默认值：true
                    .enableReserveRaw(false) // 是否保留源文件，默认值：true
                    .setCacheDir(path) // 压缩后缓存图片路径，默认值：Constants.COMPRESS_CACHE
                    .setShowCompressDialog(true) // 是否显示压缩进度条，默认值：false
                    .create();
            CompressImageManager.build(this,compressConfig, photos, new CompressImage.CompressListener() {
                @Override
                public void onCompressSuccess(ArrayList<Photo> var1) {
                    Log.i("imageCompress","success");
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    ImageView viewById = findViewById(R.id.image);
                    viewById.setImageBitmap(bitmap);
                }

                @Override
                public void onCompressFailed(ArrayList<Photo> var1, String var2) {
                    Log.e("imageCompress","false",null);
                }
            }).compress();
        }

    }
}
