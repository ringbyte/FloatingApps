package com.sj.astin.multimanager.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.os.Bundle;
import android.os.Build;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.provider.Settings;
import android.net.Uri;
import android.widget.Button;
import android.widget.Switch;
import android.widget.RemoteViews;
import android.widget.LinearLayout;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;

import com.sj.astin.multimanager.activity.SettingActivity;
import com.sj.astin.multimanager.activity.AddOnActivity;
import com.sj.astin.multimanager.service.AlwaysOnTopService;
import com.sj.astin.multimanager.R;
import com.sj.astin.multimanager.Utils;
import com.sj.astin.multimanager.TAG;
import com.sj.astin.multimanager.ui.FlatButton;


public class MainActivity extends AppCompatActivity {
    
    public static int OVERLAY_PERMISSION_REQ_CODE = 1;
    public Switch head_enable_switch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        head_enable_switch = (Switch) findViewById(R.id.switch1);
        head_enable_switch.setChecked(Utils.readBoolean(getApplicationContext(), TAG.fButton));
        head_enable_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton view, boolean checked) {
                if(checked) {
                    Intent svc = new Intent(MainActivity.this, AlwaysOnTopService.class);
                    checkPermissionOverlay();
                    startService(svc);
                    Utils.saveData(getApplicationContext(), TAG.fButton, true);
                }
                else if(!checked) {
                    Intent svc = new Intent(MainActivity.this, AlwaysOnTopService.class);
                    stopService(svc);
                    Utils.saveData(getApplicationContext(), TAG.fButton, false);
                }
            }
        });
        
        Button btn = (Button) findViewById(R.id.setting_button);
        btn.setAllCaps(false);
        
        Button btn2 = (Button) findViewById(R.id.addon_button);
        btn2.setAllCaps(false);
    }
	
    @TargetApi(Build.VERSION_CODES.M)
    public void checkPermissionOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            Toast.makeText(getApplicationContext(), "not overlay", Toast.LENGTH_LONG).show();
            Intent intentSettings = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(intentSettings, OVERLAY_PERMISSION_REQ_CODE);
        }
    }
    
    public void onSettingsClick(View v) {
        startActivity(new Intent(MainActivity.this, SettingActivity.class));
    }
    
    public void onAddOnClick(View v) {
        startActivity(new Intent(MainActivity.this, AddOnActivity.class));
    }
}
