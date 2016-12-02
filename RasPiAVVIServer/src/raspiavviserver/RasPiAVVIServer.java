/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiavviserver;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Juan
 */
public class RasPiAVVIServer extends Thread{
    
    Socket id;
    static CommandEngineServer commandEngine;
    String command = null;


    public RasPiAVVIServer(Socket s) {
        id = s;
        
    }
    
    @Override
    public void run() {
        
        
        try {
            PrintWriter out = new PrintWriter(id.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(id.getInputStream()));
            
            System.out.print("Cliente conectado: ");
            command = in.readLine();
            System.out.println(command);
            String res = commandEngine.runCommand(command);
            out.println(res);
          

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(RasPiAVVIServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("****************  Servidor RasPi AVVI  ****************");
        System.out.println("***************  Reconocimiento de voz  ***************");
        System.out.println("***********************  UDLA  ************************");
        System.out.println("****************  ING SONIDO ACUSTICA  ****************");
        System.out.println("******* Creadores: Juan Chango - Andrés Márquez *******");
        System.out.println("");
        System.out.println("Inicializando Servidor Raspberry Pi...");

        ServerSocket ss = new ServerSocket(7778);

        commandEngine = new CommandEngineServer();
        commandEngine.init();
        
        
        while (true) {
            System.out.println("Escuchando en puerto:7778...");
            Socket s = ss.accept();
            System.out.println("Cliente encontrado");
            RasPiAVVIServer t = new RasPiAVVIServer(s);
            t.start();
        }

    }
    
}
