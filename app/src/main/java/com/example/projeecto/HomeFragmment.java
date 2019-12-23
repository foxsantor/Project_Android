package com.example.projeecto;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmment extends Fragment {


    private TextInputLayout search;
    private Button clear;
    private boolean exit = false;
    private String saver ="" ;


    public HomeFragmment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_fragmment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if(exit)
                {
                    getActivity().finish();
                    System.exit(0);
                }


                if(Navigation.findNavController(getActivity(), R.id.fragichan).getCurrentDestination().getId() == R.id.searchFragment2)
                {
                    Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.notificationsFragment);
                    return;

                }else if(Navigation.findNavController(getView()).getCurrentDestination().getId() == R.id.liveChat2)
                {
                    Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.notificationsFragment);
                    return;
                }
                exit = true;
                Toast.makeText(getActivity(), "Click BACK again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        exit =false;
                    }
                }, 2000);

            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        search = view.findViewById(R.id.search);
        /*clear = view.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.getEditText().setText(null);
            }
        });
*/

        search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if(!saver.equals(search.getEditText().getText().toString()))
                    {
                    Bundle searchData = new Bundle();
                    searchData.putString("search",search.getEditText().getText().toString());
                    Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.searchFragment2,searchData);
                    }else if(saver.equals(""))
                    {
                        Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.searchFragment2);
                    }else
                    {
                        return false;
                    }
                }
                return false;
            }
        });

        search.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(search.getEditText().getText().toString().isEmpty())
                {
                    //clear.setVisibility(View.GONE);
                    Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.searchFragment2);
                }else
                {
                    //clear.setVisibility(View.VISIBLE);
                    saver = search.getEditText().getText().toString();
                    Bundle searchData = new Bundle();
                    searchData.putString("search",search.getEditText().getText().toString());
                    Navigation.findNavController(getActivity(), R.id.fragichan).navigate(R.id.searchFragment2,searchData);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


}
