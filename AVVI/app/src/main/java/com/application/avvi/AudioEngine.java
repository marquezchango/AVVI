package com.application.avvi;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by Juan on 14/11/16.
 */
public class AudioEngine {

    private static final String LOG_TAG = null;
    private int SAMPLE_RATE = 16000;

    private AudioRecord audioRecorder;
    private ByteArrayOutputStream out;
    private int factorBuffer;
    private ByteBuffer aB;
    private int numBytesRead;
    private int bytesRead;
    private int audioRecordBufferSize;

    private AudioTrack audioPlayer;
    private byte[] bytesBuffer;
    int audioTrackBufferSize;


    public AudioEngine() {
    }

    public void init(){
        setAudioRecordBufferSize();
        setAudioRecord();
        configInitParamsAudioRecord();


        setAudioTrackBufferSize();
        setAudioTrack();
        configInitParamsAudioTrack();
    }



    public void setSAMPLE_RATE(int SAMPLE_RATE) {
        this.SAMPLE_RATE = SAMPLE_RATE;
    }

    private void setAudioRecordBufferSize (){

        audioRecordBufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        if (audioRecordBufferSize == AudioRecord.ERROR || audioRecordBufferSize == AudioRecord.ERROR_BAD_VALUE) {
            audioRecordBufferSize = SAMPLE_RATE * 2;
        }
    }

    private void setAudioTrackBufferSize() {
        audioTrackBufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
        if (audioTrackBufferSize == AudioTrack.ERROR || audioTrackBufferSize == AudioTrack.ERROR_BAD_VALUE) {
            audioTrackBufferSize = audioRecordBufferSize;
        }
    }

    private void setAudioRecord(){

        audioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                audioRecordBufferSize);

        if (audioRecorder.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(LOG_TAG, "Audio Record can't initialize!");

        }
    }

    private void setAudioTrack(){
        audioPlayer = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLE_RATE,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                audioTrackBufferSize,
                AudioTrack.MODE_STREAM);
    }


    private void configInitParamsAudioRecord(){
        out = new ByteArrayOutputStream();
        factorBuffer = (int) ((1280.0/audioRecordBufferSize)*73.0);
        aB= ByteBuffer.allocateDirect(audioRecordBufferSize*factorBuffer);

    }


    private void initParamsRecording(){
        numBytesRead = 0;
        bytesRead = 0;
    }

    private void configInitParamsAudioTrack(){
        bytesBuffer = new byte[audioRecordBufferSize];

    }


    public byte[] recordAudio(){

        initParamsRecording();

        audioRecorder.startRecording();

        while (bytesRead < audioRecordBufferSize*factorBuffer) {
            numBytesRead = audioRecorder.read(aB,audioRecordBufferSize);
            out.write(aB.array(), 0, numBytesRead);
            bytesRead = bytesRead + numBytesRead;
        }
        audioRecorder.stop();
        audioRecorder.release();

        return out.toByteArray();
    }

    public void playAudio(byte[] audioDataByteArray){

        ByteArrayInputStream in = new ByteArrayInputStream(audioDataByteArray);

        audioPlayer.play();

        int bytesReadAudioPlayer;

        while((bytesReadAudioPlayer = in.read(bytesBuffer,0,bytesBuffer.length)) != -1){
            audioPlayer.write(bytesBuffer,0, bytesReadAudioPlayer);
        }

        audioPlayer.release();

    }


}
