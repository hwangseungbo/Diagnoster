package com.example.myapplication;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment4, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView tv_batteryvoltage = super.getActivity().findViewById(R.id.tv_batteryvoltage);
        TextView fr4_tv_battery = rootView.findViewById(R.id.fr4_tv_battery);
        TextView fr4_tv_voltdegree = rootView.findViewById(R.id.fr4_tv_voltdegree);
        TextView fr4_tv_volttext = rootView.findViewById(R.id.fr4_tv_volttext);
        ImageView fr4_iv_volt = rootView.findViewById(R.id.fr4_iv_volt);
        ImageView fr4_iv_page = rootView.findViewById(R.id.fr4_iv_page);
        ImageView logo = rootView.findViewById(R.id.imageView6);


        tv_batteryvoltage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fr4_tv_battery.setText(tv_batteryvoltage.getText().toString());

                //배터리 값이 0이 아닐 때만 시그널색으로 변함.
                //0일 때도 색이 변하면 savedInstance에 의해 앱을 나갓다 들어와도 색이변함.
                if(!fr4_tv_battery.getText().toString().equals("0")){
                    fr4_tv_battery.setTextColor(Color.parseColor("#FFFF1A"));
                    fr4_tv_voltdegree.setTextColor(Color.parseColor("#FFFF1A"));
                    fr4_tv_volttext.setTextColor(Color.parseColor("#FFFF1A"));
                    fr4_iv_volt.setImageDrawable(getResources().getDrawable(R.drawable.signal_volt));
                    fr4_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.color_4_page));
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.kmcp_page4));
                }
                Double temp = Double.parseDouble(fr4_tv_battery.getText().toString());
                // 볼티지 값이 12이하로 떨어질 경우경고 표시
                if(temp != 0 && temp<12){
                    fr4_tv_battery.setTextColor(Color.parseColor("#FF9522"));
                    fr4_tv_voltdegree.setTextColor(Color.parseColor("#FF9522"));
                    fr4_tv_volttext.setTextColor(Color.parseColor("#FF9522"));
                    fr4_iv_volt.setImageDrawable(getResources().getDrawable(R.drawable.emergency_volt));
                    fr4_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.emergency_4_page));
                }


            }
        });


        return rootView;
    }
}