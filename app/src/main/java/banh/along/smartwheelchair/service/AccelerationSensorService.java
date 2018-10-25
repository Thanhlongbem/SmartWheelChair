package banh.along.smartwheelchair.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;


public class AccelerationSensorService extends Service implements SensorEventListener{
    private static final String DEBUG_TAG = "AccLoggerService";
    private SensorManager sensorManager = null;
    private Sensor sensor = null;
    private Context mContext = getBaseContext();





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        Toast.makeText(getApplicationContext(),"Đang ở trong onStartCommand", Toast.LENGTH_LONG).show();


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float ax = event.values[0];
        float ay = event.values[1];
        float az = event.values[2];

        double giatoc = Math.sqrt(ax * ax + ay * ay + az * az);
        String stCoor = ax + " " + ay + " " + az + " " + giatoc;
        long time = System.currentTimeMillis();
        String body = time + "  " + stCoor;



        if(giatoc >= 15){
            Toast.makeText(getApplicationContext(),"Đang ở trong onSensorChanged", Toast.LENGTH_LONG).show();
            Intent i = new Intent("STATE_OF_HUMAN").putExtra("GiaToc", "Are you OK?");
            this.sendBroadcast(i);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Toast.makeText(getApplicationContext(),"Đang ở trong onAccuracyChanged", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Đang ở trong onDestroy", Toast.LENGTH_LONG).show();
    }

    /**
     * Hàm ghi tập tin trong Android
     * dùng openFileOutput để ghi
     * openFileOutput tạo ra FileOutputStream
     */
    public void writeData(String data)
    {
        String sdcard=Environment
                .getExternalStorageDirectory()
                .getAbsolutePath()+"/longyeubanh.txt";
        try {
            OutputStreamWriter writer=
                    new OutputStreamWriter(
                            new FileOutputStream(sdcard));
            writer.write(data);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
