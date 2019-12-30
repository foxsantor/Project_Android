package com.example.projeecto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projeecto.R;
import com.example.projeecto.entities.CategoryItems;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<CategoryItems> categories;

    // 1
    public CategoryAdapter(Context context, ArrayList<CategoryItems> categories) {
        this.mContext = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final CategoryItems categoryItems = categories.get(position);

        // 2
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.category_card, null);
        }
        // 3
        final ImageView imageView = convertView.findViewById(R.id.categoryimage);
        final TextView nameTextView = convertView.findViewById(R.id.categoryname);
        // 4

        Glide.with(mContext).asDrawable().load(categoryItems.getImage()).into(imageView);
        nameTextView.setText(categoryItems.getName());
        return convertView;
    }
}
