/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package speechserver;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Juan
 */
public class TCPIPTools {

    private byte data[];
    private int len;
    private InputStream in;
    private DataInputStream dis;

    private PrintWriter out;

    public TCPIPTools() {
    }

    public byte[] rxBytesFromClient(Socket id) throws IOException {
        in = id.getInputStream();
        dis = new DataInputStream(in);
        len = dis.readInt();
        data = new byte[len];
        dis.readFully(data);
        return data;
    }

    public void txHypothesis(Socket id, String hypothesis) throws IOException {
        out = new PrintWriter(id.getOutputStream(), true);
        out.println(hypothesis);
        

    }

}
