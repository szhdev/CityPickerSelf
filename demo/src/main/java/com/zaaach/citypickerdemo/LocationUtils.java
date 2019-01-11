package com.zaaach.citypickerdemo;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * Created by szhdev on 2019/1/10.
 */
public class LocationUtils {

    private volatile static LocationUtils mLocationUtils;

    private volatile static LocationClient mLocationClient ;
    private LocationListener mLocationListener;

    private Context mContext;

    private LocationUtils() {

    }
    public void setContext(Context context){
        mContext = context;
    }

    public LocationUtils setLocationListener(LocationListener listener){
        mLocationListener = listener;
        return this;
    }
    public static LocationUtils getInstance() {
        if (mLocationUtils == null) {
            synchronized (LocationUtils.class) {
                if (mLocationUtils == null) {
                    mLocationUtils = new LocationUtils();

                }
            }
        }
        return mLocationUtils;
    }

    public  LocationUtils init(){
        if(mLocationClient == null){
            synchronized (LocationClient.class){
                if(mLocationClient == null){
                    initLocation();
                }
            }
        }
        return this;
    }

    public void start(){
        init();
        if(!mLocationClient.isStarted()){
            mLocationClient.start();
        }
    }

    public void stop(){
        init();
        mLocationClient.stop();
    }

    public void onDestory(){
        mContext = null;
        mLocationClient = null;
        mLocationUtils = null;
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000 * 10;
        option.setScanSpan(span);//设置发起定位请求的间隔
        option.setOpenGps(true);//可选，默认false,设置是否使用gps

        //可选，是否需要地址信息，默认为不需要，即参数为false
        //如果开发者需要获得当前点的地址信息，此处必须为true
        option.setIsNeedAddress(true);

        mLocationClient = new LocationClient(mContext);//声明LocationClient类
        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                if(bdLocation.getLocType() == BDLocation.TypeServerError ||bdLocation.getLocType() == BDLocation.TypeNetWorkException||bdLocation.getLocType() == BDLocation.TypeCriteriaException){
                    mLocationListener.onError(bdLocation);
                }else{
                    mLocationListener.onSuccess(bdLocation);
                }
            }
        });//注册监听函数
    }


    public interface LocationListener {

        void onSuccess(BDLocation location);
        void onError(BDLocation location);
    }
}
