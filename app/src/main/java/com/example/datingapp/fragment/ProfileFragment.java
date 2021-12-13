package com.example.datingapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.datingapp.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ProfileFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class ProfileFragment extends Fragment {
    CircleImageView mImg;
    EditText mHobby;
    EditText mLang;
    EditText mDesc;
    ChipGroup mChipGroup;
    ChipGroup mLangChipGroup;
    List<String> mChipList;
    List<String> mLangList;


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
        mChipList = new ArrayList<>();
        mLangList = new ArrayList<>();

        displayChipData(mChipList);

        mHobby.setOnEditorActionListener(new TextView.OnEditorActionListener() { //saat mengetikkan hobby langsung muncul di chiplist
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_GO){
                    mChipList.add(mHobby.getText().toString()); //penambahan chip data ke list
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

        return view;
    }

    // display chip data
    private void displayChipData(List<String> mChipList) { //menampilkan chip list dalam bentuk bubble (background yang telah dibuat)
        mChipGroup.removeAllViews(); //saat memasukkan data yang baru, tidak akan ada duplikat
        for(String s: mChipList){
            Chip chip = (Chip) this.getLayoutInflater().inflate(R.layout.single_chip_item,null,false);
            chip.setText(s);
            chip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChipGroup.removeView(v); //untuk menghilangkan chip data
                    Chip c= (Chip) v;
                    mChipList.remove(c.getText().toString());
                }
            });
            mChipGroup.addView(chip);
        }

    }
    //display language data
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
}