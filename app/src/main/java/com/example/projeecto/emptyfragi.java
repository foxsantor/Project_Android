package com.example.projeecto;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class emptyfragi extends Fragment {

    private EmptyfragiViewModel mViewModel;
    private Button logout,persodata;

    public static emptyfragi newInstance() {
        return new emptyfragi();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        persodata = view.findViewById(R.id.persodata);
        logout = view.findViewById(R.id.logout);
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

                Navigation.findNavController(getView()).navigate(R.id.navigation_account);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.emptyfragi_fragment, container, false);
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


}
