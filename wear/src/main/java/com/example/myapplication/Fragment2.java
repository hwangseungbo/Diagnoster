package com.example.myapplication;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView tv_cooltemp = super.getActivity().findViewById(R.id.tv_cooltemp);
        TextView fr2_tv_cool = rootView.findViewById(R.id.fr2_tv_cool);
        CircularProgressBar circularProgressBar_RPM = rootView.findViewById(R.id.circularProgressBar_RPM);
        TextView fr2_tv_coolanttext = rootView.findViewById(R.id.fr2_tv_coolanttext);
        TextView fr1_tv_hourstext = rootView.findViewById(R.id.fr1_tv_hourstext);
        ImageView fr2_iv_cool = rootView.findViewById(R.id.fr2_iv_cool);
        ImageView fr2_iv_page = rootView.findViewById(R.id.fr2_iv_page);
        ImageView logo = rootView.findViewById(R.id.imageView4);



        tv_cooltemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fr2_tv_cool.setText(tv_cooltemp.getText().toString());

                //냉각수 값이 0이 아닐 경우에만 색이 변함.
                //0일 때도 색이 변하면 savedInstance에 의해 앱을 나갓다 들어와도 색이변함.
                if(!fr2_tv_cool.getText().toString().equals("0")){
                    fr2_tv_cool.setTextColor(Color.parseColor("#5EFFFF"));
                    fr1_tv_hourstext.setTextColor(Color.parseColor("#5EFFFF"));
                    fr2_tv_coolanttext.setTextColor(Color.parseColor("#5EFFFF"));
                    fr2_iv_cool.setImageDrawable(getResources().getDrawable(R.drawable.signal_cool));
                    fr2_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.color_2_page));
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.kmcp_page2));
                }

                //냉각수 값이 120도 이상일 경우 경고
                if(Integer.parseInt(fr2_tv_cool.getText().toString()) > 90) {
                    fr2_tv_cool.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_hourstext.setTextColor(Color.parseColor("#FF9522"));
                    fr2_tv_coolanttext.setTextColor(Color.parseColor("#FF9522"));
                    fr2_iv_cool.setImageDrawable(getResources().getDrawable(R.drawable.emergency_cool));
                    fr2_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.emergency_2_page));
                }

            }
        });

        return rootView;
    }
}