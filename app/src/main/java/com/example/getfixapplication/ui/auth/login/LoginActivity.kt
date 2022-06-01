package com.example.getfixapplication.ui.auth.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivityLoginBinding
import com.example.getfixapplication.ui.auth.register.SignupActivity
import com.example.getfixapplication.ui.home.HomeActivity
import com.example.getfixapplication.ui.home.HomeeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

//    private lateinit var myreference : DatabaseReference
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val WebClient = "1063081921561-nrcop7njimnvlf14njcjre8mhuueoqsq.apps.googleusercontent.com"
    val db = Firebase.firestore
    var EMAIL_KEY = "emailkey"
    var email_key = ""
//    lateinit var  username : TextInputLayout
//    lateinit var passwordd: TextInputLayout
    lateinit var google: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = binding.ed1
        val passwordd = binding.ed2
        google = binding.google
        auth = Firebase.auth
        binding.btnLogin.setOnClickListener {


//            myreference = FirebaseDatabase.getInstance().reference
//                .child("users").child(username.editText?.text.toString())
////
////
//            myreference.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    if (dataSnapshot.exists()) {
//
//                        //ambil data password dari firebase
//                        val passwordFromFirebase = dataSnapshot.child("password").value.toString()
//
//                        //validasi password dengan firebase
//                        if (passwordd.editText?.text.toString() == passwordFromFirebase) {
//
//                            //simpan username pada local
//                            val sharedPreferences = getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
//                            val editor = sharedPreferences.edit()
//                            editor.putString(email_key, username.editText?.text.toString())
//                            editor.apply()
//                            //berpindah activity
//                            val two = Intent(this@LoginActivity , HomeActivity::class.java)
//                            startActivity(two)
//                        } else {
//                            Toast.makeText(applicationContext, "Password salah", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    } else {
//                        Toast.makeText(applicationContext, "email tidak ada", Toast.LENGTH_SHORT)
//                            .show()
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Toast.makeText(applicationContext, "Database error", Toast.LENGTH_SHORT).show()
//                }
//            })

            val user = db.collection("users")

            val data1 = hashMapOf(
                username.editText?.text.toString() to "username",
                passwordd.editText?.text.toString() to "password"
            )
            user.get()

                user.whereEqualTo("username", username.editText?.text.toString()).whereEqualTo("password",passwordd.editText?.text.toString())
                .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                            val sharedPreferences =
                                getSharedPreferences(EMAIL_KEY, MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString(email_key, username.editText?.text.toString())
                            editor.apply()
                            //berpindah activity
                            val two = Intent(this@LoginActivity, HomeeActivity::class.java)
                            startActivity(two)

                        }
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "username/password salah")
                    }








        }

        val googlesignin = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WebClient)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googlesignin)

        auth = FirebaseAuth.getInstance()

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
            val exception= task.exception
            if (task.isSuccessful){
                try {
                    val account = task.getResult(ApiException::class.java)
                    Log.d("LoginActivity", "firebaseauthwithgoogle:"+account.id)
                    firebaseAuthWithGugel(account.idToken!!)
                } catch (e : ApiException) {
                    Log.w("LoginActivity", "google sign in failed", e)
                }
            }else{
                Log.w("LoginActivity", exception.toString())
            }

        }

    }

    private fun firebaseAuthWithGugel(idToken: String) {
        val credit = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credit)
            .addOnCompleteListener(this) {task ->
                if (task.isSuccessful){
                    Log.d(TAG, "signinwithcredit:sukses")
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
//                    val user = auth.currentUser
//                    updateUI(user)
                }
                else {
                    Log.w(TAG, "signinwithcredit:gagal", task.exception)
//                    updateUI(null)
                }
            }
    }

//    private fun updateUI(user: FirebaseUser?) {
//        if (user != null){
//            val intent = Intent(applicationContext, HomeActivity::class.java)
//            intent.putExtra(EXTRA_NAME, user.displayName)
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
}

