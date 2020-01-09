package com.example.projeecto;


import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.DealCatAdapter;
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
public class CategoryView extends Fragment {

    private RequestQueue requestQueue;
    private RecyclerView auction_ree,deals_ree;
    private TextView cat_name,notfound;
    private NestedScrollView nested;
    private String key ;
    private DealCatAdapter dealCatAdapter;
    private ArrayList<Parts> parts;
    private ConstraintLayout loading_1;
    private static final String URL_GET_DEALS = MainActivity.SKELETON+"parts/getCatDeal";
    private static final String URL_GET_AUCTIONS = MainActivity.SKELETON+"parts/getCatAuction";
    private static final String URL_Vues = MainActivity.SKELETON+"parts/UpdateVues";


    public CategoryView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root =  inflater.inflate(R.layout.fragment_category_view, container, false);
         parts = new ArrayList<>();
         auction_ree = root.findViewById(R.id.auctionss);
         deals_ree = root.findViewById(R.id.deals_cat);
         cat_name = root.findViewById(R.id.catname);
         notfound = root.findViewById(R.id.notfound);
        notfound.setVisibility(View.GONE);
         nested = root.findViewById(R.id.nested);

         Bundle data = getArguments();
         key = data.getString("key","");
         cat_name.setText(key);
         getDeals(key);
        return root;
    }

    private void getDeals(String key)
    {
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap< String, String > params = new HashMap<String, String>();
        params.put("key", key);
        // the entered data as the JSON body.
        final JsonArrayRequest jsObjRequest = new
                JsonArrayRequest(
                Request.Method.POST,
                URL_GET_DEALS,
                new JSONObject(params),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.length() > 0) {
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
                                     parts.add(new Parts(id,owner,name,other1,price,other2,other3,type,state,decodedString,tag_description,created,ref,vues));
                                    //loading.setVisibility(View.GONE);
                                }

                                dealCatAdapter =new DealCatAdapter(getActivity(), parts, new DealCatAdapter.OnClickedListner() {
                                    @Override
                                    public void onClicked(int position) {
                                        Bundle data = new Bundle();
                                        data.putString("owner",parts.get(position).getOwner());
                                        data.putInt("idparts",parts.get(position).getId());
                                        data.putString("state",parts.get(position).getState());
                                        data.putString("name",parts.get(position).getName());
                                        data.putString("other1",parts.get(position).getOther1());
                                        data.putString("other2",parts.get(position).getOther2());
                                        data.putString("other3",parts.get(position).getOther3());
                                        data.putString("Type",parts.get(position).getType());
                                        data.putByteArray("image",parts.get(position).getImage());
                                        data.putString("refrnce",parts.get(position).getRefrence());
                                        data.putFloat("price",parts.get(position).getPrice());
                                        data.putString("tag_description",parts.get(position).getTag_desc());
                                        data.putString("Created",parts.get(position).getCreated());
                                        data.putInt("vues",parts.get(position).getVues());
                                        UpdateVues(String.valueOf(parts.get(position).getId()));
                                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);

                                    }
                                });
                                        nested.setVisibility(View.VISIBLE);
                                        deals_ree.setAdapter(dealCatAdapter);
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nested.setVisibility(View.GONE);
                notfound.setVisibility(View.VISIBLE);
                error.printStackTrace();
                return;

            }
        });
        requestQueue.add(jsObjRequest);
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
        requestQueue.add(jsObjRequest);
    }

}
