package com.application.avvi;

/**
 * Created by Juan on 16/11/16.
 */
public class CommandEngine {

    String rasPiCmd = null;
    String speakCmd = "Lo siento, no pude reconocer ese comando, inténtalo nuevamente!";

    public CommandEngine() {
    }

    public void createCommandsToSendAndSpeak(String speechText) {
        rasPiCmd = null;
        speakCmd = "Lo siento, no pude reconocer ese comando, inténtalo nuevamente!";

        switch (speechText) {
            case "activa las lámparas" :
                rasPiCmd = "10101";
                speakCmd = "Activando las lamparas!";
                break;
            case "activa la música":
                rasPiCmd = "10201";
                speakCmd = "Activando la música!";
                break;
            case "activa la radio":
                rasPiCmd = "10301";
                speakCmd = "Activando la radio!";
                break;

            case "iba las lámparas" :
                rasPiCmd = "10101";
                speakCmd = "Activando las lamparas!";
                break;
            case "iba la música":
                rasPiCmd = "10201";
                speakCmd = "Activando la música!";
                break;
            case "iba la radio":
                rasPiCmd = "10301";
                speakCmd = "Activando la radio!";
                break;

            case "sube el audio":
                rasPiCmd = "10401";
                speakCmd = "Subiendo el volumen de la música!";
                break;
            case "dónde está la puerta":
                rasPiCmd = "10501";
                speakCmd = "La Puerta está donde escuches el beep!";
                break;

            case "des activa las lámparas":
                rasPiCmd = "10100";
                speakCmd = "Desactivando las lamparas!";
                break;
            case "des activa la música":
                rasPiCmd = "10200";
                speakCmd = "Desactivando la música!";
                break;
            case "des activa la radio":
                rasPiCmd = "10300";
                speakCmd = "Desactivando la radio!";
                break;
            case "desactivar las lámparas":
                rasPiCmd = "10100";
                speakCmd = "Desactivando las lamparas!";
                break;
            case "desactivar la música":
                rasPiCmd = "10200";
                speakCmd = "Desactivando la música!";
                break;
            case "desactivar la radio":
                rasPiCmd = "10300";
                speakCmd = "Desactivando la radio!";
                break;

            case "baja el audio":
                rasPiCmd = "10400";
                speakCmd = "Bajando el volumen de la música!";
                break;
            case "dónde están mis llaves":
                rasPiCmd = "10500";
                speakCmd = "Tus llaves están donde escuches el beep!";
                break;



        }

    }

    public String getRasPiCmd() {
        return rasPiCmd;
    }

    public void setRasPiCmd(String rasPiCmd) {
        this.rasPiCmd = rasPiCmd;
    }

    public String getSpeakCmd() {
        return speakCmd;
    }

    public void setSpeakCmd(String speakCmd) {
        this.speakCmd = speakCmd;
    }
}
