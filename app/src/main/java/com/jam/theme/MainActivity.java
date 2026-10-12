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
import java.text.SimpleDateFormat;
import java.util.*;
public class MainActivity extends AppCompatActivity {
    int theme = 0;
    String[] themes = {"NEON CYBERPUNK", "GALAXY UNIVERSE", "SUNSET BEACH", "FOREST NIGHT", "MATRIX CODE", "GOLD LUXURY", "ICE BLUE"};
    int[] colors = {0xFF00FFD1, 0xFF7C4DFF, 0xFFFF6B6B, 0xFF4CAF50, 0xFF00FF00, 0xFFFFD700, 0xFF00BFFF};
    Handler h = new Handler();
    boolean swRun=false; long swStart=0, swElapsed=0;
    boolean timerRun=false; int timerSec=300;
    @Override protected void onCreate(Bundle b){
        super.onCreate(b);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        TextView bat=findViewById(R.id.battery), themeBtn=findViewById(R.id.btnTheme);
        TextView sw=findViewById(R.id.stopwatch), timer=findViewById(R.id.timer);
        TextView extra=findViewById(R.id.extraInfo), label=findViewById(R.id.labelTop);
        View root=findViewById(R.id.root);

        Runnable loop=new Runnable(){
            public void run(){
                Intent s=registerReceiver(null,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                int lvl=s!=null? s.getIntExtra(BatteryManager.EXTRA_LEVEL,85):85;
                int tmp=s!=null? s.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,320)/10:32;
                bat.setText("🔋"+lvl+"% 🌡️"+tmp+"°C | RAM 3.2GB");
                if(swRun){
                    long now=System.currentTimeMillis()-swStart+swElapsed;
                    sw.setText(String.format("⏱️ %02d:%02d.%01d", (now/60000)%60,(now/1000)%60,(now/100)%10));
                }
                if(timerRun && timerSec>0){
                    timerSec--; timer.setText(String.format("⏳ %02d:%02d", timerSec/60, timerSec%60));
                    if(timerSec==0){ timer.setText("⏳ DONE!"); Toast.makeText(MainActivity.this,"Timer Habis Bos!",Toast.LENGTH_LONG).show(); timerRun=false; }
                }
                // Hijriah + Jawa mock
                SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss - EEEE, dd MMMM yyyy", new Locale("id","ID"));
                extra.setText(sdf.format(new Date())+" | "+themes[theme]+" SULTAN");
                h.postDelayed(this,1000);
            }
        }; h.post(loop);

        View.OnClickListener change=v->{
            theme=(theme+1)%themes.length;
            label.setText("⚡ "+themes[theme]+" ⚡");
            label.setTextColor(colors[theme]);
            findViewById(R.id.clockHour).setAlpha(0.9f);
            Toast.makeText(this,"Tema: "+themes[theme]+" - File: 50MB SULTAN",Toast.LENGTH_SHORT).show();
        };
        themeBtn.setOnClickListener(change); root.setOnClickListener(change);

        sw.setOnClickListener(v->{
            if(!swRun){ swStart=System.currentTimeMillis(); swRun=true; }
            else{ swElapsed+=System.currentTimeMillis()-swStart; swRun=false; }
        });
        sw.setOnLongClickListener(v->{ swElapsed=0; sw.setText("⏱️ 00:00.0"); swRun=false; return true; });

        timer.setOnClickListener(v->{
            if(!timerRun){ timerSec=300; timerRun=true; }
            else timerRun=!timerRun;
        });
        timer.setOnLongClickListener(v->{ timerSec=300; timer.setText("⏳ 05:00"); timerRun=false; return true; });
    }
}
