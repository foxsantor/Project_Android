package com.localparts.projeecto.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.localparts.projeecto.R;
import com.localparts.projeecto.entities.Message;

import java.util.List;


public class MessageAdapter extends  RecyclerView.Adapter<MessageAdapter.ViewHolder>{



    private List<Message> mMessages;
    private int[] mUsernameColors;
    Context ctx;
    private String username;
    private RelativeLayout mine,theris;

    public  String loadUsername()
    {
        SharedPreferences sharedPreferences= ctx.getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }


    public MessageAdapter(List<Message> messages,Context ctx) {
        this.mMessages = messages;
        this.ctx = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.messagecontrol, parent, false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder , int position) {
        username =loadUsername();
        Message message = mMessages.get(position);
        if(message.getUser().equals(username))
        viewHolder.setMyMessage(message.getMessage());
        else
        viewHolder.settheirMessage(message.getMessage(),message.getUser());
        //viewHolder.setImage(message.getImage());

    }


    @Override
    public int getItemCount() {
        return mMessages.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mMessages.get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //public ImageView mImageView;
        public TextView myMessageView,theirMessageView,nameTheirs,avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            //mImageView = (ImageView)itemView.findViewById(R.id.image);
            myMessageView = (TextView)itemView.findViewById(R.id.message_body_mine);
            theirMessageView = (TextView)itemView.findViewById(R.id.message_body);
            nameTheirs = (TextView)itemView.findViewById(R.id.name);
            mine = itemView.findViewById(R.id.mymesg);
            theris = itemView.findViewById(R.id.theirs);
            avatar = itemView.findViewById(R.id.avatar);
        }
        public void setMyMessage(String message){
            mine.setVisibility(View.VISIBLE);
            theris.setVisibility(View.GONE);
            if (null == myMessageView){
                return;
            }
            if (null==message){
                return;
            }
            myMessageView.setText(message);
        }
        public void settheirMessage(String message,String username){
            theris.setVisibility(View.VISIBLE);
            mine.setVisibility(View.GONE);
            if (null == theirMessageView){
                return;
            }
            if (null==message){
                return;
            }
            theirMessageView.setText(message);
            nameTheirs.setText(username);
            avatar.setText(""+username.toUpperCase().charAt(0));
        }

        /*public void setImage(Bitmap bmp){
            if (null==mImageView)return;

            if (null==bmp) return;

            mImageView.setImageBitmap(bmp);
        }*/

        private int getUsernameColor(String username){
            int hash = 8;
            for (int i =0 ,len =username.length();i<len;i++){
                hash = username.codePointAt(i)+(hash<<5)-hash;

            }
            int index = Math.abs(hash % mUsernameColors.length);
            return mUsernameColors[index];
        }
    }




}

