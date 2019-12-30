package com.example.projeecto;


import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Placeholder;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class viewAll extends Fragment {



    private ImageButton bck,search_advance;
    private Button Cheapst,nwest,popular;
    private TextInputLayout searchgo;
    private TextView textDA;
    private String type,saver = "";
    private ConstraintLayout layout;
    private boolean isKeyboardShowing = false;


    public viewAll() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_view_all, container, false);

        bck  = root.findViewById(R.id.bck);
        searchgo = root.findViewById(R.id.searchgo);
        search_advance  = root.findViewById(R.id.search_advance);
        Cheapst = root.findViewById(R.id.Cheapst);
        nwest =  root.findViewById(R.id.nwest);
        textDA  = root.findViewById(R.id.textDA);
        popular  = root.findViewById(R.id.popular);
        layout = root.findViewById(R.id.layout);
        Bundle type = getArguments();
        if(type != null)
        {
            this.type = type.getString("type","");
            textDA.setText(this.type);

        }else
        {
            textDA.setText("View All");
        }



        final View view= getActivity().getWindow().getDecorView();
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        view.getWindowVisibleDisplayFrame(r);
                        int screenHeight = view.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;


                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true;
                            }
                        }
                        else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                                searchgo.getEditText().clearFocus();

                            }
                        }
                    }
                });


        search_advance.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                textDA.setVisibility(View.GONE);
                Transition transition = new Slide(Gravity.RIGHT);
                transition.setDuration(400);
                transition.addTarget(searchgo.getId());
                TransitionManager.beginDelayedTransition(layout, transition);
                Transition transition2 = new Slide(Gravity.LEFT);
                transition2.setDuration(600);
                transition2.addTarget(search_advance.getId());
                TransitionManager.beginDelayedTransition(layout, transition2);
                searchgo.setVisibility(View.VISIBLE);
                searchgo.getEditText().setFocusable(true);
                searchgo.getEditText().requestFocus();
                search_advance.setVisibility(View.GONE);
            }
        });

        searchgo.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    Transition transition2 = new Slide(Gravity.LEFT);
                    transition2.setDuration(600);
                    transition2.addTarget(textDA.getId());
                    TransitionManager.beginDelayedTransition(layout, transition2);
                    textDA.setVisibility(View.VISIBLE);
                    Transition transition = new Slide(Gravity.LEFT);
                    transition.setDuration(600);
                    transition.addTarget(search_advance.getId());
                    TransitionManager.beginDelayedTransition(layout, transition);
                    searchgo.setVisibility(View.GONE);
                    search_advance.setVisibility(View.VISIBLE);
                }else
                {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                }
            }
        });


        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchgo.getVisibility()== View.VISIBLE)
                {
                         searchgo.getEditText().clearFocus();
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                }else {
                    getActivity().onBackPressed();
                }

            }
        });
        popular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle key = new Bundle();
                key.putString("key","vues DESC");
                Navigation.findNavController(getActivity(),R.id.search_load_host).navigate(R.id.loadSearch,key);
            }
        });

        Cheapst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle key = new Bundle();
                key.putString("key","Price ASC");
                Navigation.findNavController(getActivity(),R.id.search_load_host).navigate(R.id.loadSearch,key);

            }
        });

        nwest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle key = new Bundle();
                key.putString("key","Created DESC");
                Navigation.findNavController(getActivity(),R.id.search_load_host).navigate(R.id.loadSearch,key);

            }
        });




        searchgo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchgo.getEditText().getText().toString().isEmpty())
                {
                    //clear.setVisibility(View.GONE);
                    Navigation.findNavController(getActivity(), R.id.search_load_host).navigate(R.id.loadSearch);
                }else
                {
                    //clear.setVisibility(View.VISIBLE);
                    saver = searchgo.getEditText().getText().toString();
                    Bundle searchData = new Bundle();
                    searchData.putString("search",searchgo.getEditText().getText().toString());
                    Navigation.findNavController(getActivity(), R.id.search_load_host).navigate(R.id.loadSearch,searchData);
                }

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return root;
    }
}
