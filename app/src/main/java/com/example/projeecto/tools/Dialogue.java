package com.example.projeecto.tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.projeecto.R;

public class Dialogue extends AppCompatDialogFragment {


    private DialogueListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.layout_alert,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(root).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.OnOkClicked();

            }
        });

        return builder.create();
    }
    public interface DialogueListener {

        void  OnOkClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogueListener)getTargetFragment();
        }catch (ClassCastException err){
            throw  new ClassCastException(context.toString()
                    +" must implement the listenr");
        }

    }
}
