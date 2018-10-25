package banh.along.smartwheelchair;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class GoogleVoiceDetectActivity extends AppCompatActivity {



    private TextView txvResult;
    Button btnSpeak;
    BluetoothSocket socket;
    InputStream inputStream;
    OutputStream outputStream;
    byte[] buffer = new byte[1024];
    int i;
    String message;
    private Bluetooth b;
    TextToSpeech t1;


    BluetoothDevice bluetoothDevice;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_google_voice_activity);
        txvResult = (TextView) findViewById(R.id.txvResult);

        Toast.makeText(getApplicationContext(),"Đang ở trong onCreate", Toast.LENGTH_LONG).show();

        b = new Bluetooth(this);
        b.enableBluetooth();
        b.connectToName("hc_06");







    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        //intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS,0);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS,300);
        intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS,300);
        intent.putExtra(RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE,RecognizerIntent.ACTION_VOICE_SEARCH_HANDS_FREE);



        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else{
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext()," ở trong onActivityResult", Toast.LENGTH_LONG).show();

        if(requestCode == 10){
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                txvResult.setText(result.get(0));
                message = result.get(0).toString();


                if(b.isConnected()){
                    if(message == "Hello" || message == "hello"){
                        b.send("1");
                    }

                }else{
                    String toSpeak = "You need pair with another device";
                    //Toast.makeText(this, toSpeak,Toast.LENGTH_LONG).show();
                }



            }else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
                getSpeechInput(getCurrentFocus());
            } else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
                getSpeechInput(getCurrentFocus());
            } else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
                getSpeechInput(getCurrentFocus());
            } else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
                getSpeechInput(getCurrentFocus());
            } else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
                getSpeechInput(getCurrentFocus());

            }
        }else getSpeechInput(getCurrentFocus());
    }







   /* public class ConnectThread extends Thread{
        private BluetoothSocket bTSocket;

        public boolean connect(BluetoothDevice bTDevice, UUID mUUID) {
            BluetoothSocket temp = null;
            try {
                temp = bTDevice.createRfcommSocketToServiceRecord(mUUID);
            } catch (IOException e) {
                Log.d("CONNECTTHREAD","Could not create RFCOMM socket:" + e.toString());
                return false;
            }
            try {
                bTSocket.connect();
            } catch(IOException e) {
                Log.d("CONNECTTHREAD","Could not connect: " + e.toString());
                try {
                    bTSocket.close();
                } catch(IOException close) {
                    Log.d("CONNECTTHREAD", "Could not close connection:" + e.toString());
                    return false;
                }
            }
            return true;
        }

        public boolean cancel() {
            try {
                bTSocket.close();
            } catch(IOException e) {
                Log.d("CONNECTTHREAD","Could not close connection:" + e.toString());
                return false;
            }
            return true;
        }
    }

    public class ServerConnectThread extends Thread{
        private BluetoothSocket bTSocket;

        public ServerConnectThread() { }

        public void acceptConnect(BluetoothAdapter bTAdapter, UUID mUUID) {
            BluetoothServerSocket temp = null;
            try {
                temp = bTAdapter.listenUsingRfcommWithServiceRecord("Service_Name", mUUID);
            } catch(IOException e) {
                Log.d("SERVERCONNECT", "Could not get a BluetoothServerSocket:" + e.toString());
            }
            while(true) {
                try {
                    bTSocket = temp.accept();
                } catch (IOException e) {
                    Log.d("SERVERCONNECT", "Could not accept an incoming connection.");
                    break;
                }
                if (bTSocket != null) {
                    try {
                        temp.close();
                    } catch (IOException e) {
                        Log.d("SERVERCONNECT", "Could not close ServerSocket:" + e.toString());
                    }
                    break;
                }
            }
        }

        public void closeConnect() {
            try {
                bTSocket.close();
            } catch(IOException e) {
                Log.d("SERVERCONNECT", "Could not close connection:" + e.toString());
            }
        }
    }


    public class TransferandReceiver extends Thread{
        public TransferandReceiver(){};
        public void sendData(BluetoothSocket socket, int data) throws IOException{
            ByteArrayOutputStream output = new ByteArrayOutputStream(4);
            output.write(data);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(output.toByteArray());
        }

        public int receiveData(BluetoothSocket socket) throws IOException{
            byte[] buffer = new byte[4];
            ByteArrayInputStream input = new ByteArrayInputStream(buffer);
            InputStream inputStream = socket.getInputStream();
            inputStream.read(buffer);
            return input.read();
        }
    }


    public void ConnectTwoDevice(){
        BluetoothAdapter btadap = BluetoothAdapter.getDefaultAdapter();
        UUID uuid = UUID.fromString("1");


        ConnectThread connectThread = new ConnectThread();
        connectThread.connect(bluetoothDevice,uuid);

        ServerConnectThread serverConnectThread = new ServerConnectThread();
        serverConnectThread.acceptConnect(btadap,uuid);
    }

    public void DataTransfer(int data){
        ConnectTwoDevice();
        BluetoothAdapter btadap = BluetoothAdapter.getDefaultAdapter();
        TransferandReceiver transferandReceiver = new TransferandReceiver();
        try {
            transferandReceiver.sendData(socket,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}



