package com.example.projeecto;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import com.example.projeecto.MainActivity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.tools.Dialogue;
import com.example.projeecto.tools.OnbackDestrecution;
import com.example.projeecto.tools.ProgressBarAnimation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Pattern;


public class SecondRow_Fragment extends Fragment implements OnbackDestrecution , Dialogue.DialogueListener {
    private ProgressBar progressBar;
    private TextView second , end;
    private Button Register;
    private CheckBox agree;
    private ConstraintLayout loading;
    private RequestQueue requestQueue;
    private static final String URL =MainActivity.SKELETON+"register";//http://10.0.2.2:5000/api/register";
    private TextInputLayout emails,confirm_password,password;
    private static final Pattern PASSWORD_CHECHER =  Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            //"(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            "(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            ".{8,}" +               //at least 8 characters
            "$");



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();
        emails = view.findViewById(R.id.Email);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                Fragment parent = (Fragment) navHostFragment.getParentFragment();
                progressBar= parent.getView().findViewById(R.id.progressBar);
                ProgressBarAnimation animation = new ProgressBarAnimation(progressBar,60,30);
                animation.setDuration(1000);
                progressBar.startAnimation(animation);
                second = parent.getView().findViewById(R.id.two);
                second.setBackgroundResource(R.drawable.slector_round);
                second.setTextColor(Color.parseColor("#8F8E8E"));
                saveEmailRegister(emails.getEditText().getText().toString());
                Navigation.findNavController(getView()).navigate(R.id.firstrowFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()




    loadDataRegister();

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_second_row_, container, false);
        OnbackDestrecution();
        Register = root.findViewById(R.id.register_p);
        emails = root.findViewById(R.id.Email);
        password = root.findViewById(R.id.password_r);
        agree = root.findViewById(R.id.agree);
        loading = root.findViewById(R.id.loading_1);
        confirm_password = root.findViewById(R.id.confirm_password);
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();

        //on Register
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEditText(emails,password,confirm_password);
                if(!validateEmail(emails,"Email") | !Password(password,"Password") | !passwordChecker(password,confirm_password) | !checktyCheckty(agree)  ) {return;}else{
                    saveEmailRegister(emails.getEditText().getText().toString());
                    Register();
               }

                //Clearing up Errors



            }

        });
        emails.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(emails.getError() != null){
                        emails.setError(null);
                    }

                }
                return false;
            }
        });
        emails.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emails.getEditText().setBackgroundResource(R.drawable.style_edittext);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(password.getError() != null){
                        password.setError(null);
                    }

                }
                return false;
            }
        });
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password.getEditText().setBackgroundResource(R.drawable.style_edittext);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                             @Override
                                             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                 if(isChecked)
                                                 {
                                                     agree.setFocusableInTouchMode(false);
                                                     agree.setFocusable(false);
                                                     agree.setError(null);
                                                 }
                                             }
                                         }
        );

        confirm_password.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if(confirm_password.getError() != null){
                        confirm_password.setError(null);
                    }

                }
                return false;
            }
        });
        confirm_password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                confirm_password.getEditText().setBackgroundResource(R.drawable.style_edittext);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return root;
    }

    @Override
    public void OnbackDestrecution() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }

    public void saveEmailRegister( String email )
    {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.apply();

    }
    private void loadDataRegister()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email","");

        if(email.equals("null") || email ==null)
        {
            email = "";
        }


        emails.getEditText().setText(email);

    }

    private boolean validateEmail(TextInputLayout text, String type)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(Html.fromHtml("<font color='red'>"+ type +" can't be empty</font>"));
            text.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;}
        else if(!Patterns.EMAIL_ADDRESS.matcher(textToCheck).matches()){
            text.setError(Html.fromHtml("<font color='red'>Please enter a valid email address </font>"));
            text.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;
        }
        else
        {
            text.setError(null);
            return true;
        }
    }
    private boolean Password (TextInputLayout text , String type)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(Html.fromHtml("<font color='red'>"+ type +" can't be empty</font>"));
            text.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;

        }else if (!PASSWORD_CHECHER.matcher(textToCheck).matches()){
            text.setError(Html.fromHtml("<font color='red'> Weak Password </font>"));
            text.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;
        }
        else
        {
            text.setError(null);
            return true;
        }

    }

    private  boolean checkPassword(TextInputLayout password)
    {

        return (password.getError() != null);
    }
    private  boolean passwordChecker (TextInputLayout password ,TextInputLayout confirm)
    {
        if(confirm.getError()!= null)
        {confirm.setError(null);}
        String passwordToCheck = password.getEditText().getText().toString().trim();
        String confirmPasswordToCheck = confirm.getEditText().getText().toString().trim();
        if(confirmPasswordToCheck.isEmpty()){
            confirm.setError(Html.fromHtml("<font color='red'>this field can't be empty</font>"));
            confirm.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;

        }else if(!passwordToCheck.equals(confirmPasswordToCheck)){
            confirm.setError(Html.fromHtml("<font color='red'>Password does not match </font>"));
            confirm.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;
        }else if(checkPassword(password))
        {
            confirm.setError(Html.fromHtml("<font color='red'>Password must be valid first</font>"));
            confirm.getEditText().setBackgroundResource(R.drawable.border_error);
            return false;

        }
        else
        {
            confirm.setError(null);
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
    private boolean checktyCheckty(CheckBox check)
    {
        check.setFocusableInTouchMode(true);
        check.setFocusable(true);

      if(check.isChecked())
      {
          return true;
      }else
      {
          check.requestFocus();
          check.setError("must be checked");
          return false;
      }
    }
    private void progressAnimation()
    {
        NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
        Fragment parent = (Fragment) navHostFragment.getParentFragment();
        progressBar= parent.getView().findViewById(R.id.progressBar);
        end = parent.getView().findViewById(R.id.done);
        ProgressBarAnimation animation = new ProgressBarAnimation(progressBar,60,100);
        animation.setDuration(1000);
        progressBar.startAnimation(animation);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                end.setBackgroundResource(R.drawable.selector_round_colored);
                end.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_check_white,0,0);
            }
        }, 1000);
    }
    private void Register()
    {
            loading.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
            String lastName_t = sharedPreferences.getString("last_name","");
            String firstName_t = sharedPreferences.getString("first_name","");
            String email_t = sharedPreferences.getString("email","");
            String mobile_t = sharedPreferences.getString("mobile","");
            String address_t = sharedPreferences.getString("address","");
            if(lastName_t.equals("null"))
            {
                lastName_t = "";
            }
            if(firstName_t.equals("null"))
            {
                firstName_t = "";
            }
            if(mobile_t.equals("null"))
            {
                mobile_t = "";
            }
            if(address_t.equals("null"))
            {
                address_t="";
            }

        HashMap< String, String > params = new HashMap<String, String>();

        params.put("username",  email_t);
        params.put("address",  address_t);
        params.put("mobile",  mobile_t);
        params.put("firstname", firstName_t);
        params.put("lastname",  lastName_t);
        params.put("password",password.getEditText().getText().toString());

        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.has("success")){
                                progressAnimation();
                                removeAll();
                                loading.setVisibility(View.GONE);
                                openDialogue();
                            } else if (response.has("err")){
                                loading.setVisibility(View.GONE);
                                emails.setError(Html.fromHtml("<font color='red'>"+ response.getString("err")+"</font>"));
                                emails.getEditText().setBackgroundResource(R.drawable.border_error);
                                 }
                            else
                            {
                                Toast.makeText(getActivity(), " internal error happened ", Toast.LENGTH_SHORT).show();
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
    public void removeAll()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("check", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }
    private void openDialogue()
    {
        Dialogue dialogue = new Dialogue();
        dialogue.setTargetFragment(this,1);
        dialogue.show(getFragmentManager(),"");
    }


    @Override
    public void OnOkClicked() {

        Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.navigation_account);

    }
}




