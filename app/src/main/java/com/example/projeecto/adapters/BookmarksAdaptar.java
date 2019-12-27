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

import com.bumptech.glide.Glide;
import com.example.projeecto.R;
import com.example.projeecto.entities.Parts;


public class BookmarksAdaptar extends ListAdapter<Parts,BookmarksAdaptar.BookmarksHolder> {

    private OnItemClickLisnter lisnter;
    private Context mContext;

    public BookmarksAdaptar(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Parts> DIFF_CALLBACK = new DiffUtil.ItemCallback<Parts>() {
        @Override
        public boolean areItemsTheSame(@NonNull Parts oldItem, @NonNull Parts newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Parts oldItem, @NonNull Parts newItem) {
            return  oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getType().equals(newItem.getType())&&
                    oldItem.getPrice().equals(newItem.getPrice());
        }
    };

    @NonNull
    @Override
    public BookmarksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deal_view, parent, false);
        return new BookmarksHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksHolder holder, int position) {

        Parts currentItem = getItem(position);
        //String imageUrl = currentItem.getImageUrl();
        String name = currentItem.getName();
        String type = currentItem.getType();
        Float price = currentItem.getPrice();
        String pricess = priceFormInflater(price);
        byte[] image = currentItem.getImage();
        //Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        Glide.with(mContext).asBitmap().load(image).into(holder.mImageView);
        //Color checkers + other fields inputs
        holder.mTextViewName.setText(name);
        holder.mTextViewType.setText(type);
        holder.mTextViewPrice.setText("Price: "+price+" TND");
        //holder.mImageView.setImageBitmap(bmp);
        // for int types
    }


    public Parts getPartAt(int postion) {
        return getItem(postion);
    }

    class BookmarksHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextViewName,mTextViewType,mTextViewPrice;

        public BookmarksHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.imagemypart);
            mTextViewName = itemView.findViewById(R.id.namedeal);
            mTextViewPrice = itemView.findViewById(R.id.price);
            mTextViewType=itemView.findViewById(R.id.mypartType);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (lisnter != null && position != RecyclerView.NO_POSITION)
                        lisnter.OnItemClick(getItem(position));
                }
            });
        }

    }

    public interface OnItemClickLisnter {
        void OnItemClick(Parts part);
    }

    public void setOnIteemClickListener(OnItemClickLisnter listener) {
        this.lisnter = listener;
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
