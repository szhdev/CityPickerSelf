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

//    //声明AMapLocationClient类对象
//    public AMapLocationClient mLocationClient = null;
//
//
//    //声明AMapLocationClientOption对象
//    public AMapLocationClientOption mLocationOption = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_picker);
        initView();

        LocationUtils.getInstance().setLocationListener(new LocationUtils.LocationListener() {
            @Override
            public void onSuccess(BDLocation location) {
                Log.d("ss== onSuccess",location.getCity());
                cityPickerFragment.updateLocateState(LocateState.SUCCESS, location.getCity());
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


     /*   //定位  高德地图
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if (aMapLocation.getErrorCode() == 0) {
                    String city = aMapLocation.getCity();
                    String district = aMapLocation.getDistrict();
                    String location = StringUtils.extractLocation(city, district);

                    //定位成功，更新状态
                    cityPickerFragment.updateLocateState(LocateState.SUCCESS, location.replaceAll("市", ""));
                } else {
                    cityPickerFragment.updateLocateState(LocateState.FAILED, null);

                }


            }
        });*/
    }

   /* //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    mLocationClient.stopLocation();
                    cityPickerFragment.updateLocateState(LocateState.SUCCESS, amapLocation.getCity().replaceAll("市", ""));
//可在其中解析amapLocation获取相应内容。
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };*/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationUtils.getInstance().onDestory();
    }
}
