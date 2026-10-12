package com.jam.theme;
import android.os.Bundle;
import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.View;
public class MainActivity extends AppCompatActivity {
    int themeIndex = 0;
    int[] bgs = new int[]{R.drawable.bg_gradient1, R.drawable.bg_gradient2, R.drawable.bg_gradient1};
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView battery = findViewById(R.id.battery);
        TextView btnTheme = findViewById(R.id.btnTheme);
        View root = findViewById(android.R.id.content);

        // Battery
        IntentFilter f = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, f);
        int level = batteryStatus!= null? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : 80;
        battery.setText("🔋 " + level + "%");

        // Ganti tema pas di klik
        View.OnClickListener changeTheme = v -> {
            themeIndex = (themeIndex + 1) % 3;
            findViewById(R.id.clockBox).setBackgroundResource(bgs[themeIndex]);
            if(themeIndex==1){
                ((TextView)findViewById(R.id.labelTop)).setText("🌙 MIDNIGHT THEME");
            } else if(themeIndex==2){
                ((TextView)findViewById(R.id.labelTop)).setText("🔥 NEON PRO MAX");
            } else {
                ((TextView)findViewById(R.id.labelTop)).setText("⚡ TEMA JAM KEREN PRO ⚡");
            }
        };
        btnTheme.setOnClickListener(changeTheme);
        root.setOnClickListener(changeTheme);
    }
}
