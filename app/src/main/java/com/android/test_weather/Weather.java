package com.android.test_weather;

public class Weather {
    private String dayOfWeek;//星期几
    private String date;//日期
    private String temperature;//温度
    private String weather;//天气
    private String wind;//风向

    public Weather() {
    }

    public Weather(String dayOfWeek, String date, String temperature,
                   String weather,String wind) {
        super();
        this.dayOfWeek = dayOfWeek;
        this.date = date;
        this.temperature = temperature;
        this.weather = weather;
        this.wind=wind;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWind() {
        return wind;
    }

    @Override
    public String toString() {
        return "Weather [dayOfWeek=" + dayOfWeek + ", date=" + date
                + ", temperature=" + temperature + ", weather=" + weather + ",wind="+wind+"]";
    }

}