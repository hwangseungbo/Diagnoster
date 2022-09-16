package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.myapplication.databinding.ActivityIntroBinding;

public class IntroActivity extends Activity {

    private ActivityIntroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);


    }

    @Override
    protected  void onPause() {
        super.onPause();
        finish();
    }

    // 워치의 5시방향 버튼 클릭 이벤트
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Toast.makeText(this,String.valueOf(keyCode),Toast.LENGTH_SHORT).show();
        if (event.getRepeatCount() == 0) {
            //Toast.makeText(this,String.valueOf(tv_KNOT.getCurrentTextColor()),Toast.LENGTH_LONG).show();
            if (keyCode == 4) {


                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}