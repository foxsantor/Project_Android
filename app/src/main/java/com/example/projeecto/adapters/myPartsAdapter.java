package com.example.projeecto.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projeecto.MainActivity;
import com.example.projeecto.R;
import com.example.projeecto.entities.Parts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class myPartsAdapter extends RecyclerView.Adapter <myPartsAdapter.myPartsViewHolder> {

private Context mContext;
private ArrayList<Parts> myparts;
private OnClickedListner lisnter;
private RequestQueue requestQueue;


    public interface  OnClickedListner{

        void onClicked(int pos);
        void downvVote2(View v, int position);
    }

    public void setOnItemClicked(OnClickedListner listner){

        this.lisnter=listner;
    }


@Override
public myPartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.mypart_cardview, parent, false);
        return new myPartsViewHolder(root,lisnter);
        }

@Override
public void onBindViewHolder(myPartsViewHolder holder, final int position) {
        final Parts currentItem=  myparts.get(position);
        //String imageUrl = currentItem.getImageUrl();
        String name = currentItem.getName();
        String type = currentItem.getType();
        //image Reader
        byte[] image = currentItem.getImage();
        //Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

        //Color checkers + other fields inputs

        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        holder.mTextViewName.setText(type);
    holder.mTextViewType.setText(MainActivity.capitalize(name));

        //holder.mImageView.setImageBitmap(bmp);


        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        }

@Override
public int getItemCount() {
        return myparts.size();
        }

    public class myPartsViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;
    public TextView mTextViewName,mTextViewType;
    public FloatingActionButton disable;

    public myPartsViewHolder(View itemView,final OnClickedListner listner) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.imagemypart);
        mTextViewName = itemView.findViewById(R.id.namemypart);
        mTextViewType=itemView.findViewById(R.id.mypartType);
        disable = itemView.findViewById(R.id.floatingActionButton);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lisnter != null){
                    int postion = getAdapterPosition();
                    if(postion !=RecyclerView.NO_POSITION){
                        {
                            lisnter.onClicked(postion);

                        }}
                }}
        });

        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lisnter.downvVote2(v, getAdapterPosition());
                notifyItemChanged(getAdapterPosition());
            }
        });


    }

}
    public myPartsAdapter(ArrayList<Parts> list){

        this.myparts=list;
    }

    public myPartsAdapter(Context context, ArrayList<Parts> sells, myPartsAdapter.OnClickedListner listener) {
        mContext = context;
        myparts = sells;
        this.lisnter=listener;
    }







}
