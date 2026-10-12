package com.jam.theme;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.View;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    int theme = 0;
    String[] themeNames = {"NEON CYBER", "GALAXY NIGHT", "SUNSET VIBE", "FOREST DARK", "CYBERPUNK"};
    int[] themeColors = {0xFF00FFD1, 0xFF7C4DFF, 0xFFFF6B6B, 0xFF4CAF50, 0xFFFF00FF};
    Handler h = new Handler();
    boolean stopwatchRunning = false;
    long stopwatchStart = 0;
    long elapsed = 0;
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        TextView battery = findViewById(R.id.battery);
        TextView themeBtn = findViewById(R.id.btnTheme);
        TextView stopwatchTxt = findViewById(R.id.stopwatch);
        TextView info = findViewById(R.id.extraInfo);
        View root = findViewById(R.id.root);

        // Battery update loop
        Runnable update = new Runnable(){
            public void run(){
                IntentFilter f = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                Intent s = registerReceiver(null, f);
                int lvl = s!=null? s.getIntExtra(BatteryManager.EXTRA_LEVEL,80):80;
                int temp = s!=null? s.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,0)/10:0;
                battery.setText("🔋 "+lvl+"% 🌡️ "+temp+"°C");
                if(stopwatchRunning){
                    long now = System.currentTimeMillis() - stopwatchStart + elapsed;
                    stopwatchTxt.setText(String.format("⏱️ %02d:%02d.%01d", (now/60000)%60, (now/1000)%60, (now/100)%10));
                }
                info.setText("RAM OK • THEME: "+themeNames[theme]+" • PRO MAX");
                h.postDelayed(this, 1000);
            }
        };
        h.post(update);

        View.OnClickListener change = v -> {
            theme = (theme+1)%themeNames.length;
            findViewById(R.id.clockHour).setBackgroundColor(0x00000000);
            root.setBackgroundColor(0xFF0A0E17 + theme*0x101010);
            ((TextView)findViewById(R.id.labelTop)).setText("⚡ "+themeNames[theme]+" ⚡");
            ((TextView)findViewById(R.id.labelTop)).setTextColor(themeColors[theme]);
            Toast.makeText(this, "Tema: "+themeNames[theme], Toast.LENGTH_SHORT).show();
        };
        themeBtn.setOnClickListener(change);
        root.setOnClickListener(change);

        // Stopwatch click
        stopwatchTxt.setOnClickListener(v -> {
            if(!stopwatchRunning){ stopwatchStart=System.currentTimeMillis(); stopwatchRunning=true; }
            else { elapsed+=System.currentTimeMillis()-stopwatchStart; stopwatchRunning=false; }
            if(elapsed>60000*5) elapsed=0;
        });
        stopwatchTxt.setOnLongClickListener(v->{ elapsed=0; stopwatchTxt.setText("⏱️ 00:00.0"); stopwatchRunning=false; return true; });
    }
}
