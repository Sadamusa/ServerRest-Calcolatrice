/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * @author delfo
 */
public class GetHandlerV3 extends GetHandlerV2 implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // Verifica che sia una richiesta GET
        if (!exchange.getRequestMethod().equalsIgnoreCase("GET")) {
            inviaErrore(exchange, 405, "Metodo non consentito. Usa GET");
            return;
        }
        
        try {
            // Estrae i parametri dalla query string
            Map<String, String> parametri = estraiParametri(exchange.getRequestURI().getQuery());

            // Validazione parametri
            if (!parametri.containsKey("operando1")
                    || !parametri.containsKey("operando2")
                    || !parametri.containsKey("operatore")) {
                inviaErrore(exchange, 400,
                        "Parametri mancanti. Necessari: operando1, operando2, operatore");
                return;
            }

            // Parsing dei valori
            double operando1 = Double.parseDouble(parametri.get("operando1"));
            double operando2 = Double.parseDouble(parametri.get("operando2"));
            String operatore = parametri.get("operatore");

            // Esegue il calcolo con la versione V2
            double risultato = CalcolatriceServiceV2.calcola(operando1, operando2, operatore);

            // Crea l'oggetto risposta V2 (con timestamp, versione_api e request_id automatici)
            OperazioneResponseV2 response = new OperazioneResponseV2(
                    operando1, operando2, operatore, risultato
            );

            String jsonRisposta = gson.toJson(response);
            
            // Invia risposta con headers aggiuntivi per V2
            inviaRispostaV2(exchange, 200, jsonRisposta, response.getRequestID());

        } catch (NumberFormatException e) {
            inviaErrore(exchange, 400, "Operandi non validi. Devono essere numeri");
        } catch (IllegalArgumentException e) {
            inviaErrore(exchange, 400, e.getMessage());
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore interno del server: " + e.getMessage());
        }
    }

    /**
     * Invia una risposta di successo con headers aggiuntivi per API v2
     */
    private void inviaRispostaV2(HttpExchange exchange, int codice, String jsonRisposta, String requestId)
            throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("API-Version", "2.0");
        exchange.getResponseHeaders().set("X-Request-ID", requestId);

        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}