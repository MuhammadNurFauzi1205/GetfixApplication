package com.example.getfixapplication.ui

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInRequest: BeginSignInRequest
    val WebClient = "116303479224-qe4kiq0uj2dfitj8raejbd5bsgvqsja1.apps.googleusercontent.com"

    var EMAIL_KEY = "emailkey"
    var email_key = ""
    lateinit var  username : TextInputLayout
    lateinit var passwordd: TextInputLayout
    lateinit var google: ImageView
    private val REQ_ONE_TAP = 1001
    private var showOneTapUI = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username = binding.ed1
        passwordd = binding.ed2
        google = binding.google
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {


            myreference = FirebaseDatabase.getInstance().reference
                .child("users").child(username.editText.toString())


            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        //ambil data password dari firebase
                        val passwordFromFirebase = dataSnapshot.child("password").value.toString()

                        //validasi password dengan firebase
                        if (passwordd.editText.toString() == passwordFromFirebase) {

                            //simpan username pada local
                            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(email_key, username.editText.toString())
                            editor.apply()
                            //berpindah activity
                            val two = Intent(this@LoginActivity , HomeActivity::class.java)
                            startActivity(two)
                        } else {
                            Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "email tidak ada", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(applicationContext, "Database error", Toast.LENGTH_SHORT).show()
                }
            })
        }

        val googlesignin = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WebClient)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googlesignin)

        binding.google.setOnClickListener {
            GoogleSign()
        }


        binding.tvActionDaftar.setOnClickListener {
            val signup = Intent(this, SignupActivity::class.java)
            startActivity(signup)
        }


    }

    private fun GoogleSign(){
        val signinIntent = googleSignInClient.signInIntent
        startActivityForResult(signinIntent, RC_SIGN_IN)
    }

    companion object{
        const val RC_SIGN_IN = 1001
        const val EXTRA_NAME = "extra_name"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode ==  RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseauthwithgoogle:"+account.id)
                firebaseAuthWithGugel(account.id!!)
            } catch (e : ApiException) {
                Log.w(TAG, "google sign in failed", e)
            }
        }

    }

    private fun firebaseAuthWithGugel(token: String) {
        val credit = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credit)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signinwithcredit:sukses")
                    val user = auth.currentUser
                    updateUI(user)
                }
                else {
                    Log.w(TAG, "signinwithcredit:gagal", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            intent.putExtra(EXTRA_NAME, user.displayName)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        currentUser
    }
}