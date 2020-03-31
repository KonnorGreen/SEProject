package softwareengineering;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class serv {
    private static String indexHTML;
    private static String adminPanelHTML;
    private static String merchantPanelHTML;
    private static String shoppingCartHTML;
    private static String stylesheetCSS;
    
    // https://stackoverflow.com/questions/11640025/how-to-obtain-the-query-string-in-a-get-with-java-httpserver-httpexchange
    public static Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }
    
    public static void main(String[] args) throws Exception {
        InputStream in1 = SoftwareEngineering.class.getResourceAsStream("index.html");
        indexHTML = new BufferedReader(new InputStreamReader(in1)).lines().collect(Collectors.joining("\n"));
        
        InputStream in2 = SoftwareEngineering.class.getResourceAsStream("adminPanel.html");
        adminPanelHTML = new BufferedReader(new InputStreamReader(in2)).lines().collect(Collectors.joining("\n"));
        
        InputStream in3 = SoftwareEngineering.class.getResourceAsStream("merchantPanel.html");
        merchantPanelHTML = new BufferedReader(new InputStreamReader(in3)).lines().collect(Collectors.joining("\n"));
        
        InputStream in4 = SoftwareEngineering.class.getResourceAsStream("shoppingCart.html");
        shoppingCartHTML = new BufferedReader(new InputStreamReader(in4)).lines().collect(Collectors.joining("\n"));
        
        InputStream in5 = SoftwareEngineering.class.getResourceAsStream("stylesheet.css");
        stylesheetCSS = new BufferedReader(new InputStreamReader(in5)).lines().collect(Collectors.joining("\n"));
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        server.createContext("/ping", new pingHandler());
        server.createContext("/sendsms", new SMSHandler());
        
        server.createContext("/index.html", new indexHandler());
        server.createContext("/adminPanel.html", new adminHandler());
        server.createContext("/merchantPanel.html", new merchantHandler());
        server.createContext("/shoppingCart.html", new shoppingHandler());
        server.createContext("/stylesheet.css", new styleHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class indexHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = indexHTML.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class adminHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = adminPanelHTML.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class merchantHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = merchantPanelHTML.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class shoppingHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = shoppingCartHTML.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class styleHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte[] response = stylesheetCSS.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class pingHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = "Pong!".getBytes();
            //byte[] response = indexHTML.getBytes();
            
            StringBuilder sb = new StringBuilder();
            InputStream argumentStream = t.getRequestBody();
            int i;
            while((i = argumentStream.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println("aaa: " + sb.toString());
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class SMSHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = "Pong!".getBytes();
            //byte[] response = indexHTML.getBytes();
            
            Map<String,String> arguments = queryToMap(t.getRequestURI().getQuery());
            if(arguments.containsKey("phoneNumber") && arguments.containsKey("carrier")) {
                System.out.println("Attempting to send SMS..");
                int securityCode = textsend.sendSMS(arguments.get("phoneNumber"), arguments.get("carrier"));
                if(securityCode > 0) {
                    response = String.valueOf(securityCode).getBytes();
                }
            } else {
                System.out.println("Malformed SMS request!");
                response = "Malformed request!".getBytes();
            }
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
}