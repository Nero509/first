package com.example.nero.sensors.socialmedia

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nero.sensors.R
import android.widget.Toast
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.twitter.sdk.android.core.*
import com.twitter.sdk.android.core.Twitter
import kotlinx.android.synthetic.main.activity_twitter.*
import com.twitter.sdk.android.core.identity.TwitterAuthClient
import com.example.nero.sensors.MainActivity
import com.twitter.sdk.android.core.TwitterException
import com.squareup.picasso.Picasso
import android.R.attr.name
import android.R.attr.data
import com.twitter.sdk.android.core.models.User
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterApiClient




class Twitter : AppCompatActivity() {

    var TWITTER_CONSUMER_KEY = "8AcC9O67rgm0evNe1cSRxVNSt"
    var TWITTER_CONSUMER_SECRET = "k2aJSEpJjNypQfc31cdoanPp0A2k7AoSfcRxzz6mAiewEGrwbi"

    // Preference Constants
    var PREFERENCE_NAME = "twitter_oauth"
    val PREF_KEY_OAUTH_TOKEN = "oauth_token"
    val PREF_KEY_OAUTH_SECRET = "oauth_token_secret"
    val PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn"

    val TWITTER_CALLBACK_URL = "oauth://t4jsample"

    // Twitter oauth urls
    val URL_TWITTER_AUTH = "auth_url"
    val URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier"
    val URL_TWITTER_OAUTH_TOKEN = "oauth_token"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = TwitterConfig.Builder(this)
                .logger(DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(TwitterAuthConfig(TWITTER_CONSUMER_KEY,TWITTER_CONSUMER_SECRET))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build()

        //finally initialize twitter with created configs
        Twitter.initialize(config)


        setContentView(R.layout.activity_twitter)

        login.callback = object : com.twitter.sdk.android.core.Callback<TwitterSession>() {
            override fun success(result: Result<TwitterSession>) {
                Toast.makeText(this@Twitter,"GOOD",Toast.LENGTH_SHORT).show()
                getData(result.data)

            }

            override fun failure(exception: TwitterException?) {
                exception?.printStackTrace()
                Toast.makeText(this@Twitter,"FAIL",Toast.LENGTH_SHORT).show()}
        }

    }

    private fun getData(twitterSession:TwitterSession){
        val client=TwitterAuthClient()
        client.requestEmail(twitterSession,object:Callback<String>(){
            override fun success(result: Result<String>) {
                val uu=("User Id : " + twitterSession.getUserId() +
                        "\nScreen Name : " + twitterSession.getUserName() + "\nEmail Id : " + result.data)
                Log.d("gffff",uu)
                val twitterApiClient = TwitterCore.getInstance().apiClient

                //Link for Help : https://developer.twitter.com/en/docs/accounts-and-users/manage-account-settings/api-reference/get-account-verify_credentials

                //pass includeEmail : true if you want to fetch Email as well
                val call = twitterApiClient.accountService.verifyCredentials(true, false, true)
                call.enqueue(object : Callback<User>() {
                    override fun success(result: Result<User>) {
                        val user = result.data
                        var imageProfileUrl = user.profileImageUrl
                        Log.d("gffff",imageProfileUrl)
                        logs(user.email?:"NULL")
                        logs(user.id)
                        logs(user.screenName)
                        logs(user.name)
                    }

                    override fun failure(exception: TwitterException) {
                        Toast.makeText(this@Twitter, "Failed to authenticate. Please try again.", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            override fun failure(exception: TwitterException?) {
                exception?.printStackTrace()
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        login.onActivityResult(requestCode, resultCode, data)
    }

    private fun logs(st:Any){
        Log.d("PROFILE",st.toString())
    }
}
