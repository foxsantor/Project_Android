package com.example.projeecto;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.example.projeecto.tools.OnbackDestrecution;
import com.example.projeecto.tools.ProgressBarAnimation;
import com.facebook.login.LoginManager;

public class UserMenuFragment extends Fragment implements OnbackDestrecution {

    private EmptyfragiViewModel mViewModel;
    private Button logout,persodata,myGarage;
    private RequestQueue requestQueue;

    public static UserMenuFragment newInstance() {
        return new UserMenuFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        persodata = view.findViewById(R.id.persodata);
        logout = view.findViewById(R.id.logout);
        myGarage = view.findViewById(R.id.myGarage);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.navigation_market_place);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        OnbackDestrecution();
        persodata.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
         Navigation.findNavController(getView()).navigate(R.id.action_emptyfragi_to_user_account);

                                         }
                                     }
        );
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAll();
                LoginManager.getInstance().logOut();
                Navigation.findNavController(getView()).navigate(R.id.navigation_account);
            }
        });
        myGarage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_emptyfragi_to_myGarage_fragment);
            }
        });
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.emptyfragi_fragment, container, false);
        OnbackDestrecution();
            return  root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EmptyfragiViewModel.class);
        // TODO: Use the ViewModel
    }

    public void removeAll()
    {
        SharedPreferences sharedPreferences= getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }




    @Override
    public void OnbackDestrecution() {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }
}
