package com.example.projeecto.adapters;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.projeecto.R;
import com.example.projeecto.entities.Comment;
import com.example.projeecto.entities.Parts;
import com.example.projeecto.entities.Votes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.commentViewHolder> {

    private Context mContext;
    private String username;
    private ArrayList<Comment> comments;
    private OnClickedListner lisnter;





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
        int idcomment = currentItem.getIdComment();


        for(int j = 0; j < currentItem.votes.size();j++)
        {
            if(idcomment == currentItem.votes.get(j).getCommentid())
            {
                if(currentItem.votes.get(j).getRef().equals("up"))
                {
                    holder.upVote.setBackgroundResource(R.drawable.upvote_blue);
                    holder.upVote.setImageResource(R.drawable.upvote_blue);
                    holder.downVote.setClickable(true);
                    holder.upVote.setClickable(false);
                }
                if(currentItem.votes.get(j).getRef().equals("down"))
                {
                    holder.downVote.setBackgroundResource(R.drawable.downvote_blue);
                    holder.downVote.setImageResource(R.drawable.downvote_blue);
                    holder.downVote.setClickable(false);
                    holder.upVote.setClickable(true);

                }
            }
        }






        //String htmlText1 = type.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText2 = name.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText3 = pricess.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //Color checkers + other fields inputs
        //holder.cAvatar.setText(Html.fromHtml(htmlText2));
        /**/
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

    public CommentAdapter(Context context, ArrayList<Comment> comments, CommentAdapter.OnClickedListner listener) {
        mContext = context;
        this.comments = comments;
        this.lisnter=listener;

    }
    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=mContext.getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }

}
