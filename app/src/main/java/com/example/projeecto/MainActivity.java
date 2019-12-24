package com.example.projeecto;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.tools.Dialogue;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private String username;
    private NavHostFragment mNavHostFragment;
    public static final String SKELETON = "http://192.168.1.10:5000/api/"; //"http://192.168.43.242:5000/api/";
    private static final String URL = SKELETON;
    private static RequestQueue requestQueue;
    private static boolean res;
    private static Context conx;
    private boolean response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        conx = this;
        //Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_market_place, R.id.navigation_bookmarks, R.id.navigation_account, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        response = MainActivity.checekConnection();
        ActionBar bar = getSupportActionBar();
        getSupportActionBar().hide();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3B3F42")));
    }


    public static boolean checekConnection() {
        requestQueue = Volley.newRequestQueue(conx);
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.GET,
                URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        if (response.has("message")) {
                            res = true;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                res = false;

            }
        });

        requestQueue.add(jsObjRequest);
        return res;
    }
}




