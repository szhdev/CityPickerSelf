# CityPickerSelf
城市选择、定位（百度）、搜索及右侧字母导航，类似美团 百度糯米 饿了么等APP选择城市功能    
借鉴：https://github.com/qiwei0727/CityPicker 原始：https://github.com/zaaach/CityPicker

没有特殊要求可以参考以下使用方法

可以在需要的地方直接使用

使用：

第一步
 
  初始化：  建议在Application中
  
  LocationUtils.getInstance().setContext(this);
  
第二部
  
  创建Activity引入CityPickerFragment;

 cityPickerFragment = new CityPickerFragment();
 getSupportFragmentManager().beginTransaction()
          .add(R.id.fl_activity_city_picker_container, cityPickerFragment).commit();
  
  在需要的定位显示的地方直接调用：
  
  LocationUtils.getInstance().setLocationListener(new LocationUtils.LocationListener() {
        
            //成功
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
