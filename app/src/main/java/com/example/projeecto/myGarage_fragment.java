package com.example.projeecto;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.sellsAdapter;
import com.example.projeecto.entities.Parts;
import com.example.projeecto.adapters.myPartsAdapter;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class myGarage_fragment extends Fragment {
    private static final String URL = MainActivity.SKELETON+"/parts/myparts" ;
    private String username;
    private RequestQueue requestQueue;
    private ConstraintLayout loadingBar,bigView;
    private ArrayList<Parts> mPartsList;
    private myPartsAdapter adapter;
    private RecyclerView mRecyclerView;
    private FloatingActionButton addPart;
    private ProgressBar progress_bar_4;
    private ImageButton restart2,search_advance;
    private SearchView searchView2;


    public static myGarage_fragment newInstance() {
        myGarage_fragment fragment = new myGarage_fragment();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_my_garage_fragment, container, false);

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingBar = view.findViewById(R.id.loading_1);

        progress_bar_4= view.findViewById(R.id.progress_bar_4);
        searchView2 = view.findViewById(R.id.searchView2);
        restart2 = view.findViewById(R.id.restart2);
        search_advance =view.findViewById(R.id.search_advance);
        bigView = view.findViewById(R.id.bigView);
        bigView.setVisibility(View.GONE);

        mRecyclerView = view.findViewById(R.id.recyli);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mPartsList = new ArrayList<>();
        addPart = view.findViewById(R.id.addpart);
        addPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.add_part_fragment);
            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        getMyparts();

        restart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMyparts();
            }
        });





    }
    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }

    private void getMyparts() {
        restart2.setVisibility(View.INVISIBLE);
        progress_bar_4.setVisibility(View.VISIBLE);
        loadingBar.setVisibility(View.VISIBLE);


        username = loadUsername();
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
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
                                    int id = hit.getInt("idparts");
                                    String owner = hit.getString("owner");
                                    String name = hit.getString("name");
                                    String other1 = hit.getString("other1");
                                    String StatusSell = hit.getString("Sell");
                                    String other2 = hit.getString("other2");
                                    String other3 = hit.getString("other3");
                                    String tag_description = hit.getString("tag_description");
                                    String created = hit.getString("Created");
                                    String type = hit.getString("Type");
                                    String state = hit.getString("state");
                                    String images = hit.getString("String_image");
                                    String ref = hit.getString("refrence");
                                    byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                                    mPartsList.add( new Parts(id,name,ref,other1,other2,other3,created,type,tag_description,decodedString,owner,state,StatusSell));
                                }
                                adapter = new myPartsAdapter(getActivity(),mPartsList,new myPartsAdapter.OnClickedListner() {
                                    @Override
                                    public void onClicked(int pos) {
                                        Bundle data = new Bundle();
                                        data.putString("owner",mPartsList.get(pos).getOwner());
                                        data.putString("Sell",mPartsList.get(pos).getStatusSell());
                                        data.putString("idparts",String.valueOf(mPartsList.get(pos).getId()));
                                        data.putString("name",mPartsList.get(pos).getName());
                                        data.putString("other1",mPartsList.get(pos).getOther1());
                                        data.putString("other2",mPartsList.get(pos).getOther2());
                                        data.putString("other3",mPartsList.get(pos).getOther3());
                                        data.putString("Type",mPartsList.get(pos).getType());
                                        data.putByteArray("image",mPartsList.get(pos).getImage());
                                        data.putString("refrnce",mPartsList.get(pos).getRefrence());
                                        data.putString("tag_description",mPartsList.get(pos).getTag_desc());
                                        data.putString("Created",mPartsList.get(pos).getCreated());
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.add_part_fragment,data);
                                    }
                                });
                                mRecyclerView.setAdapter(adapter);
                                bigView.setVisibility(View.VISIBLE);
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                loadingBar.setVisibility(View.GONE);
                                }
                            }, 1500);
                            } else {
                                Toast.makeText(getActivity(), ""+jresponse.getString("failed"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                final Handler handler = new Handler();

                bigView.setVisibility(View.GONE);
                restart2.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_bar_4.setVisibility(View.GONE);
                        restart2.setVisibility(View.VISIBLE);
                    }
                }, 2000);
                error.printStackTrace();
                return;
            }
        });

        requestQueue.add(jsObjRequest);
    }

}
