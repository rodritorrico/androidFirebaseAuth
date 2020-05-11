package com.example.login
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {

    private var mAuth: FirebaseAuth? = null
    private var user: FirebaseUser? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener{

            signIn(email.text.toString(), password.text.toString())  //Firebase
            this.login(email.text.toString(), password.text.toString())  //Spinerr
        }

        create_account.setOnClickListener{
            startActivity(Intent(this, CreateAccount::class.java))
        }
    }



    override fun onStart() {
        super.onStart();
        val currentUser = mAuth!!.currentUser
        //updateUI(currentUser);
    }

    private fun login(email: String, password: String){

        this.showLoading()
        val runnable = Runnable {
            this.hideLoading()
            if(email.isEmpty()) {
                this.showErrorName(this.getString(R.string.login_form_username_empty))
            }else{
                if(password.isEmpty()){
                    this.showErrorPassword(this.getString(R.string.login_form_password_empty))
                } else{
                    if(user != null) {
                        this.showMessage(this.getString(R.string.login_form_success))
                        //TODO:go to other view
                    }else{
                        this.showMessage(this.getString(R.string.login_form_error))
                    }
                }

            }


        }
        Handler().postDelayed(runnable, 6000)
    }

     private fun signIn(email: String, password: String) {

         if(email.isNotEmpty() and password.isNotEmpty()){
             mAuth!!.signInWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this) { task ->
                     if (task.isSuccessful) {
                         user = mAuth!!.currentUser
                         println(user!!.email);
                         //user?.let { updateUI(it) }
                     } else {
                         println("Error");
                     }
                 }

         }


    }


   private fun showErrorName(message: String) {
        email.error = message
    }

    private fun showErrorPassword(message: String) {
        password.error = message
    }

    private fun showMessage(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
        group.visibility = View.GONE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        group.visibility = View.VISIBLE
    }


}