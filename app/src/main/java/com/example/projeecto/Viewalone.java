package com.example.projeecto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Viewalone extends Fragment implements OnbackDestrecution {

    TextView Created,name,owner,other1,other2,other3,refrence,tag_description,price,type;
    FloatingActionButton shopping;
    Button commentSender,Contact;
    EditText comment;
    ImageView imageView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_viewalone, container, false);
        OnbackDestrecution();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();
        //init
        Created =view.findViewById(R.id.date_a);
        name  =view.findViewById(R.id.name_a);
        owner  =view.findViewById(R.id.ownert);
        other1  =view.findViewById(R.id.other1t);
        other2 =view.findViewById(R.id.other2t);
        other3 =view.findViewById(R.id.other3t);
        refrence =view.findViewById(R.id.refrencet);
        tag_description =view.findViewById(R.id.textView24);
        price  =view.findViewById(R.id.textView17);
        type=view.findViewById(R.id.textView19);
        shopping =view.findViewById(R.id.Addtofavorite);
        imageView = view.findViewById(R.id.imageView4);
        Bundle data = getArguments();

        if(null!= data) {

            Created.setText(data.getString("Created"));
            name.setText(data.getString("name"));
            owner.setText(data.getString("owner"));
            other1.setText(data.getString("other1"));
            other2.setText(data.getString("other2"));
            other3.setText(data.getString("other3"));
            refrence.setText(data.getString("refrnce"));
            tag_description.setText(data.getString("tag_description"));
            price.setText(data.getFloat("price")+ " TND");
            type.setText(data.getString("Type"));
            byte[] image = data.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bmp);

        }

    }

    @Override
    public void OnbackDestrecution() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }


}