package com.example.projeecto.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.User_account;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;


import com.example.projeecto.R;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private  EditText username_l,password_l;
    private DashboardViewModel dashboardViewModel;
    private RequestQueue requestQueue;
    private static final String EMAIL = "email";
    private  static  final  String PUBLICPROFILE = "public_profile";
    private  CallbackManager callbackManager;
    private String username;
    private LoginButton loginButton;
    private Button signIn;
    private static final String URL = "http://10.0.2.2:5000/api/login";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadData(view);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        callbackManager = CallbackManager.Factory.create();
        checkLoginStatus();
        loginButton =  root.findViewById(R.id.login_button);

        loginButton.setPermissions(Arrays.asList(EMAIL,PUBLICPROFILE));
        loginButton.setFragment(this);
        Button fb =  root.findViewById(R.id.fb);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick(); }});

        username_l = root.findViewById(R.id.username_login);
        password_l = root.findViewById(R.id.password_login);
        signIn = root.findViewById(R.id.signin);

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        signIn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(final View v) {

                                          HashMap < String, String > params = new HashMap<String, String>();

                                          params.put("username", username_l.getText().toString());
                                          params.put("password", password_l.getText().toString());
                                          // the entered data as the JSON body.
                                          JsonObjectRequest jsObjRequest = new
                                                  JsonObjectRequest(Request.Method.POST,
                                                  URL,
                                                  new JSONObject(params),
                                                  new Response.Listener<JSONObject>() {
                                                      @Override
                                                      public void onResponse(JSONObject response) {
                                                          try {
                                                              if(response.getString("message").equals("JSON Data received successfully")) {
                                                                  username = username_l.getText().toString();
                                                                  saveDataLogin(username);
                                                                  loadData(root);

                                                              }else
                                                              {
                                                                  Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                                              }
                                                          } catch (JSONException e) {

                                                              e.printStackTrace();
                                                          }
                                                      }
                                                  }, new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {
                                                  Toast.makeText(getActivity(), "what"+error.getMessage(), Toast.LENGTH_SHORT).show();
                                                  Log.e("VOLLEY", error.getMessage());
                                              }
                                          });

                                          requestQueue.add(jsObjRequest);
                                      }


                                  });




        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUserData(AccessToken newAccessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");
                    saveDataFacebook(first_name,last_name,email,id);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
        Bundle params = new Bundle();
        params.putString("fields","first_name,last_name,email,id");
        request.setParameters(params);
        request.executeAsync();

    }
    private void checkLoginStatus()
    {
        if(AccessToken.getCurrentAccessToken()!=null){
            loadUserData(AccessToken.getCurrentAccessToken());

        }
    }
    private void loadFragment(View view){


        Navigation.findNavController(view).navigate(R.id.emptyfragi);

    }
    public void saveDataLogin(String username)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",username);
        editor.apply();
        Toast.makeText(this.getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public  void saveDataFacebook(String firstName,String lastName,String email,String id)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name",firstName);
        editor.putString("last_name",lastName);
        editor.putString("email",email);
        editor.putString("id",id);
        editor.apply();
        Toast.makeText(this.getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();

    }
    public void loadData(View view)
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        if(!username.isEmpty() || !username.equals(""))
        {
        loadFragment(view);
        }

    }



}