package com.orobator.sensordemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by AndrewOrobator on 3/30/16.
 */
public class ProximityFragment extends Fragment implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private TextView proximityTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_proximity, container, false);
        proximityTextView = (TextView) view.findViewById(R.id.proximity_TextView);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_PROXIMITY) {
            return;
        }

        String proximity = getString(R.string.proximity_cm, event.values[0]);
        proximityTextView.setText(proximity);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
