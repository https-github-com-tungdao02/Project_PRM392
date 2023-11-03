package com.example.project_prm392;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project_prm392.entity.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences shared_pref;
    SharedPreferences.Editor editor;
    TextView _tv_user_account;
    EditText _edt_email_account,_edt_phone_account,_edt_img_account,_edt_gender_account;
    Button btn_update_account,btn_change_account,btn_delete_account;
    CircleImageView _imv_account;

    RadioButton rdo_male,rdo_female,rdo_other;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        _tv_user_account=view.findViewById(R.id._tv_user_account);
        _edt_email_account=view.findViewById(R.id._edt_email_account);
        _edt_gender_account=view.findViewById(R.id._edt_gender_account);
        _edt_img_account=view.findViewById(R.id._edt_img_account);
        _edt_phone_account=view.findViewById(R.id._edt_phone_account);
        _imv_account=view.findViewById(R.id._imv_account);
        btn_change_account=view.findViewById(R.id.btn_change_account);
        btn_delete_account=view.findViewById(R.id.btn_delete_account);
        btn_update_account=view.findViewById(R.id.btn_update_account);
        rdo_male=view.findViewById(R.id.rdo_male);
        rdo_female=view.findViewById(R.id.rdo_female);
        rdo_other=view.findViewById(R.id.rdo_other);

        rdo_other.setEnabled(false);
        rdo_other.setVisibility(View.INVISIBLE);
        rdo_female.setEnabled(false);
        rdo_female.setVisibility(View.INVISIBLE);
        rdo_male.setEnabled(false);
        rdo_male.setVisibility(View.INVISIBLE);


        shared_pref= getActivity().getSharedPreferences("account",MODE_PRIVATE);
        editor= shared_pref.edit();
        String username=shared_pref.getString("username","");
        _tv_user_account.setText(username);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    Glide.with(view).load(user.getImage()).into(_imv_account);
                    _edt_img_account.setEnabled(false);
                    _edt_gender_account.setEnabled(false);
                    _edt_phone_account.setEnabled(false);
                    _edt_email_account.setEnabled(false);

                    _edt_gender_account.setText(user.getGender());
                    _edt_email_account.setText(user.getEmail());
                    _edt_phone_account.setText(user.getPhone());
                    _edt_img_account.setText(user.getImage());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_update_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_update_account.getText().equals("Update Infor")){
                    btn_update_account.setText("Confirm");
                    _edt_img_account.setEnabled(true);
                    _edt_phone_account.setEnabled(true);
                    _edt_email_account.setEnabled(true);
                    _edt_gender_account.setVisibility(View.INVISIBLE);

                    rdo_other.setEnabled(true);
                    rdo_female.setEnabled(true);
                    rdo_male.setEnabled(true);

                    rdo_other.setVisibility(View.VISIBLE);
                    rdo_female.setVisibility(View.VISIBLE);
                    rdo_male.setVisibility(View.VISIBLE);

                    String gender= _edt_gender_account.getText().toString();
                    if(gender.equals("Male")){
                        rdo_male.setChecked(true);
                    }
                    if(gender.equals("Female")){
                        rdo_female.setChecked(true);
                    }
                    if(gender.equals("Other")){
                        rdo_other.setChecked(true);
                    }
                }else {
                    btn_update_account.setText("Update Infor");
                    if(rdo_male.isChecked()){
                        _edt_gender_account.setText(rdo_male.getText());
                    }if(rdo_other.isChecked()){
                        _edt_gender_account.setText(rdo_other.getText());
                    }if(rdo_female.isChecked()){
                        _edt_gender_account.setText(rdo_female.getText());
                    }
                    reference.child(username).child("email").setValue(_edt_email_account.getText().toString());
                    reference.child(username).child("gender").setValue(_edt_gender_account.getText().toString());
                    reference.child(username).child("image").setValue(_edt_img_account.getText().toString());
                    reference.child(username).child("phone").setValue(_edt_phone_account.getText().toString());



                    _edt_img_account.setEnabled(false);
                    _edt_gender_account.setEnabled(false);
                    _edt_phone_account.setEnabled(false);
                    _edt_email_account.setEnabled(false);
                    _edt_gender_account.setVisibility(View.VISIBLE);
                    rdo_other.setEnabled(false);
                    rdo_other.setVisibility(View.INVISIBLE);
                    rdo_female.setEnabled(false);
                    rdo_female.setVisibility(View.INVISIBLE);
                    rdo_male.setEnabled(false);
                    rdo_male.setVisibility(View.INVISIBLE);

                }
            }
        });
        rdo_male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdo_male.isChecked()){
                    rdo_female.setChecked(false);
                    rdo_other.setChecked(false);
                }
                else{

                }
            }
        });
        rdo_female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdo_male.isChecked()){
                    rdo_male.setChecked(false);
                    rdo_other.setChecked(false);
                }
                else{

                }
            }
        });
        rdo_other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rdo_other.isChecked()){
                    rdo_female.setChecked(false);
                    rdo_male.setChecked(false);
                }
                else{

                }
            }
        });
        btn_change_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog= new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.change_password_dialog);
                Window window= dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
                dialog.setCancelable(false);
                EditText edt_current_password= dialog.findViewById(R.id.emailBox);
                EditText edt_new_password= dialog.findViewById(R.id.userNameBox);
                EditText edt_confirm_password= dialog.findViewById(R.id.edt_confirm_password);

                Button btnCancel_pass=dialog.findViewById(R.id.btnCancel);
                Button btnReset_pass=dialog.findViewById(R.id.btnReset);

                btnCancel_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnReset_pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pass=shared_pref.getString("password","");
                        if(edt_current_password.getText().toString().equals(pass)&& edt_new_password.getText().toString().equals(edt_confirm_password.getText().toString())){

                            reference.child(username).child("password").setValue(edt_new_password.getText().toString());
                            editor.putString("password",edt_new_password.getText().toString());
                            editor.commit();
                            Toast.makeText(getActivity(),"Change password successfull",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(getActivity(),"Check password again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.show();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}