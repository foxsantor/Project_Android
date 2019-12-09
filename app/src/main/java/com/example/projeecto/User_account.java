package com.example.projeecto;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.tools.OnbackDestrecution;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class User_account extends Fragment implements OnbackDestrecution {
    EditText firstName,lastName,address,mobilePhone,email,password;
    TextView passwordView;
    String firstName_t,lastName_t,email_t,username,address_t,mobile_t;
    private UserAccountViewModel mViewModel;
    private RequestQueue requestQueue;
    private NavHostFragment mNavHostFragment;
    private  static final  String URL =MainActivity.SKELETON+"getUser";//http://10.0.2.2:5000/api/getUser";

    public static User_account newInstance() {
        return new User_account();
    }
    ProgressBar mProgressBar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        OnbackDestrecution();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.navigation_market_place);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        getUserData();

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.user_account_fragment, container, false);
        OnbackDestrecution();
        mProgressBar = root.findViewById(R.id.progress_bar);
        firstName = root.findViewById(R.id.firstname_p);
        lastName = root.findViewById(R.id.lastname_p);
        address = root.findViewById(R.id.adress_p);
        mobilePhone = root.findViewById(R.id.mobile_p);
        email = root.findViewById(R.id.email_p);




        return root;
    }


    private void loadDataForm()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        lastName_t = sharedPreferences.getString("last_name","");
        firstName_t = sharedPreferences.getString("first_name","");
        email_t = sharedPreferences.getString("email","");
        mobile_t = sharedPreferences.getString("mobile","");
        address_t = sharedPreferences.getString("address","");
        if(lastName_t.equals("null"))
        {
            lastName_t = "";
        }
        if(firstName_t.equals("null"))
        {
            firstName_t = "";
        }
        if(mobile_t.equals("null"))
        {
            mobile_t = "";
        }
        if(address_t.equals("null"))
        {
            address_t="";
        }

        lastName.setText(lastName_t);
        firstName.setText(firstName_t);
        email.setText(email_t);
        address.setText(address_t);
        mobilePhone.setText(mobile_t);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserAccountViewModel.class);
        // TODO: Use the ViewModel
    }

    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }


    @Override
    public void OnbackDestrecution() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }

    public void saveDataUser(String username , String firstName , String lastname,String mobile,String address)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",username);
        editor.putString("first_name",firstName);
        editor.putString("last_name",lastname);
        editor.putString("mobile",mobile);
        editor.putString("address",address);
        editor.apply();

    }

    public  void getUserData() {
        username=loadUsername();
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        HashMap< String, String > params = new HashMap<String, String>();
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


                            if(response.length() > 0) {
                                JSONObject jresponse = response.getJSONObject(0);

                                saveDataUser(jresponse.getString("username"),
                                        jresponse.getString("firstname"),
                                        jresponse.getString("lastname"),
                                        jresponse.getString("tele_num"),
                                        jresponse.getString("address"));
                                loadDataForm();
                                mProgressBar.setVisibility(View.GONE);

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
