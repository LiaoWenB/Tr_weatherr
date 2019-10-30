package com.android.test_weather;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.test_weather.adapter.WeatherAdapter;
import com.android.test_weather.util.HttpCallbackListener;
import com.android.test_weather.util.HttpUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivity extends Activity {
    private EditText ecCity;
    private ImageButton btnQuery;
    private Button btnBack;
    private ListView FutureWeather;
    public static final int SHOW_RESPONSE=1;
    private List<Weather> data;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response=(String )msg.obj;
                    if(response!=null){
                        parseWithJSON(response);
                        WeatherAdapter weatherAdapter=new WeatherAdapter
                                (WeatherActivity.this,
                                        R.layout.activity_weather_listitem, data);
                     FutureWeather.setAdapter(weatherAdapter);

                        TranslateAnimation trananimation=new TranslateAnimation(-FutureWeather.getMeasuredWidth(), 0,0,1);
                        //设置动画时间
                        trananimation.setDuration(2000);
                        //设置是否记录移动后的位置，true时动画将停留在当前位置，false将回到开始位置
                        trananimation.setFillAfter(true);
                        //设置插值器，可以理解为用于改变运动形式的东西
                        //（现在设置的运动形式，会有弹跳效果）
                        trananimation.setInterpolator(new BounceInterpolator());
                        FutureWeather.startAnimation(trananimation);
//                        将构建好的适配器对象传递进去，完成Listview和数据之间的关联

                    }
                    break;

                default:
                    break;
            }
        }

        private void parseWithJSON(String response) {
            // TODO Auto-generated method stub
            data=new ArrayList<Weather>();
            JsonParser parser=new JsonParser();//json解析器
            JsonObject obj=(JsonObject) parser.parse(response);


            //获取返回状态吗
            String resultcode=obj.get("resultcode").getAsString();
            //状态码如果是200说明数据返回成功
            if(resultcode!=null&&resultcode.equals("200")){

                JsonObject resultObj=obj.get("result").getAsJsonObject();

                JsonArray futureWeatherArray=resultObj.get("future").getAsJsonArray();
                for(int i=0;i<futureWeatherArray.size();i++){
                    Weather  weather=new Weather();
                    JsonObject weatherObject=futureWeatherArray.get(i).getAsJsonObject();
                    weather.setDayOfWeek(weatherObject.get("week").getAsString());
                    weather.setDate(weatherObject.get("date").getAsString());
                    weather.setTemperature(weatherObject.get("temperature")
                            .getAsString());
                    weather.setWeather(weatherObject.get("weather")
                            .getAsString());
                    weather.setWind(weatherObject.get("wind").getAsString());
                    data.add(weather);
                }
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        initViews();
        setListeners();
    }


    public void initViews() {
        ecCity = (EditText) findViewById(R.id.etCity);
        btnQuery = (ImageButton) findViewById(R.id.btnQuery);
        btnBack = (Button) findViewById(R.id.btnback);
        FutureWeather = (ListView) findViewById(R.id.FutureWeather);
        data = new ArrayList<Weather>();
    }

    private void setListeners() {
        btnQuery.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                String cityName = ecCity.getText().toString();
                try {
                    cityName = URLEncoder.encode(cityName, "utf-8");


                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

                String weatherUrl ="http://v.juhe.cn/weather/index?format=2&cityname="+cityName+"&key=cca05d3567f2737db658d9ce5186b1c1";

                HttpUtil.sendHttpRequest(weatherUrl, new HttpCallbackListener() {

                    public void onFinish(String response) {
                        // TODO Auto-generated method stub
                        Message message = new Message();
                        message.what = SHOW_RESPONSE;
                        //将服务器返回的结果存放到Message中
                        message.obj = response.toString();
                        handler.sendMessage(message);
                    }

                    public void onError(Exception e) {
                        // TODO Auto-generated method stub
                        System.out.println("访问失败");
                    }
                });
            }
        });
       btnBack.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               switch (v.getId()){
                   case R.id.btnback:
                       AlertDialog.Builder dialog=new AlertDialog.Builder(WeatherActivity.this);
                       dialog.setTitle("退出程序");
                       dialog.setMessage("你确定要退出程序吗？");
                       dialog.setCancelable(false);
                       dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                                     finish();
                           }
                       });
                       dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       });
                       dialog.show();
                       break;
                      default:
                          break;
               }
           }
       });

        }



}




