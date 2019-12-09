package com.example.projeecto.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.projeecto.R;

import java.util.ArrayList;

public class spinnerAdapter extends ArrayAdapter<String> {

    public  spinnerAdapter(Context context, ArrayList<String> list)
    {
        super(context,0,list);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root =  initView(position, convertView, parent);
        TextView tv = root.findViewById(R.id.textspinner);
        if (position == 0) {
            // Set the hint text color gray
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return root;
    }
    @Override
    public boolean isEnabled(int position) {
        if (position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        } else {
            return true;
        }
    }
    private View initView(int postion,View convertion,ViewGroup parent){
        if(convertion == null){
            convertion= LayoutInflater.from(getContext()).inflate(R.layout.custom_spinner,parent,false);}


        TextView name = convertion.findViewById(R.id.textspinner);
        String currentitem = getItem(postion);
        if(currentitem !=null){

            name.setText(currentitem);}

        return convertion;
    }
}
