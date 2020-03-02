package com.localparts.projeecto.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.localparts.projeecto.MainActivity;
import com.localparts.projeecto.R;
import com.localparts.projeecto.entities.Comment;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.commentViewHolder> {

    private Context mContext;
    private String username;
    private ArrayList<Comment> comments;
    private OnClickedListner lisnter;
    private String click ;
    private RequestQueue requestQueue;
    final private static String URL_Disable = MainActivity.SKELETON+"comments/DisableComments";
    final private static String URL_EDIT = MainActivity.SKELETON+"comments/EditComment";



    public interface  OnClickedListner{

        void upvVote(View v, int position);
        void downvVote(View v, int position);
        void upvVote2(View v, int position);
        void downvVote2(View v, int position);
    }




    @Override
    public commentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.comments_view, parent, false);
        return new commentViewHolder(root,lisnter);
    }

    @Override
    public void onBindViewHolder(final commentViewHolder holder, final int position) {

        final Comment currentItem=  comments.get(position);
        String username = currentItem.getUsername();
        Date created = currentItem.getCreated();
        PrettyTime p = new PrettyTime();
        final String text = currentItem.getText();
        int vote = currentItem.getVote();
        final int idcomment = currentItem.getIdComment();
        holder.upVote2.setVisibility(View.INVISIBLE);
        holder.downVote2.setVisibility(View.INVISIBLE);
        holder.textView40.setVisibility(View.GONE);
        for(int j = 0; j < currentItem.votes.size();j++)
        {
            if(idcomment == currentItem.votes.get(j).getCommentid())
            {
                if(currentItem.votes.get(j).getRef().equals("up"))
                {

                holder.upVote.setVisibility(View.INVISIBLE);
                    holder.upVote2.setVisibility(View.VISIBLE);


                }
                if(currentItem.votes.get(j).getRef().equals("down"))
                {
                    holder.downVote.setVisibility(View.INVISIBLE);
                    holder.downVote2.setVisibility(View.VISIBLE);

                }
            }
        }

        if(currentItem.getState() == 2 )
        {
            holder.textView40.setVisibility(View.VISIBLE);
        }
        if(!loadUsername().equals(currentItem.getUserEmail()))
        {
            holder.menu.setVisibility(View.GONE);
            holder.textView38.setVisibility(View.GONE);
        }

        //String htmlText1 = type.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText2 = name.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //String htmlText3 = pricess.replace(searchText,"<font color='#536878'><strong>"+searchText+"</strong></font>");
        //Color checkers + other fields inputs
        //holder.cAvatar.setText(Html.fromHtml(htmlText2));
        /**/


        holder.menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(mContext, holder.menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.commentdrop);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.deleteComment:
                                Disabllecomment(currentItem.getIdComment());
                                notifyItemRemoved(position);
                                Navigation.findNavController(v).navigate(R.id.commentsection);
                                return true;
                            case R.id.editComment:
                                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                LayoutInflater layoutInflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View dialogueView =layoutInflater.inflate(R.layout.layoutsavecomment,null);
                                alert.setView(dialogueView);
                                final TextInputLayout textInputLayout = dialogueView.findViewById(R.id.argax);
                                Button I = dialogueView.findViewById(R.id.i);
                                Button B = dialogueView.findViewById(R.id.b);
                                alert.setTitle("Edit Comment");
                                textInputLayout.getEditText().setText(currentItem.getText());

                                I.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String current_comment = textInputLayout.getEditText().getText().toString();
                                        current_comment = "<i>" + current_comment + "</i>";
                                        textInputLayout.getEditText().setText(current_comment);
                                    }
                                });
                                B.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        String current_comment = textInputLayout.getEditText().getText().toString();
                                        current_comment = "<b>" + current_comment + "</b>";
                                        textInputLayout.getEditText().setText(current_comment);
                                    }
                                });

                                //TextInputLayout textInputLayout =
                                // Set an EditText view to get user input

                                alert.setPositiveButton("Save", null);
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Canceled.
                                    }
                                });

                                final AlertDialog alerti =alert.show();
                               Button save = alerti.getButton(AlertDialog.BUTTON_POSITIVE);
                               save.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View vi) {
                                       if(!textInputLayout.getEditText().getText().toString().isEmpty() && !textInputLayout.getEditText().getText().toString().equals("") && textInputLayout.getEditText().getText().toString()!=null)
                                       {
                                           EditComment(currentItem.getIdComment(),textInputLayout.getEditText().getText().toString());
                                           notifyItemChanged(position);
                                           Navigation.findNavController(v).navigate(R.id.commentsection);
                                           alerti.dismiss();

                                       }

                                   }
                               });
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
        holder.cText.setText(Html.fromHtml(text));
        holder.cVotes.setText(String.valueOf(vote));
        holder.cName.setText(username);
        holder.cDate.setText(p.format(created));
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
        public ImageButton upVote,downVote,upVote2,downVote2,menu;
        public TextView cAvatar,cText,cDate,cName,cVotes,textView38,textView40;


        public commentViewHolder(View itemView,final OnClickedListner listner) {

            super(itemView);
            cAvatar = itemView.findViewById(R.id.avatar);
            cText = itemView.findViewById(R.id.text);
            cDate = itemView.findViewById(R.id.Date);
            cName = itemView.findViewById(R.id.name);
            upVote = itemView.findViewById(R.id.upvote);
            cVotes = itemView.findViewById(R.id.votes);
            downVote = itemView.findViewById(R.id.downvote);
            downVote2 = itemView.findViewById(R.id.downvote2);
            upVote2 = itemView.findViewById(R.id.upvote2);
            menu = itemView.findViewById(R.id.menu);
            textView38= itemView.findViewById(R.id.textView38);
            textView40 = itemView.findViewById(R.id.textView40);



            upVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    lisnter.upvVote(v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            downVote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lisnter.downvVote(v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            upVote2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    lisnter.upvVote2(v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            downVote2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lisnter.downvVote2(v, getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
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

    private void Disabllecomment(int commentid)
    {

        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("idcomment", String.valueOf(commentid));
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL_Disable, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.has("success")) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext, "Something went Wrong while Disabling", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private void EditComment(int commentid,String text)
    {

        requestQueue = Volley.newRequestQueue(mContext);
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("idcomment", String.valueOf(commentid));
        params.put("text",text);
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL_EDIT, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.has("success")) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext, "Something went Wrong while editing comment", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        requestQueue.add(jsObjRequest);
    }


}
