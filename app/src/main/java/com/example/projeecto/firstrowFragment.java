package com.example.projeecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeecto.tools.OnbackDestrecution;
import com.example.projeecto.tools.ProgressBarAnimation;
import com.example.projeecto.ui.home.HomeViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class firstrowFragment extends Fragment implements OnbackDestrecution {

    ProgressBar progressBar;
    TextView second;
    EditText address;
    TextInputLayout firstname_l,lastname_l,mobilephone_l;
    Button next;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();


        next = view.findViewById(R.id.next);
        firstname_l = view.findViewById(R.id.firstname);
        lastname_l = view.findViewById(R.id.lastname_r);
        mobilephone_l = view.findViewById(R.id.mobile);
        address = view.findViewById(R.id.address);
        loadDataRegister();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_account);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        firstname_l.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(firstname_l.getError() != null){
                        firstname_l.setError(null);
                    }

                }
                return false;
            }
        });
        mobilephone_l.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(mobilephone_l.getError() != null){
                        mobilephone_l.setError(null);
                    }

                }
                return false;
            }
        });
        lastname_l.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(lastname_l.getError() != null){
                        lastname_l.setError(null);
                    }

                }
                return false;
            }
        });

        firstname_l.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstname_l.getEditText().setBackgroundResource(R.drawable.style_edittext);
                firstname_l.getEditText().refreshDrawableState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mobilephone_l.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mobilephone_l.getEditText().setBackgroundResource(R.drawable.style_edittext);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        lastname_l.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastname_l.getEditText().setBackgroundResource(R.drawable.style_edittext);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                Fragment parent = (Fragment) navHostFragment.getParentFragment();


                resetEditText(firstname_l,lastname_l,mobilephone_l);
                if(!validateEmpty(firstname_l,"First name") | !validateEmpty(lastname_l,"Last name") | !validateEmpty(mobilephone_l,"Mobile phone") ) {return;}else{
                    saveDataRegister(firstname_l.getEditText().getText().toString(), lastname_l.getEditText().getText().toString(), mobilephone_l.getEditText().getText().toString(), address.getText().toString());
                    progressBar = parent.getView().findViewById(R.id.progressBar);
                    ProgressBarAnimation animation = new ProgressBarAnimation(progressBar, 30, 60);
                    animation.setDuration(1000);
                    progressBar.startAnimation(animation);
                    second = parent.getView().findViewById(R.id.two);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            second.setBackgroundResource(R.drawable.selector_round_colored);
                            second.setTextColor(Color.parseColor("#FFFFFF"));

                        }
                    }, 1000);
                    Navigation.findNavController(getView()).navigate(R.id.action_firstrowFragment_to_secondRow_Fragment);

                }}
        });
        //



    }



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_firstrow, container, false);
        OnbackDestrecution();


        return root;
    }


    @Override
    public void OnbackDestrecution() {
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }
    public void saveDataRegister( String firstName , String lastname,String mobile,String address)
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name",firstName);
        editor.putString("last_name",lastname);
        editor.putString("mobile",mobile);
        editor.putString("address",address);
        editor.apply();

    }
    private void loadDataRegister()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        String lastName_r = sharedPreferences.getString("last_name","");
        String firstName_r = sharedPreferences.getString("first_name","");
        String mobile_r = sharedPreferences.getString("mobile","");
        String address_r = sharedPreferences.getString("address","");

        if(lastName_r.equals("null") || lastName_r ==null)
        {
            lastName_r = "";
        }
        if(firstName_r.equals("null")|| firstName_r ==null)
        {
            firstName_r = "";
        }
        if(mobile_r.equals("null")|| mobile_r ==null)
        {
            mobile_r = "";
        }
        if(address_r.equals("null")|| address_r ==null)
        {
            address_r="";
        }

        lastname_l.getEditText().setText(lastName_r);
        firstname_l.getEditText().setText(firstName_r);
        address.setText(address_r);
        mobilephone_l.getEditText().setText(mobile_r);
    }

    private boolean validateEmpty(TextInputLayout text , String type)
    {
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(Html.fromHtml("<font color='red'>"+ type +" Can't be empty</font>"));
            text.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;}
        else
        {
            text.setError(null);
            return true;
        }

    }
    private void resetEditText(TextInputLayout text1, TextInputLayout text2 ,TextInputLayout text3)
    {
        text1.setError(null);
        text1.getEditText().setBackgroundResource(R.drawable.style_edittext);
        text2.setError(null);
        text2.getEditText().setBackgroundResource(R.drawable.style_edittext);
        text3.setError(null);
        text3.getEditText().setBackgroundResource(R.drawable.style_edittext);
    }


}
