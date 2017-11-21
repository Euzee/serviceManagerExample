package example.sm.euzee.github.com;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.github.euzee.permission.PermissionUtil;
import com.github.euzee.permission.ShortPerCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            PermissionUtil.microphone(this, new ShortPerCallback() {
            });
        }

    }
}
