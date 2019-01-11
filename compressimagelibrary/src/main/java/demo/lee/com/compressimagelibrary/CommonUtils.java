package demo.lee.com.compressimagelibrary;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.widget.Toast;

public class CommonUtils {
    public CommonUtils() {
    }

    public static void hasCamera(Activity activity, Intent intent, int requestCode) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity为空");
        } else {
            PackageManager pm = activity.getPackageManager();
            boolean hasCamera = pm.hasSystemFeature("android.hardware.camera") || pm.hasSystemFeature("android.hardware.camera.front") || Camera.getNumberOfCameras() > 0;
            if (hasCamera) {
                activity.startActivityForResult(intent, requestCode);
            } else {
                Toast.makeText(activity, "当前设备没有相机", 0).show();
                throw new IllegalStateException("当前设备没有相机");
            }
        }
    }

    public static Intent getCameraIntent(Uri outPutUri) {
        Intent intent = new Intent();
        intent.addFlags(1);
        intent.setAction("android.media.action.IMAGE_CAPTURE");
        intent.putExtra("output", outPutUri);
        return intent;
    }

    public static void openAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }

    public static ProgressDialog showProgressDialog(Activity activity, String... progressTitle) {
        if (activity != null && !activity.isFinishing()) {
            String title = "提示";
            if (progressTitle != null && progressTitle.length > 0) {
                title = progressTitle[0];
            }

            ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setTitle(title);
            progressDialog.setCancelable(false);
            progressDialog.show();
            return progressDialog;
        } else {
            return null;
        }
    }
}
