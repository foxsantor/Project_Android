package com.example.projeecto.ui.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projeecto.R;
import com.example.projeecto.ViewModels.BookmarkViewModel;
import com.example.projeecto.adapters.BookmarksAdaptar;
import com.example.projeecto.entities.Parts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeFragment extends Fragment {


    private RecyclerView bookmarks;
    private BookmarkViewModel bookmarkViewModel;
    private FloatingActionButton deleteAll;
    private NestedScrollView nested;
    private ConstraintLayout hider;
    private TextView swipe;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bookmarkViewModel= ViewModelProviders.of(this).get(BookmarkViewModel.class);
        View root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        bookmarks = root.findViewById(R.id.bookmarked);
        deleteAll = root.findViewById(R.id.deleteall);
        nested = root.findViewById(R.id.nested);
        swipe = root.findViewById(R.id.swipe);
        hider = root.findViewById(R.id.hider);

        bookmarks.setLayoutManager(new LinearLayoutManager(getContext()));
        final BookmarksAdaptar adapter = new BookmarksAdaptar(getActivity());
        bookmarks.setAdapter(adapter);
        bookmarkViewModel.getAllParts().observe(this, new Observer<List<Parts>>() {
            @Override
            public void onChanged(List<Parts> parts) {
                if(parts.isEmpty())
                {
                   nested.setVisibility(View.GONE);
                   deleteAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
                   deleteAll.setClickable(false);
                   swipe.setVisibility(View.GONE);
                   hider.setVisibility(View.GONE);
                }else
                {
                    swipe.setVisibility(View.VISIBLE);
                    deleteAll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                    deleteAll.setClickable(true);
                    nested.setVisibility(View.VISIBLE);
                    hider.setVisibility(View.VISIBLE);
                    adapter.submitList(parts);
                }

            }

        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                bookmarkViewModel.delete(adapter.getPartAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Deal deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(bookmarks);

        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookmarkViewModel.deleteAll();
                Toast.makeText(getActivity(), "Bookmarks cleared", Toast.LENGTH_SHORT).show();
            }
        });


        adapter.setOnIteemClickListener(new BookmarksAdaptar.OnItemClickLisnter() {
            @Override
            public void OnItemClick(Parts part) {
                Bundle data = new Bundle();
                data.putString("other1",part.getOther1());
                data.putString("other2",part.getOther2());
                data.putString("other3",part.getOther3());
                data.putString("Created",part.getCreated());
                data.putString("name",part.getName());
                data.putString("owner",part.getOwner());
                data.putInt("idparts",part.getId());
                data.putString("refrnce",part.getRefrence());
                data.putString("tag_description",part.getTag_desc());
                data.putFloat("price",part.getPrice());
                data.putString("Type",part.getType());
                data.putString("state",part.getState());
                data.putByteArray("image",part.getImage());

                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.viewalone,data);
            }
        });

        return root;
    }

}