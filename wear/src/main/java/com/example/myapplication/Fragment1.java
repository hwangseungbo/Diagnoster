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

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        CircularProgressBar cb = rootView.findViewById(R.id.circularProgressBar_RPM);
        //cb.setProgress(10f);

        TextView tv_KNOT = super.getActivity().findViewById(R.id.tv_KNOT);
        TextView fr1_tv_knot = rootView.findViewById(R.id.fr1_tv_knot);

        int knot = Integer.parseInt(tv_KNOT.getText().toString());
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
                cb.setProgress(knot);
            }
        });

        return rootView;
    }
}