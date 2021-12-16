package com.example.datingapp.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datingapp.HomeActivity;
import com.example.datingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link SignUpFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private Button mSignUpBtn;
    private EditText mDob;
    private FirebaseFirestore mStore;
    private int USER_AGE=0;
    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        mName = view.findViewById(R.id.signup_name);
        mEmail = view.findViewById(R.id.signup_email);
        mPassword = view.findViewById(R.id.signup_password);
        mSignUpBtn =view.findViewById(R.id.signup_btn);
        mSignUpBtn =view.findViewById(R.id.signup_btn);
        mStore = FirebaseFirestore.getInstance();
        mDob=view.findViewById(R.id.dob);

        Calendar calendar = Calendar.getInstance();
        final int todayYear = calendar.get(Calendar.YEAR);
        final int todayMonth = calendar.get(Calendar.MONTH);
        final int todayDay = calendar.get(Calendar.DAY_OF_MONTH);
        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        USER_AGE = todayYear - year;
                        mDob.setText(dayOfMonth+"/"+month+"/"+year);
                    }
                },todayYear,todayMonth,todayDay).show();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=mEmail.getText().toString();
                String password=mPassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Map<String,Object> map = new HashMap<>();
                            map.put("name",mName.getText().toString());
                            map.put("dob",mDob.getText().toString());
                            map.put("age",USER_AGE);
                            mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Account Created", Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getContext(), HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return view;
    }
}