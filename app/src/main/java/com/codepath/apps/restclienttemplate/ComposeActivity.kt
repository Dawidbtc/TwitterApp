package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.codepath.apps.restclienttemplate.models.Tweet
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONObject

class ComposeActivity : AppCompatActivity() {

    lateinit var etCompose : EditText
    lateinit var btnTweet: Button
    lateinit var client: TwitterClient
    lateinit var charCount: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose)

        etCompose = findViewById<EditText>(R.id.editTextTextMultiLine)
        btnTweet = findViewById<Button>(R.id.sendTweet)
        client = TwitterApplication.getRestClient(this)
        charCount = findViewById<TextView>(R.id.charCount)
        etCompose.addTextChangedListener {
            charCount.text=(280-etCompose.text.length).toString()
            if (etCompose.text.length>280){
                charCount.setTextColor(Color.RED)
            }
        }
        btnTweet.setOnClickListener{
            val tweetContent = etCompose.text.toString()
            if (tweetContent.isEmpty()){
                Toast.makeText(this,"Can't send empty tweet", Toast.LENGTH_SHORT).show()
            }
            else if (tweetContent.length > 280){
                Toast.makeText(this, "Tweet is too long!", Toast.LENGTH_SHORT).show()
            }
            else{
                client.publishTweet(tweetContent, object : JsonHttpResponseHandler(){
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e("Publish Tweet:","Failed to public tweet", throwable)
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        val tweet = Tweet.fromJson(json.jsonObject)
                        val intent = Intent()
                        intent.putExtra("tweet",tweet)
                        setResult(RESULT_OK,intent)
                        finish()
                    }

                })
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        var colorDrawable = ColorDrawable(Color.parseColor("#1DA1F2"))
        var actionbar=getSupportActionBar()
        actionbar?.setDisplayShowHomeEnabled(true)
        actionbar?.setLogo(R.drawable.twitter)
        actionbar?.setDisplayUseLogoEnabled(true)
        actionbar?.setBackgroundDrawable(colorDrawable)
        return true
    }
}