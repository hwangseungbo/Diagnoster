package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        //CircularProgressBar circularProgressBar_RPM = rootView.findViewById(R.id.circularProgressBar_RPM);
        //circularProgressBar_RPM.setProgress(2500);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView tv_KNOT = super.getActivity().findViewById(R.id.tv_KNOT);
        TextView fr1_tv_knot = rootView.findViewById(R.id.fr1_tv_knot);
        TextView tv_rpm = super.getActivity().findViewById(R.id.tv_rpm);
        TextView fr1_tv_rpm = rootView.findViewById(R.id.fr1_tv_rpm);
        TextView tv_hour = super.getActivity().findViewById(R.id.tv_hour);
        TextView fr1_tv_hour = rootView.findViewById(R.id.fr1_tv_hour);
        TextView fr1_tv_knottext = rootView.findViewById(R.id.fr1_tv_knottext);
        TextView fr1_tv_hourstext = rootView.findViewById(R.id.fr1_tv_hourstext);
        TextView fr1_tv_rpmtext = rootView.findViewById(R.id.fr1_tv_rpmtext);
        TextView fr1_tv_x1000 = rootView.findViewById(R.id.fr1_tv_x1000);
        ImageView fr1_iv_page = rootView.findViewById(R.id.fr1_iv_page);
        ImageView logo = rootView.findViewById(R.id.imageView3);


        int knot = Integer.parseInt(tv_KNOT.getText().toString());

        //float yvalue = imageView.getY();
        //imageView.setY(yvalue - 50);

        //cb.setProgress(knot);

        tv_KNOT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 텍스트 변경 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 텍스트 변경중일 때
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 텍스트 변경 후
                int knot = Integer.parseInt(tv_KNOT.getText().toString());
                fr1_tv_knot.setText(String.valueOf(knot));


            }
        });

        tv_rpm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int rpm = Integer.parseInt(tv_rpm.getText().toString());
                String RPM;

                rpm = rpm / 100;

                double rpm2 = rpm / 10.0;
                RPM = String.valueOf(rpm2);

                /*
                if(rpm>1000){
                    rpm = rpm / 1000;
                    RPM = String.valueOf(rpm);
                }else if(rpm > 100){
                    rpm = (rpm*10)/1000;
                    RPM = "." + String.valueOf(rpm);
                }else{
                    rpm = 0;
                    RPM = "0";
                }
                 */

                fr1_tv_rpm.setText(RPM);

                // rpm값이 0.0이 아닐 경우에만 색이 변함.
                //0.0일 때도 색이 변하면 savedInstance에 의해 앱을 나갓다 들어와도 색이변함.
                if(!fr1_tv_rpm.getText().toString().equals("0.0")){
                    fr1_tv_hour.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_hourstext.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_rpm.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_rpmtext.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_x1000.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_knot.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_tv_knottext.setTextColor(Color.parseColor("#82C3F4"));
                    fr1_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.color_1_page));
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.kmcp_page1));
                }

                //rpm 값이 6이상(실제론6000이상)일 경우 경고
                if(Double.parseDouble(fr1_tv_rpm.getText().toString()) > 6.0){
                    fr1_tv_hour.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_hourstext.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_rpm.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_rpmtext.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_x1000.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_knot.setTextColor(Color.parseColor("#FF9522"));
                    fr1_tv_knottext.setTextColor(Color.parseColor("#FF9522"));
                    fr1_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.emergency_1_page));
                }
            }
        });

        tv_hour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String hours = tv_hour.getText().toString();
                while(hours.length() < 9){
                    hours = "0" + hours;
                }
                hours = hours.substring(0,3) + "," + hours.substring(3,6) + "," + hours.substring(6,hours.length());

                fr1_tv_hour.setText(hours);
            }
        });


        return rootView;
    }
}