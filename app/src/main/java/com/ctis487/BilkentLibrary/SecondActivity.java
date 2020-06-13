package com.ctis487.BilkentLibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private GestureDetectorCompat mDetector;
    TextView tvName,tvAuthor,textView;
    ImageView charImg;
    Intent i;
    String name,author;
    int imgID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_second);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvName=findViewById(R.id.tvName);
        tvAuthor=findViewById(R.id.tvAuthor);
        charImg=findViewById(R.id.charImg);
        textView=findViewById(R.id.textView);

        i=getIntent();

        name=i.getStringExtra("name");

        author=i.getStringExtra("author");

        tvName.setText(name);
        textView.setText(author);

        MyGestureListener mgl = new MyGestureListener();
        mDetector = new GestureDetectorCompat(this, mgl);

        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mDetector.onTouchEvent(event);
            }
        });



        if(name.equals("A Study In Scarlet")){
            tvAuthor.setText(R.string.asis);
            charImg.setImageResource(R.drawable.astudyinscarlet);
        }
        else if(name.equals("The Lord Of The Rings: The Fellowship of The Ring")){
            tvAuthor.setText(R.string.lotr);
            charImg.setImageResource(R.drawable.lotr);
        }
        else if(name.equals("The Hunger Games")){
            tvAuthor.setText(R.string.thg);
            charImg.setImageResource(R.drawable.hungergames);
        }
        else if(name.equals("Crime and Punishment")){
            tvAuthor.setText(R.string.cap);
            charImg.setImageResource(R.drawable.crimeandpunishment);
        }
        else if(name.equals("War and Peace")){
            tvAuthor.setText(R.string.wap);
            charImg.setImageResource(R.drawable.warandpeace);
        }



    }


    public void onClick(View view) {
        final Intent intent=new Intent(this,MainActivity.class);

        startActivity(intent);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {

            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {

            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event) {


            if(name.equals("A Study In Scarlet")){
               makeAndShowDialogBox(author,R.drawable.astudyinscarlet,R.string.asis_a);
            }
            else if(name.equals("The Lord Of The Rings: The Fellowship of The Ring")){
                makeAndShowDialogBox(author,R.drawable.lotr,R.string.lotr_a);
            }
            else if(name.equals("The Hunger Games")){
                makeAndShowDialogBox(author,R.drawable.hungergames,R.string.thg_a);
            }
            else if(name.equals("Crime and Punishment")){
                makeAndShowDialogBox(author,R.drawable.crimeandpunishment,R.string.cap_a);;
            }
            else if(name.equals("War and Peace")){
                makeAndShowDialogBox(author ,R.drawable.warandpeace,R.string.wap_a);
            }

            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);


        }
    }

    private void makeAndShowDialogBox(String hname, int logo, int hWords) {
        AlertDialog.Builder mDialogBox = new AlertDialog.Builder(this);
        // set message, title, and icon
        mDialogBox.setTitle(hname);
        mDialogBox.setMessage(hWords);
        mDialogBox.setIcon(logo);

        // Set three option buttons
        mDialogBox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // whatever should be done when answering "YES" goes
                        // here
                    }
                });
        mDialogBox.create();
        mDialogBox.show();
    }

}
