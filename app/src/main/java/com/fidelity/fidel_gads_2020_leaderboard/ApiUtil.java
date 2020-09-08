package com.fidelity.fidel_gads_2020_leaderboard;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil(){}

    public static final String BASE_URL_URL =
            "https://gadsapi.herokuapp.com";


    public static URL buildURL(String leaders){
        URL url = null;
        Uri uri = Uri.parse(BASE_URL_URL + leaders);
        try {
            url = new URL(uri.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }


    public static String getJson(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();


        try{
            InputStream stream = conn.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            Boolean hasData = scanner.hasNext();
            if(hasData)
                return scanner.next();
            else
                return null;
        }catch (Exception e){
            Log.d("Error:", e.toString());
            return null;
        }finally {
            conn.disconnect();
        }
    }

    public static ArrayList<LeanersInfo> getLearnersFromJson(String json){
        final String NAME = "name";
        final String HOURS = "hours";
        final String SCORE = "score";
        final String COUNTRY = "country";
        final String BADGEURL = "badgeUrl";
        String score = null;

        ArrayList<LeanersInfo> learners = new ArrayList<LeanersInfo>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            int numberOfBooks = jsonArray.length();
            for(int i = 0; i < numberOfBooks; i++){
                JSONObject jsonLearners = jsonArray.getJSONObject(i);
                if(jsonLearners.has(HOURS)){
                    score = jsonLearners.getString(HOURS);
                }else {
                    score = jsonLearners.getString(SCORE);
                }

                LeanersInfo learner = new LeanersInfo(
                        i,
                        jsonLearners.getString(NAME),
                        score,
                        jsonLearners.getString(COUNTRY),
                        jsonLearners.getString(BADGEURL)

                );
                learners.add(learner);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        learners.size();
        return learners;
    }

}
