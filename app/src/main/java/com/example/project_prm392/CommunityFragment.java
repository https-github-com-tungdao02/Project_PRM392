package com.example.project_prm392;

import static java.util.UUID.randomUUID;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.*;
import java.util.*;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.example.project_prm392.entity.Community;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

    private  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    final List<Community> communityList = new ArrayList<>();

    private ImageView commentBtn;
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
        commentBtn = view.findViewById(R.id.commentButton);
        final RecyclerView recyclerView = view.findViewById(R.id.communityRecyclerView);
        final CommunityAdapter adapter = new CommunityAdapter(communityList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentDialog();
            }
        });

        // Attach ValueEventListener to fetch and display data
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UpdateDataView(snapshot);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UpdateDataView(snapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                UpdateDataView(snapshot);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                UpdateDataView(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            void UpdateDataView(DataSnapshot dataSnapshot){
                String description = dataSnapshot.child("description").getValue(String.class);
                String date = dataSnapshot.child("date").getValue(String.class);
                int like = dataSnapshot.child("like").getValue(Integer.class);
                Date dateTimed =null;
                try {
                    dateTimed = sdf.parse(date);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Community community = new Community(description, dateTimed, like);
                community.firebaseId = UUID.fromString(dataSnapshot.getKey());
                for(int i =0 ; i <communityList.size();i++){
                    if (communityList.get(i).firebaseId.equals(community.firebaseId)){
                        communityList.remove(i);
                        break;
                    }
                }

                communityList.add(community);
                Collections.sort(communityList);
                adapter.notifyDataSetChanged();
            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                communityList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    if(childSnapshot.child("book_id").getValue(Integer.class) == 1 ){

                        String description = childSnapshot.child("description").getValue(String.class);
                        String date = childSnapshot.child("date").getValue(String.class);
                        int like = childSnapshot.child("like").getValue(Integer.class);
                        Date dateTimed =null;
                        try {
                            dateTimed = sdf.parse(date);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        Community community = new Community(description, dateTimed, like);
                        community.firebaseId = UUID.fromString(childSnapshot.getKey());
                        communityList.add(community);
                    }
                }
                Collections.sort(communityList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors if any
            }

        });
        return view;
    }
    private void showCommentDialog() {
        CommentFragment dialog = new CommentFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        dialog.show(fragmentManager, "MyDialog");
    }

}