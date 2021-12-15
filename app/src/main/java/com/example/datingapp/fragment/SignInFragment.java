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
    private Button mLoginBtn;
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        mEmail = view.findViewById(R.id.login_email);
        mPassword = view.findViewById(R.id.login_password);
        mLoginBtn = view.findViewById(R.id.login_button);
        mAuth=FirebaseAuth.getInstance();
        mDialog=new ProgressDialog(getContext());
        mDialog.setTitle("Logging in");
        mDialog.setMessage("Please wait while logging in");
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    mDialog.show();
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mDialog.dismiss();;
                                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                mDialog.dismiss();
                                Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        return  view;

    }
}