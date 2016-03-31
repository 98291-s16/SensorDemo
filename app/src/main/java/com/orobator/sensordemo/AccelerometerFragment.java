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
public class AccelerometerFragment extends Fragment implements SensorEventListener {
    private TextView xAxisAccelerationTextView;
    private TextView yAxisAccelerationTextView;
    private TextView zAxisAccelerationTextView;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accelerometer, container, false);

        xAxisAccelerationTextView = (TextView) view.findViewById(R.id.xAxisTextView);
        yAxisAccelerationTextView = (TextView) view.findViewById(R.id.yAxisTextView);
        zAxisAccelerationTextView = (TextView) view.findViewById(R.id.zAxisTextView);

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        // Units in m/s^2
        double xAxisAcceleration = event.values[0];
        double yAxisAcceleration = event.values[1];
        double zAxisAcceleration = event.values[2];

        String xAxis = getString(R.string.x_axis_acceleration, xAxisAcceleration);
        String yAxis = getString(R.string.y_axis_acceleration, yAxisAcceleration);
        String zAxis = getString(R.string.z_axis_acceleration, zAxisAcceleration);

        xAxisAccelerationTextView.setText(xAxis);
        yAxisAccelerationTextView.setText(yAxis);
        zAxisAccelerationTextView.setText(zAxis);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
