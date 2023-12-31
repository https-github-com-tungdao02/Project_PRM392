package com.example.project_prm392;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_prm392.entity.History;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class  HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences shared_pref;
    SharedPreferences.Editor editor;
    Button btn_history_fragment_delete;
    CheckBox cb_history_fragment_select_all;
    List<History> list;
    List<History> selected = new ArrayList<>();
    FirebaseDatabase database;
     DatabaseReference databaseReference;
    RecyclerView recyclerView;
    HistoryAdapter adapter;
    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
         database = FirebaseDatabase.getInstance();
         databaseReference = database.getReference("history");
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("history");
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        shared_pref= getActivity().getSharedPreferences("account",MODE_PRIVATE);
        editor= shared_pref.edit();
        String username=shared_pref.getString("username","");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    History history = dataSnapshot.getValue(History.class);
                    if(history.getUser().getUser().equals(username)){
                        list.add(history);
                    }

                }
                list.sort(new Comparator<History>() {
                    @Override
                    public int compare(History o1, History o2) {
                        return -o1.getTime().compareTo(o2.getTime());
                    }
                });
                recyclerView=view.findViewById(R.id.rcv_history);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                // Create and set the adapter
                adapter = new HistoryAdapter(list, new HistoryAdapter.OnDeleteClickListener() {
                    @Override
                    public void onDeleteClick(History history) {
//                        new AlertDialog.Builder(getActivity())
//                                .setTitle("Delete Confirm")
//                                .setNegativeButton("Cancel",null)
//                                .setMessage("Do you want to delete")
//                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        databaseReference.child(history.getId()+"").removeValue(new DatabaseReference.CompletionListener() {
//                                            @Override
//                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                                Toast.makeText(getActivity(),"Delete successfull",Toast.LENGTH_SHORT).show();
//                                            }
//
//                                        });
//                                    }
//                                })
//                                .show();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                        alertDialogBuilder.setMessage("Do you want to delete");
                        TextView textView = new TextView(getContext());
                        textView.setText("Select an option");
                        textView.setPadding(20, 30, 20, 30);
                        textView.setTextSize(20F);
                        textView.setBackgroundColor(getResources().getColor(R.color.bookaland700));
                        textView.setTextColor(Color.BLACK);
                        alertDialogBuilder.setCustomTitle(textView);
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        databaseReference.child(history.getId()+"").removeValue(new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                Toast.makeText(getActivity(),"Delete successfull",Toast.LENGTH_SHORT).show();
                                            }

                                        });
                                    }
                                });

                        alertDialogBuilder.setNegativeButton("No",null);
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                });
                adapter.setSelected(selected);
                recyclerView.setAdapter(adapter);
                btn_history_fragment_delete=view.findViewById(R.id.btn_history_fragment_delete);
                btn_history_fragment_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selected=adapter.getSelected();
                        if(selected.isEmpty()){
                            Toast.makeText(getActivity(),"Don't have data to delete",Toast.LENGTH_SHORT).show();
                        }else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Delete Confirm")
                                .setNegativeButton("Cancel",null)
                                .setMessage("Do you want to delete")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        for (History history: selected) {
                                            databaseReference.child(history.getId()+"").removeValue(new DatabaseReference.CompletionListener() {
                                                @Override
                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                }
                                            });
                                        }
                                    }
                                })
                                .show();

                        }
                    }
                });
                cb_history_fragment_select_all= view.findViewById(R.id.cb_history_fragment_select_all);
                cb_history_fragment_select_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(cb_history_fragment_select_all.isChecked()){
                            adapter.setSelectAll(true);
                        }else {
                            adapter.setSelectAll(false);
                        }
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



//        cb_history_fragment_select_all.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(!adapter.getSelected().isEmpty()){
//                    for (History history:adapter.getSelected()) {
//                        databaseReference.child(history.getId()+"").removeValue(new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                                Toast.makeText(getActivity(),"Delete successfull",Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            }
//        });
        // Inflate the layout for this fragment
        return view;
    }
}