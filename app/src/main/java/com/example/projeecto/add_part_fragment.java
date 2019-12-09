package com.example.projeecto;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.adapters.spinnerAdapter;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class add_part_fragment extends Fragment implements OnbackDestrecution {

    private ImageView container;
    private FloatingActionButton addImage,addImageCamera;
    private static final int GALLERY_REQUEST = 1;
    private String base64Code,username,cateegory;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private static final String URL = MainActivity.SKELETON+"parts/add";
    private Button others,close1,close2,close3,save;
    private RequestQueue requestQueue;
    private Spinner category;
    private EditText tag_desc;
    private ConstraintLayout otherSets,other1Set,other2Set,other3Set,loading;
    private TextInputLayout other1x,other2x,other3x,other1,other2,other3,name,refrence;



    public add_part_fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.add_part_fragment, container, false);
        OnbackDestrecution();




        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();

        username = getUsername();
        container = view.findViewById(R.id.image_container);
        addImage = view.findViewById(R.id.addImage);
        addImageCamera= view.findViewById(R.id.seealone);
        category =  view.findViewById(R.id.Category);
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

       SpinnerLoader(category);
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





       addImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
               photoPickerIntent.setType("image/*");
               startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

           }
       });

        addImageCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {

                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }

            }
        });

        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               addPart();
            }
        });



    }

    @Override
    public void OnbackDestrecution() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
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
        Toast.makeText(getActivity(), ""+base64String, Toast.LENGTH_SHORT).show();
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
                        container.setImageBitmap(bitmap);
                        base64Code=transformerImageBase64(container);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            container.setImageBitmap(photo);
            base64Code=transformerImageBase64(container);

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


    public String getUsername() {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
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
    private void addPart()
    {
        loading.setVisibility(View.VISIBLE);

        HashMap<String,String> params = sortData();

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")) {

                                loading.setVisibility(View.GONE);
                                Toast.makeText(getActivity(), " good job "+response.getString("success"), Toast.LENGTH_SHORT).show();
                                //openDialogue();
                            }
                            else
                            {
                                Toast.makeText(getActivity(), " internal error happened "+response.getString("error"), Toast.LENGTH_SHORT).show();
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
        String ref_data,name_data,other1_data,other2_data,other3_data,type_data,tag_desc_data,owner_data,image_data;
        image_data = base64Code;
        owner_data = username;
        ref_data = refrence.getEditText().getText().toString();
        name_data = name.getEditText().getText().toString();
        other1_data = other1x.getEditText().getText().toString()+","+other1.getEditText().getText().toString();
        other2_data = other2x.getEditText().getText().toString()+","+other2.getEditText().getText().toString();
        other3_data = other3x.getEditText().getText().toString()+","+other3.getEditText().getText().toString();
        tag_desc_data = tag_desc.getText().toString();
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

        data.put("username",  owner_data);
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



}

