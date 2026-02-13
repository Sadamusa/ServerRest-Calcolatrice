/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package serverrest.V3;

import serverrest.V2.GetHandlerV2;
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
            if (!parametri.containsKey("unita1")
                    || !parametri.containsKey("unita2")
                    || !parametri.containsKey("valore")) {
                inviaErrore(exchange, 400,
                        "Parametri mancanti. Necessari: operando1, operando2, operatore");
                return;
            }

            // Parsing dei valori
            String unita1 = parametri.get("unita1");
            String unita2 = parametri.get("unita2");            
            Double valore = Double.parseDouble(parametri.get("valore"));

            // Esegue il calcolo con la versione V3
            ConvertitoreService.calcola(unita1, unita2, valore);

            // Crea l'oggetto risposta V3 (con timestamp, versione_api e request_id automatici)
            OperazioneResponseV3 response = new OperazioneResponseV3(
                    unita1, unita2, valore);

            String jsonRisposta = gson.toJson(response);
            
            // Invia risposta con headers aggiuntivi per V3
            inviaRispostaV3(exchange, 200, jsonRisposta, response.getRequestID());

        } catch (NumberFormatException e) {
            inviaErrore(exchange, 400, "Operandi non validi. Devono essere numeri");
        } catch (IllegalArgumentException e) {
            inviaErrore(exchange, 400, e.getMessage());
        } catch (Exception e) {
            inviaErrore(exchange, 500, "Errore interno del server: " + e.getMessage());
        }
    }

    /**
     * Invia una risposta di successo con headers aggiuntivi per API v3
     */
    private void inviaRispostaV3(HttpExchange exchange, int codice, String jsonRisposta, String requestId)
            throws IOException {

        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("API-Version", "3.0");
        exchange.getResponseHeaders().set("X-Request-ID", requestId);

        byte[] bytes = jsonRisposta.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(codice, bytes.length);

        OutputStream os = exchange.getResponseBody();
        os.write(bytes);
        os.close();
    }
}