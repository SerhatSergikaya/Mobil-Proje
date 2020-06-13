package com.ctis487.BilkentLibrary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ParseBookJSONService extends IntentService{
    JSONArray albums;
    ArrayList<Book> albumList;
    private static final String TAG_ALBUMS = "albums";
    private static final String TAG_NAME = "albumName";
    private static final String TAG_ARTISTNAME = "artistName";
    private static final String TAG_TYPE = "albumType";
    private static final String TAG_IMG = "imgName";

    public ParseBookJSONService(){
        super("MyServiceAlbum");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        albumList = new  ArrayList<Book>();

        String filename = intent.getStringExtra("filename");

        String jsonfileContent = loadFileFromAsset(filename);

        Log.d("Response: ", "> " + jsonfileContent);

        if (jsonfileContent != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonfileContent);

                // Getting JSON Array node
                albums = jsonObj.getJSONArray(TAG_ALBUMS);

                // looping through all Contacts
                for (int i = 0; i < albums.length(); i++) {
                    JSONObject album = albums.getJSONObject(i);

                    String name = album.getString(TAG_NAME);
                    String artist = album.getString(TAG_ARTISTNAME);
                    String type = album.getString(TAG_TYPE);
                    String img = album.getString(TAG_IMG);

                    Book a = new Book(name, artist, type, img);
                    albumList.add(a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent broascastIntent = new Intent();
        Bundle b = new Bundle();
        b.putParcelableArrayList("albumlist", albumList);
        broascastIntent.putExtras(b);

        broascastIntent.setAction("ALBUM_JSON_PARSE_ACTION");

        getBaseContext().sendBroadcast(broascastIntent);

        Log.d("Service",":servis END" );


    }
    private String loadFileFromAsset(String fileName) {
        String jsonfileContent = null;
        try {

            InputStream is = getBaseContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            jsonfileContent = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return jsonfileContent;
    }

}
