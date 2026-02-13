/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest.V3;


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

        switch (unita1) {
            case "METRO":
            case "MT":
            case "M":
                if(unita2.equals("YARD")){                    
                    return convertiMetri(valore);
                } else {
                    throw new IllegalArgumentException("Unita' consentite: METRI & YARD");
                }
                

            case "YARD":
            case "YARDE":
            case "Y":
            case "YD":
                if(unita2.equals("Metri")){                    
                    return convertiYard(valore);
                } else {
                    throw new IllegalArgumentException("Unita' consentite: METRI & YARD");
                }
                
            default:
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
