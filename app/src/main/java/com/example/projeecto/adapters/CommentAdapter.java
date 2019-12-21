package com.example.projeecto.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeecto.R;
import com.example.projeecto.entities.Comment;
import com.example.projeecto.entities.Parts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.commentViewHolder> {

    private Context mContext;
    private ArrayList<Comment> comments;
    private OnClickedListner lisnter;
    private Bundle data;



    public interface  OnClickedListner{

        void upvVote(View v, int position);
        void downvVote(View v, int position);
    }




    @Override
    public commentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.comments_view, parent, false);
        return new commentViewHolder(root,lisnter);
    }

    @Override
    public void onBindViewHolder(commentViewHolder holder, int position) {

        Comment currentItem=  comments.get(position);
        String username = currentItem.getUsername();
        String text = currentItem.getText();
        int vote = currentItem.getVote();
        //String htmlText1 = type.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText2 = name.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText3 = pricess.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //Color checkers + other fields inputs
        //holder.cAvatar.setText(Html.fromHtml(htmlText2));
        holder.cText.setText(Html.fromHtml(text));
        holder.cVotes.setText(String.valueOf(vote));
        holder.cName.setText(username);
        holder.cAvatar.setText(""+username.toUpperCase().charAt(0));
        //holder.cDate.setText("Price: "+Html.fromHtml(htmlText3)+" TND");
        //holder.mImageView.setImageBitmap(bmp);
        //Picasso.with(mContext).load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class commentViewHolder extends RecyclerView.ViewHolder {
        public ImageButton upVote,downVote;
        public TextView cAvatar,cText,cDate,cName,cVotes;

        public commentViewHolder(View itemView,final OnClickedListner listner) {

            super(itemView);
            cAvatar = itemView.findViewById(R.id.avatar);
            cText = itemView.findViewById(R.id.text);
            cDate = itemView.findViewById(R.id.Date);
            cName = itemView.findViewById(R.id.name);
            upVote = itemView.findViewById(R.id.upvote);
            cVotes = itemView.findViewById(R.id.votes);
            downVote = itemView.findViewById(R.id.downvote);

            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    lisnter.upvVote(v, getAdapterPosition());
                }
            });
            downVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lisnter.downvVote(v, getAdapterPosition());
                }
            });






        }

    }
    public CommentAdapter(ArrayList<Comment> list){

        this.comments= list;
    }

    public CommentAdapter(Context context, ArrayList<Comment> comments,Bundle data, CommentAdapter.OnClickedListner listener) {
        mContext = context;
        this.data=data;
        this.comments = comments;
        this.lisnter=listener;
        notifyDataSetChanged();
    }


}
