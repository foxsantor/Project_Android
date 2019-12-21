package com.example.projeecto.ui.notifications;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
    private NestedScrollView helper;
    private sellsAdapter mSellsAdapter;
    private ConstraintLayout validView,Refresh,loading;
    private Button restart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        restart = root.findViewById(R.id.restart);
        loading = root.findViewById(R.id.loading_1);
        helper = root.findViewById(R.id.helper);
        loading.setVisibility(View.GONE);
        validView = root.findViewById(R.id.bigView);
        mRecyclerView = root.findViewById(R.id.Sells);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mSellsList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());


        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                        Navigation.findNavController(v).navigate(R.id.notificationsFragment);
            }
        });
        GetSells();

        return root;
    }



    private void GetSells() {
        loading.setVisibility(View.VISIBLE);
        helper.setBackgroundColor(Color.parseColor("#FFF5F5F5"));
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                restart.setVisibility(View.GONE);
                                validView.setVisibility(View.VISIBLE);
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
                                loading.setVisibility(View.GONE);
                                helper.setBackgroundColor(Color.parseColor("#E6E9F0"));
                            }

                            mSellsAdapter = new sellsAdapter(getActivity(), mSellsList, new sellsAdapter.DetailsAdapterListener() {
                                @Override
                                public void DetailOnClick(View v, int position) {
                                    Bundle data = new Bundle();
                                    data.putString("owner",mSellsList.get(position).getOwner());
                                    data.putInt("idparts",mSellsList.get(position).getId());
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
                                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);
                                }
                            });
                            mRecyclerView.setAdapter(mSellsAdapter);

                        } catch (JSONException e) {
                            loading.setVisibility(View.GONE);
                            helper.setBackgroundColor(Color.parseColor("#E6E9F0"));
                            validView.setVisibility(View.GONE);
                            restart.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            final Handler handler = new Handler();
                validView.setVisibility(View.GONE);
                restart.setVisibility(View.INVISIBLE);

            handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loading.setVisibility(View.GONE);
                helper.setBackgroundColor( Color.parseColor("#E6E9F0"));
                restart.setVisibility(View.VISIBLE);
            }
        }, 3000);
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);
    }
}