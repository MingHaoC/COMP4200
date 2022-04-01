package com.example.comp4200;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4200.model.Tweet;

import java.util.ArrayList;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ItemViewHolder> {

    Context context;
    ArrayList<Tweet> tweets;


    public  TweetRecyclerAdapter(Context context, ArrayList<Tweet> tweets){
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){

        String tweetContent = tweets.get(position).getContent();
        String username = "" + tweets.get(position).getUserId();

        holder.tweetContent.setText(tweetContent);
        holder.tweetUser.setText(username);

    }

    @Override
    public int getItemCount(){
        return tweets.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tweetUser;
        TextView tweetContent;

        CardView cardView;

        ImageButton likes;
        ImageButton replies;

        ImageView tweetImage;
        ImageView profileImage;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            tweetUser = itemView.findViewById(R.id.tvPostUsername);
            tweetContent = itemView.findViewById(R.id.tvPostDesc);

            cardView = itemView.findViewById(R.id.task);

            likes = itemView.findViewById(R.id.ibComment2);
            replies = itemView.findViewById(R.id.ibComment);

            tweetImage = itemView.findViewById(R.id.imageView2);
            profileImage = itemView.findViewById(R.id.ivPostPicture);

        }

    }

}
