/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

/**
 *
 * @author delfo
 */
public class CalcolatriceServiceV2 extends CalcolatriceServiceV1 {
    /**
     * Esegue l'operazione matematica richiesta
     *
     * @param operando1 Il primo operando
     * @param operando2 Il secondo operando
     * @param operatore L'operatore (SOMMA, SOTTRAZIONE, MOLTIPLICAZIONE,
     * DIVISIONE)
     * @return Il risultato dell'operazione
     * @throws IllegalArgumentException se l'operatore non è valido o divisione
     * per zero
     */
    public static double calcola(double operando1, double operando2, String operatore)
            throws IllegalArgumentException {

        if (operatore == null || operatore.trim().isEmpty()) {
            throw new IllegalArgumentException("Operatore non può essere vuoto");
        }

        // Convertiamo l'operatore in maiuscolo per gestire input case-insensitive
        String op = operatore.toUpperCase().trim();

        switch (op) {
            case "POTENZA":
            case "POW":
            case "^":
                return potenza(operando1, operando2);

            case "MODULO":
            case "MOD":
            case "%":
                return modulo(operando1, operando2);

            case "RADICE":
            case "SQRT":
                return radice(operando1, operando2);
                
            default:
// Riusa le operazioni base della classe padre
                return CalcolatriceServiceV1.calcola(operando1, operando2, operatore);
        }
    }
    
    /**
     * Calcola la potenza
     */
    public static double potenza(double base, double esponente) {
        return Math.pow(base, esponente);
    }
    
    /**
     * Calcola il modulo
     */
    public static double modulo(double dividendo, double divisore) {
        if (divisore == 0) {
            throw new IllegalArgumentException("Modulo per zero");
        }
        return dividendo % divisore;
    }
    
    /**
     * Calcola la radice n-esima
     */
    public static double radice(double radicando, double indice) {
        if (indice == 0) {
            throw new IllegalArgumentException("Indice non può essere zero");
        }
        return Math.pow(radicando, 1.0 / indice);
    }
}
