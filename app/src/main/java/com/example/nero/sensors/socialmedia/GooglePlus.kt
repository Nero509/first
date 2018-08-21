package com.example.nero.sensors.socialmedia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.nero.sensors.R
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_google_plus.*
import com.google.android.gms.common.api.ApiException

class GooglePlus : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_plus)

        val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        //val gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val client=GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build()


        btn_sign_in.setOnClickListener {
            val sign=Auth.GoogleSignInApi.getSignInIntent(client)
            startActivityForResult(sign,700)
        }

        btn_sign_out.setOnClickListener { _ ->
            Auth.GoogleSignInApi.signOut(client).setResultCallback {
                Toast.makeText(this@GooglePlus,"LOG OUT",Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==700){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            Toast.makeText(this@GooglePlus,task.isSuccessful.toString(),Toast.LENGTH_SHORT).show()
            try {
                val account = task.getResult(ApiException::class.java)
                // Signed in successfully, show authenticated UI.
                Log.d("DETAILS",account.email)
                Log.d("DETAILS",account.displayName)
                Log.d("DETAILS",account.photoUrl.toString())
                Log.d("DETAILS",account.id)


                Toast.makeText(this@GooglePlus,account.email,Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {

                Toast.makeText(this@GooglePlus,"EXP",Toast.LENGTH_SHORT).show()
            }


        }
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}
