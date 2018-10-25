package banh.along.smartwheelchair;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import static banh.along.smartwheelchair.MainActivity.REQUEST_BLUETOOTH;

public class GoogleVoiceDetectActivity extends AppCompatActivity {



    private TextView txvResult;
    ImageView btnSpeak;
    Bluetooth myBluetooth;
    TextToSpeech t1;

    BluetoothAdapter bluetoothAdapter;
    BluetoothDevice bluetoothDevice;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detect_google_voice_activity);
        txvResult = (TextView) findViewById(R.id.txvResult);
        btnSpeak = findViewById(R.id.btnSpeak);


        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }

        myBluetooth = new Bluetooth(getParent());
        myBluetooth.enableBluetooth();
        myBluetooth.connectToName("hc_06");







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
                String body = txvResult.getText().toString();
                myBluetooth.send(body);


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
}



