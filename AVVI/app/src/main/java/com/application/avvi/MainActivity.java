package com.application.avvi;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    public byte[] byteArrayAudioRecorded;
    String byteArraySpeechString64;
    String resRasPi;
    TextToSpeech t1;
    boolean stateProcess = false;
    String rasPiCmd = null;
    CommandEngine commandEngine;


    final int timeLoadingApp = 10000;

    final String NAMESPACE = "http://com/";
    final String URL="http://192.168.0.60:8080/Speech2TextWebService/SpeechToText?WSDL";
    final String METHOD_NAME = "speech2text";
    final String SOAP_ACTION = "http://com/speech2text";

    final String RASPI_IP = "192.168.0.59";
    final String RASPI_PORT = "7778";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.eyew);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_loading_screen);

        commandEngine = new CommandEngine();
        final ProgressBar loadingBar = (ProgressBar)findViewById(R.id.progressBar);

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

                    Locale loc = new Locale("es", "", "");
                    if (t1.isLanguageAvailable(loc) >= TextToSpeech.LANG_AVAILABLE) {
                        t1.setLanguage(loc);
                    }

                }

            }
        });

        Thread loading = new Thread(){
            @Override
            public void run() {
                boolean isTerminated = false;
                int progressValue = 0;
                try {

                    while (!isTerminated) {
                        loadingBar.setProgress(progressValue);
                        sleep(timeLoadingApp/10);
                        progressValue+=10;

                        if (progressValue > 90){
                            isTerminated=true;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };loading.start();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                speak("Hola soy Avi!, toca la pantalla para comenzar.");
                setContentView(R.layout.activity_main);
            }
        }, timeLoadingApp);

    }

    @Override
    public void onDestroy() {
        if (t1 != null) {
            t1.stop();
            t1.shutdown();
        }
        super.onDestroy();
    }

    public void setStringRes(String res){
        TextView resSpeechText = (TextView) findViewById(R.id.resString);
        resSpeechText.setText(res);
    }

    public void onClickScreen(View vista) throws IOException, InterruptedException {
        t1.stop();
        AudioEngine audioEngine = new AudioEngine();
        audioEngine.init();
        byteArrayAudioRecorded = audioEngine.recordAudio();
        byteArraySpeechString64 = Base64.encodeToString(byteArrayAudioRecorded,Base64.NO_WRAP);
        Speech2TextWebServer webService = new Speech2TextWebServer();
        webService.execute();
        setStringRes("Procesando...");
        speak("Espera un momento");
    }


    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            t1.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


    public class RasPiServer extends AsyncTask<String,Integer,String>{
        @Override
        protected String doInBackground(String... strings) {
            stateProcess = true;
            String res = null;
            ClientTCPEngine clientTCPEngine = new ClientTCPEngine("MAC", strings[0], Integer.parseInt(strings[1]) );
            try {
                clientTCPEngine.init();
                clientTCPEngine.txCommand(rasPiCmd);
                res = clientTCPEngine.rxCommand();
            } catch (IOException e) {
                stateProcess = false;
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            resRasPi = res;
            setStringRes(commandEngine.getSpeakCmd());
        }
    }



    public class Speech2TextWebServer extends AsyncTask<Void,Integer,String> {

        @Override
        protected String doInBackground(Void... voids) {

            stateProcess = true;
            String res = null;
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("audioData",byteArraySpeechString64);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            HttpTransportSE ht = new HttpTransportSE(URL);

            try {
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
                res = response.toString();
            }
            catch (Exception e)
            {
                stateProcess = false;
                Log.i("Error: ",e.getMessage());
                e.printStackTrace();
            }
            return res;
        }

        protected void onPostExecute(String res) {
            commandEngine.createCommandsToSendAndSpeak(res);
            rasPiCmd = commandEngine.getRasPiCmd();
            if (rasPiCmd != null){
                setStringRes(commandEngine.getSpeakCmd());
                new RasPiServer().execute(RASPI_IP,RASPI_PORT);
            }else{
                setStringRes("Inténtalo de nuevo!");
            }
            speak(commandEngine.getSpeakCmd());
            stateProcess = false;
       }
    }

}
