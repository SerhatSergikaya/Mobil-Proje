package com.ctis487.BilkentLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    IntentFilter mIntentFilter;
    ArrayList<Book> albumList;
    RecyclerView recyclerViewAlbums;
    ToggleButton toggleButton;
    Dialog customDialog;
    TextView tvNumberOfAlbumstv;
    //For dialog
    TextView tvName, tvArtist;
    Button btnSave, btnCancel;
    int selectedPoistion=-1;
    DatabaseHelper databaseHelper;
    Book a =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        databaseHelper= new DatabaseHelper(this);

        tvNumberOfAlbumstv = findViewById(R.id.tvNumberOfAlbums);
        toggleButton = findViewById(R.id.toggleButton);
        recyclerViewAlbums = findViewById(R.id.recyclerViewAlbums);


        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerViewAlbums.setLayoutManager(lm);

        Intent intent = new Intent(this, ParseBookJSONService.class);
        intent.putExtra("filename", "books.json");
        startService(intent);

        Log.d("Burda",":intentFilter" );
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("ALBUM_JSON_PARSE_ACTION");


        // Register the receiver
        registerReceiver(mIntentReceiver, mIntentFilter);

        Log.d("Burda",":registered" );
        toggleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButton.isChecked())
                {
                    ArrayList<Book> albumsinAlbumTable = BookDB.getAllAlbums(databaseHelper);
                    MyRecyclerViewAdapter adpter = new MyRecyclerViewAdapter(MainActivity.this, albumsinAlbumTable);
                    recyclerViewAlbums.setAdapter(adpter);
                    tvNumberOfAlbumstv.setText("Selected books: "+albumsinAlbumTable.size());
                }
                else {
                    if(albumList !=null){
                        MyRecyclerViewAdapter adpter = new MyRecyclerViewAdapter(MainActivity.this, albumList);
                        recyclerViewAlbums.setAdapter(adpter);
                        tvNumberOfAlbumstv.setText("Available books: "+albumList.size());
                    }
                }
            }
        });
    }

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Storing the received data into a Bundle

            Log.d("Service","Braodcast mesage taken" );
            Bundle b = intent.getExtras();
            albumList = b.getParcelableArrayList("albumlist");
            fillListView();
        }
    };

    void fillListView() {
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, albumList);
        recyclerViewAlbums.setAdapter(adapter);
        tvNumberOfAlbumstv.setText("Available books: "+albumList.size());
    }
    public void displayDialog(int selectedPoistion, Book a ){
        //Book selectedalbum = albumList.get(selectedPoistion);
        final Book selectedalbum = a;

        customDialog = new Dialog(this);
        customDialog.setContentView(R.layout.dialog);
        customDialog.setTitle("Custom Dialog");

        tvName = (TextView) customDialog.findViewById(R.id.tvName);
        tvArtist = (TextView) customDialog.findViewById(R.id.tvArtist);

        tvName.setText(a.getName());
        tvArtist.setText(a.getArtist());

        btnSave = (Button) customDialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new InsertAsynTask().execute(selectedalbum);
                customDialog.dismiss();
            }
        });

        btnCancel = (Button) customDialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        customDialog.show();
    }

    class InsertAsynTask extends AsyncTask<Book, Void, String>{

        @Override
        protected String doInBackground(Book... albums) {
            Book selectedALbum = albums[0];
            BookDB.insertAlbum(databaseHelper, selectedALbum.getName(), selectedALbum.getArtist(), selectedALbum.getType(), selectedALbum.getImgName());
            return null;
        }

        @Override
        protected void onPostExecute(String msg) {
            super.onPostExecute(msg);
            Toast.makeText(MainActivity.this, "Book added", Toast.LENGTH_LONG).show();
            ArrayList<Book> albumsinAlbumTable = BookDB.getAllAlbums(databaseHelper);
            MyRecyclerViewAdapter adpter = new MyRecyclerViewAdapter(MainActivity.this, albumsinAlbumTable);
            recyclerViewAlbums.setAdapter(adpter);
            tvNumberOfAlbumstv.setText("Selected books: "+albumsinAlbumTable.size());
        }
    }
}

