/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speechserver;

/**
 *
 * @author Juan
 */
import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;


public class SpeechServer extends Thread {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public Socket id;
    public static SpeechEngine speechEngine;
    public static TCPIPTools netTools;
    public AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
    

    public SpeechServer(Socket s) {
        id = s;
        
    }

    @Override
    public void run() {
        
        try {
 
            byte data[] = netTools.rxBytesFromClient(id);
            
            InputStream stream = speechEngine.formatWaveByteInputTCPIP(data, format);
            
            System.out.println("--->Reconociendo");
            
            String result = null;
            result = speechEngine.speech2str(stream);
            
            netTools.txHypothesis(id, result);
            
            System.out.println("--->Resultado:" + result);
            System.out.println("--->FIN");
            
            //speechEngine.saveWaveFileFromByteArray(data, format, "/Users/Juan/Desktop/AudiosCMU/audioSpeechServer.wav");
            
            
        } catch (IOException ex) {
            Logger.getLogger(SpeechServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // TODO code application logic here
        
        System.out.println("****************  Servidor Speech2Text  ***************");
        System.out.println("***************  Reconocimiento de voz  ***************");
        System.out.println("***********************  UDLA  ************************");
        System.out.println("****************  ING SONIDO ACUSTICA  ****************");
        System.out.println("******* Creadores: Juan Chango - Andrés Márquez *******");
        System.out.println("");
        System.out.println("Inicializando Sphinx...");
        
        speechEngine = new SpeechEngine();
        netTools = new TCPIPTools();
        speechEngine.init();

        ServerSocket ss = new ServerSocket(7777);

        while (true) {
            System.out.println("Escuchando en puerto:7777...");
            Socket s = ss.accept();
            System.out.println("Cliente encontrado");
            SpeechServer t = new SpeechServer(s);
            t.start();
        }

        //ss.close();

    }

}
