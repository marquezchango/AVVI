/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Juan
 */
public class ClientSpeechEngine {

    private String clientName;//= "MAC";
    private String ipAddress;
    private int port;

    private OutputStream out;
    private DataOutputStream dos;
    
    private BufferedReader in;

    private Socket s;//= new Socket("192.168.2.68", 7777);

    public ClientSpeechEngine(String clientName, String ipAddress, int port) {
        this.clientName = clientName;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void init() throws IOException {
        s = new Socket(ipAddress, port);
    }

    public void txBytesToServer(byte[] data) throws IOException {
        out = s.getOutputStream();
        dos = new DataOutputStream(out);

        dos.writeInt(data.length);
        dos.write(data, 0, data.length);
    }

    public String rxHypothesis() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return in.readLine();

    }

}
