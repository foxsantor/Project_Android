package com.example.projeecto;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.projeecto.ViewModels.BookmarkViewModel;
import com.example.projeecto.adapters.CommentAdapter;
import com.example.projeecto.adapters.myPartsAdapter;
import com.example.projeecto.adapters.sellsAdapter;
import com.example.projeecto.entities.Comment;
import com.example.projeecto.entities.Parts;
import com.example.projeecto.entities.Votes;
import com.example.projeecto.tools.OnbackDestrecution;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.lang.ref.WeakReference;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class Viewalone extends Fragment implements OnbackDestrecution {

    TextView Created, name, owner, other1, other2, other3, refrence, tag_description, price, type, other1_v, other2_v, other3_v, portrait,vues,num;
    ImageButton shopping,edit,toggel,back;
    ConstraintLayout other1c, other2c, other3c, appear, loading,layout,host;
    Button commentSender, Contact, I, B;
    String other1s, other2s, other3s, username,fullName,nums;
    private BookmarkViewModel bookmarkViewModel;
    private TextInputLayout comment;
    ImageView imageView;
    boolean isKeyboardShowing = false;
    int idDeal;
    private static final String URL_ADD = MainActivity.SKELETON + "comments/addComment";
    private static final String URL_GETUSERCOMMENT = MainActivity.SKELETON + "comments/getUsernameandComments";
    RequestQueue requestQueue;

    public Viewalone() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_viewalone, container, false);
        OnbackDestrecution();
        Created = view.findViewById(R.id.date_a);
        num = view.findViewById(R.id.num);
        Contact = view.findViewById(R.id.Contact);
        host = view.findViewById(R.id.host);
        other1c = view.findViewById(R.id.other1);
        other2c = view.findViewById(R.id.other2);
        other3c = view.findViewById(R.id.other3);
        other1_v = view.findViewById(R.id.other1v);
        other2_v = view.findViewById(R.id.other2v);
        loading = view.findViewById(R.id.loading);
        other3_v = view.findViewById(R.id.otherv);
        name = view.findViewById(R.id.name_a);
        layout = view.findViewById(R.id.layout);
        owner = view.findViewById(R.id.ownert);
        other1 = view.findViewById(R.id.other1t);
        other2 = view.findViewById(R.id.other2t);
        other3 = view.findViewById(R.id.other3t);
        vues = view.findViewById(R.id.views);
        refrence = view.findViewById(R.id.refrencet);
        tag_description = view.findViewById(R.id.textView24);
        price = view.findViewById(R.id.textView17);
        type = view.findViewById(R.id.textView19);
        shopping = view.findViewById(R.id.Addtofavorite);
        imageView = view.findViewById(R.id.imageView4);
        edit = view.findViewById(R.id.modifie);
        comment = view.findViewById(R.id.comment);
        appear = view.findViewById(R.id.appear);
        commentSender = view.findViewById(R.id.send);
        toggel = view.findViewById(R.id.hide);
        back = view.findViewById(R.id.back);
        I = view.findViewById(R.id.I);
        B = view.findViewById(R.id.B);
        username = loadUsername();
        final Bundle data = getArguments();
        shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDeal();
            }
        });

        if (null != data) {

            other1s = data.getString("other1");
            other2s = data.getString("other2");
            other3s = data.getString("other3");

            if (other1s.isEmpty()) {
                this.other1c.setVisibility(View.GONE);
            } else {
                if (other1s.contains(",")) {
                    String[] other1TextView;
                    other1TextView = other1s.split(",");
                    other1_v.setText(other1TextView[0]);
                    other1.setText(other1TextView[1]);
                } else {
                    other1.setText(other1s);
                }
            }
            if (other2s.isEmpty()) {
                this.other2c.setVisibility(View.GONE);
            } else {
                if (other2s.contains(",")) {
                    String[] other2TextView;
                    other2TextView = other2s.split(",");
                    other2_v.setText(other2TextView[0]);
                    other2.setText(other2TextView[1]);
                } else {
                    other2.setText(other2s);
                }
            }
            if (other3s.isEmpty()) {
                this.other3c.setVisibility(View.GONE);
            } else {
                if (other3s.contains(",")) {
                    String[] other3TextView;
                    other3TextView = other3s.split(",");
                    other3_v.setText(other3TextView[0]);
                    other3.setText(other3TextView[1]);
                } else {
                    other3.setText(other3s);
                }
            }

            if (this.other1c.getVisibility() == View.GONE) {
                this.other2c.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
                this.other3c.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E6E9F0")));
            }
            if (this.other2c.getVisibility() == View.GONE) {
                this.other3c.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E6E9F0")));
            }
            if (this.other2c.getVisibility() == View.GONE && this.other1c.getVisibility() == View.GONE) {
                this.other3c.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            }

            String dtStart = data.getString("Created");
            SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            try {
                Date date = format.parse(dtStart);
                PrettyTime p = new PrettyTime();
                date = new Date(date.getTime()+1*3600*1000);
                Created.setText(p.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }




            vues.setText(String.valueOf(data.getInt("vues")));
            name.setText(data.getString("name"));
            //owner.setText(data.getString("owner"));

            if(username.equals(data.getString("owner")))
            {
                shopping.setVisibility(View.GONE);
                edit.setVisibility(View.VISIBLE);
            }
            idDeal = data.getInt("idparts", 0);
            getuseerComments(data.getString("owner"),idDeal);

            refrence.setText(data.getString("refrnce"));
            tag_description.setText(data.getString("tag_description"));
            price.setText(priceFormInflater(data.getFloat("price"))+ " TND");
            type.setText(data.getString("Type"));
            byte[] image = data.getByteArray("image");
            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            imageView.setImageBitmap(bmp);


        }

        toggel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(host.getVisibility() == View.VISIBLE)
                {
                    host.setVisibility(View.GONE);
                    toggel.setImageResource(R.drawable.ic_navigate_next_black_24dp);
                }else
                {
                    host.setVisibility(View.VISIBLE);
                    toggel.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getActivity().onBackPressed();
                                    }
                                }

        );
        I.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = comment.getEditText().getText().toString();
                current_comment = "<i>" + current_comment + "</i>";
                comment.getEditText().setText(current_comment);
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_comment = comment.getEditText().getText().toString();
                current_comment = "<b>" + current_comment + "</b>";
                comment.getEditText().setText(current_comment);
            }
        });

        comment.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    appear.setVisibility(View.VISIBLE);
                }
                if (!validateEmpty(comment.getEditText())) {
                    commentSender.setClickable(false);
                    commentSender.setBackgroundResource(R.drawable.editblend);
                } else {
                    commentSender.setClickable(true);
                    commentSender.setBackgroundResource(R.drawable.blenddarker);

                }
                return false;
            }
        });
        comment.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!validateEmpty(comment.getEditText())) {
                    commentSender.setClickable(false);
                    commentSender.setBackgroundColor(getResources().getColor(R.color.gray));

                } else {
                    commentSender.setClickable(true);
                    commentSender.setBackgroundColor(getResources().getColor(R.color.DrakLord));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        commentSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle data = getArguments();
                addComment(comment.getEditText().getText().toString(), username, idDeal);
                getuseerComments(data.getString("owner"),idDeal);
                Bundle searchData = new Bundle();
                searchData.putInt("id",idDeal);
                Navigation.findNavController(getActivity(), R.id.comment_host).navigate(R.id.commentsection,searchData);

            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OnbackDestrecution();


        Contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle owner = getArguments();
                Bundle data = new Bundle();
                data.putString("owner", owner.getString("owner"));
                data.putString("price", price.getText().toString());
                data.putString("name", name.getText().toString());
                Navigation.findNavController(getView()).navigate(R.id.action_viewalone_to_contactInterface, data);
            }
        });



// ContentView is the root view of the layout of this activity/fragment
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
                                Contact.setVisibility(View.GONE);
                            }
                        }
                        else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                                Contact.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


    }



    @Override
    public void OnbackDestrecution() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }

    private void saveDeal() {
        Bundle data = getArguments();
        String other1 = data.getString("other1");
        String other2 = data.getString("other2");
        String other3 = data.getString("other3");
        String Created = data.getString("Created");
        String name = data.getString("name");
        String owner = data.getString("owner");
        Integer idDeal = data.getInt("idparts", 0);
        String refrence = data.getString("refrnce");
        String tag_description = data.getString("tag_description");
        Float price = data.getFloat("price");
        String type = data.getString("Type");
        String state = data.getString("state");
        byte[] image = data.getByteArray("image");
        Parts part = new Parts(idDeal,owner,name,other1,price,other2,other3,type,state,image,tag_description,Created,refrence);
        bookmarkViewModel= ViewModelProviders.of(this).get(BookmarkViewModel.class);
        bookmarkViewModel.insert(part);
        Toast.makeText(getActivity(), "Deal BookMarked", Toast.LENGTH_SHORT).show();
    }

    public String loadUsername() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email", "");
        return username;
    }

    private void addComment(String text, String username, int idParts) {
        loading.setVisibility(View.VISIBLE);
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        //loading.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("text", text);
        params.put("username", username);
        params.put("dealid", Integer.toString(idParts));


        // the entered data as the JSON body.
        JsonObjectRequest jsObjRequest = new
                JsonObjectRequest(Request.Method.POST,
                URL_ADD, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("success")) {

                                //loading.setVisibility(View.GONE);
                                comment.getEditText().setText("");
                                //openDialogue();

                                loading.setVisibility(View.GONE);

                            } else {
                                Toast.makeText(getActivity(), " internal error happened " + response.getString("error"), Toast.LENGTH_SHORT).show();
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
    private boolean validateEmpty(EditText text) {
        String textToCheck = text.getText().toString().trim();
        if (textToCheck.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }
    public static String priceFormInflater(Float price)
    {


        if (price == Math.floor(price)) {
            return String.format("%.0f", price);
        } else {
            return Float.toString(price);
    }


}

    private void getuseerComments(String username,int dealid)
    {
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.start();
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("dealid", String.valueOf(dealid));
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST, URL_GETUSERCOMMENT, new JSONObject(params), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject hit = response.getJSONObject(i);
                        nums = String.valueOf(hit.getInt("result"));
                        fullName = MainActivity.capitalize(hit.getString("lastname"))+" "+MainActivity.capitalize(hit.getString("firstname"));
                        owner.setText(fullName);
                        num.setText(nums);

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                return;

            }
        });

        requestQueue.add(request);
    }

}

