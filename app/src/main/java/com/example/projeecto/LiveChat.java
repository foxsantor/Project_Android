package com.example.projeecto;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.projeecto.adapters.MessageAdapter;
import com.example.projeecto.entities.Message;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import org.json.JSONException;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;


/**
 * A simple {@link Fragment} subclass.
 */
public class LiveChat extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1,username,owner,fullname;
    private String mParam2;
    private EditText mInputMessageView;
    private RecyclerView mMessagesView;
    private OnFragmentInteractionListener mListener;
    private List<com.example.projeecto.entities.Message> mMessages = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;

    public LiveChat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_chat, container, false);
    }





    // TODO: Rename and change types of parameters


    private Socket socket;
    {
        try{
            socket = IO.socket("http://192.168.1.5:5000");
        }catch(URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    public static LiveChat newInstance(String param1, String param2) {
        LiveChat fragment = new LiveChat();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Bundle owna = getArguments();
        owner = owna.getString("email");
        fullname = owna.getString("Full_name");*/
        socket.connect();
        socket.on("message", handleIncomingMessages);
        socket.on("identify",handler);
        socket.on("chat message", handleIncomingMessages);
        if (socket.connected()){
            Toast.makeText(getActivity(), "Connected!!",Toast.LENGTH_SHORT).show();
        }

    }





    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mAdapter = new MessageAdapter(mMessages,getActivity());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Iddentify();
        mMessagesView = (RecyclerView) view.findViewById(R.id.messageListView);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter);
        ImageButton sendButton = (ImageButton) view.findViewById(R.id.sendButton);
        mInputMessageView = (EditText) view.findViewById(R.id.textField);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });


    }
    private void sendMessage(){
        String message = mInputMessageView.getText().toString().trim();
        mInputMessageView.setText("");
        username=loadUsername();
        addMessage(message,username);
        JSONObject sendText = new JSONObject();
        try{
            sendText.put("text",message);
            sendText.put("fullname",fullname);
            sendText.put("owner",owner);
            sendText.put("username",username);
            socket.emit("message", sendText);

        }catch(JSONException e){

        }

    }

    private void Iddentify(){
        username=loadUsername();
        JSONObject sendText = new JSONObject();
        try{
            sendText.put("username",username);
            socket.emit("identify", sendText);

        }catch(JSONException e){}
    }

    public void sendImage(String path)
    {
        JSONObject sendData = new JSONObject();
        try{
            sendData.put("image", encodeImage(path));
            Bitmap bmp = decodeImage(sendData.getString("image"));
            addImage(bmp);
            socket.emit("message",sendData);

        }catch(JSONException e){

        }
    }

    private void addMessage(String message ,String fullname) {

        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .message(message,fullname).build());

        mAdapter = new MessageAdapter( mMessages,getActivity());
        mAdapter.notifyItemInserted(0);
        scrollToBottom();
    }

    private void addImage(Bitmap bmp){
        mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                .image(bmp).build());
        mAdapter = new MessageAdapter( mMessages,getActivity());
        mAdapter.notifyItemInserted(0);
        scrollToBottom();
    }
    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    private String encodeImage(String path)
    {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);

        return encImage;

    }

    private Bitmap decodeImage(String data)
    {
        byte[] b = Base64.decode(data,Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(b,0,b.length);
        return bmp;
    }
    private Emitter.Listener handleIncomingMessages = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    String username;
                    //String imageText;
                    try {
                        message = data.getString("text").toString();
                        username = data.getString("username").toString();

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(),"123")
                                .setSmallIcon(R.drawable.ic_chat_bubble_outline_black_24dp)
                                .setContentTitle(username)
                                .setContentText(message)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
                        notificationManager.notify(1, builder.build());

                        addMessage(message,username);

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    };

    private Emitter.Listener handler = new Emitter.Listener(){
        @Override
        public void call(final Object... args){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
    public  String loadUsername()
    {
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("share", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","");
        return username;
    }


}
