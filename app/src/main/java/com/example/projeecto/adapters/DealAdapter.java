package com.example.projeecto.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeecto.MainActivity;
import com.example.projeecto.R;
import com.example.projeecto.entities.Parts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DealAdapter extends RecyclerView.Adapter <DealAdapter.myPartsViewHolder> {

    private Context mContext;
    private ArrayList<Parts> myparts;
    private OnClickedListner lisnter;
    private String searchText;

    public interface  OnClickedListner{

        void onClicked(int pos);
    }

    public void setOnItemClicked(OnClickedListner listner){

        this.lisnter=listner;
    }


    @Override
    public myPartsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.deal_view, parent, false);
        return new myPartsViewHolder(root,lisnter);
    }

    @Override
    public void onBindViewHolder(myPartsViewHolder holder, int position) {
        Parts currentItem=  myparts.get(position);
        //String imageUrl = currentItem.getImageUrl();
        String name = currentItem.getName();
        String type = currentItem.getType();
        Float price = currentItem.getPrice();
        String pricess = priceFormInflater(price);


        String htmlText1= type.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");
        String htmlText2 = name.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");
        String htmlText3 = pricess.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");






// Only searchText would be displayed in a different color.

        //image Reader
        byte[] image = currentItem.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

        //Color checkers + other fields inputs


        holder.mTextViewName.setText(Html.fromHtml(htmlText2));
        holder.mTextViewType.setText(Html.fromHtml(htmlText1));
        holder.mTextViewPrice.setText("Price: "+Html.fromHtml(htmlText3)+" TND");
        holder.mImageView.setImageBitmap(bmp);


        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return myparts.size();
    }

    public class myPartsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewName,mTextViewType,mTextViewPrice;

        public myPartsViewHolder(View itemView,final OnClickedListner listner) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imagemypart);
            mTextViewName = itemView.findViewById(R.id.namedeal);
            mTextViewPrice = itemView.findViewById(R.id.price);
            mTextViewType=itemView.findViewById(R.id.mypartType);

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

        }

    }
    public DealAdapter(ArrayList<Parts> list){

        this.myparts=list;
    }

    public DealAdapter(Context context, ArrayList<Parts> sells, DealAdapter.OnClickedListner listener,String text) {
        mContext = context;
        myparts = sells;
        this.lisnter=listener;
        this.searchText = text;
    }

    public static String priceFormInflater(Float price)
    {


        if (price == Math.floor(price)) {
            return String.format("%.0f", price);
        } else {
            return Float.toString(price);
        }


    }





}
