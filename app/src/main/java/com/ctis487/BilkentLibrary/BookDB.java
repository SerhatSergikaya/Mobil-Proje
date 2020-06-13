package com.ctis487.BilkentLibrary;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class BookDB {

    public static final String TABLE_NAME ="album";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_ARTIST = "artist";
    public static final String FIELD_TYPE = "type";
    public static final String FIELD_IMG = "imgName";

    public static final String CREATE_TABLE_SQL = "CREATE TABLE "+TABLE_NAME+" ("+FIELD_ID +" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "+FIELD_NAME+" text, "+FIELD_ARTIST+" text, "+FIELD_TYPE+" text, "+FIELD_IMG+" BLOB );";
    public static final String DROP_TABLE_SQL = "DROP TABLE if exists "+TABLE_NAME;

    public static ArrayList<Book> getAllAlbums(DatabaseHelper db){

        Cursor cursor = db.getAllRecords(TABLE_NAME, null);
        //Cursor cursor  db.getAllRecordsMethod2("SELECT * FROM "+TABLE_NAME, null)
        ArrayList<Book> data=new ArrayList<>();
        Book aalbum = null;
        while (cursor.moveToNext()) {
           int id = cursor.getInt(0);
           String name = cursor.getString(1);
           String artist = cursor.getString(2);
            String type = cursor.getString(3);
           String imgName= cursor.getString(4);

           aalbum= new Book(id, name, artist, type, imgName);
           data.add(aalbum);
        }
        return data;
    }
    public static ArrayList<Book> findAlbum(DatabaseHelper db, String key){

        String where = FIELD_NAME+" like '%"+key+"%'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME,null, where);
        ArrayList<Book> data=new ArrayList<>();
        Book aalbum = null;
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String artist = cursor.getString(2);
            String type = cursor.getString(3);
            String imgName = cursor.getString(4);

            aalbum= new Book(id, name, artist, type, imgName);
            data.add(aalbum);
        }
        return data;
    }
    public static boolean isAlbumExist(DatabaseHelper db, String albumName){
        String where = FIELD_NAME +" = '"+albumName+"'";
        Cursor cursor = db.getSomeRecords(TABLE_NAME,null, where);
        if(cursor.moveToNext()){
            return true;
        }
        return  false;
    }
    public static long insertAlbum(DatabaseHelper db, String name, String artist, String type, String imgName){
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_ARTIST, artist);
        contentValues.put(FIELD_TYPE, type);
        contentValues.put(FIELD_IMG, imgName);
        long res = db.insert(TABLE_NAME,contentValues);
        return res;
    }
    public static boolean updateAlbum(DatabaseHelper db, int id, String name, String artist, String type, String imgName){
        ContentValues contentValues = new ContentValues( );
        contentValues.put(FIELD_NAME, name);
        contentValues.put(FIELD_ARTIST, artist);
        contentValues.put(FIELD_TYPE, type);
        contentValues.put(FIELD_IMG, imgName);
        String where = FIELD_ID +" = "+id;
        boolean res = db.update(TABLE_NAME,contentValues,where);

        return res;
    }
    public static boolean deleteAlbum(DatabaseHelper db, int id){
        String where = FIELD_ID +" = "+ id;
        boolean res = db.delete(TABLE_NAME,where);
        return res;
    }
}
