package com.example.comp4200.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comp4200.R;
import com.example.comp4200.TweetRecyclerAdapter;
import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.impl.TweetServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TweetFragment extends Fragment {

    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_GET_LIKES = "is_likes";

    private String userId;
    private boolean isLikes;
    private List<Tweet> tweets;
    private RecyclerView recyclerView;
    private TweetRecyclerAdapter adapter;

    public TweetFragment() {
        tweets = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userId User ID to filter.
     * @return A new instance of fragment Tweet.
     */
    public static TweetFragment newInstance(String userId, Boolean isLikes) {
        TweetFragment fragment = new TweetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        args.putBoolean(ARG_GET_LIKES, isLikes);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getString(ARG_USER_ID);
            isLikes = getArguments().getBoolean(ARG_GET_LIKES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweet, container, false);

        adapter = new TweetRecyclerAdapter(getActivity(), tweets);
        recyclerView = view.findViewById(R.id.recyclerViewTweets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        if (!userId.isEmpty()) {
            if (isLikes) {
                new TweetServiceImpl().getLikedTweets(getActivity(), userId, obj -> {
                    tweets.clear();
                    if (obj instanceof List<?>) {
                        for (Tweet tweet : (List<Tweet>) obj) {
                            if (tweet.getLikes().containsKey(userId) && tweet.getLikes().get(userId))
                                tweets.add(tweet);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            } else {
                new TweetServiceImpl().getUserTweets(getActivity(), userId, obj -> {
                    tweets.clear();
                    if (obj instanceof List<?>) {
                        for (Tweet tweet : (List<Tweet>) obj) {
                            tweets.add(tweet);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
        return view;
    }
}