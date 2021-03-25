package demo.lee.com.imagecompress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import demo.lee.com.compressimagelibrary.CompressConfig;
import demo.lee.com.compressimagelibrary.CompressImage;
import demo.lee.com.compressimagelibrary.CompressImageManager;
import demo.lee.com.compressimagelibrary.Photo;

public class MainActivity extends AppCompatActivity {
    ImageView image1, image2;
    TextView text1, text2;
    ArrayList<String> list = new ArrayList<>();

    private CompressConfig compressConfig; // 压缩配置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        XXPermissions.with(MainActivity.this)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
//                .permission(Permission.Group.STORAGE)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        ToastUtils.s(MainActivity.this, "获取权限成功");
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        ToastUtils.s(MainActivity.this, "获取权限成功，部分权限未正常授予");
                    }
                });


    }

    public void openAlarm(View view) {
        list.clear();
        Glide.with(MainActivity.this).load("").into(image1);
        Glide.with(MainActivity.this).load("").into(image2);

        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .loadImageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                .theme(R.style.picture_WeChat_style)
                .isWeChatStyle(true)
                .forResult(10099);
    }

    public void compress(View view) {
        ArrayList<Photo> photos = new ArrayList<>();
//        String path =Environment.getExternalStorageDirectory()+"/bc4.jpg";
        if (list.size() == 0) {
            Toast.makeText(this, "请选择需要压缩的照片", Toast.LENGTH_SHORT).show();
            return;
        }
        String path = list.get(0);
        File file = new File(path);
        if (file.exists()) {
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
            CompressImageManager.build(this, compressConfig, photos, new CompressImage.CompressListener() {
                @Override
                public void onCompressSuccess(ArrayList<Photo> var1) {
                    String path = var1.get(0).getCompressPath();
                    if (path == null) {
                        path = var1.get(0).getOriginalPath();
                    }
                    Log.i("imageCompress", "success" + path);

                    Glide.with(MainActivity.this).load(path).into(image2);

                    File file = new File(path);

                    text2.setText(path + "，大小为：" + file.length() / 1000 + "K");
                }

                @Override
                public void onCompressFailed(ArrayList<Photo> var1, String var2) {
                    Log.e("imageCompress", "false", null);
                }
            }).compress();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            for (int i = 0; i < selectList.size(); i++) {
                list.add(selectList.get(i).getPath());
            }
            File file = new File(list.get(0));

            text1.setText(list.get(0) + "，大小为：" + file.length() / 1000 + "K");

            Glide.with(MainActivity.this).load(list.get(0)).into(image1);
        }
    }
}
