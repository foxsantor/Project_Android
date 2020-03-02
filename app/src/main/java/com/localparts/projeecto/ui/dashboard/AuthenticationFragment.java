package com.localparts.projeecto.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.localparts.projeecto.MainActivity;
import com.localparts.projeecto.tools.StaticUse;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;



import com.localparts.projeecto.R;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;
import java.util.HashMap;

public class AuthenticationFragment extends Fragment {


    private RequestQueue requestQueue;
    private TextInputLayout password_l,username_l;
    private static final String EMAIL = "email";
    private  static  final  String PUBLICPROFILE = "public_profile";
    private  CallbackManager callbackManager;
    private String username, strEmail,strName,strId,strFirstname,strLastname;
    private  String [] names;
    private LoginButton loginButton;
    private Button signIn ,fb,register;
    private static final String URL = MainActivity.SKELETON+"login";//"http://10.0.2.2:5000/api/login";
    private static final String URL_PROVIDER = MainActivity.SKELETON+"registaprovider";//http://10.0.2.2:5000/api/registaprovider";
    private String[] PERMISSION = new String[]{"user_photos", "email",
            "public_profile", "user_friends",
            "user_likes", "user_hometown",
            "user_birthday"};
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadData(view);
        register = view.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll_check();
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_account_to_registerFragment_1);
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        final View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //initializing graphic components
        loginButton = root.findViewById(R.id.login_button);
        fb =  root.findViewById(R.id.fb);

        username_l = root.findViewById(R.id.email_login);
        password_l = root.findViewById(R.id.username_text_input_layout);
        signIn = root.findViewById(R.id.signin);
        //login facebook button customization
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
                doFBLogin();
            }});


        //volley Req
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        signIn.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(final View v) {
                                          if(!StaticUse.validateEmpty(username_l,"Email")| !StaticUse.validateEmail(username_l) | !StaticUse.validateEmpty(password_l,"Password"))
                                          {return;}
                                          else{ Login();}
                                      }
                                  });

        return root;
    }




    private void loadFragment(View view){


        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.emptyfragi);

    }

    public void saveDataLogin(String username)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",username);

        editor.apply();

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


    private void doFBLogin() {
        callbackManager = CallbackManager.Factory.create();
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(PERMISSION));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject json,
                                    GraphResponse response) {
                                if (response.getError() != null) {
                                    Toast.makeText(getActivity(), "failed login", Toast.LENGTH_SHORT).show();
                                } else {
                                    try {

                                        strEmail = json.getString("email");
                                        strName = json.getString("name");
                                        strId = json.getString("id");
                                        names = strName.split(" ",2);
                                        strFirstname= names[0];
                                        strLastname = names[1];
                                        RegisterProvider(strEmail,strFirstname,strLastname,strId,"1");
                                        loadFragment(getView());

                                    } catch (JSONException e) {
                                    }
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,first_name,last_name,picture.type(large)");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getActivity(), "error"+error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void Login()
    {
        HashMap < String, String > params = new HashMap<String, String>();

        params.put("username", username_l.getEditText().getText().toString());
        params.put("password", password_l.getEditText().getText().toString());
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.getString("message").equals("JSON Data received successfully")) {

                                Toast.makeText(getActivity(), ""+response.getString("message"), Toast.LENGTH_SHORT).show();
                            }else
                            {
                                username = username_l.getEditText().getText().toString();
                                saveDataLogin(username);
                                loadData(getView());
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", error.getMessage());
            }
        });

        requestQueue.add(jsObjRequest);



    }
    public void removeAll_check()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    private void RegisterProvider(String username,String firstname, String lastname, String uniqueid,String provider)
    {
        HashMap < String, String > params = new HashMap<String, String>();

        params.put("username",  username);
        saveDataLogin(username);
        params.put("provider_facebook",  provider);
        params.put("uniqueid",  uniqueid);
        params.put("firstname", firstname);
        params.put("lastname",  lastname);

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL_PROVIDER,
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