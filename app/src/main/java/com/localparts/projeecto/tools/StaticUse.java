package com.localparts.projeecto.tools;


import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.util.Patterns;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Random;

public class StaticUse extends AppCompatActivity {

    public static final String SKELETON = "http://192.168.1.8:5000/api/";
    public static final String SKELETON_PRIMITIVE = "http://192.168.1.8:5000/";
    public static final String SHARED_NAME_USER ="user";
    public static final String SHARED_NAME_ADMIN ="admin";
    public static final String SHARED_NAME_USER_LOG ="userLog";
    private static String email,password,fullName;
    private static long id;

    public static String loadEmail(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        return email;
    }
    public static String loadAdminPassword(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        email = sharedPreferences.getString("password","");
        return email;
    }

    public static boolean hasAdmin(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        password = sharedPreferences.getString("password","");
        if(!password.isEmpty() && !password.equals(""))
            return true;
        else
            return false;
    }

    public static void clearShared(String name,Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public static void  saveEmail(Context context,String email)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email",email);
        editor.apply();

    }

    public static void  saveAdmin(Context context,String password)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_ADMIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("password",password);
        editor.apply();

    }


    public static boolean validateEmpty(TextInputLayout text , String type)
    {
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError(""+ type +" can't be empty");
            return false;}
        else
        {
            text.setError(null);
            return true;
        }

    }

    public static boolean validateEmail(TextInputLayout text)
    {
        if(text.getError()!= null)
        {text.setError(null);}
        String textToCheck = text.getEditText().getText().toString().trim();
        if(textToCheck.isEmpty()){
            text.setError("Email can't be empty");
            return false;}
        else if(!Patterns.EMAIL_ADDRESS.matcher(textToCheck).matches()){
            text.setError("Please enter a valid email address ");
            return false;
        }
        else
        {
            text.setError(null);
            return true;
        }
    }

    public  static void removeData(String name,String key, Context context)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();

    }
    public static void editData(String name, Context context, String key,String data)
    {
        SharedPreferences sharedPreferences=context.getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,data);
        editor.commit();
    }

    public static String FloatFormInflater(Float number)
    {

        if (number == Math.floor(number)) {
            return String.format("%.0f", number);
        } else {
            return Float.toString(number);
        }

    }

    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static byte[] transformerImageBase64(ImageView container)
    {
        BitmapDrawable drawable = (BitmapDrawable) container.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return imageInByte;
    }




    public static boolean loggedInInternal(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        fullName = sharedPreferences.getString("name","");
        if(!fullName.isEmpty() && !fullName.equals(""))
            return true;
        else
            return false;
    }
    public static boolean loggedInInternalAdmin(Context context)
    {
        SharedPreferences sharedPreferences= context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        fullName = sharedPreferences.getString("name","");
        password = sharedPreferences.getString("password","");
        if(!fullName.isEmpty() && !fullName.equals("") && !password.isEmpty() && !password.equals("")&& fullName.equals("Administrator ")&& password.equals(loadAdminPassword(context)))
            return true;
        else
            return false;
    }



    public static void  saveSession(Context context,String password,String fullName,long id)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_NAME_USER_LOG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",fullName);
        editor.putString("password",password);
        editor.putLong("id",id);
        editor.apply();
    }

    public static byte[] imageGetter(ImageView imageView)
    {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();
        return  imageInByte;
    }


    public static void backgroundAnimator(ViewGroup viewGroup)
    {
        AnimationDrawable animationDrawable = (AnimationDrawable)viewGroup.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

    }

    public static final String insertImage(ContentResolver cr,
                                           Bitmap source,
                                           String title,
                                           String description) {

        ContentValues values = new ContentValues();
        values.put(Images.Media.TITLE, title);
        values.put(Images.Media.DISPLAY_NAME, title);
        values.put(Images.Media.DESCRIPTION, description);
        values.put(Images.Media.MIME_TYPE, "image/jpeg");
        // Add the date meta data to ensure the image is added at the front of the gallery
        values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());

        Uri url = null;
        String stringUrl = null;    /* value to be returned */

        try {
            url = cr.insert(Images.Media.EXTERNAL_CONTENT_URI, values);

            if (source != null) {
                OutputStream imageOut = cr.openOutputStream(url);
                try {
                    source.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                // Wait until MINI_KIND thumbnail is generated.
                Bitmap miniThumb = Images.Thumbnails.getThumbnail(cr, id, Images.Thumbnails.MINI_KIND, null);
                // This is for backward compatibility.
                storeThumbnail(cr, miniThumb, id, 50F, 50F,Images.Thumbnails.MICRO_KIND);
            } else {
                cr.delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                cr.delete(url, null, null);
                url = null;
            }
        }

        if (url != null) {
            stringUrl = url.toString();
        }

        return stringUrl;
    }

    private static final Bitmap storeThumbnail(
            ContentResolver cr,
            Bitmap source,
            long id,
            float width,
            float height,
            int kind) {

        // create the matrix to scale it
        Matrix matrix = new Matrix();

        float scaleX = width / source.getWidth();
        float scaleY = height / source.getHeight();

        matrix.setScale(scaleX, scaleY);

        Bitmap thumb = Bitmap.createBitmap(source, 0, 0,
                source.getWidth(),
                source.getHeight(), matrix,
                true
        );

        ContentValues values = new ContentValues(4);
        values.put(Images.Thumbnails.KIND,kind);
        values.put(Images.Thumbnails.IMAGE_ID,(int)id);
        values.put(Images.Thumbnails.HEIGHT,thumb.getHeight());
        values.put(Images.Thumbnails.WIDTH,thumb.getWidth());

        Uri url = cr.insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream thumbOut = cr.openOutputStream(url);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
            thumbOut.close();
            return thumb;
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }


    public static String randomizerName(String bytes) {
        byte[] array = new byte[4]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return "BioReg_"+generatedString+bytes;
    }


}
