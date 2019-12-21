package com.example.projeecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.CommentAdapter;
import com.example.projeecto.adapters.myPartsAdapter;
import com.example.projeecto.adapters.sellsAdapter;
import com.example.projeecto.entities.Comment;
import com.example.projeecto.entities.Parts;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Viewalone extends Fragment implements OnbackDestrecution {

    TextView Created,name,owner,other1,other2,other3,refrence,tag_description,price,type,other1_v,other2_v,other3_v,portrait;
    FloatingActionButton shopping;
    ConstraintLayout other1c,other2c,other3c,appear;
    Button commentSender,Contact,I,B;
    String other1s,other2s,other3s,username;
    private ArrayList<Comment> commentList;
    private CommentAdapter adapter;
    private RecyclerView mRecyclerView;
    EditText comment;
    ImageView imageView;
    RequestQueue requestQueue;
    private static final String URL = MainActivity.SKELETON+"comments/getComments";
    private static final String URL_ADD = MainActivity.SKELETON+"comments/addComment";
    private static final String URL_vote = MainActivity.SKELETON+"comments/UpdateVotes";
    private static final String URL_checker = MainActivity.SKELETON+"comments/checkVoter";
    private int idDeal;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewalone, container, false);
        OnbackDestrecution();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();
        //init
        Created =view.findViewById(R.id.date_a);
        Contact = view.findViewById(R.id.Contact);
        other1c = view.findViewById(R.id.other1);
        other2c = view.findViewById(R.id.other2);
        other3c = view.findViewById(R.id.other3);
        other1_v = view.findViewById(R.id.other1v);
        other2_v = view.findViewById(R.id.other2v);
        other3_v = view.findViewById(R.id.otherv);
        name  =view.findViewById(R.id.name_a);
        owner  =view.findViewById(R.id.ownert);
        other1  =view.findViewById(R.id.other1t);
        other2 =view.findViewById(R.id.other2t);
        other3 =view.findViewById(R.id.other3t);
        refrence =view.findViewById(R.id.refrencet);
        tag_description =view.findViewById(R.id.textView24);
        price  =view.findViewById(R.id.textView17);
        type=view.findViewById(R.id.textView19);
        shopping =view.findViewById(R.id.Addtofavorite);
        imageView = view.findViewById(R.id.imageView4);
        mRecyclerView = view.findViewById(R.id.comments);
        comment =view.findViewById(R.id.comment);
        appear = view.findViewById(R.id.appear);
        portrait = view.findViewById(R.id.portrait);
        commentSender = view.findViewById(R.id.send);
        username = loadUsername();
        I = view.findViewById(R.id.I);
        B = view.findViewById(R.id.B);
        Bundle data = getArguments();

        if(null!= data) {

            other1s=data.getString("other1");
            other2s=data.getString("other2");
            other3s= data.getString("other3");

            if(other1s.isEmpty())
            {
                this.other1c.setVisibility(View.GONE);
            }else
            {
                if(other1s.contains(",")){
                    String[] other1TextView;
                    other1TextView = other1s.split(",");
                    other1_v.setText(other1TextView[0]);
                    other1.setText(other1TextView[1]);}
                else {
                    other1.setText(other1s);
                }
            }
            if(other2s.isEmpty())
            {
                this.other2c.setVisibility(View.GONE);
            }else
            {
                if(other2s.contains(",")){
                    String[] other2TextView;
                    other2TextView = other2s.split(",");
                    other2_v.setText(other2TextView[0]);
                    other2.setText(other2TextView[1]);}
                else {
                    other2.setText(other2s);
                }
            }
            if(other3s.isEmpty())
            {
                this.other3c.setVisibility(View.GONE);
            }else
            {
                if(other3s.contains(",")){
                    String[] other3TextView;
                    other3TextView = other3s.split(",");
                    other3_v.setText(other3TextView[0]);
                    other3.setText(other3TextView[1]);}
                else {
                    other3.setText(other3s);
                }
            }

            if(this.other1c.getVisibility() == View.GONE )
            {
                this.other2c.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#FFFFFF")));
                this.other3c.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#E6E9F0")));
            }
            if(this.other2c.getVisibility() == View.GONE  )
            {
                this.other3c.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#E6E9F0")));
            }
            if(this.other2c.getVisibility() == View.GONE  && this.other1c.getVisibility() == View.GONE )
            {
                this.other3c.setBackgroundDrawable(new ColorDrawable( Color.parseColor("#FFFFFF")));
            }

            Created.setText(data.getString("Created"));
            name.setText(data.getString("name"));
            owner.setText(data.getString("owner"));
            idDeal = data.getInt("idparts",0);
            getComments(idDeal);
            refrence.setText(data.getString("refrnce"));
            tag_description.setText(data.getString("tag_description"));
            price.setText(data.getFloat("price")+ " TND");
            type.setText(data.getString("Type"));
            byte[] image = data.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bmp);

        }
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = comment.getText().toString();
                current_comment = "<i>"+current_comment+"</i>";
                comment.setText(current_comment);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = comment.getText().toString();
                current_comment = "<b>"+current_comment+"</b>";
                comment.setText(current_comment);
            }
        });
        comment.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                appear.setVisibility(View.VISIBLE);
                }
                if(!validateEmpty(comment))
                {
                    commentSender.setClickable(false);
                    commentSender.setBackgroundResource(R.drawable.editblend);
                }else
                {
                    commentSender.setClickable(true);
                    commentSender.setBackgroundResource(R.drawable.blenddarker);

                }
                return false;
            }
        });

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!validateEmpty(comment))
                {
                    commentSender.setClickable(false);
                    commentSender.setBackgroundResource(R.drawable.editblend);
                }else
                {
                    commentSender.setClickable(true);
                    commentSender.setBackgroundResource(R.drawable.blenddarker);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        commentSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment(comment.getText().toString(),username,idDeal);

            }
        });




        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentList = new ArrayList<>();

        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = new Bundle();
                data.putString("owner",owner.getText().toString());
                data.putString("price",price.getText().toString());
                data.putString("name",name.getText().toString());
                Navigation.findNavController(getView()).navigate(R.id.action_viewalone_to_contactInterface,data);
            }
        });





    }

    @Override
    public void OnbackDestrecution() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }

    private void getComments(int idParts) {

        OnbackDestrecution();
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("dealid", Integer.toString(idParts));
        // the entered data as the JSON body.
        final JsonArrayRequest jsObjRequest = new
                JsonArrayRequest(
                Request.Method.POST,
                URL,
                new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jresponse = response.getJSONObject(0);
                            if (response.length() > 0) {
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject hit = response.getJSONObject(i);
                                    int idComments = hit.getInt("idcomments");
                                    String firstname = hit.getString("firstname");
                                    String lastname = hit.getString("lastname");
                                    String username = hit.getString("username");
                                    String text = hit.getString("text");
                                    int votes = hit.getInt("votes");
                                    int dealid = hit.getInt("dealid");
                                    int state = hit.getInt("state");
                                    String created = hit.getString("created");
                                    Date date = new Date();
                                    try {
                                         date = parse(created);
                                    }catch (Exception e) { e.printStackTrace();}

                                    if(state != 1)
                                    commentList.add( new Comment(idComments,dealid,votes,text,lastname+" "+firstname,date));
                                      }

                                adapter = new CommentAdapter(getActivity(), commentList, checkVoter(), new CommentAdapter.OnClickedListner() {
                                    @Override
                                    public void upvVote(View v, int position) {

                                        UpadateVotes(commentList.get(position).getIdComment(),username,"up");

                                    }

                                    @Override
                                    public void downvVote(View v, int position) {

                                        UpadateVotes(commentList.get(position).getIdComment(),username,"down");

                                    }
                                });
                                mRecyclerView.setAdapter(adapter);
                                //loadingBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.getMessage().contains("failed"))
                {
                    return;
                }
                Toast.makeText(getActivity(), "Connection Lost " + error, Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });

        requestQueue.add(jsObjRequest);
    }

    public static Date parse(String input ) throws java.text.ParseException {

        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
        //things a bit.  Before we go on we have to repair this.
        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );

        //this is zero time so we need to add that TZ indicator for
        if ( input.endsWith( "Z" ) ) {
            input = input.substring( 0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;

            String s0 = input.substring( 0, input.length() - inset );
            String s1 = input.substring( input.length() - inset, input.length() );

            input = s0 + "GMT" + s1;
        }

        return df.parse( input );

    }

    private void addComment(String text,String username,int idParts)
    {
        //loading.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("text", text);
        params.put("username", username);
        params.put("dealid", Integer.toString(idParts));
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL_ADD, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")) {

                                //loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ""+response.getString("success"), Toast.LENGTH_SHORT).show();
                                //openDialogue();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " internal error happened "+response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost to the Server", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsObjRequest);
    }
    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }
    private boolean validateEmpty(EditText text)
    {
        String textToCheck = text.getText().toString().trim();
        if(textToCheck.isEmpty()){
            return false;}
        else
        {
            return true;
        }

    }

    private void UpadateVotes(int commentid ,String username,String ref)
    {
         //loading.setVisibility(View.VISIBLE);

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commentid", String.valueOf(commentid));
        params.put("ref", ref);
        params.put("username", username);

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL_vote, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")) {

                                //loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ""+response.getString("success"), Toast.LENGTH_SHORT).show();
                                //openDialogue();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " internal error happened "+response.getString("notfound"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost to the Server", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsObjRequest);




    }
    private Bundle checkVoter ()
    {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);

        final Bundle data = new Bundle();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_checker,new JSONObject(params), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject hit = response.getJSONObject(i);
                        int id = hit.getInt("idVotes");
                        int idcomment = hit.getInt("idcomment");
                        String username = hit.getString("username");
                        String ref = hit.getString("ref");
                        data.putInt("idVotes",id);
                        data.putInt("idcomment",idcomment);
                        data.putString("username",username);
                        data.putString("ref",ref);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();

            }
        });

        requestQueue.add(request);
        return data;
    }


    }

