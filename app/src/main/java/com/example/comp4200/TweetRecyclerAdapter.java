package com.example.comp4200;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class TweetRecyclerAdapter extends RecyclerView.Adapter<TweetRecyclerAdapter.ItemViewHolder> {

    Context context;
    List<Tweet> tweets;
    boolean liked = false;
    int likeCount;


    public  TweetRecyclerAdapter(Context context, List<Tweet> tweets){
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

        //TODO: connect to db and set likeCount
        // setting likeCount to 0 for now.
        //int likes = something;
        likeCount = 0;

        String tweetContent = tweets.get(position).getContent();
        String username = "" + tweets.get(position).getDisplayName();

        holder.tweetContent.setText(tweetContent);
        holder.tweetUser.setText(username);

        holder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(new Intent(view.getContext(), ProfileActivity.class));
            }
        });

        //TODO: connect to DB and see if the current user has liked this tweet.
        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(liked){
                    holder.likes.setImageResource(R.drawable.like);
                    unlikeTweet();
                    likeCount -= 1;
                    holder.likeCounter.setText("" + likeCount);
                }else{
                    holder.likes.setImageResource(R.drawable.like_full);
                    likeTweet();
                    likeCount += 1;
                    holder.likeCounter.setText("" + likeCount);
                }

            }
        });

    }

    @Override
    public int getItemCount(){
        return tweets.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tweetUser;
        TextView tweetContent;
        TextView likeCounter;

        CardView cardView;

        ImageButton likes;
        ImageButton replies;

        ImageView profileImage;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            tweetUser = itemView.findViewById(R.id.tvPostUsername);
            tweetContent = itemView.findViewById(R.id.tvPostDesc);

            cardView = itemView.findViewById(R.id.task);

            likes = itemView.findViewById(R.id.tweet_ibLike);
            //replies = itemView.findViewById(R.id.tweet_ibComment);
            profileImage = itemView.findViewById(R.id.tweet_profileImage);

            likeCounter = itemView.findViewById(R.id.tweet_likeCounter);

        }

    }

    //TODO: connect to DB
    //functions to interact with DB
    public void likeTweet(){
        Toast.makeText(this.context, "Liked tweet!", Toast.LENGTH_SHORT).show();
        liked = true;
    }

    public void unlikeTweet(){
        Toast.makeText(this.context, "Unliked tweet!" , Toast.LENGTH_SHORT).show();
        liked = false;
    }

}
