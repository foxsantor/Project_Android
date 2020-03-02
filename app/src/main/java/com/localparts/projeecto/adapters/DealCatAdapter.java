package com.localparts.projeecto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.localparts.projeecto.R;
import com.localparts.projeecto.entities.Parts;

import java.util.ArrayList;

    public class DealCatAdapter extends RecyclerView.Adapter <DealCatAdapter.myDealsCatViewHolder> {

        private Context mContext;
        private ArrayList<Parts> myparts;
        private DealCatAdapter.OnClickedListner lisnter;

        public interface  OnClickedListner{

            void onClicked(int pos);
        }

        public void setOnItemClicked(DealCatAdapter.OnClickedListner listner){

            this.lisnter=listner;
        }


        @Override
        public myDealsCatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.category_deal, parent, false);
            return new myDealsCatViewHolder(root,lisnter);
        }

        @Override
        public void onBindViewHolder(myDealsCatViewHolder holder, int position) {
            Parts currentItem=  myparts.get(position);
            //String imageUrl = currentItem.getImageUrl();
            String name = currentItem.getName();
            Float Price = currentItem.getPrice();
            //image Reader
            byte[] image = currentItem.getImage();
            //Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
            //Color checkers + other fields inputs
            holder.mTextViewName.setText(name);
            holder.mTextViewPrice.setText(priceFormInflater(Price)+ "TND");
            //holder.mImageView.setImageBitmap(bmp);


            //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
        }

        @Override
        public int getItemCount() {
            return myparts.size();
        }

        public class myDealsCatViewHolder extends RecyclerView.ViewHolder {
            public ImageView mImageView;
            public TextView mTextViewName,mTextViewPrice;

            public myDealsCatViewHolder(View itemView,final DealCatAdapter.OnClickedListner listner) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.image_cat);
                mTextViewName = itemView.findViewById(R.id.name_cat);
                mTextViewPrice=itemView.findViewById(R.id.price);

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
        public DealCatAdapter(ArrayList<Parts> list){

            this.myparts=list;
        }

        public DealCatAdapter(Context context, ArrayList<Parts> sells, DealCatAdapter.OnClickedListner listener) {
            mContext = context;
            myparts = sells;
            this.lisnter=listener;
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


