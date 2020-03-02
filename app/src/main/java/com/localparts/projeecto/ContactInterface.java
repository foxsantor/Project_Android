package com.localparts.projeecto;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactInterface extends Fragment  {



    private Button email,call,chat;
    private static final String URL= MainActivity.SKELETON+"getUser";
    private TextView reagarding,price,contacting;
    private String owner,name,priceu,firstname,lastname,tele,fullName;
    private RequestQueue requestQueue;





    public ContactInterface() {
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

        return inflater.inflate(R.layout.fragment_contact_interface, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        email = view.findViewById(R.id.email);
        call = view.findViewById(R.id.call);
        chat = view.findViewById(R.id.chat);
        reagarding = view.findViewById(R.id.reagrding);
        price = view.findViewById(R.id.price);
        contacting = view.findViewById(R.id.contacting);
        final Bundle  data = getArguments();
        if (data != null) {
            owner = data.getString("owner");
            priceu = data.getString("price");
            name = data.getString("name");
            getUserData(owner);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent callIntent = new Intent(Intent.ACTION_DIAL);//ACTION_DIAL);
                String fullTele;
                if(!tele.contains("+216"))
                    fullTele = "+216"+tele;
                else
                    fullTele = tele;
                callIntent.setData(Uri.parse("tel:"+fullTele));
                startActivity(callIntent);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",owner, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Deal : "+name);
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

       chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle owners = new Bundle();
                owners.putString("email",owner);
                owners.putString("Full_name",fullName);
                owners.putChar("first_Letter",lastname.toUpperCase().charAt(0));
                Navigation.findNavController(getView()).navigate(R.id.action_contactInterface_to_liveChat2,owners);
            }
        });

    }
    public  void getUserData(String owner) {

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        HashMap< String, String > params = new HashMap<String, String>();
        params.put("username", owner);
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

                            if(response.length() > 0) {
                                JSONObject jresponse = response.getJSONObject(0);

                                        firstname = jresponse.getString("firstname");
                                        lastname = jresponse.getString("lastname");
                                        tele = jresponse.getString("tele_num");
                                    fullName = lastname +" "+ firstname;
                                price.setText(Html.fromHtml("Suggested Price by the owner is <strong>"+priceu+"</strong>"));
                                reagarding.setText(Html.fromHtml("Regarding the Deal named <font color='#536878'><strong>"+name+"</strong></font>"));
                                contacting.setText(Html.fromHtml("Contacting <strong>"+fullName+"</strong>"));

                                //mProgressBar.setVisibility(View.GONE);
                            }else
                            {
                                Toast.makeText(getActivity(), "error retrieving data", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost "+error, Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsObjRequest);
    }




}
