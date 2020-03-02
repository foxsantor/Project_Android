package com.localparts.projeecto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class User_account extends Fragment  {

    private TextInputLayout firstName,lastName,address,mobilePhone,password;
    private ImageButton edit1_firstname,edit2_lastname,edit3_password,edit7_mobile,edit6_address;
    private TextView firstname_x,lasstname_x,mobile_x,username_x,address_x,password_x,avatar4,modified;
    private String firstName_t,lastName_t,email_t,username,address_t,mobile_t,modified_t;
    private UserAccountViewModel mViewModel;
    private RequestQueue requestQueue;
    private NavHostFragment mNavHostFragment;
    private AppBarLayout appbar;
    private Button refresh;
    private ImageView background;
    private Toolbar toolbar;
    private Menu collapsedMenu;
    private ConstraintLayout nocon,firstnameLayout,lastnameLayout,passwordLayout,addressLayout,mobileLayout,firstnameView,lastnameView,passwordView,addressView,mobileView;
    private CollapsingToolbarLayout collaspsing;
    private boolean appBarExpanded=true;
    private ProgressBar mProgressBar;
    private  static final  String URL =MainActivity.SKELETON+"getUser";//http://10.0.2.2:5000/api/getUser";
    private static final String URL_update = MainActivity.SKELETON+"updateUserF";

    public static User_account newInstance() {
        return new User_account();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //getUserData();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.user_account_fragment, container, false);
        setHasOptionsMenu(true);

        edit1_firstname = root.findViewById(R.id.edit1);
        edit2_lastname = root.findViewById(R.id.edit2);
        edit3_password= root.findViewById(R.id.edit3);
        edit7_mobile= root.findViewById(R.id.edit7);
        edit6_address = root.findViewById(R.id.edit6);
        firstname_x = root.findViewById(R.id.firstname);
        lasstname_x = root.findViewById(R.id.lastname);
        mobile_x = root.findViewById(R.id.Mobile);
        username_x = root.findViewById(R.id.email);
        address_x = root.findViewById(R.id.address);
        password_x = root.findViewById(R.id.Password);
        modified = root.findViewById(R.id.modified);
        mProgressBar = root.findViewById(R.id.progress_bar);
        firstName = root.findViewById(R.id.firstnameedit);
        lastName = root.findViewById(R.id.lastnameedit);
        password =root.findViewById(R.id.Passwordedit);
        address = root.findViewById(R.id.addressedit);
        mobilePhone = root.findViewById(R.id.mobiledit);
        avatar4 = root.findViewById(R.id.avatar4);
        appbar = root.findViewById(R.id.appbar);
        toolbar = root.findViewById(R.id.toolbar);
        collaspsing = root.findViewById(R.id.collaspsing);
        background = root.findViewById(R.id.header);
        nocon = root.findViewById(R.id.nocon);
        refresh = root.findViewById(R.id.refresh);

        if(!haveNetworkConnection())
        {
            nocon.setVisibility(View.VISIBLE);
            edit1_firstname.setEnabled(false);
            edit2_lastname.setEnabled(false);
            edit3_password.setEnabled(false);
            edit6_address.setEnabled(false);
            edit7_mobile.setEnabled(false);
        }


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveNetworkConnection())
                {
                    edit1_firstname.setEnabled(true);
                    edit2_lastname.setEnabled(true);
                    edit3_password.setEnabled(true);
                    edit6_address.setEnabled(true);
                    edit7_mobile.setEnabled(true);
                    nocon.setVisibility(View.GONE);
                }else
                {
                    return;
                }
            }
        });

        firstnameLayout = root.findViewById(R.id.namelayout);
        lastnameLayout= root.findViewById(R.id.lastnamelayout);
        passwordLayout= root.findViewById(R.id.Passwordlayout);
        addressLayout= root.findViewById(R.id.addresslayout);
        mobileLayout= root.findViewById(R.id.mobilelayout);

        firstnameView= root.findViewById(R.id.firstnameView);
        lastnameView= root.findViewById(R.id.lastnameView);
        passwordView= root.findViewById(R.id.PasswordView);
        addressView= root.findViewById(R.id.adddessView);
        mobileView= root.findViewById(R.id.mobileView);

        firstName.getEditText().setClickable(false);




        Glide.with(getActivity()).asDrawable().load(R.drawable.background).into(background);
        avatar4.setVisibility(View.GONE);
        loadDataForm();
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.DrakLord));
                collaspsing.setContentScrimColor(vibrantColor);
                collaspsing.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //Log.d(User_account.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);
                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) == 336) {
                    appBarExpanded = false;

                    getActivity().invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    getActivity().invalidateOptionsMenu();
                }
            }
        });

        edit1_firstname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    edit1_firstname.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                firstName.getEditText().requestFocus();
                firstnameView.setVisibility(View.GONE);
                Animator(firstName,firstnameLayout,0,0);
            }

        });
        firstName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),firstName.getEditText().getText().toString(),"firstname",firstname_x);
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);

                    return true;
                }
                return false;
            }
        });
        address.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),address.getEditText().getText().toString(),"address",address_x);
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                    return true;
                }
                return false;
            }
        });
        password.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    Toast.makeText(getActivity(), "password here ", Toast.LENGTH_SHORT).show();
                   /* UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),firstName.getEditText().getText().toString(),"firstname",firstname_x);
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(password,firstnameLayout,1,0);*/
                    return true;
                }
                return false;
            }
        });
        lastName.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),lastName.getEditText().getText().toString(),"lastname",lasstname_x);
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                    return true;
                }
                return false;
            }
        });
        mobilePhone.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    UserMenuFragment.checker=0;
                    UpadateUser(username=loadUsername(),mobilePhone.getEditText().getText().toString(),"phone",mobile_x);
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                    return true;
                }
                return false;
            }
        });

        edit2_lastname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    edit1_firstname.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                lastName.getEditText().requestFocus();
                lastnameView.setVisibility(View.GONE);
                Animator(lastName,lastnameLayout,0,0);
            }
        });


        edit3_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    edit1_firstname.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                passwordView.setVisibility(View.GONE);
                Animator(password,passwordLayout,0,0);
            }
        });


        edit6_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    edit1_firstname.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                if(mobilePhone.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(mobileView,mobileLayout,0,1);
                    Animator(mobilePhone,mobileLayout,1,0);
                }
                address.getEditText().requestFocus();
                addressView.setVisibility(View.GONE);
                Animator(address,addressLayout,0,0);
            }
        });


        edit7_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!haveNetworkConnection())
                {
                    nocon.setVisibility(View.VISIBLE);
                    edit1_firstname.setEnabled(false);
                    edit2_lastname.setEnabled(false);
                    edit3_password.setEnabled(false);
                    edit6_address.setEnabled(false);
                    edit7_mobile.setEnabled(false);
                    return;
                }
                if(lastName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(lastnameView,lastnameLayout,0,1);
                    Animator(lastName,lastnameLayout,1,0);
                }
                if(password.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(passwordView,passwordLayout,0,1);
                    Animator(password,passwordLayout,1,0);
                }
                if(address.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(addressView,addressLayout,0,1);
                    Animator(address,addressLayout,1,0);
                }
                if(firstName.getVisibility() == View.VISIBLE)
                {
                    AnimatorView(firstnameView,firstnameLayout,0,1);
                    Animator(firstName,firstnameLayout,1,0);
                }
                mobilePhone.getEditText().requestFocus();
                mobileView.setVisibility(View.GONE);
                Animator(mobilePhone,mobileLayout,0,0);

            }
        });





        return root;
    }


    private void loadDataForm()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("care", Context.MODE_PRIVATE);
        lastName_t = sharedPreferences.getString("last_name","");
        firstName_t = sharedPreferences.getString("first_name","");
        email_t = sharedPreferences.getString("email","");
        mobile_t = sharedPreferences.getString("mobile","");
        address_t = sharedPreferences.getString("address","");
        modified_t = sharedPreferences.getString("modified","");

        lastName.getEditText().setText(MainActivity.capitalize(lastName_t));
        lasstname_x.setText(MainActivity.capitalize(lastName_t));
        avatar4.setText(""+lastName_t.toUpperCase().charAt(0));
        firstName.getEditText().setText(MainActivity.capitalize(firstName_t));
        firstname_x.setText(MainActivity.capitalize(firstName_t));
        username_x.setText(email_t);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        try {
            Date date = format.parse(modified_t);
            DateFormat dateFormat = new SimpleDateFormat("dd LLLL yyyy");
            String strDate = dateFormat.format(date);
            modified.setText("Last modified: "+strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        collaspsing.setTitle(MainActivity.capitalize(lastName_t)+" "+MainActivity.capitalize(firstName_t));

        if(!address_t.equals("null")) {
            address.getEditText().setText(address_t);
        address_x.setText(address_t);}
        else{
        address_x.setText("");
            address.getEditText().setText("");
        }

        mobilePhone.getEditText().setText(mobile_t);
        mobile_x.setText(mobile_t);
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

    public void saveDataUser(String username , String firstName , String lastname,String mobile,String address)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("care", Context.MODE_PRIVATE);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
        collapsedMenu = menu;

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (collapsedMenu != null && !appBarExpanded) {
            //collapsed
            avatar4.setVisibility(View.VISIBLE);
        } else {
            avatar4.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        if (item.getTitle() == "Add") {
            Toast.makeText(getActivity(), "clicked add", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void Animator(TextInputLayout object, ViewGroup Layout, int state ,int direction )
    {
        Transition transition2;
        if(direction==0)
        {
             transition2 = new Slide(Gravity.RIGHT);
        }else
        {
            transition2 = new Slide(Gravity.RIGHT);
        }
        transition2.setDuration(300);
        transition2.addTarget(object.getId());
        TransitionManager.beginDelayedTransition(Layout, transition2);
        if(state ==0){
        object.setVisibility(View.VISIBLE);
        }else {
          object.setVisibility(View.GONE);
        }}


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void AnimatorView(ViewGroup object, ViewGroup Layout, int state ,int direction )
    {
        Transition transition2;
        if(direction==0)
        {
            transition2 = new Slide(Gravity.RIGHT);
        }else
        {
            transition2 = new Slide(Gravity.RIGHT);
        }
        transition2.setDuration(300);
        transition2.addTarget(object.getId());
        TransitionManager.beginDelayedTransition(Layout, transition2);
        if(state ==0){
            object.setVisibility(View.VISIBLE);
        }else {
            object.setVisibility(View.GONE);
        }}


    private void UpadateUser(String username, final String target , String label, final TextView targetView)
    {
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap< String, String > params = new HashMap<String, String>();
        params.put("username", username);
        params.put(label,target);
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL_update, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            if(response.has("success")) {

                                targetView.setText(MainActivity.capitalize(target));
                                DateModfied();
                            }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost to the Server", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
                return;
            }
        });
        requestQueue.add(jsObjRequest);
    }


    private  boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile || MainActivity.getConnction();
    }
    private void DateModfied()
    {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("dd LLLL yyyy");
            String strDate = dateFormat.format(date);
            modified.setText("Last modified: "+strDate);

    }

}
