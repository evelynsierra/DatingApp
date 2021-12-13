package com.example.datingapp.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.datingapp.HomeActivity;
import com.example.datingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;


public class SignInFragment extends Fragment {
    private EditText mEmail;
    private EditText mPassword;
    private Button mloginBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
    public SignInFragment() {
        // Required empty public constructor
    }

//    // TODO: Rename and change types and number of parameters
//    public static SignInFragment newInstance(String param1, String param2) {
//        SignInFragment fragment = new SignInFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        mEmail = view.findViewById(R.id.login_email);
        mPassword = view.findViewById(R.id.login_password);
        mloginBtn = view.findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        mDialog = new ProgressDialog(getContext());
        mDialog.setTitle("Logging In");
        mDialog.setMessage("Please wait while logging in");

        mloginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) { //jika tidak kosong maka boleh masuk
                    mDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                             if(task.isSuccessful()) {
                                 mDialog.dismiss();
                                 Toast.makeText(getContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                                 Intent intent = new Intent(getContext(), HomeActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent. FLAG_ACTIVITY_CLEAR_TASK);
                                 startActivity(intent);

                             }else {
                                 mDialog.dismiss();
                                 Toast.makeText(getContext(),""+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                             }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            mDialog.dismiss();
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        return view;
    }
}