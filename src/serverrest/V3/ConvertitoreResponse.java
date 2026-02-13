/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest.V3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 *
 * @author delfo
 */
public class ConvertitoreResponse {

    private String unita1;
    private String unita2;
    private double valore;
    private String conversione;

    //Campi aggiuntivi per conversione
    private String timestamp;
    private String versioneApi;
    private String requestID;

    // Costruttore vuoto necessario per GSON
    public ConvertitoreResponse() {
    }

    // Costruttore con parametri
    public ConvertitoreResponse(String unita1, String unita2, double valore, String conversione) {
        //                                                            ^^^^^^ corretto
        this.unita1 = unita1;
        this.unita2 = unita2;
        this.valore = valore;
        this.conversione = conversione; // Usa il parametro passato

        // Metadata automatici
        this.timestamp = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.versioneApi = "3.0";
        this.requestID = UUID.randomUUID().toString();
    }

    public String getUnita1() {
        return unita1;
    }

    public void setUnita1(String unita1) {
        this.unita1 = unita1;
    }

    public String getUnita2() {
        return unita2;
    }

    public void setUnita2(String unita2) {
        this.unita2 = unita2;
    }

    public double getValore() {
        return valore;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public String getConversione() {
        return conversione;
    }

    // Getter
    public void setConversione(String conversione) {
        this.conversione = conversione;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getVersioneApi() {
        return versioneApi;
    }

    public void setVersioneApi(String versioneApi) {
        this.versioneApi = versioneApi;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

}
