package com.example.project_prm392;

import static java.util.UUID.randomUUID;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.project_prm392.entity.Community;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;

import java.text.*;
import java.util.*;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseReference databaseReference;

    private  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private CommunityAdapter communityAdapter;
    private EditText newCommunityText;
    private Button addCommunityButton;
    final List<Community> communityList = new ArrayList<>();
    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Communities");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.communityRecyclerView);
        newCommunityText = view.findViewById(R.id.newCommunityText);
        addCommunityButton = view.findViewById(R.id.addCommunityButton);

        final CommunityAdapter adapter = new CommunityAdapter(communityList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Attach ValueEventListener to fetch and display data
        addCommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy nội dung mới của community từ EditText
                String newCommunityContent = newCommunityText.getText().toString();
                Community community = new Community(newCommunityContent,"2/2/2022",20);
                community.book_id = 2;
                databaseReference.child(UUID.randomUUID().toString()).setValue(community).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        communityList.add(community);
                        adapter.notifyDataSetChanged();
                    }
                });

                newCommunityText.setText("");

                adapter.notifyDataSetChanged();
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if(childSnapshot.child("book_id").getValue(Integer.class) == 2 ){
                        String description = childSnapshot.child("description").getValue(String.class);
                        String date = childSnapshot.child("date").getValue(String.class);
                        int like = childSnapshot.child("like").getValue(Integer.class);
                        int id = childSnapshot.child("id").getValue(Integer.class);


                        Community community = new Community(id,description, date, like);
                        communityList.add(community);
                    }
                }

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if any
            }
        });

        return view;
    }

}