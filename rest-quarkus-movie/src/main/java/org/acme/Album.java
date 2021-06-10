package org.acme;

import io.vertx.core.http.HttpConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Album {
     private static HttpURLConnection connection;
     public static void main(String[] args){

         //We get the response as an input stream
         BufferedReader reader;
         String line;//Reads every line
         StringBuffer responseContent = new StringBuffer(); //Append each line and build response content

         try {
             URL url = new URL("http://jsonplaceholder.typicode.com/albums");
             //open connection
             connection = (HttpURLConnection) url.openConnection();

             //request setup
             connection.setRequestMethod("GET");
             connection.setConnectTimeout(5000);
             connection.setReadTimeout(5000);

             int status = connection.getResponseCode();

             if (status>299){
                 reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                 while((line = reader.readLine()) != null){
                     responseContent.append(line);
                 }
                 reader.close();
             }else{
                 reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                 while((line = reader.readLine()) != null){
                     responseContent.append(line);
                 }
                 reader.close();
             }
         } catch (MalformedURLException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }finally{
             connection.disconnect();
         }
     }
}
