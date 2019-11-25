package com.example.projeecto;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class User_account extends Fragment {
    EditText firstName,lastName,address,mobilePhone,email,password;
    TextView passwordView;
    String firstName_t,lastName_t,email_t,username;
    private UserAccountViewModel mViewModel;
    private  static final  String URL ="http://10.0.2.2:5000/api/login/getUser";
    public static User_account newInstance() {
        return new User_account();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loadUsername(username);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_account_fragment, container, false);
        loadDataForm();
        firstName = root.findViewById(R.id.firstname_p);
        lastName = root.findViewById(R.id.lastname_p);
        address = root.findViewById(R.id.adress_p);
        mobilePhone = root.findViewById(R.id.mobile_p);
        email = root.findViewById(R.id.email_p);
        lastName.setText(lastName_t);
        firstName.setText(firstName_t);
        email.setText(email_t);


        return root;
    }



    private void loadDataForm()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        lastName_t = sharedPreferences.getString("last_name","");
        firstName_t = sharedPreferences.getString("first_name","");
        email_t = sharedPreferences.getString("email","");

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserAccountViewModel.class);
        // TODO: Use the ViewModel
    }

    public  void loadUsername(String username)
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
    }


}
