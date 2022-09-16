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

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        ImageView imageView = rootView.findViewById(R.id.imageView);
        TextView tv_fuellevel = super.getActivity().findViewById(R.id.tv_fuellevel);
        TextView fr3_tv_fuellevel = rootView.findViewById(R.id.fr3_tv_fuellevel);
        TextView tv_fuelefficiency = super.getActivity().findViewById(R.id.tv_fuelefficiency);
        TextView fr3_tv_fueleficiency = rootView.findViewById(R.id.fr3_tv_fueleficiency);
        TextView fr3_tv_mileage = rootView.findViewById(R.id.fr3_tv_mileage);
        TextView fr3_tv_kml = rootView.findViewById(R.id.fr3_tv_kml);
        TextView fr3_tv_percent = rootView.findViewById(R.id.fr3_tv_percent);
        TextView fr3_tv_fueltext = rootView.findViewById(R.id.fr3_tv_fueltext);
        ImageView fr3_iv_fuel = rootView.findViewById(R.id.fr3_iv_fuel);
        ImageView fr3_iv_page = rootView.findViewById(R.id.fr3_iv_page);
        ImageView logo = rootView.findViewById(R.id.imageView5);


        tv_fuellevel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fr3_tv_fuellevel.setText(tv_fuellevel.getText().toString());

                //연료 값이 0이 아닐 때만 색이변함.
                //0일 때도 색이 변하면 savedInstance에 의해 앱을 나갓다 들어와도 색이변함.
                if(!fr3_tv_fuellevel.getText().toString().equals("0")){
                    fr3_tv_fuellevel.setTextColor(Color.parseColor("#54F296"));
                    fr3_tv_mileage.setTextColor(Color.parseColor("#54F296"));
                    fr3_tv_kml.setTextColor(Color.parseColor("#54F296"));
                    fr3_tv_fueleficiency.setTextColor(Color.parseColor("#54F296"));
                    fr3_tv_percent.setTextColor(Color.parseColor("#54F296"));
                    fr3_tv_fueltext.setTextColor(Color.parseColor("#54F296"));
                    fr3_iv_fuel.setImageDrawable(getResources().getDrawable(R.drawable.signal_fuel));
                    fr3_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.color_3_page));
                    logo.setImageDrawable(getResources().getDrawable(R.drawable.kmcp_page3));
                }
                int temp = Integer.parseInt(fr3_tv_fuellevel.getText().toString());

                //연료 값이 10%이하일 경우 경고
                if(temp!=0 && temp<=10){
                    fr3_tv_fuellevel.setTextColor(Color.parseColor("#FF9522"));
                    fr3_tv_mileage.setTextColor(Color.parseColor("#FF9522"));
                    fr3_tv_kml.setTextColor(Color.parseColor("#FF9522"));
                    fr3_tv_fueleficiency.setTextColor(Color.parseColor("#FF9522"));
                    fr3_tv_percent.setTextColor(Color.parseColor("#FF9522"));
                    fr3_tv_fueltext.setTextColor(Color.parseColor("#FF9522"));
                    fr3_iv_fuel.setImageDrawable(getResources().getDrawable(R.drawable.emergency_fuel));
                    fr3_iv_page.setImageDrawable(getResources().getDrawable(R.drawable.emergency_3_page));
                }


            }
        });

        tv_fuelefficiency.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fr3_tv_fueleficiency.setText(tv_fuelefficiency.getText().toString());
            }
        });

        return rootView;
    }
}