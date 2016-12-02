/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiavviserver;


/**
 *
 * @author Juan
 */
public class CommandEngineServer {

    static RasPiGPIOEngine gpio;

    public CommandEngineServer() {
    }

    public void init() {
        gpio = new RasPiGPIOEngine();
        gpio.init();

    }
    
    
    public String runCommand(String command) throws InterruptedException{
        String state = null;

        int code = Integer.parseInt(command);
        int typeCmd = code/10000;
        int chCmd = (code= code-typeCmd*10000)/100;
        int stateCmd = (code= code-chCmd*100)/1;
                
        //System.out.println(code);
        //System.out.println(typeCmd);
        //System.out.println(chCmd);
        //System.out.println(stateCmd);

        switch (typeCmd) {
            case 1:
                gpio.setPin(chCmd, stateCmd);
                state = "Ready";
                break;
            
        }
 
        return state;
    }
    
    
    

}
