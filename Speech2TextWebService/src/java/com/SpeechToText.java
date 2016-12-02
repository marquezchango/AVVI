/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;



import java.io.IOException;
import java.util.Base64;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;




/**
 *
 * @author Juan
 */
@WebService(serviceName = "SpeechToText")
public class SpeechToText {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "speech2text")
    public String speech2text(@WebParam(name = "audioData") String audioDataString64) throws IOException {
        //TODO write your implementation code here:
        
        
        byte[] audioData = Base64.getDecoder().decode(audioDataString64);
        
        
        ClientSpeechEngine clientSpeechEngine = new ClientSpeechEngine("MAC", "localhost", 7777);
        clientSpeechEngine.init();
        clientSpeechEngine.txBytesToServer(audioData);
        
        String res = clientSpeechEngine.rxHypothesis();
        System.out.println(res);
        
        return res;
    }
}
