package com.example.datingapp.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.datingapp.HomeActivity;
import com.example.datingapp.MainActivity;
import com.example.datingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {
    private static final int RESULT_LOAD_IMG = 1212;
    CircleImageView mImg;
    EditText mHobby;
    EditText mLang;
    EditText mDesc;
    ChipGroup mChipGroup;
    ChipGroup mLangChipGroup;
    List<String> mChipList;
    List<String> mLangList;
    Button mSaveProfile;
    Uri url=null;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private StorageReference mStorage;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ProfileFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ProfileFragment newInstance(String param1, String param2) {
//        ProfileFragment fragment = new ProfileFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        mImg = view.findViewById(R.id.pro_image);
        mLang = view.findViewById(R.id.pro_lang);
        mHobby = view.findViewById(R.id.pro_hobby);
        mDesc = view.findViewById(R.id.pro_desc);
        mChipGroup = view.findViewById(R.id.chip_c);
        mLangChipGroup = view.findViewById(R.id.chip_lang);
        mSaveProfile = view.findViewById(R.id.save_pro);
        mAuth=FirebaseAuth.getInstance();
        mStore=FirebaseFirestore.getInstance();
        mStorage=FirebaseStorage.getInstance().getReference();
        mChipList = new ArrayList<>();
        mLangList = new ArrayList<>();

        //get profiledata
        getProfileData();

        displayChipData(mChipList);
        mImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
        mHobby.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO){
                    mChipList.add(mHobby.getText().toString());
                    displayChipData(mChipList);
                    mHobby.setText("");
                    return true;
                }
                return false;
            }
        });
        mLang.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_GO){
                    mLangList.add(mLang.getText().toString());
                    displayLangData(mLangList);
                    mLang.setText("");
                    return true;
                }
                return false;
            }
        });
        //logout
        Button btn=view.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();
            }
        });
        //save data
        mSaveProfile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
                if(url!=null){
                    mStorage.child(ts+"/").putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        String downloadUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
//                        Log.i("TAG", "onSuccess: "+downloadUrl);
                            Task<Uri> res = taskSnapshot.getStorage().getDownloadUrl();
                            res.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUrl = uri.toString();
                                    Map<String,Object> map=new HashMap<>();
                                    map.put("hobby",String.join(",",mChipList));
                                    map.put("lang",String.join(",",mLangList));
                                    map.put("desc",mDesc.getText().toString());
                                    map.put("img_url",downloadUrl);
                                    mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                                            .update(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                            });

                        }
                    });
                }else{
                    Map<String,Object> map=new HashMap<>();
                    map.put("hobby",String.join(",",mChipList));
                    map.put("lang",String.join(",",mLangList));
                    map.put("desc",mDesc.getText().toString());
                    mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                            .update(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });



        return view;
    }

    private void getProfileData() {
        mStore.collection("Users").document(mAuth.getCurrentUser().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    String desc = task.getResult().getString("desc");
                    String hobby = task.getResult().getString("hobby");
                    String img_url = task.getResult().getString("img_url");
                    String lang = task.getResult().getString("lang");
                    mDesc.setText(desc);
                    List<String> mList = Arrays.asList(hobby.split("\\s*,\\s*"));
                    mChipList.addAll(mList);
                    displayChipData(mChipList);
                    List<String> cList = Arrays.asList(lang.split("\\s*,\\s*"));
                    mLangList.addAll(cList);
                    displayLangData(mLangList);
                    Glide.with(getContext()).load(img_url).into(mImg);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            url =imageUri;
            Glide.with(getContext()).load(imageUri).into(mImg);

        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private void displayLangData(final List<String> mLangList) {
        mLangChipGroup.removeAllViews();
        for(String s: mLangList){
            Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.single_chip_item,null,false);
            chip.setText(s);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLangChipGroup.removeView(v);
                    Chip c= (Chip) v;
                    mLangList.remove(c.getText().toString());
                }
            });
            mLangChipGroup.addView(chip);
        }
    }

    private void displayChipData(final List<String> mChipList) {
        mChipGroup.removeAllViews();
        for(String s: mChipList){
            Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.single_chip_item,null,false);
            chip.setText(s);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChipGroup.removeView(v);
                    Chip c= (Chip) v;
                    mChipList.remove(c.getText().toString());
                }
            });
            mChipGroup.addView(chip);
        }

    }
}