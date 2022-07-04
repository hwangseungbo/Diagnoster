package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        TextView tv_rpm = super.getActivity().findViewById(R.id.tv_rpm);
        TextView fr2_tv_rpm = rootView.findViewById(R.id.fr2_tv_rpm);
        CircularProgressBar circularProgressBar_RPM = rootView.findViewById(R.id.circularProgressBar_RPM);

        tv_rpm.addTextChangedListener(new TextWatcher() {
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
                fr2_tv_rpm.setText(tv_rpm.getText().toString());
                circularProgressBar_RPM.setProgress(Integer.parseInt(tv_rpm.getText().toString()));
            }
        });

        return rootView;
    }
}