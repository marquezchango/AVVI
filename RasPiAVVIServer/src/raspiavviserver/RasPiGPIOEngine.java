/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raspiavviserver;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.*;

/**
 *
 * @author Juan
 */
public class RasPiGPIOEngine {
    
    private GpioController gpio;
    private GpioPinDigitalOutput pin1;
    private GpioPinDigitalOutput pin2;
    private GpioPinDigitalOutput pin3;
    private GpioPinDigitalOutput pin4;
    private GpioPinDigitalOutput pin5;
    

    public RasPiGPIOEngine() {
    
    }
    
    
    public void init() {
        gpio = GpioFactory.getInstance();

        pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, PinState.LOW);
        //pin1.setShutdownOptions(true, PinState.LOW);

        pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);
        //pin2.setShutdownOptions(true, PinState.LOW);

        pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);
        //pin3.setShutdownOptions(true, PinState.LOW);

        pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);
        //pin4.setShutdownOptions(true, PinState.LOW);

        pin5 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW);
        //pin5.setShutdownOptions(true, PinState.LOW);
    }

    
    
    
    
    public void setHighPin(int pinNumber) throws InterruptedException {
        
        switch (pinNumber) {
            case 1:
                pin1.high();
                break;
            case 2:
                pin2.high();
                break;
            case 3:
                pin3.high();
                break;
            case 4:
                pin4.high();
                break;
            case 5:
                pin5.high();
                break;
        }


    }
    
    public void setLowPin(int pinNumber) throws InterruptedException {
       
        switch (pinNumber) {
            case 1:
                pin1.low();
                break;
            case 2:
                pin2.low();
                break;
            case 3:
                pin3.low();
                break;
            case 4:
                pin4.low();
                break;
            case 5:
                pin5.low();
                break;
        }

    }
    
    public void togglePin(int pinNumber) throws InterruptedException {
       
        switch (pinNumber) {
            case 1:
                pin1.toggle();
                break;
            case 2:
                pin2.toggle();
                break;
            case 3:
                pin3.toggle();
                break;
            case 4:
                pin4.toggle();
                break;
            case 5:
                pin5.toggle();
                break;
        }

    }
    
    
     public void setPin(int pinNumber, int state) throws InterruptedException {
         
         if (state > 0){
             setHighPin(pinNumber);
         }else{
             setLowPin(pinNumber);
         }
         
     }
    
    
            
            
}
