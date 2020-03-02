package com.localparts.projeecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RegisterFragment_1 extends Fragment  {


    public static RegisterFragment_1 newInstance() {
        return new RegisterFragment_1();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        removeAll();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register_fragment_1, container, false);




        return root;
    }
    public void removeAll()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }





}
