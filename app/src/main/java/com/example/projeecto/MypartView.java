package com.example.projeecto;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projeecto.adapters.spinnerAdapter;
import com.example.projeecto.tools.editText2;
import com.android.volley.RequestQueue;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MypartView extends Fragment {


    private ImageView container,dummy;
    private FloatingActionButton addImage,addImageCamera;
    private static final int GALLERY_REQUEST = 1;
    private String base64Code,idparts,cateegory,o1,o2,o3,statusSell;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private static final String URL = MainActivity.SKELETON+"parts/updatePart";
    private static final String URL1 = MainActivity.SKELETON+"parts/addSell";
    private static final String URL_Neutralizer = MainActivity.SKELETON+"parts/neutralizeParts";
    private TextView creationDate,numberComments,numberViews;
    private Switch activator;
    private Toolbar toolbar;
    private AppBarLayout appbar;
    private ImageButton others,close1,close2,close3;
    private Menu collapsedMenu;
    private CollapsingToolbarLayout collaspsing;
    private boolean appBarExpanded=true;
    private static final String URL_GETSELL = MainActivity.SKELETON+"parts/getUsernameandComments";
    private Button save,stateButton,refresh;
    private RequestQueue requestQueue;
    private Spinner category;
    private ConstraintLayout otherSets,other1Set,other2Set,other3Set,loading,sellView,nocon;
    private TextInputLayout other1x,other2x,other3x,other1,other2,other3,name,refrence,tag_desc,price,textInputLayoutspin;

    public MypartView() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mypart_view, container, false);
    }
    private void SpinnerLoader(Spinner spinner)
    {
        ArrayList<String> spinnerArray =  new ArrayList<>();
        spinnerArray.add("Choose a Category *");
        spinnerArray.add("Brakes");
        spinnerArray.add("Filtering/Oil");
        spinnerArray.add("Suspension and Steering");
        spinnerArray.add("Transmission-Gearbox");
        spinnerArray.add("Exterior/Interior Equipment and Accessories");
        spinnerArray.add("Engine compartment");
        spinnerArray.add("Exhaust");
        spinnerArray.add("Electrical and lighting");
        spinnerArray.add("Air conditioning");
        spinnerArray.add("Locks-closures");
        spinnerArray.add("Tyres");
        spinnerArray.add("Others");
        spinnerAdapter adapter = new spinnerAdapter(getActivity(),spinnerArray);
        spinner.setAdapter(adapter);
    }
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        creationDate = view.findViewById(R.id.creeationdate);
        numberComments = view.findViewById(R.id.numberofcomments);
        numberViews = view.findViewById(R.id.numberofview);
        sellView = view.findViewById(R.id.sellview);
        stateButton = view.findViewById(R.id.buttonsate);
        textInputLayoutspin = view.findViewById(R.id.textInputLayoutspin);
        price = view.findViewById(R.id.sellInput);
        nocon = view.findViewById(R.id.nocon);
        dummy = view.findViewById(R.id.dummy);
        activator = view.findViewById(R.id.activator);
        collaspsing = view.findViewById(R.id.collaspsing);
        toolbar = view.findViewById(R.id.toolbar);
        container = view.findViewById(R.id.app_bar_image);
        addImage = view.findViewById(R.id.addImage);
        addImageCamera= view.findViewById(R.id.seealone);
        category =  view.findViewById(R.id.Category);
        refresh = view.findViewById(R.id.refresh);
        appbar = view.findViewById(R.id.appbar);
        SpinnerLoader(category);
        others = view.findViewById(R.id.others);
        otherSets = view.findViewById(R.id.othersets);
        other1Set = view.findViewById(R.id.other1set);
        other2Set = view.findViewById(R.id.other2set);
        other3Set = view.findViewById(R.id.other3set);
        close1 = view.findViewById(R.id.close1);
        close2 = view.findViewById(R.id.close2);
        close3 = view.findViewById(R.id.close3);
        save = view.findViewById(R.id.save);
        tag_desc = view.findViewById(R.id.tag_desc);
        name = view.findViewById(R.id.name);
        refrence = view.findViewById(R.id.refrence);
        other1 = view.findViewById(R.id.other1);
        other2 = view.findViewById(R.id.other2);
        other3 = view.findViewById(R.id.other3);
        other1x = view.findViewById(R.id.other1x);
        other2x = view.findViewById(R.id.other2x);
        other3x = view.findViewById(R.id.other3x);
        loading = view.findViewById(R.id.loading_1);
        //sell = view.findViewById(R.id.sell);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        if(!haveNetworkConnection())
        {
            nocon.setVisibility(View.VISIBLE);
            activator.setEnabled(false);
            save.setEnabled(false);
            save.setClickable(false);
        }

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(haveNetworkConnection())
                {
                    activator.setEnabled(true);
                    save.setEnabled(true);
                    save.setClickable(true);
                    nocon.setVisibility(View.GONE);
                }else
                {
                    return;
                }
            }
        });



        Bundle data = getArguments();

        if(null!= data) {

            name.getEditText().setText(data.getString("name"));
            statusSell = data.getString("Sell");
            idparts = data.getString("idparts");
            if(!data.getString("other1").isEmpty() || !data.getString("other1").equals("") ){
                otherSets.setVisibility(View.VISIBLE);
             other1Set.setVisibility(View.VISIBLE);
             o1 =data.getString("other1");
            String[] other1TextView;
            other1TextView = o1.split(",");
            other1x.getEditText().setText(other1TextView[0]);
            other1.getEditText().setText(other1TextView[1]);}

            if(!data.getString("other2").isEmpty()|| !data.getString("other2").equals("")){
                otherSets.setVisibility(View.VISIBLE);
                other2Set.setVisibility(View.VISIBLE);
                o2 =data.getString("other2");
                String[] other2TextView;
                other2TextView = o2.split(",");
                other2x.getEditText().setText(other2TextView[0]);
                other2.getEditText().setText(other2TextView[1]);}

            if(!data.getString("other3").isEmpty()|| !data.getString("other3").equals("")){
                otherSets.setVisibility(View.VISIBLE);
                other3Set.setVisibility(View.VISIBLE);
                o3 =data.getString("other3");

                DateCreated(data.getString("Created"));
                String[] other3TextView;
                other3TextView = o3.split(",");
                other3x.getEditText().setText(other3TextView[0]);
                other3.getEditText().setText(other3TextView[1]);}

            if(data.getString("Sell").equals("YES"))
            {
               activator.setChecked(true);
               sellView.setVisibility(View.VISIBLE);
               price.getEditText().setText(String.valueOf(data.getFloat("Price")));
            }

            //other1.setText(data.getString("other1"));
            //other2.setText(data.getString("other2"));
            //other3.setText(data.getString("other3"));
            refrence.getEditText().setText(data.getString("refrnce"));
            tag_desc.getEditText().setText(data.getString("tag_description"));
            cateegory=data.getString("Type");
            category.setSelection(getIndex(category,cateegory));
            byte[] image = data.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            Glide.with(getActivity()).asBitmap().load(bmp).into(container);

            /*Palette.from(bmp).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(Palette palette) {
                    int vibrantColor = palette.getVibrantColor(getResources().getColor(R.color.DrakLord));
                    collaspsing.setContentScrimColor(vibrantColor);
                    collaspsing.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary));
                }
            });*/

        }
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


        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherSets.setVisibility(View.VISIBLE);
                if(other1Set.getVisibility() == View.GONE)
                {
                    other1Set.setVisibility(View.VISIBLE);

                }else if (other1Set.getVisibility() == View.VISIBLE && other2Set.getVisibility() == View.GONE)
                {
                    other2Set.setVisibility(View.VISIBLE);

                }else if(other2Set.getVisibility() == View.VISIBLE && other3Set.getVisibility() == View.GONE)
                {
                    other3Set.setVisibility(View.VISIBLE);

                }
                if(other2Set.getVisibility() == View.VISIBLE && other1Set.getVisibility() == View.VISIBLE && other3Set.getVisibility() == View.VISIBLE)
                {
                    others.setClickable(false);
                    others.setBackgroundResource(R.drawable.gray_add);

                }
            }
        });


        close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other1Set.setVisibility(View.GONE);
                others.setClickable(true);
                others.setBackgroundResource(R.drawable.ic_add_circle_accent_24dp);
                if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
                {
                    otherSets.setVisibility(View.GONE);
                }

            }
        });
        close2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other2Set.setVisibility(View.GONE);
                others.setClickable(true);
                others.setBackgroundResource(R.drawable.ic_add_circle_accent_24dp);
                if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
                {

                    otherSets.setVisibility(View.GONE);
                }

            }

        });
        close3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other3Set.setVisibility(View.GONE);
                others.setClickable(true);
                others.setBackgroundResource(R.drawable.ic_add_circle_accent_24dp);
                if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
                {
                    otherSets.setVisibility(View.GONE);
                }

            }
        });

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0)
                {
                    cateegory = (String) parent.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        /*addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });*/



       /* addImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });*/

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmpty(name,"Name")  | !validateSpinner(cateegory) | !validateOthers(other1x,other1) | !validateOthers(other2x,other2)| !validateOthers(other3x,other3)) {return;}else{
                    UpadatePart();}

            }
        });
        activator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sellView.setVisibility(View.VISIBLE);

                }else
                {
                    sellView.setVisibility(View.GONE);
                    Neutralize();

                }
            }
        });


        price.getEditText().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()!=KeyEvent.ACTION_DOWN){
                    return false;}
                if(keyCode == KeyEvent.KEYCODE_ENTER ){
                    if(!validateEmpty(price,"Price")){
                        price.getEditText().requestFocus();
                        return false;}else{
                        AddSell();
                        return true;
                    }
                }
                return false;

            }
        });
    }

    private String transformerImageBase64(ImageView container)
    {
        String base64String ;
        BitmapDrawable drawable = (BitmapDrawable) container.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        base64String = Base64.encodeToString(bb,0);
        //Toast.makeText(getActivity(), ""+base64String, Toast.LENGTH_SHORT).show();
        return base64String;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        dummy.setImageBitmap(bitmap);
                        Glide.with(getActivity()).asBitmap().load(bitmap).into(container);
                        base64Code=transformerImageBase64(dummy);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Glide.with(getActivity()).asBitmap().load(photo).into(container);
            dummy.setImageBitmap(photo);
            base64Code=transformerImageBase64(dummy);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    private void UpadatePart()
    {
        //loading.setVisibility(View.VISIBLE);

        HashMap<String,String> params = sortData();

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("succes")) {

                               // loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ""+response.getString("succes"), Toast.LENGTH_SHORT).show();
                                //openDialogue();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " internal error happened "+response.getString("notfound"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost to the Server", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsObjRequest);




    }
    private HashMap<String,String> sortData()
    {
        HashMap<String,String> data= new HashMap<>();
        String ref_data,name_data,other1_data,other2_data,other3_data,type_data,tag_desc_data,image_data;
        image_data = base64Code;
        ref_data = refrence.getEditText().getText().toString();
        name_data = name.getEditText().getText().toString();
        other1_data = other1x.getEditText().getText().toString()+","+other1.getEditText().getText().toString();
        other2_data = other2x.getEditText().getText().toString()+","+other2.getEditText().getText().toString();
        other3_data = other3x.getEditText().getText().toString()+","+other3.getEditText().getText().toString();
        tag_desc_data = tag_desc.getEditText().getText().toString();
        type_data = cateegory;

        if(ref_data.equals("null"))
        {
            ref_data = "";
        }
        if(tag_desc_data.equals("null"))
        {
            tag_desc_data = "";
        }
        if(other1_data.equals(","))
        {
            other1_data = "";
        }
        if(other2_data.equals(","))
        {
            other2_data = "";
        }
        if(other3_data.equals(","))
        {
            other3_data = "";
        }
        data.put("idparts", idparts);
        data.put("name",  name_data);
        data.put("refrence",  ref_data);
        data.put("Type", type_data);
        data.put("other1",  other1_data);
        data.put("other2",  other2_data);
        data.put("tag_description",  tag_desc_data);
        data.put("other3",  other3_data);
        data.put("String_image",  image_data);
        return data;
    }

    private void Neutralize()
    {

        //loading.setVisibility(View.VISIBLE);
        HashMap < String, String > params = new HashMap<String, String>();

        params.put("idparts", idparts);
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL_Neutralizer,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")) {

                                Toast.makeText(getActivity(), ""+response.getString("success"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                       // loading.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost"+error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", error.getMessage());
                return;
            }
        });

        requestQueue.add(jsObjRequest);


    }
    private void AddSell()
    {

       // loading.setVisibility(View.VISIBLE);
        HashMap < String, String > params = new HashMap<String, String>();

        params.put("idparts", idparts);
        params.put("price", price.getEditText().getText().toString());
        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.PUT,
                URL1,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")) {
                                //loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), ""+response.getString("success"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Connection Lost"+error.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });

        requestQueue.add(jsObjRequest);


    }

    private void getuseerComments(String username,int dealid)
    {
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("dealid", String.valueOf(dealid));
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_GETSELL, new JSONObject(params), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject hit = response.getJSONObject(i);
                        String nums = String.valueOf(hit.getInt("result"));
                        numberComments.setText(nums);

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

        requestQueue.add(request);
    }
    private  boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        boolean haveComnectedServr = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
             if(MainActivity.getConnction())
                 haveComnectedServr = true;
        }
        return haveConnectedWifi || haveConnectedMobile || haveComnectedServr;
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
        } else {
            collapsedMenu.add("Camera")
                    .setIcon(R.drawable.ic_photo_camera_accent_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            collapsedMenu.add("Gallary")
                    .setIcon(R.drawable.ic_insert_photo_black_24dp)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        if (item.getTitle() == "Camera") {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {

                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
            }
            else
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        }
        if (item.getTitle() == "Gallary") {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateEmpty(TextInputLayout text , String type)
    {
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(""+ type +" Can't be empty");
            return false;}
        else
        {
            text.setError(null);
            return true;
        }

    }

    private boolean validateSpinner(String spinner) {
        if ((spinner == null || spinner.equals("") || spinner.isEmpty())) {
            textInputLayoutspin.setError("Must Choose a Category !");
            return false;
        } else {
            textInputLayoutspin.setError(null);
            return true;

        }
    }

    private boolean validateOthers(TextInputLayout Label, TextInputLayout container)
    {

        String textLabel =Label.getEditText().getText().toString();
        String textConatiner = container.getEditText().getText().toString();
        if(textLabel.isEmpty() && textConatiner.isEmpty())
        {
            Label.setError(null);
            container.setError(null);
            return true;
        }else if (textConatiner.contains(","))
        {
            Label.setError("*");
            container.setError("Both fields Should not contain ','");
            return false;
        }else if (textLabel.contains(","))
        {
            Label.setError("*");
            container.setError("Should not contain ','");
            return false;
        }else if(!textLabel.isEmpty() && !textConatiner.isEmpty())
        {
            Label.setError(null);

            container.setError(null);
            return true;
        }else
        {
            container.setError("Both fields must either be empty or filled ");
            Label.setError("*");
            return false;
        }

    }
    private void DateCreated(String created)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        try {
        Date date = format.parse(created);
        DateFormat dateFormat = new SimpleDateFormat("dd LLLL yyyy");
        String strDate = dateFormat.format(date);
        creationDate.setText(strDate);
    } catch (ParseException e) {
        e.printStackTrace();
    }

    }


}
