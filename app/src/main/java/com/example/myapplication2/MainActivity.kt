package com.example.myapplication2

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await
import kotlin.math.log

class MainActivity: AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue
    private lateinit var listIntent: Intent
    private var emailText : String = ""
    private var passwordText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init of view elements
        val textView : TextView = findViewById(R.id.textView)
        val email : TextView = findViewById(R.id.emailText)
        val password : TextView = findViewById(R.id.passwordText)
        val apiBtn : Button = findViewById(R.id.button)

        //retrofit 2 request
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.sendReq2().enqueue(
            object : Callback<ResponseModel2> {
                //overriding response function of retrofit
                override fun onResponse(
                    call: Call<ResponseModel2>,
                    response: retrofit2.Response<ResponseModel2>
                ) {
                    //variable that makes it easier to have access to token
                    val res = response.body()?.support?.text.toString()

                    //click listener inside of request -> allows to send response via Intent
                    apiBtn.setOnClickListener {
                        if (emailText != null){
                            emailText = email.text.toString()
                            passwordText = password.text.toString()
                            val email2 : String=emailText
                            val password2 : String= passwordText
                            listIntent = Intent(this@MainActivity, AdditionalActivity::class.java)
                            listIntent.putExtra("email", email2)
                            listIntent.putExtra("password", password2)
                            listIntent.putExtra("res", res)
                            startActivity(listIntent)}
                        else{
                            textView.text= "$emailText i $passwordText"}
                    }

                }
                //overriding failure function of retrofit
                override fun onFailure(call: Call<ResponseModel2>, t: Throwable) {
                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )


    }
}