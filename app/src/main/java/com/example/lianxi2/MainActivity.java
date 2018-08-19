package com.example.lianxi2;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.os.Handler;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {

    private boolean supportsEs2;
    private STLView stlView=null;
    private STLView stlView2=null;
    private RelativeLayout stllay=null;
    private SeekBar d1=null;
    private SeekBar d2=null;
    private SeekBar d3=null;
    private ArrayList<String> stlsourse =new ArrayList<>();


    private final class myseekbarlistener implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.d1:
                    stlView.setrot(0,progress);
                    break;
                case R.id.d2:
                    stlView.setrot(1,progress-180);
                    break;
                case R.id.d3:
                    stlView.setrot(2,progress-180);
                    break;
            }

        }
        @Override public void onStartTrackingTouch(SeekBar seekBar) {; }
        @Override public void onStopTrackingTouch(SeekBar seekBar) {; }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkSupported();

        if (supportsEs2) {
            setContentView(R.layout.activity_main);
            stlsourse.add("part1.STL");
            stlsourse.add("part2.STL");
            stlsourse.add("part3.STL");
            stlsourse.add("part4.STL");
           stlView = new STLView(this);
           stlView.setstlsourse(stlsourse);
           stllay=findViewById(R.id.stllay);
           d1=findViewById(R.id.d1);
           d2=findViewById(R.id.d2);
           d3=findViewById(R.id.d3);
           d1.setOnSeekBarChangeListener(new myseekbarlistener());
            d2.setOnSeekBarChangeListener(new myseekbarlistener());
            d3.setOnSeekBarChangeListener(new myseekbarlistener());

           stllay.addView(stlView);

        } else {
            setContentView(R.layout.activity_main);
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(stlView !=null){
        stlView.onResume();}
    }

    private void checkSupported() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        supportsEs2 = configurationInfo.reqGlEsVersion >= 0x2000;

        boolean isEmulator = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                && (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));

        supportsEs2 = supportsEs2 || isEmulator;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stlView != null) {
            stlView.onPause();
        }
    }


}