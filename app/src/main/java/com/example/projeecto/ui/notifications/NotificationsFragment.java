package com.example.projeecto.ui.notifications;

import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Type;

import com.example.projeecto.MainActivity;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.R;
import com.example.projeecto.adapters.sellsAdapter;
import com.example.projeecto.entities.Parts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private static final String URL = MainActivity.SKELETON+"parts/getSells";//"http://10.0.2.2:5000/api/parts/getSells";
    private RecyclerView mRecyclerView;
    private ArrayList<Parts> mSellsList;
    private RequestQueue mRequestQueue;
    private sellsAdapter mSellsAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        mRecyclerView = root.findViewById(R.id.Sells);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSellsList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());
        GetSells();
        final EditText search = root.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button hiden =  root.findViewById(R.id.clickto);
        hiden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setVisibility(View.GONE);
            }
        });
        notificationsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });




        return root;
    }



    private void GetSells() {
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
                                byte[] decodedString = Base64.decode(images, Base64.DEFAULT);
                                if(!state.equals("SOLD"))
                                mSellsList.add(new Parts(id,owner,name,other1,price,other2,other3,type,state,decodedString,tag_description,created,ref));

                            }

                            mSellsAdapter = new sellsAdapter(getActivity(), mSellsList, new sellsAdapter.DetailsAdapterListener() {
                                @Override
                                public void DetailOnClick(View v, int position) {
                                    Bundle data = new Bundle();
                                    data.putString("owner",mSellsList.get(position).getOwner());
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
                                    Navigation.findNavController(v).navigate(R.id.action_navigation_market_place_to_viewalone,data);
                                }
                            });
                            mRecyclerView.setAdapter(mSellsAdapter);

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

        mRequestQueue.add(request);
    }
}