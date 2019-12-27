package com.example.projeecto;


import android.os.Bundle;

import androidx.annotation.Nullable;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.DealAdapter;
import com.example.projeecto.adapters.SearchDealsAdapter;
import com.example.projeecto.adapters.sellsAdapter;
import com.example.projeecto.entities.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoadSearch extends Fragment {

    private static final String URL = MainActivity.SKELETON+"parts/getSells";
    private static final String URL_Vues = MainActivity.SKELETON+"parts/UpdateVues";
    private static final String URL_Order = MainActivity.SKELETON+"parts/getSellSorted";
    private static final String url_deals = MainActivity.SKELETON+"/parts/searchSell";
    private ConstraintLayout loading,bigView;
    private RequestQueue mRequestQueue;
    private ArrayList<Parts> mSellsList;
    private ImageButton refresh;
    private ProgressBar progressBar;
    private SearchDealsAdapter searchDealsAdapter;
    private RecyclerView views;
    private String searchKey;


    public LoadSearch() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_load_search, container, false);

        loading = root.findViewById(R.id.loading);
        views = root.findViewById(R.id.views);
        bigView =root.findViewById(R.id.bigView);
        refresh = root.findViewById(R.id.refresh);
        progressBar = root.findViewById(R.id.progressBar2);
        mSellsList = new ArrayList<>();
        bigView.setVisibility(View.GONE);
        Bundle key = getArguments();

        if(key != null)
        {
            if(!key.getString("search","").equals(""))
            searchKey = key.getString("search");
            getSearchDeals("%" + searchKey + "%");
            if(!key.getString("key","").equals(""))
            GetSorted(key.getString("key"));
        }else
        {
            GetSells();
        }

        views.setHasFixedSize(true);
        views.setLayoutManager(new LinearLayoutManager(getContext()));

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetSells();
                Navigation.findNavController(v).navigate(R.id.loadSearch);
            }
        });


        return root;
    }

    private void GetSells() {

        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.start();
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.INVISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject hit = response.getJSONObject(i);
                        int id = hit.getInt("idparts");
                        String owner = hit.getString("owner");
                        String name = hit.getString("name");
                        String other1 = hit.getString("other1");
                        Float price = Float.parseFloat(hit.getString("Price"));
                        String other2 = hit.getString("other2");
                        String other3 = hit.getString("other3");
                        String tag_description = hit.getString("tag_description");
                        String created = hit.getString("Created");
                        String type = hit.getString("Type");
                        String state = hit.getString("state");
                        String images = hit.getString("String_image");
                        String ref = hit.getString("refrence");
                        int vues = hit.getInt(("vues"));
                        byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                        if(!state.equals("SOLD"))
                            mSellsList.add(new Parts(id,owner,name,other1,price,other2,other3,type,state,decodedString,tag_description,created,ref,vues));

                        }

                    searchDealsAdapter = new SearchDealsAdapter(getActivity(), mSellsList, new SearchDealsAdapter.DetailsAdapterListener() {
                        @Override
                        public void DetailOnClick(View v, int position) {
                            Bundle data = new Bundle();
                            data.putString("owner",mSellsList.get(position).getOwner());
                            data.putInt("idparts",mSellsList.get(position).getId());
                            data.putString("state",mSellsList.get(position).getState());
                            data.putString("name",mSellsList.get(position).getName());
                            data.putString("other1",mSellsList.get(position).getOther1());
                            data.putString("other2",mSellsList.get(position).getOther2());
                            data.putString("other3",mSellsList.get(position).getOther3());
                            data.putString("Type",mSellsList.get(position).getType());
                            data.putByteArray("image",mSellsList.get(position).getImage());
                            data.putString("refrnce",mSellsList.get(position).getRefrence());
                            data.putFloat("price",mSellsList.get(position).getPrice());
                            data.putString("tag_description",mSellsList.get(position).getTag_desc());
                            data.putString("Created",mSellsList.get(position).getCreated());
                            data.putInt("vues",mSellsList.get(position).getVues());
                            UpdateVues(String.valueOf(mSellsList.get(position).getId()));
                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);
                        }
                    });
                    views.setAdapter(searchDealsAdapter);
                    final Handler handlers = new Handler();
                    handlers.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            loading.setVisibility(View.GONE);
                            bigView.setVisibility(View.VISIBLE);

                        }
                    }, 1500);
                } catch (JSONException e) {
                    loading.setVisibility(View.VISIBLE);
                    bigView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Handler handler = new Handler();
                progressBar.setVisibility(View.VISIBLE);
                bigView.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        refresh.setVisibility(View.VISIBLE);
                    }
                }, 3000);
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);
    }

    private void GetSorted(String key) {

        HashMap< String, String > params = new HashMap<String, String>();
        params.put("key",  key);
        mRequestQueue = Volley.newRequestQueue(getContext());
        mRequestQueue.start();
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        refresh.setVisibility(View.INVISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_Order,new JSONObject(params), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {


                    for (int i = 0; i < response.length(); i++) {
                        JSONObject hit = response.getJSONObject(i);
                        int id = hit.getInt("idparts");
                        String owner = hit.getString("owner");
                        String name = hit.getString("name");
                        String other1 = hit.getString("other1");
                        Float price = Float.parseFloat(hit.getString("Price"));
                        String other2 = hit.getString("other2");
                        String other3 = hit.getString("other3");
                        String tag_description = hit.getString("tag_description");
                        String created = hit.getString("Created");
                        String type = hit.getString("Type");
                        String state = hit.getString("state");
                        String images = hit.getString("String_image");
                        String ref = hit.getString("refrence");
                        int vues = hit.getInt(("vues"));
                        byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                        if(!state.equals("SOLD"))
                            mSellsList.add(new Parts(id,owner,name,other1,price,other2,other3,type,state,decodedString,tag_description,created,ref,vues));

                    }

                    searchDealsAdapter = new SearchDealsAdapter(getActivity(), mSellsList, new SearchDealsAdapter.DetailsAdapterListener() {
                        @Override
                        public void DetailOnClick(View v, int position) {
                            Bundle data = new Bundle();
                            data.putString("owner",mSellsList.get(position).getOwner());
                            data.putInt("idparts",mSellsList.get(position).getId());
                            data.putString("state",mSellsList.get(position).getState());
                            data.putString("name",mSellsList.get(position).getName());
                            data.putString("other1",mSellsList.get(position).getOther1());
                            data.putString("other2",mSellsList.get(position).getOther2());
                            data.putString("other3",mSellsList.get(position).getOther3());
                            data.putString("Type",mSellsList.get(position).getType());
                            data.putByteArray("image",mSellsList.get(position).getImage());
                            data.putString("refrnce",mSellsList.get(position).getRefrence());
                            data.putFloat("price",mSellsList.get(position).getPrice());
                            data.putString("tag_description",mSellsList.get(position).getTag_desc());
                            data.putString("Created",mSellsList.get(position).getCreated());
                            data.putInt("vues",mSellsList.get(position).getVues());
                            UpdateVues(String.valueOf(mSellsList.get(position).getId()));
                            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);
                        }
                    });
                    views.setAdapter(searchDealsAdapter);
                    final Handler handlers = new Handler();
                    handlers.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            loading.setVisibility(View.GONE);
                            bigView.setVisibility(View.VISIBLE);

                        }
                    }, 1500);
                } catch (JSONException e) {
                    loading.setVisibility(View.VISIBLE);
                    bigView.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                final Handler handler = new Handler();
                progressBar.setVisibility(View.VISIBLE);
                bigView.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        refresh.setVisibility(View.VISIBLE);
                    }
                }, 3000);
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);
    }

    private void UpdateVues(String idparts)
    {

        HashMap< String, String > params = new HashMap<String, String>();
        params.put("idparts",  idparts);

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL_Vues,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Connection Lost "+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        mRequestQueue.add(jsObjRequest);
    }
    private void getSearchDeals(final String key) {

        //loadingBar.setVisibility(View.VISIBLE);
        if(key.equals("%%"))
        {
            GetSells();
            return;
        }
        loading.setVisibility(View.VISIBLE);

                mRequestQueue = Volley.newRequestQueue(getContext());
                mRequestQueue.start();
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
                                            Float price = Float.parseFloat(hit.getString("Price"));
                                            String other2 = hit.getString("other2");
                                            String other3 = hit.getString("other3");
                                            String tag_description = hit.getString("tag_description");
                                            String created = hit.getString("Created");
                                            String type = hit.getString("Type");
                                            String state = hit.getString("state");
                                            String images = hit.getString("String_image");
                                            String ref = hit.getString("refrence");
                                            int vues = hit.getInt(("vues"));
                                            byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                                            if (!state.equals("SOLD"))
                                                mSellsList.add(new Parts(id, owner, name, other1, price, other2, other3, type, state, decodedString, tag_description, created, ref, vues));
                                        }

                                        searchDealsAdapter = new SearchDealsAdapter(getActivity(), mSellsList, new SearchDealsAdapter.DetailsAdapterListener() {
                                            @Override
                                            public void DetailOnClick(View v, int position) {
                                                Bundle data = new Bundle();
                                                data.putString("owner", mSellsList.get(position).getOwner());
                                                data.putInt("idparts", mSellsList.get(position).getId());
                                                data.putString("state", mSellsList.get(position).getState());
                                                data.putString("name", mSellsList.get(position).getName());
                                                data.putString("other1", mSellsList.get(position).getOther1());
                                                data.putString("other2", mSellsList.get(position).getOther2());
                                                data.putString("other3", mSellsList.get(position).getOther3());
                                                data.putString("Type", mSellsList.get(position).getType());
                                                data.putByteArray("image", mSellsList.get(position).getImage());
                                                data.putString("refrnce", mSellsList.get(position).getRefrence());
                                                data.putFloat("price", mSellsList.get(position).getPrice());
                                                data.putString("tag_description", mSellsList.get(position).getTag_desc());
                                                data.putString("Created", mSellsList.get(position).getCreated());
                                                data.putInt("vues", mSellsList.get(position).getVues());
                                                UpdateVues(String.valueOf(mSellsList.get(position).getId()));
                                                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.viewalone, data);
                                            }
                                        },searchKey);
                                        views.setAdapter(searchDealsAdapter);
                                        final Handler handlers = new Handler();
                                        handlers.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                loading.setVisibility(View.GONE);
                                                bigView.setVisibility(View.VISIBLE);

                                            }
                                        }, 1500);
                                    }} catch (JSONException e) {
                                        loading.setVisibility(View.VISIBLE);
                                        bigView.setVisibility(View.GONE);
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    final Handler handler = new Handler();
                                    progressBar.setVisibility(View.VISIBLE);
                                    bigView.setVisibility(View.GONE);
                                    loading.setVisibility(View.VISIBLE);

                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            refresh.setVisibility(View.VISIBLE);
                                        }
                                    }, 3000);
                                    error.printStackTrace();

                                }
                            });

                mRequestQueue.add(jsObjRequest);
    }

}
