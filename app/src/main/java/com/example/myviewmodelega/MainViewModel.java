package com.example.myviewmodelega;

import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    static final String APP_ID = "ace702e642a032fc75f35553f8108e9d";
    private MutableLiveData<ArrayList<WeatherModel>> ListWeather = new MutableLiveData<>();

    LiveData<ArrayList<WeatherModel>> getListWeather() {
        return ListWeather;
    }

    void setListWeather(final String cities) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<WeatherModel> ListItems = new ArrayList<>();
        String url = "http://api.openweathermap.org/data/2.5/group?id=" + cities + "&units=metric&appid=" + APP_ID;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray responseArray = responseObject.getJSONArray("list");

                    for (int i = 0; i < responseArray.length(); i++) {
                        WeatherModel model = new WeatherModel();
                        JSONObject weather = responseArray.getJSONObject(i);
                        model.setId(weather.getInt("id"));
                        model.setName(weather.getString("name"));
                        model.setCurrentWeather(weather.getJSONArray("weather").getJSONObject(0).getString("main"));
                        model.setDescription(weather.getJSONArray("weather").getJSONObject(0).getString("description"));
                        double tempInKelvin = weather.getJSONObject("main").getDouble("temp");
                        double tempInCelcius = tempInKelvin - 273;
                        model.setTemperature(new DecimalFormat("##.##").format(tempInCelcius));
                        ListItems.add(model);

                    }

                    ListWeather.postValue(ListItems);
                } catch (Exception e) {
                    Log.d("exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ", error.getMessage());
            }
        });


    }
}
