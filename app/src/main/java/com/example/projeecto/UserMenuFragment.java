package com.example.projeecto;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.SectionsPagerAdapter;
import com.example.projeecto.tools.OnbackDestrecution;
import com.example.projeecto.tools.ProgressBarAnimation;
import com.facebook.login.LoginManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UserMenuFragment extends Fragment{

    private EmptyfragiViewModel mViewModel;
    private Button persodata,myGarage;
    private RequestQueue requestQueue;
    private MaterialButton logout;
    private ConstraintLayout hider;
    private ImageButton refresh;
    private  String lastName_t,firstName_t,created;
    private static int checker =0;
    private String username;
    private TextView avatar2,textView44,textView48;
    private  static final  String URL =MainActivity.SKELETON+"getUser";//http://10.0.2.2:5000/api/getUser";

    public static UserMenuFragment newInstance() {
        return new UserMenuFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        persodata = view.findViewById(R.id.persodata);
        logout = view.findViewById(R.id.Logouto);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getActivity(), getChildFragmentManager());
        ViewPager viewPager =  view.findViewById(R.id.viewer);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs =  view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        myGarage = view.findViewById(R.id.myGarage);
        avatar2 = view.findViewById(R.id.avatar2);
        textView44 = view.findViewById(R.id.textView44);
        textView48 = view.findViewById(R.id.textView48);
        getUserData();
        loadDataForm();







        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.navigation_market_place);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        persodata.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
         Navigation.findNavController(getView()).navigate(R.id.action_emptyfragi_to_user_account);

                                         }
                                     }
        );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
                LoginManager.getInstance().logOut();
                Navigation.findNavController(getView()).navigate(R.id.navigation_account);
            }
        });
        myGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_emptyfragi_to_myGarage_fragment);
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.emptyfragi_fragment, container, false);

            return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmptyfragiViewModel.class);
        // TODO: Use the ViewModel
    }

    public void removeAll()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

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

                                 firstName_t = jresponse.getString("firstname");
                                 lastName_t =  jresponse.getString("lastname");
                                 String createds = jresponse.getString("created");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
                                try {
                                    Date date = format.parse(createds);
                                    PrettyTime p = new PrettyTime();
                                    date = new Date(date.getTime()+1*3600*1000);
                                    created = p.format(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                         saveDataUser(jresponse.getString("username"),
                                                 firstName_t,
                                                 lastName_t,
                                        jresponse.getString("tele_num"),
                                        jresponse.getString("address"),
                                                 jresponse.getString("provider_facebook"),created);


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

                error.printStackTrace();
                return;

            }
        });

        requestQueue.add(jsObjRequest);



    }

    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }
    public void saveDataUser(String username , String firstName , String lastname,String mobile,String address,String provider,String created)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",username);
        editor.putString("first_name",firstName);
        editor.putString("last_name",lastname);
        editor.putString("mobile",mobile);
        editor.putString("address",address);
        editor.putString("Provider_facebook",provider);
        editor.putString("created",created);
        editor.apply();

    }
    private void loadDataForm()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
         lastName_t = sharedPreferences.getString("last_name","");
         firstName_t = sharedPreferences.getString("first_name","");
         String createds = sharedPreferences.getString("created","");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        try {
            Date date = format.parse(createds);
            PrettyTime p = new PrettyTime();
            date = new Date(date.getTime()+1*3600*1000);
            created = p.format(date);
            textView48.setText(created);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        textView44.setText(MainActivity.capitalize(lastName_t)+" "+MainActivity.capitalize(firstName_t));
        avatar2.setText(""+lastName_t.toUpperCase().charAt(0));

    }
}
