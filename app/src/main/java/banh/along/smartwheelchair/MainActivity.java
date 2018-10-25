package banh.along.smartwheelchair;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

import banh.along.smartwheelchair.service.AccelerationSensorService;


public class MainActivity extends AppCompatActivity{
    /* Button btnGoogleVoice;
     Button btIMU;
     Button btnTextToSpeech;*/
    Button btnONOFF;
    Button btnTay;

    private BluetoothAdapter BTAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private BroadcastReceiver mReceiver;



    TextToSpeech t1;

    public static int REQUEST_BLUETOOTH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(MainActivity.this, AccelerationSensorService.class);
        startService(intent);
        IntentFilter intentFilter = new IntentFilter(
                "STATE_OF_HUMAN");
         mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                //extract our message from intent
                String msg_for_me = intent.getStringExtra("GiaToc");
                //log our message value
                Log.e("InchooTutorial", msg_for_me);

            }
        };
        //registering our receiver
        this.registerReceiver(mReceiver, intentFilter);





        BTAdapter = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = BTAdapter.getBondedDevices();
        final ArrayList list = new ArrayList();
        btnONOFF = findViewById(R.id.btnONOFF);
//        btnGoogleVoice = findViewById(R.id.btnGoogleVoice);
//        btIMU = findViewById(R.id.btIMU);
//        btnTextToSpeech = findViewById(R.id.btnTextToSpeech);

        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GoogleVoiceDetectActivity.class);
                startActivity(intent);
            }
        });

        t1 =new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        if (!BTAdapter.isEnabled()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth");
            builder.setMessage("Để tiếp tục bạn cần bật Bluetooth của thiết bị và kết nối tới một thiết bị khác");
            builder.setCancelable(false);
            builder.setPositiveButton("Bật", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(turnOn, 0);
                }
            });
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }






       /* btnGoogleVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!BTAdapter.isEnabled()){
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent,REQUEST_BLUETOOTH);
                    Intent intent1 = new Intent(MainActivity.this, GoogleVoiceDetectActivity.class);
                    startActivity(intent1);
                }else if(BTAdapter.isEnabled()){
                    //TODO - DO SOMETHING
                    Intent intent = new Intent(MainActivity.this, GoogleVoiceDetectActivity.class);
                    startActivity(intent);
                }
            }

        });



        btIMU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SensorDeviceProcess.class);
                startActivity(intent);
            }
        });

        btnTextToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = "Now is 9pm, do you want to turn off the lamp";
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

*/

        /*Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                ObDevice newDevice= new ObDevice(device.getName());
                device_data.add(String.valueOf(newDevice));
            }
            listDeviceRecyclerView.setVisibility(VISIBLE);
            imgBack.setVisibility(View.GONE);
        }*/

    }







}



