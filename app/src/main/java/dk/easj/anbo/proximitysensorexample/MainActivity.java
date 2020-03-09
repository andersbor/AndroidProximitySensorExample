package dk.easj.anbo.proximitysensorexample;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final String LOG_TAG = "MINE";
    private SensorManager mSensorManager;
    private Sensor mProximity, mLight, mAcceleration;
    private TextView sensorView, sensorView2, sensorView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mAcceleration = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        TextView messageView = findViewById(R.id.messageView);
        sensorView = findViewById(R.id.censorView);
        sensorView2 = findViewById(R.id.censorView2);
        sensorView3 = findViewById(R.id.censorView3);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAcceleration, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String sensorName = event.sensor.getName();
        switch (event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                float light = event.values[0];
                Log.d(LOG_TAG, "Light " + light);
                sensorView.setText(sensorName + " Light " + light);
                break;
            case Sensor.TYPE_PROXIMITY:
                float distance_in_cm = event.values[0];
                Log.d(LOG_TAG, "Proximity " + distance_in_cm + " cm");
                sensorView2.setText(sensorName + " Proximity " + distance_in_cm);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                sensorView3.setText(sensorName + " Acceleration " + x + " " + y + " " + z);
                Log.d(LOG_TAG, "Acceleration: " + x + " " + y + " " + z);
                break;
        }
        /*
        // http://developer.android.com/guide/topics/sensors/sensors_position.html
        final float distanceInCentimeters = event.values[0];
        if (distanceInCentimeters == 0) {
            messageView.setText("Near");
            Log.d("MINE", "Near");
        } else {
            String message = String.format("Far. Distance %s cm", distanceInCentimeters);
            messageView.setText(message);
            Log.d("MINE", message);
        }
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
