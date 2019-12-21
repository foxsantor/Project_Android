package com.example.projeecto;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.DealAdapter;
import com.example.projeecto.adapters.myPartsAdapter;
import com.example.projeecto.entities.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private RecyclerView deals,auctions,announcement;
    private static final String url_deals = MainActivity.SKELETON+"/parts/searchSell";
    private static final String url_auctions = MainActivity.SKELETON+"/parts/searchAuction";
    private static final String url_announcement = MainActivity.SKELETON+"";
    private String searchKey,username;
    private ConstraintLayout loading,View;
    private RequestQueue requestQueue;
    private TextView text22,text26;
    private ArrayList<Parts> dealList;
    private DealAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View = view.findViewById(R.id.Searchvalid);
        deals = view.findViewById(R.id.Deals_res);
        text22 = view.findViewById(R.id.textView22);
        text26 = view.findViewById(R.id.textView26);
        loading = view.findViewById(R.id.loading_1);
        deals.setHasFixedSize(true);
        deals.setLayoutManager(new LinearLayoutManager(getContext()));
        dealList = new ArrayList<>();
        View.setVisibility(android.view.View.GONE);
        final Bundle data = getArguments();

        if(data != null)
        {
                    loading.setVisibility(View.GONE);
                    searchKey = data.getString("search");
                    getSearchDeals("%" + searchKey + "%");

        }else
        {
            View.setVisibility(android.view.View.GONE);
            text22.setText("FIND YOUR VEHICLE PARTS");
            text26.setVisibility(android.view.View.VISIBLE);
            text26.setText("Find an auto part, an auction or an announcement");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        return  root;
    }




    private void getSearchDeals(final String key) {

        //loadingBar.setVisibility(View.VISIBLE);
        if(key.equals("%%"))
        {
            View.setVisibility(android.view.View.GONE);
            return;
        }
        username = loadUsername();
        View.setVisibility(android.view.View.VISIBLE);
        loading.setVisibility(View.VISIBLE);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
        @Override
        public void run() {
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("search",key);
        // the entered data as the JSON body.
        final JsonArrayRequest jsObjRequest = new
                JsonArrayRequest(
                Request.Method.POST,
                url_deals,
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
                                    Float price = Float.parseFloat(hit.getString("Price"));
                                    byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                                    dealList.add( new Parts(id,name,ref,other1,other2,other3,created,type,tag_description,decodedString,owner,state,StatusSell,price));
                                }

                                adapter = new DealAdapter(getActivity(),dealList,new DealAdapter.OnClickedListner() {
                                    @Override
                                    public void onClicked(int pos) {

                                        Bundle data = new Bundle();
                                        data.putString("owner",dealList.get(pos).getOwner());
                                        data.putString("Sell",dealList.get(pos).getStatusSell());
                                        data.putString("idparts",String.valueOf(dealList.get(pos).getId()));
                                        data.putString("name",dealList.get(pos).getName());
                                        data.putString("other1",dealList.get(pos).getOther1());
                                        data.putString("other2",dealList.get(pos).getOther2());
                                        data.putString("other3",dealList.get(pos).getOther3());
                                        data.putString("Type",dealList.get(pos).getType());
                                        data.putString("Type",String.valueOf(dealList.get(pos).getPrice()));
                                        data.putByteArray("image",dealList.get(pos).getImage());
                                        data.putString("refrnce",dealList.get(pos).getRefrence());
                                        data.putString("tag_description",dealList.get(pos).getTag_desc());
                                        data.putString("Created",dealList.get(pos).getCreated());

                                        if(dealList.get(pos).getOwner().equals(username))
                                        {
                                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.mypartView,data);
                                        }else
                                        {
                                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);
                                        }
                                    }
                                },searchKey);

                                deals.setAdapter(adapter);
                                loading.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                text26.setVisibility(android.view.View.GONE);

                if(error.getMessage().contains("failed"))
                {
                    View.setVisibility(android.view.View.GONE);
                    text22.setText("NO DATA FOUND");

                }else
                {
                    View.setVisibility(android.view.View.GONE);
                    text22.setText("SOMETHING WENT WRONG TRY SEARCH AGAIN");
                }
                error.printStackTrace();

            }
        });

        requestQueue.add(jsObjRequest);
        }
        }, 1800);
    }
    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }

}
