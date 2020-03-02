package com.localparts.projeecto;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.localparts.projeecto.adapters.CommentAdapter;
import com.localparts.projeecto.entities.Comment;
import com.localparts.projeecto.entities.Votes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class Commentsection extends Fragment {

    private String username;
    private RequestQueue requestQueue;
    private ConstraintLayout loading,view,noComment;
    private ProgressBar loadingDown;
    private RecyclerView mRecyclerView;
    private ArrayList<Comment> commentList;
    private ArrayList<Votes> votesList;
    Viewalone parent;
    private CommentAdapter adapter;
    private static final String URL = MainActivity.SKELETON + "comments/getComments";
    private static final String URL_vote = MainActivity.SKELETON + "comments/UpdateVotes";
    private static final String URL_checker = MainActivity.SKELETON + "comments/checkVoter";
    private static final String URL_neutralize = MainActivity.SKELETON+ "comments/Neutralize";
    private int idDeal ;

    public Commentsection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_commentsection, container, false);
        loading = root.findViewById(R.id.loading);
        view = root.findViewById(R.id.view);
        mRecyclerView = root.findViewById(R.id.comments);
        noComment = root.findViewById(R.id.nocomment);
        loadingDown = root.findViewById(R.id.progress_bar_);
        username = loadUsername();
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentList = new ArrayList<>();
        votesList = new ArrayList<>();
        Bundle data = getArguments();
        if(data != null)
        {
            idDeal =  data.getInt("id",0);
            if(idDeal != 0)
            {

                checkVoter(idDeal);
                getComments(idDeal);
                view.setVisibility(View.VISIBLE);

            }

        }else
        {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
            parent = (Viewalone) navHostFragment.getParentFragment();
            idDeal = parent.idDeal;
            checkVoter(idDeal);
            getComments(idDeal);

            view.setVisibility(View.VISIBLE);

        }



        return root;

    }



    public String loadUsername() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email", "");
        return username;
    }



    private void UpadateVotes(int commentid, String username, String ref) {

        loading.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
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

                            if (response.has("success")) {

                                loading.setVisibility(View.GONE);
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                return;

            }
        });

        requestQueue.add(jsObjRequest);


    }
    private void checkVoter(final int dealid) {

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("dealid", String.valueOf(dealid));
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_checker, new JSONObject(params), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject hit = response.getJSONObject(i);
                        int id = hit.getInt("idVotes");
                        int idcomment = hit.getInt("idcomments");
                        String username = hit.getString("username");
                        String ref = hit.getString("ref");
                        String state = "yes";
                        votesList.add(new Votes(id, username, ref, idcomment,state));
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                return;

            }
        });

        requestQueue.add(request);


    }
    private void getComments(final int idParts) {
        loading.setVisibility(View.VISIBLE);
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
                                    SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                                    try {
                                        date = format.parse(created);
                                        date = new Date(date.getTime()+1*3600*1000);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (state == 0 || state  == 2 )
                                        commentList.add(new Comment(idComments, dealid, votes, text, MainActivity.capitalize(lastname) + " " + MainActivity.capitalize(firstname), date, votesList,username,state));
                                    }
                                    adapter = new CommentAdapter(getActivity(), commentList, new CommentAdapter.OnClickedListner() {
                                    @Override
                                    public void upvVote2(View v, int position) {
                                        NeutralizeVotes(commentList.get(position).getIdComment(), username);
                                        Bundle data =getArguments();
                                        Navigation.findNavController(getView()).navigate(R.id.commentsection, data);
                                    }
                                    @Override
                                    public void downvVote2(View v, int position) {
                                        NeutralizeVotes(commentList.get(position).getIdComment(), username);
                                        Bundle data =getArguments();
                                        Navigation.findNavController(getView()).navigate(R.id.commentsection, data);
                                    }
                                    @Override
                                    public void upvVote(final View v, int position) {

                                        //NeutralizeVotes(commentList.get(position).getIdComment(), username);
                                        UpadateVotes(commentList.get(position).getIdComment(), username, "up");
                                        Bundle data =getArguments();
                                        Navigation.findNavController(getView()).navigate(R.id.commentsection, data);
                                    }
                                    @Override
                                    public void downvVote(final View v, int position) {


                                           // NeutralizeVotes(commentList.get(position).getIdComment(), username);

                                            UpadateVotes(commentList.get(position).getIdComment(), username, "down");


                                        Bundle data =getArguments();

                                        Navigation.findNavController(getView()).navigate(R.id.commentsection, data);
                                    }
                                });
                                mRecyclerView.setAdapter(adapter);
                                final Handler handlers = new Handler();
                                handlers.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loading.setVisibility(View.GONE);
                                        view.setVisibility(View.VISIBLE);
                                    }
                                }, 1500);

                                //loadingBar.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
                error.printStackTrace();
                return;
            }
        });
        requestQueue.add(jsObjRequest);
    }
    private void NeutralizeVotes(int commentid, String username)
    {
        loading.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("commentid", String.valueOf(commentid));
        params.put("username", username);
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL_neutralize, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            if (response.has("success"))
                            {
                                loading.setVisibility(View.GONE);
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Something went Wrong  in Neutrilze", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        requestQueue.add(jsObjRequest);
    }
}
