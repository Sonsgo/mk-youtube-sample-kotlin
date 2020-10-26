package com.mkabore.yt

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.services.youtube.YouTubeScopes
import com.mkabore.yt.data.db.DatabaseBuilder
import com.mkabore.yt.data.db.DatabaseHelper
import com.mkabore.yt.data.db.DatabaseHelperImpl
import com.mkabore.yt.data.db.entity.User
import com.mkabore.yt.util.AppConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val RC_SIGN_IN = 10001

class LoginActivity : AppCompatActivity() {

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mDataBaseHelper:  DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mDataBaseHelper =  DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        checkIfLoginRequired()

        setContentView(R.layout.activity_login)

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(AppConstants.CLIENT_ID)
                .requestServerAuthCode(AppConstants.CLIENT_ID)
                .requestScopes(Scope(YouTubeScopes.YOUTUBE))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val googleLoginButton = findViewById<Button>(R.id.google_login_btn)

        googleLoginButton.setOnClickListener {
            signIn()
        }
    }


    private fun checkIfLoginRequired( )
    {
        GlobalScope.launch(Dispatchers.Main) {

            val users: List<User> = mDataBaseHelper.getUsers()
            if (users.isNotEmpty())
            {
                startMainActivity()
            }
        }
    }


    private fun startMainActivity()
    {
        finish()
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(
            signInIntent, RC_SIGN_IN
        )
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                // Update your UI here
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }


    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(
                ApiException::class.java
            )

            // Signed in successfully
            val googleId = account?.id ?: ""
            val googleFirstName = account?.givenName ?: ""
            val googleLastName = account?.familyName ?: ""
            val googleEmail = account?.email ?: ""
            val googleProfilePicURL = account?.photoUrl.toString()
            Log.i("Google Profile Pic URL", googleProfilePicURL)
            val googleIdToken = account?.idToken ?: ""
            val authCode =  account?.serverAuthCode?: ""

            val usersToInsertInDB = mutableListOf<User>()
            val user = User(
                1001,
                googleId,
                googleFirstName,
                googleLastName,
                googleIdToken,
                authCode,
                googleEmail,
                googleProfilePicURL
            )
            usersToInsertInDB.add(user)
            GlobalScope.launch(Dispatchers.Main) {
                mDataBaseHelper.insertAllUsers(usersToInsertInDB)
                startMainActivity()
            }
        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Log.e(
                "failed code=", e.statusCode.toString()
            )
        }
    }
}