package com.example.comp4200;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp4200.model.Tweet;
import com.example.comp4200.service.impl.LikesService;

import java.util.Date;
import java.util.List;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ItemViewHolder> {

    Context context;
    List<Tweet> tweets;
    boolean liked = false;
    int likeCount = 0;


    public TweetRecyclerAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

        //TODO: connect to db and set likeCount
        // setting likeCount to 0 for now.
        Tweet tweet = tweets.get(position);
        String userId = context.getSharedPreferences("user", Context.MODE_PRIVATE).getString("id", "");
        String tweetContent = tweet.getContent();
        String username = "" + tweet.getDisplayName();
        likeCount = tweet.getLikes().size();
        if (tweet.getLikes().containsKey(userId) && tweet.getLikes().get(userId)) {
            holder.likes.setImageResource(R.drawable.like_full);
        }

        if (tweet.getCreatedAt() != null) {
            Date date = new Date(tweet.getCreatedAt());
            holder.datePosted.setText(date.toString());
        }

        holder.tweetContent.setText(tweetContent);
        holder.tweetUser.setText(username);
        holder.likeCounter.setText("" + likeCount);

        holder.profileImage.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ProfileActivity.class);
            intent.putExtra("user_id", tweets.get(position).getUserId());
            view.getContext().startActivity(intent);
        });

        holder.likes.setOnClickListener(view -> {
            if (tweet.getLikes().containsKey(userId)) {
                holder.likes.setImageResource(R.drawable.like);
                unlikeTweet(tweet.getTweetId(), userId);
            } else {
                holder.likes.setImageResource(R.drawable.like_full);
                likeTweet(tweet.getTweetId(), userId);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tweetUser;
        TextView tweetContent;
        TextView likeCounter;
        TextView datePosted;
        CardView cardView;
        ImageButton likes;
        ImageView profileImage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            tweetUser = itemView.findViewById(R.id.tvPostUsername);
            tweetContent = itemView.findViewById(R.id.tvPostDesc);
            datePosted = itemView.findViewById(R.id.tweet_datePosted);
            cardView = itemView.findViewById(R.id.task);
            likes = itemView.findViewById(R.id.tweet_ibLike);
            profileImage = itemView.findViewById(R.id.imageButton);
            likeCounter = itemView.findViewById(R.id.tweet_likeCounter);

        }

    }

    //TODO: connect to DB
    //functions to interact with DB
    public void likeTweet(String tweetId, String uId) {
        new LikesService().likeTweet(tweetId, uId);
        Toast.makeText(this.context, "Liked tweet!", Toast.LENGTH_SHORT).show();
        liked = true;
    }

    public void unlikeTweet(String tweetId, String uId) {
        new LikesService().unlikeTweet(tweetId, uId);
        Toast.makeText(this.context, "Unliked tweet!", Toast.LENGTH_SHORT).show();
        liked = false;
    }

}
