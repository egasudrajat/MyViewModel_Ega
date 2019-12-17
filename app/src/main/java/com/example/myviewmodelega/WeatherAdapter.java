package com.example.myviewmodelega;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private ArrayList<WeatherModel> List = new ArrayList<>();

    void setData(ArrayList<WeatherModel> list) {
        List.clear();
        List.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.MyViewHolder holder, int position) {
        holder.bind(List.get(position));
//        WeatherModel model = List.get(position);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKota, tvTemperatur, tvDeskripsi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaKota = itemView.findViewById(R.id.textKota);
            tvDeskripsi = itemView.findViewById(R.id.textDesc);
            tvTemperatur = itemView.findViewById(R.id.textTemp);
        }

        void bind(WeatherModel weatherModel){
        tvNamaKota.setText(weatherModel.getName());
        tvTemperatur.setText(weatherModel.getTemperature());
        tvDeskripsi.setText(weatherModel.getDescription());

        }
    }
}
