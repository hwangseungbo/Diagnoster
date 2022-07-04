package com.example.myapplication;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.viewpager.widget.ViewPager;


// GIF add library +
import com.bumptech.glide.Glide;
// GIF add code -

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.wear.input.WearableButtons;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;


public class MainActivity extends FragmentActivity implements DataClient.OnDataChangedListener {

    private FrameLayout frame1;
    private TextView tv_rpm, tv_hour, tv_KNOT;
    private ActivityMainBinding binding;
    private ScrollView scrollView;

    private static final String COUNT_KEY = "com.example.key.count";
    private int count = 0;

    private static String KNOT, data190, data247;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        frame1 = findViewById(R.id.frame1);

        //mTextView = binding.text;
        tv_rpm = findViewById(R.id.tv_rpm);
        tv_hour = findViewById(R.id.tv_hour);
        tv_KNOT = findViewById(R.id.tv_KNOT);

        //사용가능버튼 갯수 조회
        int count = WearableButtons.getButtonCount(this);
        String c = String.valueOf(count);
        //Toast.makeText(this,c,Toast.LENGTH_SHORT).show();

        StartGameDialogFragment sgdf = new StartGameDialogFragment();
        //sgdf.show();



        ViewPager pager = findViewById(R.id.pager);
        pager.setOffscreenPageLimit(4);

        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager(), 1);

        Fragment1 fragment1 = new Fragment1();
        adapter.addItem(fragment1);

        Fragment2 fragment2 = new Fragment2();

        Bundle bundle = new Bundle();
        //String message = tv_rpm.getText().toString();
        //bundle.putString("message", message);
        //fragment2.setArguments(bundle);

        adapter.addItem(fragment2);

        Fragment3 fragment3 = new Fragment3();
        adapter.addItem(fragment3);

        Fragment4 fragment4 = new Fragment4();
        adapter.addItem(fragment4);

        pager.setAdapter(adapter);

    }


    //워치 데이터 관련 3종 @Overrride
    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {

            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                if(item.getUri().getPath().compareTo("/watch0") == 0){
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    KNOT = dataMap.getString("KNOT");
                    tv_KNOT.setText(KNOT);


                }
                else if(item.getUri().getPath().compareTo("/watch1") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data190 = dataMap.getString("190");
                    tv_rpm.setText(data190);
                    //Toast.makeText(this,dataMap.getString("190")+" "+dataMap.getString("247"),Toast.LENGTH_SHORT).show();

                }else if(item.getUri().getPath().compareTo("/watch2") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data247 = dataMap.getString("247");
                    tv_hour.setText(data247);
                }

            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }



    // 워치의 5시방향 버튼 클릭 이벤트
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Toast.makeText(this,String.valueOf(keyCode),Toast.LENGTH_SHORT).show();
        if (event.getRepeatCount() == 0) {
            if (keyCode == 4) {
                if(frame1.getVisibility()==View.VISIBLE){
                    frame1.setVisibility(View.INVISIBLE);

                }else {
                    frame1.setVisibility(View.VISIBLE);
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    public class StartGameDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("R.string.dialog_start_game")
                    .setPositiveButton("R.string.start", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // START THE GAME
                        }
                    })
                    .setNegativeButton("R.string.cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }









}