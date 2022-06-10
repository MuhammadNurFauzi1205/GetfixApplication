package com.example.getfixapplication.ui.auth.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.getfixapplication.databinding.ActivityLoginBinding
import com.example.getfixapplication.ui.auth.register.SignupActivity
import com.example.getfixapplication.ui.home.NavigationHomeActivity
import com.example.getfixapplication.utils.ConstVal.RC_SIGN_IN
import com.example.getfixapplication.utils.ConstVal.UID_TOKEN
import com.example.getfixapplication.utils.ConstVal.USERNAME
import com.example.getfixapplication.utils.ConstVal.USER_ID_SESSION
import com.example.getfixapplication.utils.hideKeyboard
import com.example.getfixapplication.utils.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    val WebClient = "1063081921561-nrcop7njimnvlf14njcjre8mhuueoqsq.apps.googleusercontent.com"

    val db = Firebase.firestore
    lateinit var google: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.ed1
        val passwordd = binding.ed2

        google = binding.google
        auth = Firebase.auth

        val googlesignin = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WebClient)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googlesignin)
        auth = FirebaseAuth.getInstance()
        val sharedPreferences =
            getSharedPreferences(USER_ID_SESSION, MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        binding.btnLogin.setOnClickListener {
            hideKeyboard()

            username.editText?.let {
                if (it.text.isNullOrEmpty()) {
                    username.editText!!.error = "Masukan username anda"
                    return@setOnClickListener
                }
            }
            // Check if password less 6 characters
            passwordd.editText?.let {
                if (it.text.isNullOrEmpty()) {
                    passwordd.editText!!.error = "Masukan password anda"
                    return@setOnClickListener
                }
                if (it.text.toString().length < 6) {
                    passwordd.editText!!.error = "Password harus lebih dari 6 karakter"
                    return@setOnClickListener
                }
            }

            val dbUser = db.collection("users")

            auth.signInWithEmailAndPassword(username.editText?.text.toString(), passwordd.editText?.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val uid = user?.uid
                        editor.putString(USER_ID_SESSION, uid)
                        if (uid != null) {
                            dbUser.document(uid).get().addOnSuccessListener {
                                val name = it.data?.get("username")
                                editor.putString(USERNAME, name.toString())
                            }
                        }
                        editor.apply()
                        val two = Intent(this@LoginActivity, NavigationHomeActivity::class.java)
                        startActivity(two)
                        finish()
                    } else {
                        task.exception?.message?.let {
                            showToast(this, it)
                        }
                    }
                }
        }

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
                    val intent = Intent(this, NavigationHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Log.w(TAG, "signinwithcredit:gagal", task.exception)
                }
            }
    }
}

