package com.application.avvi;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Juan on 9/11/16.
 */
public class ClientTCPEngine {

    String clientName;
    String ipAddress;
    int port;

    OutputStream out;
    DataOutputStream dos;

    BufferedReader in;

    Socket s;

    public ClientTCPEngine(String clientName, String ipAddress, int port) {
        this.clientName = clientName;
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public void init() throws IOException {
        s = new Socket(ipAddress, port);
    }

     public void txCommand(String command) throws IOException {
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        out.println(command);
        out.flush();

    }

    public String rxCommand() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        return in.readLine();

    }



}
