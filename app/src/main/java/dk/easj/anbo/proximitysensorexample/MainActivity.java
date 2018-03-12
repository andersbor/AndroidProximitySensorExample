package dk.easj.anbo.proximitysensorexample;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView messageView;
    private TextView censorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        messageView = findViewById(R.id.messageView);
        censorView = findViewById(R.id.censorView);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        String censorname = event.sensor.getName();
        censorView.setText(censorname + " accuracy " + event.accuracy);
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
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
