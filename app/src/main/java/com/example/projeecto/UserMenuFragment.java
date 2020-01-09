package com.example.projeecto;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.widget.NestedScrollView;
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
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projeecto.adapters.SectionsPagerAdapter;
import com.example.projeecto.tools.OnbackDestrecution;
import com.example.projeecto.tools.ProgressBarAnimation;
import com.facebook.login.LoginManager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class UserMenuFragment extends Fragment{

    private EmptyfragiViewModel mViewModel;
    private Button persodata,myGarage,chat;
    private RequestQueue requestQueue;
    private MaterialButton logout;
    private ConstraintLayout hider,lois ;
    private AppBarLayout appbar;
    private ImageButton refresh;
    private ImageView background;
    public static int checker = 0;
    private  String lastName_t,firstName_t,created;
    private String username;
    private TextView avatar2,textView44,textView48;
    private  static final  String URL = MainActivity.SKELETON+"getUser";

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
        chat = view.findViewById(R.id.chat);
        myGarage = view.findViewById(R.id.myGarage);
        avatar2 = view.findViewById(R.id.avatar2);
        textView44 = view.findViewById(R.id.textView44);
        textView48 = view.findViewById(R.id.textView48);
        refresh = view.findViewById(R.id.refresh);
        hider = view.findViewById(R.id.layouthider);
        appbar = view.findViewById(R.id.appbar);
        lois = view.findViewById(R.id.lois);
        background = view.findViewById(R.id.imageView11);
        Glide.with(getActivity()).asDrawable().load(R.drawable.background).into(background);
        getUserData();

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
         Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.user_account);

                                         }
                                     }
        );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
                removeAllUser();
                checker = 0;
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

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.liveChat2);

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

    public void removeAllUser()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("care", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }


    public  void getUserData() {

        //hider.setVisibility(View.GONE);
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
                                if(checker == 0){
                                         saveDataUser(jresponse.getString("username"),
                                                 firstName_t,
                                                 lastName_t,
                                        jresponse.getString("tele_num"),
                                        jresponse.getString("address"),
                                                 jresponse.getString("provider_facebook"),created,jresponse.getString("modified"));
                                checker =1;
                            }
                            }
                            //lois.setVisibility(View.VISIBLE);
                            //appbar.setVisibility(View.VISIBLE);

                            loadDataForm();
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //lois.setVisibility(View.GONE);
                //appbar.setVisibility(View.GONE);
                //hider.setVisibility(View.VISIBLE);
                loadDataForm();
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
    public void saveDataUser(String username , String firstName , String lastname,String mobile,String address,String provider,String created,String modified)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("care", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",username);
        editor.putString("first_name",firstName);
        editor.putString("last_name",lastname);
        editor.putString("mobile",mobile);
        editor.putString("address",address);
        editor.putString("Provider_facebook",provider);
        editor.putString("created",created);
        editor.putString("modified",modified);
        editor.apply();

    }
    private void loadDataForm()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("care", Context.MODE_PRIVATE);
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
        avatar2.setText(lastName_t.toUpperCase().charAt(0)+"");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle owna = getArguments();
        owner = owna.getString("email");
        fullname = owna.getString("Full_name");*/
        socket.connect();
        socket.on("message", handleIncomingMessages);
        socket.on("identify",handler);
        socket.on("chat message", handleIncomingMessages);
        if (socket.connected()){
            Toast.makeText(getActivity(), "Connected!!",Toast.LENGTH_SHORT).show();
        }

    }

    private Socket socket;
    {
        try{
            socket = IO.socket("http://192.168.1.5:5000");
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }
    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    String username;
                    //String imageText;
                    try {
                        message = data.getString("text").toString();
                        username = data.getString("username").toString();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),"123")
                                .setSmallIcon(R.drawable.ic_chat_bubble_outline_black_24dp)
                                .setContentTitle(username)
                                .setContentText(message)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
                        notificationManager.notify(1, builder.build());

                        //addMessage(message,username);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    };

    private Emitter.Listener handler = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}
