/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speechserver;

import edu.cmu.sphinx.frontend.util.StreamDataSource;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 *
 * @author Juan
 */
public class SpeechEngine {

    private URL configURL ; //= RxCMU.class.getResource("config.xml");

    private ConfigurationManager cm ;//= new ConfigurationManager(configURL);
    private Recognizer recognizer ;//= (Recognizer) cm.lookup("recognizer");

    /* allocate the resource necessary for the recognizer */
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;

    private StreamDataSource dataSource ;//= (StreamDataSource) cm.lookup("DataSource");
    
    private File wavFile;
    
    private Result result;

    public SpeechEngine() {
        
        configURL = speechserver.SpeechServer.class.getResource("config.xml");
        cm = new ConfigurationManager(configURL);
        recognizer = (Recognizer) cm.lookup("recognizer");
        dataSource = (StreamDataSource) cm.lookup("DataSource");
        
    }
    
       
    
    public void init() {
       
        recognizer.allocate();

    }
    
    public void stop() {
       
        recognizer.deallocate();

    }
    
    
    
    public String speech2str (InputStream stream){
        dataSource.setInputStream(stream);
        String resultText = null;
        
        while ((result = recognizer.recognize()) != null) {

            resultText = result.getBestResultNoFiller();
            //System.out.println(resultText);
        }
        
        return resultText;
    }
    
    
    public InputStream formatWaveByteInputTCPIP(byte[] data, AudioFormat format) throws IOException{
        int len = data.length;
        InputStream stream = new ByteArrayInputStream(data);
        AudioInputStream ais = new AudioInputStream(stream, format, len/ format.getFrameSize());
        ByteArrayOutputStream out1 = new ByteArrayOutputStream(len);
        AudioSystem.write(ais, fileType, out1);
        data= out1.toByteArray();
        stream = new ByteArrayInputStream(data);
        return stream;
    }
    
    
    public void saveWaveFileFromByteArray(byte[] data, AudioFormat format,String fileAddress) throws IOException{
        wavFile = new File(fileAddress);
        int len = data.length;
        InputStream stream = new ByteArrayInputStream(data);
        AudioInputStream ais = new AudioInputStream(stream, format, len/ format.getFrameSize());
        AudioSystem.write(ais, fileType, wavFile);
    }
    
    
    

}
