package com.example.projeecto.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.projeecto.R;


public class editText2 extends AppCompatEditText {

    private static final int[] STATE_ERROR = {R.attr.state_errors};

    private boolean mIsError = false;

    public editText2( Context context )
    {
        super( context );
        init();
    }

    public editText2( Context context, AttributeSet attribute_set )
    {
        super( context, attribute_set );
        init();
    }

    public editText2( Context context, AttributeSet attribute_set, int def_style_attribute )
    {
        super( context, attribute_set, def_style_attribute );
        init();
    }

    private void init() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // empty
            }
        });
    }

    @Override
    public void setError(CharSequence error) {
        mIsError = error != null;
        super.setError(error);
        refreshDrawableState();
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        mIsError = error != null;
        super.setError(error, icon);
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (mIsError) {
            mergeDrawableStates(drawableState, STATE_ERROR);
        }
        return drawableState;
    }


    @Override
    public boolean onKeyPreIme( int key_code, KeyEvent event )
    {
        if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP )
            this.clearFocus();

        return super.onKeyPreIme( key_code, event );
    }
}
