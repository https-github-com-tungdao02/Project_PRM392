package com.example.project_prm392;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_prm392.entity.Book;
import com.example.project_prm392.entity.Page;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReadPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReadPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DatabaseReference ref;

    private int page =1;
    public TextView pageContentTextView;
    public String bookKey;
    public ReadPageFragment() {
        // Required empty public constructor
    }

    public ReadPageFragment(int page, int bookKey){

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadPageFragment newInstance(String param1, String param2) {
        ReadPageFragment fragment = new ReadPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref = db.getReference("page");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read_page, container, false);
        pageContentTextView = view.findViewById(R.id.pageContentTextView);
        ImageView menuBtn = view.findViewById(R.id.menuButton);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bookId", Context.MODE_PRIVATE);
        bookKey = sharedPreferences.getString("keyId","");
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCommentDialog();
            }
        });
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Book book = childSnapshot.child("book").getValue(Book.class);
                    if(book.getId() == Integer.parseInt(bookKey) && childSnapshot.child("number").getValue(int.class) == page){
                        String content = childSnapshot.child("content").getValue(String.class);
                        pageContentTextView.setText(content);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void showCommentDialog() {
        PageFragment dialog = new PageFragment(pageContentTextView,Integer.parseInt(bookKey));
        FragmentManager fragmentManager = getChildFragmentManager();
        dialog.show(fragmentManager, "ChapterDialog");
    }
}