package com.localparts.projeecto.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.localparts.projeecto.R;
import com.localparts.projeecto.entities.Parts;
import com.google.android.material.button.MaterialButton;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchDealsAdapter extends RecyclerView.Adapter<SearchDealsAdapter.SearchViewHolder> {

    private Context mContext;
    private ArrayList<Parts> Sells;
    private DetailsAdapterListener onClickListener;
    private String searchText;


    public SearchDealsAdapter(Context context, ArrayList<Parts> sells, DetailsAdapterListener listener,String text) {
        mContext = context;
        Sells = sells;
        this.onClickListener=listener;
        this.searchText = text;
    }


    public SearchDealsAdapter(Context context, ArrayList<Parts> sells, DetailsAdapterListener listener) {
        mContext = context;
        Sells = sells;
        this.onClickListener=listener;
    }


    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.view_deals_search, parent, false);
        return new SearchViewHolder(root);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Parts currentItem = Sells.get(position);

        String created = currentItem.getCreated();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        try {
            Date date = format.parse(created);
            PrettyTime p = new PrettyTime();
            date = new Date(date.getTime()+1*3600*1000);
            holder.Created.setText(p.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        String name = currentItem.getName();
        Float price = currentItem.getPrice();
        String type = currentItem.getType();
        String vues = String.valueOf(currentItem.getVues());
        String pricess = priceFormInflater(price);

        if(searchText != null )
        {
            String htmlText1= type.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");
            String htmlText2 = name.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");
            String htmlText3 = pricess.replace(searchText.toUpperCase(),"<font color='#536878'><strong>"+searchText+"</strong></font>");

            holder.Price.setText(Html.fromHtml(htmlText3));
            holder.type.setText(Html.fromHtml(htmlText1));
            holder.Name.setText(Html.fromHtml(htmlText2));

        }else
        {
            holder.Price.setText(pricess);
            holder.type.setText(type);
            holder.Name.setText(name);

        }



        byte[] image = currentItem.getImage();
        Glide.with(mContext).asBitmap().load(image).into(holder.image);
        //Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        //holder.image.setImageBitmap(bmp);
        holder.views.setText(vues);

        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return Sells.size();
    }

    public class SearchViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView type,Name,Created,Price;
        public MaterialButton views;


        public SearchViewHolder(View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.Name);
            views = itemView.findViewById(R.id.views);
            type = itemView.findViewById(R.id.type);
            Created = itemView.findViewById(R.id.Created);
            image = itemView.findViewById(R.id.image);
            Price = itemView.findViewById(R.id.Price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null){
                        int postion = getAdapterPosition();
                        if(postion !=RecyclerView.NO_POSITION){
                            {
                                onClickListener.DetailOnClick(v,postion);

                            }}
                    }}

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
