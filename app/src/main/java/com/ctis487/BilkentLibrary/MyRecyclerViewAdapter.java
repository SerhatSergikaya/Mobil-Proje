package com.ctis487.BilkentLibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {


    Context context;
    ArrayList<Book> data;
    MainActivity ma;
    DatabaseHelper dbHelper;

    public MyRecyclerViewAdapter(Context context, ArrayList<Book> data) {
        this.context = context;
        this.data =data;
        ma = (MainActivity)context;
        dbHelper = new DatabaseHelper(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //BIND DATA
        final Book album = data.get(position);
        if(BookDB.isAlbumExist(dbHelper, album.getName()))
            holder.itemLinarLayout.setBackgroundColor(Color.BLUE);

        holder.itemAlbumAname.setText(album.getName());
        holder.itemAlbumArtist.setText(album.getArtist());





    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Intent intent=new Intent(context,SecondActivity.class);
        LinearLayout itemLinarLayout;
        TextView itemAlbumAname;
        TextView itemAlbumArtist;

        MyViewHolder(View viewItem){
            super(viewItem);
            itemLinarLayout = viewItem.findViewById(R.id.itemLinearLayout);
            itemAlbumAname = viewItem.findViewById(R.id.lvAlbumAname);
            itemAlbumArtist = viewItem.findViewById(R.id.lvAlbumArtist);

            viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Book album = data.get(getLayoutPosition());
                    ma.displayDialog(getLayoutPosition(), album );
                    return false;
                }
            });

            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Book album = data.get(getLayoutPosition());
                    String bname =album.getName();
                    String author=album.getArtist();
                    intent.putExtra("name",bname);
                    intent.putExtra("author",author);

                    context.startActivity(intent);
                }
            });
        }
    }


}
