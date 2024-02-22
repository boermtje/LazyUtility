package net.botwithus.api.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class Internet {

    /**
     * Sends a GET request to the specified address and returns the response as a String.
     *
     * @param address the ip address or domain name to perform a GET request on
     * @return the response from the GET request as a String
     * @throws IOException if an exception is thrown while establishing the connection or performing the GET request
     */
    public static String get(String address) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = URI.create(address).toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
        } finally {
            conn.disconnect();
        }
        return result.toString();
    }
}
