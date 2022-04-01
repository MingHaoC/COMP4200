package com.example.comp4200.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp4200.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Tweet extends Fragment {

    private static final String ARG_USER_ID = "user_id";

    private String userId;
    private ArrayList<Object> tweets; //TODO: needs tweet model

    public Tweet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId User ID to filter.
     * @return A new instance of fragment Tweet.
     */
    public static Tweet newInstance(String userId) {
        Tweet fragment = new Tweet();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
        }

        if (!userId.isEmpty()){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            // TODO: Get List of Tweets filtered by userId
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }
}