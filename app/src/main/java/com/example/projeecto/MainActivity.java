package com.example.projeecto;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private ConstraintLayout container;
    private static boolean res;
    private static Context conx;
    private boolean response;
    boolean isKeyboardShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);

        conx = this;
        //Navigation
        final BottomNavigationView navView = findViewById(R.id.nav_view);
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
        final View view= this.getWindow().getDecorView();
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                                new ViewTreeObserver.OnGlobalLayoutListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                    @Override
                                    public void onGlobalLayout() {

                                        Rect r = new Rect();
                                        view.getWindowVisibleDisplayFrame(r);
                                        int screenHeight = view.getRootView().getHeight();

                                        // r.bottom is the position above soft keypad or device button.
                                        // if keypad is shown, the r.bottom is smaller than that before.
                                        int keypadHeight = screenHeight - r.bottom;


                                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                                            // keyboard is opened
                                            if (!isKeyboardShowing) {

                                                isKeyboardShowing = true;
                                                navView.setVisibility(View.GONE);

                                            }
                                        }
                                        else {
                                            // keyboard is closed
                                            if (isKeyboardShowing) {
                                                isKeyboardShowing = false;
                                                Transition transition = new Slide(Gravity.BOTTOM);
                                                transition.setDuration(300);
                                                transition.addTarget(navView.getId());
                                                TransitionManager.beginDelayedTransition(container, transition);
                                                navView.setVisibility(View.VISIBLE);

                                            }
                                        }
                    }
                });
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

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }




}




