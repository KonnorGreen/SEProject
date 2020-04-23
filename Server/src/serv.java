package softwareengineering;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import projectdb.ProjectDB;
import static projectdb.ProjectDB.addItem;
import static projectdb.ProjectDB.createProduct;
import static projectdb.ProjectDB.createSettings;
import static projectdb.ProjectDB.createTransaction;
import static projectdb.ProjectDB.dailyTransactionReport;
import static projectdb.ProjectDB.saveTransaction;
import static projectdb.ProjectDB.setSubtotal;
import static projectdb.ProjectDB.setTotal;

public class serv {
    private static final Gson gson = new Gson();
    private static ProjectDB database;
    
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
    
    static class fileServer implements HttpHandler {
        private String fileToServe;
        public fileServer(String file) {
            fileToServe = file;
        }
        public void handle(HttpExchange t) throws IOException {
            byte[] response = fileToServe.getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    public static void createContextForFileServing(HttpServer server, String fileName) {
        InputStream in = SoftwareEngineering.class.getResourceAsStream(fileName);
        String file = new BufferedReader(new InputStreamReader(in)).lines().collect(Collectors.joining("\n"));
        server.createContext("/"+fileName, new fileServer(file));
    }
    
    public static void main(String[] args) throws Exception {
        createSettings("admin");
        
        for(int i=0;i<5;i++) {
            createProduct();
        }
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        
        // API calls
        server.createContext("/ping", new pingHandler());
        server.createContext("/sendsms", new SMSHandler());
        server.createContext("/finalizetransaction", new finalizeTransactionHandler());
        server.createContext("/getrewardpoints", new getRewardPointsHandler());
        server.createContext("/managerewards", new manageRewardsHandler());
        server.createContext("/getsettings", new getSettingsHandler());
        server.createContext("/getreports", new getReportsHandler());
        
        // Webpage requests
        createContextForFileServing(server, "index.html");
        createContextForFileServing(server, "adminPanel.html");
        createContextForFileServing(server, "merchantPanel.html");
        createContextForFileServing(server, "shoppingCart.html");
        createContextForFileServing(server, "stylesheet.css");
        createContextForFileServing(server, "reports.html");
        
        server.setExecutor(null); // creates a default executor
        server.start();
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
    
    // Must be POST
    static class finalizeTransactionHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = "Pong!".getBytes();
            //byte[] response = indexHTML.getBytes();
            
            System.out.println(t.getRequestURI().getQuery());
            
            StringBuilder sb = new StringBuilder();
            InputStream argumentStream = t.getRequestBody();
            int i;
            while((i = argumentStream.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println("bbb: " + sb.toString());
            
            transactionData data = gson.fromJson(sb.toString(),transactionData.class);
            System.out.println("Shopping cart " + data.shoppingCartUID + " is being processed..");
            
            try {
                int transactionID = createTransaction();
            
                BigDecimal subTotal = new BigDecimal("0.00");
                BigDecimal total = new BigDecimal("0.00");
                BigDecimal totalDiscountPercentage = new BigDecimal("0.00");

                for(shoppingCartData s : data.shoppingCart) {
                    //subTotal = subTotal + (s.amount * s.price);
                    subTotal = subTotal.add(new BigDecimal(s.amount).multiply(new BigDecimal(s.price)));
                    addItem(transactionID, s.UID, (int) Math.ceil(s.amount));
                    System.out.println(s.displayName);
                }

                total = total.add(subTotal);
                setSubtotal(transactionID,subTotal.doubleValue());

                for(discountCartData s : data.discountCart) {
                    //totalDiscountPercentage = totalDiscountPercentage + s.discountPercentage;
                    totalDiscountPercentage = totalDiscountPercentage.add(new BigDecimal(s.discountPercentage));
                    System.out.println(s.discountName);
                }

                //total = total - (total * (totalDiscountPercentage/100));
                BigDecimal discountSavings = total.multiply(totalDiscountPercentage.divide(new BigDecimal(100)));
                total = total.subtract(discountSavings);
                total = total.setScale(2, RoundingMode.FLOOR);
                discountSavings = discountSavings.setScale(2, RoundingMode.CEILING);

                setTotal(transactionID,total.doubleValue());
                saveTransaction();

                System.out.println("Shopping cart " + data.shoppingCartUID + " has been processed! Total recieved was $" + total + ", amount saved through discounts was $" + discountSavings);
            } catch(Exception e) { e.printStackTrace(); }
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class getRewardPointsHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = "Pong!".getBytes();
            //byte[] response = indexHTML.getBytes();
            
            /*Map<String,String> arguments = queryToMap(t.getRequestURI().getQuery());
            if(arguments.containsKey("phoneNumber")) {
                int phone = Integer.getInteger(arguments.get("phoneNumber"));
                rewards rewardAccount = database.enroll(phone);
                double points = rewardAccount.getBalance();
                response = String.valueOf(points).getBytes();
                System.out.println("Phone number "+phone+" has "+points+" reward points");
            } else {
                System.out.println("Malformed request!");
                response = "Malformed request!".getBytes();
            }*/
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class manageRewardsHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {         
            byte [] response = "Pong!".getBytes();

            StringBuilder sb = new StringBuilder();
            InputStream argumentStream = t.getRequestBody();
            int i;
            while((i = argumentStream.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println("ccc: " + sb.toString());
            
            try {
                manageRewardData data = gson.fromJson(sb.toString(), manageRewardData.class);
                database.setPointsPerDollar("admin",data.pointsPerDollar);
                database.setDollarsPerPoint("admin",data.dollarsPerPoint);
            } catch(Exception e) {
                e.printStackTrace();
            }
            //setPointsPerDollar("admin",100);
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
            
            System.out.println("HELP");
        }
    }
    
    static class getSettingsHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {         
            byte [] response = gson.toJson(new settingsData()).getBytes();
            
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
    
    static class getReportsHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            byte [] response = gson.toJson(new reportsData()).getBytes();
            System.out.println(gson.toJson(new reportsData()));
                
            t.sendResponseHeaders(200, response.length);
            OutputStream os = t.getResponseBody();
            
            os.write(response);
            os.close();
        }
    }
}