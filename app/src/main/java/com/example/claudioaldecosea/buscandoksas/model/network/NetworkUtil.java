package com.example.claudioaldecosea.buscandoksas.model.network;

import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import com.example.claudioaldecosea.buscandoksas.domain.House;
import com.example.claudioaldecosea.buscandoksas.domain.Houses;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtil {

    private HttpURLConnection urlConnection = null;
    private BufferedReader reader = null;
    private String BASE_URL;

    public NetworkUtil(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public ArrayList<House> getHouses(String postData) {
        Houses houses = new Houses();

        try {
            //agregar los parametros a la URL
            String finalURL = String.format(BASE_URL);
            Uri builtURI = Uri.parse(finalURL).buildUpon().build();
            URL requestURL = new URL(builtURI.toString());

            //abro la conexion
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            //en este caso el metodo es POST
            urlConnection.setRequestMethod("POST");
            String authorization = "123456";
            urlConnection.setRequestProperty("Authorization", authorization);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoOutput(true);
            urlConnection.getOutputStream().write(postData.getBytes());
            urlConnection.connect();

            //creo un BufferedReader, desde el InputStream obtenido
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            //reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();
            for (int c; (c = reader.read()) >= 0;)
                sb.append((char)c);

            String response = sb.toString();

            Gson gson = new Gson();

            houses = gson.fromJson(response, Houses.class);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return houses.getResponse();
    }
}
