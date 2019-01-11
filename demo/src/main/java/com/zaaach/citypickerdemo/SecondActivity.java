package com.zaaach.citypickerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.zaaach.citypicker.CityPickerFragment;
import com.zaaach.citypicker.model.LocateState;

/**
 * 引用例子
 */
public class SecondActivity extends AppCompatActivity  {
    private CityPickerFragment cityPickerFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        initView();

        LocationUtils.getInstance().setLocationListener(new LocationUtils.LocationListener() {
            @Override
            public void onSuccess(BDLocation location) {
                Log.d("ss== onSuccess",location.getCity());
                cityPickerFragment.updateLocateState(LocateState.SUCCESS, location.getCity().replaceAll("市", ""));
                LocationUtils.getInstance().stop();
            }

            @Override
            public void onError(BDLocation location) {
                Log.d("ss== onError",String.valueOf(location.getLocType()));
                cityPickerFragment.updateLocateState(LocateState.FAILED, null);
            }
        }).start();
    }



    protected void initView() {
        cityPickerFragment = new CityPickerFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_activity_city_picker_container, cityPickerFragment).commit();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
