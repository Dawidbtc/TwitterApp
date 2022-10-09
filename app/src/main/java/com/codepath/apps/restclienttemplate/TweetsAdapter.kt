package com.codepath.apps.restclienttemplate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.models.Tweet

class TweetsAdapter(val tweets: ArrayList<Tweet>) : RecyclerView.Adapter<TweetsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        val view = inflater.inflate(R.layout.item_tweet,parent,false)

        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tweet: Tweet = tweets.get(position)
        holder.UserName.text = tweet.user?.name
        holder.TweetBody.text = tweet.body
        holder.timestamp.text = tweet.createdAt
        Glide.with(holder.itemView).load(tweet.user?.publicImageUrl).into(holder.ProfileImage)
    }

    override fun getItemCount(): Int {
        return tweets.size
    }

    fun clear(){
        tweets.clear()
        notifyDataSetChanged()
    }

    fun addAll(tweetList: List<Tweet>){
        tweets.addAll(tweetList)
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ProfileImage = itemView.findViewById<ImageView>(R.id.ProfileImage)
        val UserName = itemView.findViewById<TextView>(R.id.tvUsername)
        val TweetBody = itemView.findViewById<TextView>(R.id.tvTweetBody)
        val timestamp = itemView.findViewById<TextView>(R.id.timestamp)
    }
}