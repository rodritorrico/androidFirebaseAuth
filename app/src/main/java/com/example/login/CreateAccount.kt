package com.example.login

import android.R.attr
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_create_account.*


class CreateAccount : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        mAuth = FirebaseAuth.getInstance();


        btnRegister.setOnClickListener{

            createUser(email.text.toString(),password.text.toString())
        }
    }

    fun createUser(email: String, password: String){
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        user= mAuth!!.currentUser
                        println(user)
                        goToLogin()
                    } else {
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
    }


    fun goToLogin(){
        startActivity(Intent(this, MainActivity::class.java))
    }



}
