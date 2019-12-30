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
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Html;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.projeecto.adapters.spinnerAdapter;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class add_part_fragment extends Fragment  {

    private ImageView container,ImageView10;
    private static final int GALLERY_REQUEST = 1;
    private String base64Code,username,cateegory;
    private static final int  MY_CAMERA_PERMISSION_CODE = 2;
    private static final int CAMERA_REQUEST=3;
    private static final String URL = MainActivity.SKELETON+"parts/add";
    private Button save;
    private CardView Buttons;
    private ImageButton others,close1,close2,close3,bck,addImage,addImageCamera,closeButton;
    private RequestQueue requestQueue;
    private Spinner category;
    private TextView imageHinter,textView15,imagenote;
    private ConstraintLayout otherSets,other1Set,other2Set,other3Set,loading;
    private TextInputLayout other1x,other2x,other3x,other1,other2,other3,name,refrence,tag_desc,textInputLayoutspin;
    private static final Pattern PASSWORD_CHECHER =  Pattern.compile("^" +
            //"(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            //"(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            "(?=.*[@#$%^&+=,.!?])" + "$");   //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            //".{8,}" +               //at least 8 characters




    public add_part_fragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.add_part_fragment, container, false);





        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        username = getUsername();
        container = view.findViewById(R.id.image_container);
        textView15  = view.findViewById(R.id.textView15);
        addImage = view.findViewById(R.id.addImage);
        addImageCamera= view.findViewById(R.id.seealone);
        category =  view.findViewById(R.id.Category);
        others = view.findViewById(R.id.others);
        otherSets = view.findViewById(R.id.othersets);
        other1Set = view.findViewById(R.id.other1set);
        other2Set = view.findViewById(R.id.other2set);
        other3Set = view.findViewById(R.id.other3set);
        imagenote =view.findViewById(R.id.imagenote);
        ImageView10 = view.findViewById(R.id.imageView10);
        close1 = view.findViewById(R.id.close1);
        close2 = view.findViewById(R.id.close2);
        close3 = view.findViewById(R.id.close3);
        bck = view.findViewById(R.id.bck);
        save = view.findViewById(R.id.save);
        tag_desc = view.findViewById(R.id.tag_desc);
        name = view.findViewById(R.id.name);
        refrence = view.findViewById(R.id.refrence);
        other1 = view.findViewById(R.id.other1);
        other2 = view.findViewById(R.id.other2);
        other3 = view.findViewById(R.id.other3);
        other1x = view.findViewById(R.id.other1x);
        textInputLayoutspin =view.findViewById(R.id.textInputLayoutspin);
        other2x = view.findViewById(R.id.other2x);
        other3x = view.findViewById(R.id.other3x);
        loading = view.findViewById(R.id.loading_1);
        closeButton = view.findViewById(R.id.closeimage);
        Buttons = view.findViewById(R.id.buttons);
        imageHinter = view.findViewById(R.id.buttontext);
        name.getEditText().clearFocus();




        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        others.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View v) {
               otherSets.setVisibility(View.VISIBLE);
               if(other1Set.getVisibility() == View.GONE)
               {
                   animateText(otherSets,1,other1Set);
                   other1Set.setVisibility(View.VISIBLE);

               }else if (other1Set.getVisibility() == View.VISIBLE && other2Set.getVisibility() == View.GONE)
               {
                   animateText(otherSets,1,other2Set);
                   other2Set.setVisibility(View.VISIBLE);

               }else if(other2Set.getVisibility() == View.VISIBLE && other3Set.getVisibility() == View.GONE)
               {
                   animateText(otherSets,1,other3Set);
                   other3Set.setVisibility(View.VISIBLE);

               }
               if(other2Set.getVisibility() == View.VISIBLE && other1Set.getVisibility() == View.VISIBLE && other3Set.getVisibility() == View.VISIBLE)
               {
                   others.setClickable(false);
                   others.setImageResource(R.drawable.gray_add);

               }
           }
       });


       close1.setOnClickListener(new View.OnClickListener() {
           @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
           @Override
           public void onClick(View v) {
               other1Set.setVisibility(View.GONE);
               others.setClickable(true);
               other1x.getEditText().setText("");
               other1.getEditText().setText("");
               others.setImageResource(R.drawable.ic_add_circle_accent_24dp);
               if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
               {
                   otherSets.setVisibility(View.GONE);
               }

           }
       });
        close2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                other2Set.setVisibility(View.GONE);
                other2x.getEditText().setText("");
                other2.getEditText().setText("");
                others.setClickable(true);
                others.setBackgroundResource(R.drawable.ic_add_circle_accent_24dp);
                if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
                {

                    otherSets.setVisibility(View.GONE);
                }

            }

        });
        close3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                other3Set.setVisibility(View.GONE);
                other3x.getEditText().setText("");
                other3.getEditText().setText("");
                others.setClickable(true);
                others.setBackgroundResource(R.drawable.ic_add_circle_accent_24dp);
                if(other1Set.getVisibility() == View.GONE && other2Set.getVisibility() == View.GONE && other3Set.getVisibility() == View.GONE)
                {
                    otherSets.setVisibility(View.GONE);
                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.setVisibility(View.GONE);
                imageHinter.setVisibility(View.VISIBLE);
                closeButton.setVisibility(View.GONE);
                Buttons.setVisibility(View.VISIBLE);

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
                if(!validateEmpty(name,"Name") | !validateImage(base64Code) | !validateSpinner(cateegory) | !validateOthers(other1x,other1) | !validateOthers(other2x,other2)| !validateOthers(other3x,other3)) {return;}else{
               addPart();}
            }
        });



    }



    private String transformerImageBase64(ImageView container)
    {
        String base64String ;
        BitmapDrawable drawable = (BitmapDrawable) container.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        base64String = Base64.encodeToString(imageInByte,0);
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
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] imageInByte = stream.toByteArray();
                        long lengthbmp = imageInByte.length;
                        if(lengthbmp >= 1000000*10)
                        {
                            imagenote.setText(Html.fromHtml("<font color=red>"+imagenote.getText().toString()+"</font>"));
                            return;
                        }
                        container.setVisibility(View.VISIBLE);
                        imageHinter.setVisibility(View.GONE);
                        closeButton.setVisibility(View.VISIBLE);
                        Buttons.setVisibility(View.GONE);

                        Glide.with(getActivity()).asBitmap().load(bitmap).into(container);
                        //container.setImageBitmap(bitmap);
                       // Glide.with(getActivity()).asBitmap().load(bitmap).into(ImageView10);
                        //ImageView10.setImageBitmap(bitmap);
                        base64Code=transformerImageBase64(ImageView10);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            container.setVisibility(View.VISIBLE);
            imageHinter.setVisibility(View.GONE);
            closeButton.setVisibility(View.VISIBLE);
            Buttons.setVisibility(View.GONE);
            //container.setImageBitmap(photo);
            Glide.with(getActivity()).asBitmap().load(photo).into(container);
            //Glide.with(getActivity()).asBitmap().load(photo).into(ImageView10);
            ImageView10.setImageBitmap(photo);
            base64Code=transformerImageBase64(ImageView10);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateText(ViewGroup view , int direction, ConstraintLayout object )
    {
        Transition transition;

        if(direction == 1)
            transition = new Slide(Gravity.RIGHT);
        else
            transition = new Slide(Gravity.LEFT);
        transition.setDuration(600);
        transition.addTarget(object.getId());
        TransitionManager.beginDelayedTransition(view, transition);
        object.setVisibility(View.VISIBLE);
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

    private boolean validateImage(String base64)
    {
        if(( base64 == null || base64.equals("")|| base64.isEmpty()))
        {
            textView15.setText(Html.fromHtml("<font color=red>"+textView15.getText().toString()+"</font>"));
            return false;
        }else
        {
            textView15.setText(Html.fromHtml("<font color=white>"+textView15.getText().toString()+"</font>"));
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




}

