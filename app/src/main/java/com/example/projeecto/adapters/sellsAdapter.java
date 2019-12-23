package com.example.projeecto.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeecto.R;
import com.example.projeecto.entities.Parts;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class sellsAdapter extends RecyclerView.Adapter<sellsAdapter.SellsViewHolder> {

    private Context mContext;
    private ArrayList<Parts> Sells;
    private DetailsAdapterListener onClickListener;
    private ConstraintLayout other1,other2,other3;
    private TextView other1_v,other2_v,other3_v;



    public sellsAdapter(Context context, ArrayList<Parts> sells, DetailsAdapterListener listener) {
        mContext = context;
        Sells = sells;
        this.onClickListener=listener;
    }

    @Override
    public SellsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.parts_view, parent, false);
        return new SellsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(SellsViewHolder holder, int position) {
        Parts currentItem = Sells.get(position);

        //String imageUrl = currentItem.getImageUrl();
        String owner = currentItem.getOwner();
        String name = currentItem.getName();
        String other1 = currentItem.getOther1();
        Float price = currentItem.getPrice();
        String other2 = currentItem.getOther2();
        String other3 = currentItem.getOther3();
        String type = currentItem.getType();
        String vues = String.valueOf(currentItem.getVues());
        //image Reader
        byte[] image = currentItem.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);

        //Color checkers + other fields inputs
        if(other1.isEmpty())
        {
        this.other1.setVisibility(View.GONE);
        }else
        {
            if(other1.contains(",")){
            String[] other1TextView;
            other1TextView = other1.split(",");
            other1_v.setText(other1TextView[0]);
            holder.mTextViewOther1.setText(other1TextView[1]);}
            else {
            holder.mTextViewOther1.setText(other1);
        }
        }
        if(other2.isEmpty())
        {
            this.other2.setVisibility(View.GONE);
        }else
        {
            if(other2.contains(",")){
            String[] other2TextView;
            other2TextView = other2.split(",");
            other2_v.setText(other2TextView[0]);
            holder.mTextViewOther2.setText(other2TextView[1]);}
            else {
            holder.mTextViewOther2.setText(other2);
                }
        }
        if(other3.isEmpty())
        {
            this.other3.setVisibility(View.GONE);
        }else
        {
            if(other3.contains(",")){
            String[] other3TextView;
            other3TextView = other3.split(",");
            other3_v.setText(other3TextView[0]);
            holder.mTextViewOther3.setText(other3TextView[1]);}
            else {
                holder.mTextViewOther3.setText(other3);
            }
        }

        if(this.other1.getVisibility() == View.GONE )
        {
            this.other2.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#FFFFFF")));
            this.other3.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#E6E9F0")));
        }
        if(this.other2.getVisibility() == View.GONE  )
        {
            this.other3.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#E6E9F0")));
        }
        if(this.other2.getVisibility() == View.GONE  && this.other1.getVisibility() == View.GONE )
        {
            this.other3.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#FFFFFF")));
        }

        holder.mTextViewName.setText(name);
        holder.mTextViewPrice.setText(priceFormInflater(price)+" TND");
        holder.mTextViewType.setText(type);
        holder.mImageView.setImageBitmap(bmp);
        holder.mTextViewOwner.setText(owner);
        holder.views.setText(vues);

        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return Sells.size();
    }

    public class SellsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewName,mTextViewPrice,mTextViewOwner,mTextViewOther1,mTextViewOther2,mTextViewOther3,mTextViewType,views;
        public MaterialButton viewAlone;


        public SellsViewHolder(View itemView) {
            super(itemView);

            other1 = itemView.findViewById(R.id.other1);
            other2 = itemView.findViewById(R.id.other2);
            other3 = itemView.findViewById(R.id.other3);
            other1_v = itemView.findViewById(R.id.other1v);
            other2_v = itemView.findViewById(R.id.other2v);
            other3_v =  itemView.findViewById(R.id.otherv);
            viewAlone = itemView.findViewById(R.id.seealone);
            mImageView = itemView.findViewById(R.id.imageView3);
            mTextViewName = itemView.findViewById(R.id.Name);
            mTextViewPrice = itemView.findViewById(R.id.Price);
            mTextViewOwner = itemView.findViewById(R.id.ownert);
            mTextViewType = itemView.findViewById(R.id.textView19);
            mTextViewOther1 = itemView.findViewById(R.id.other1t);
            mTextViewOther2 = itemView.findViewById(R.id.other2t);
            mTextViewOther3 = itemView.findViewById(R.id.other3t);
            views = itemView.findViewById(R.id.textView35);

            viewAlone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    onClickListener.DetailOnClick(v, getAdapterPosition());
                }
            });

        }

    }
    public interface DetailsAdapterListener {

        void DetailOnClick(View v, int position);

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
