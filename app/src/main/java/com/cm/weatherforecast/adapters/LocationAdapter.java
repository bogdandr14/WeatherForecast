package com.cm.weatherforecast.adapters;

import static com.cm.weatherforecast.activities.MainActivity.CITY_NAME_MESSAGE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cm.weatherforecast.R;
import com.cm.weatherforecast.activities.MainActivity;
import com.cm.weatherforecast.activities.SettingsActivity;
import com.cm.weatherforecast.modals.LocationModal;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private Context context;
    private Activity locationManagerActivity;
    private List<LocationModal> locationModalArrayList;

    public LocationAdapter(Context context, List<LocationModal> locationModalArrayList) {

        this.context = context;
        this.locationModalArrayList = locationModalArrayList;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item_location, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationModal modal = locationModalArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature() + "°C");
        holder.cityNameTV.setText(modal.getLocationName());
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "delete location", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {

        return locationModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView temperatureTV, cityNameTV;
        private ImageView deleteIV;
        private LinearLayout locationsLL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cityNameTV = itemView.findViewById(R.id.idLocationTV);
            temperatureTV = itemView.findViewById(R.id.idTemperatureTV);
            deleteIV = itemView.findViewById(R.id.idDeleteLocationIV);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setBackgroundColor(0xf3342);
            String reply = cityNameTV.getText().toString();
            Intent replyIntent = new Intent(context, MainActivity.class);
            replyIntent.putExtra(CITY_NAME_MESSAGE, reply);
//            setResult(RESULT_OK,replyIntent);
//            finish(); //TODO
            Toast.makeText(context, "return to main activity", Toast.LENGTH_SHORT).show();
            context.startActivity(replyIntent);

        }
    }
}