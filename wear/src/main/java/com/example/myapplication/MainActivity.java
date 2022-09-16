package com.example.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.viewpager.widget.ViewPager;


//GPS관련 3종
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

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

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends FragmentActivity implements DataClient.OnDataChangedListener, LocationListener {


    private TextView tv_KNOT, tv_rpm, tv_hour, tv_cooltemp, tv_fuellevel, tv_fuelefficiency, tv_batteryvoltage,
                        tv_degree1, tv_degree2, tv_degree3, tv_degree4, tv_degree5, tv_degree6, tv_degree7;
    private ActivityMainBinding binding;
    private ScrollView sv;

    private static final String COUNT_KEY = "com.example.key.count";
    private int count = 0;

    private static String KNOT="0", data190="0", data247="0", data110="0", data96="0", data184="0", data168="0";

    //GPS 관련 변수
    public static LocationManager locationManager;
    public static Location mLastlocation = null;
    public static double speed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(R.layout.activity_main);

        sv = findViewById(R.id.sv);
        //mTextView = binding.text;
        //1page
        tv_rpm = findViewById(R.id.tv_rpm);
        tv_hour = findViewById(R.id.tv_hour);
        tv_KNOT = findViewById(R.id.tv_KNOT);
        //2page
        tv_cooltemp = findViewById(R.id.tv_cooltemp);
        //3page
        tv_fuellevel = findViewById(R.id.tv_fuellevel);
        tv_fuelefficiency = findViewById(R.id.tv_fuelefficiency);
        //4page
        tv_batteryvoltage = findViewById(R.id.tv_batteryvoltage);

        tv_degree1 = findViewById(R.id.tv_degree1);
        tv_degree2 = findViewById(R.id.tv_degree2);
        tv_degree3 = findViewById(R.id.tv_degree3);
        tv_degree4 = findViewById(R.id.tv_degree4);
        tv_degree5 = findViewById(R.id.tv_degree5);
        tv_degree6 = findViewById(R.id.tv_degree6);
        tv_degree7 = findViewById(R.id.tv_degree7);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // GPS 사용 가능 여부 확인
        /*
        boolean isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!isEnable){
            Enable_GPS.setTextColor(Color.parseColor("#FF0000"));   // GPS사용이 불가할 경우 KNOT글자색이 붉은색으로 변한다.
        }
        */
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);

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


        sv.setOnGenericMotionListener(new View.OnGenericMotionListener() {
            @Override
            public boolean onGenericMotion(View v, MotionEvent ev) {
                if (ev.getAction() == MotionEvent.ACTION_SCROLL && ev.isFromSource(InputDeviceCompat.SOURCE_ROTARY_ENCODER)) {

                    float delta = -ev.getAxisValue(MotionEventCompat.AXIS_SCROLL) *
                            ViewConfigurationCompat.getScaledVerticalScrollFactor(ViewConfiguration.get(getApplicationContext()),getApplicationContext());

                    if(delta>0)
                    {
                        //Toast.makeText(getApplicationContext(),String.valueOf(delta), Toast.LENGTH_SHORT).show();
                        pager.setCurrentItem(pager.getCurrentItem()+1, true);

                    }else{
                        //Toast.makeText(getApplicationContext(),String.valueOf(delta), Toast.LENGTH_SHORT).show();
                        pager.setCurrentItem(pager.getCurrentItem()-1, true);
                    }

                    return true;
                }
                return false;
            }
        });

        //GPS가 내장되어있는지 확인
        /*
        if (!hasGps()) {
            Log.d("GPS", "This hardware doesn't have GPS.");
            // Fall back to functionality that does not use location or
            // warn the user that location function is not available.
            Toast.makeText(this, "내장 GPS가 없습니다.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "내장 GPS가 있습니다.", Toast.LENGTH_SHORT).show();
        }
         */

    }

    private boolean hasGps() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
    }


    //워치 데이터 관련 3종 @Overrride
    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
        //Toast.makeText(this,"onResume",Toast.LENGTH_LONG).show();
        tv_KNOT.setText(KNOT);
        tv_rpm.setText(data190);
        tv_hour.setText(data247);
        tv_cooltemp.setText(data110);
        tv_fuellevel.setText(data96);
        tv_fuelefficiency.setText(data184);
        tv_batteryvoltage.setText(data168);

    }
    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
        //Toast.makeText(this,"onPause",Toast.LENGTH_LONG).show();
        KNOT = tv_KNOT.getText().toString();
        data190 = tv_rpm.getText().toString();
        data247 = tv_hour.getText().toString();
        data110 = tv_cooltemp.getText().toString();
        data96 = tv_fuellevel.getText().toString();
        data184 = tv_fuelefficiency.getText().toString();
        data168 = tv_batteryvoltage.getText().toString();

    }
    @Override
    public void onDataChanged(DataEventBuffer dataEvents) {
        for (DataEvent event : dataEvents) {

            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();

                //KNOT 데이터는 워치의 내장 GPS이용.
                /*
                if(item.getUri().getPath().compareTo("/watch0") == 0){
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    KNOT = dataMap.getString("KNOT");
                    tv_KNOT.setText(KNOT);
                }
                else */ if(item.getUri().getPath().compareTo("/watch1") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data190 = dataMap.getString("190");
                    tv_rpm.setText(data190);
                    //Toast.makeText(this,dataMap.getString("190")+" "+dataMap.getString("247"),Toast.LENGTH_SHORT).show();

                }
                else if(item.getUri().getPath().compareTo("/watch2") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data247 = dataMap.getString("247");
                    tv_hour.setText(data247);
                }
                else if(item.getUri().getPath().compareTo("/watch3") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data110 = dataMap.getString("110");
                    tv_cooltemp.setText(data110);
                }
                else if(item.getUri().getPath().compareTo("/watch4") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data96 = dataMap.getString("96");
                    tv_fuellevel.setText(data96);
                }
                else if(item.getUri().getPath().compareTo("/watch5") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data184 = dataMap.getString("184");
                    tv_fuelefficiency.setText(data184);
                }
                else if(item.getUri().getPath().compareTo("/watch6") == 0)
                {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    data168 = dataMap.getString("168");
                    tv_batteryvoltage.setText(data168);
                }

            }
            else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }




    // 워치의 5시방향 버튼 클릭 이벤트
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //Toast.makeText(this,String.valueOf(keyCode),Toast.LENGTH_SHORT).show();
        if (event.getRepeatCount() == 0) {
            //Toast.makeText(this,String.valueOf(tv_KNOT.getCurrentTextColor()),Toast.LENGTH_LONG).show();
            if (keyCode == 4) {

                if(tv_KNOT.getCurrentTextColor() == 3587855 ){
                    tv_KNOT.setTextColor(Color.parseColor("#36BF0F"));
                    tv_rpm.setTextColor(Color.parseColor("#36BF0F"));
                    tv_hour.setTextColor(Color.parseColor("#36BF0F"));
                    tv_cooltemp.setTextColor(Color.parseColor("#36BF0F"));
                    tv_fuellevel.setTextColor(Color.parseColor("#36BF0F"));
                    tv_fuelefficiency.setTextColor(Color.parseColor("#36BF0F"));
                    tv_batteryvoltage.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree1.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree2.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree3.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree4.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree5.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree6.setTextColor(Color.parseColor("#36BF0F"));
                    tv_degree7.setTextColor(Color.parseColor("#36BF0F"));
                }else {
                    tv_KNOT.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_rpm.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_hour.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_cooltemp.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_fuellevel.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_fuelefficiency.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_batteryvoltage.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree1.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree2.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree3.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree4.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree5.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree6.setTextColor(Color.parseColor("#0036BF0F"));
                    tv_degree7.setTextColor(Color.parseColor("#0036BF0F"));
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



    //GPS관련 Override
    @Override
    public void onLocationChanged(Location location) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        double deltaTime = 0;

        try{
            // getSpeed() 함수를 이용하여 속도 계산
            double getSpeed = Double.parseDouble((String.format("%f", location.getSpeed())));
            // m/s 를 km/h로 바꾸려면 3.6을 곱해주면된다.
            getSpeed = (getSpeed * 3.6)/1.852;
            int getspeed = (int) getSpeed;
            tv_KNOT.setText(String.valueOf(getspeed));
            KNOT = tv_KNOT.getText().toString();
        } catch (Exception e) { }


        String formatDate = sdf.format(new Date(location.getTime()));

        // 위치변경이 두번째로 변경된 경우 계산에 의해 속도 계산
        if (mLastlocation != null) {
            deltaTime = (location.getTime() - mLastlocation.getTime()) / 1000;
            // 속도 계산
            speed = mLastlocation.distanceTo(location) / deltaTime;
            String formatLastDate = sdf.format(new Date(mLastlocation.getTime()));

            try {
                double calSpeed = Double.parseDouble(String.format("%f, speed"));
                // m/s 를 km/h로 바꾸려면 3.6을 곱해주면된다.
                calSpeed = (calSpeed * 3.6)/1.852;
                int calspeed = (int) calSpeed;
                tv_KNOT.setText(String.valueOf(calspeed));
                KNOT = tv_KNOT.getText().toString();
            }catch (Exception e) { }
        }
        //현재위치를 지난위치로 변경
        mLastlocation = location;
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }
    @Override
    public void onProviderEnabled(String provider) {
        // 권한체크
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        // 위치정보 업데이트
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,0, this);
    }
    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //권한이 없을 경우 최초 권한 요청 또는 사용자에 의한 재용청 확인
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION) &&
                    ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // 권한 재요청
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                return;
            } else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
                return;
            }
        }
    }





}