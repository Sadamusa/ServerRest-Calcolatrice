/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest.V3;

import serverrest.V2.*;
import serverrest.V1.CalcolatriceServiceV1;

/**
 *
 * @author delfo
 */
public class ConvertitoreService  {
    
    public static double calcola(String unita1, String unita2, Double valore)
            throws IllegalArgumentException {

        if (valore == null) {
            throw new IllegalArgumentException("Operatore non può essere vuoto");
        }

        // Convertiamo l'operatore in maiuscolo per gestire input case-insensitive
        unita1 = unita1.toUpperCase().trim();
        unita2 = unita2.toUpperCase().trim();

        switch (unita2) {
            case "METRO":
            case "MT":
            case "M":
                return convertiMetri(valore);

            case "YARD":
            case "YARDE":
            case "Y":
            case "YD":
                return convertiYard(valore);
                
            default:
// Riusa le operazioni base della classe padre
                return convertiMetri(valore);
        }
    }
    
    /**
     * Calcola la potenza
     */
    public static double convertiMetri(double valore) {
        return valore = valore * 1.09361;
    }
    
    public static double convertiYard(double valore) {
        return valore = valore / 0.9144;
    }
    
    
}
