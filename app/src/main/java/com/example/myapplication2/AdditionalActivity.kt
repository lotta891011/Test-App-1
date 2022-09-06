package com.example.myapplication2

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AdditionalActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additional)

        //init of view elements
        val textView: TextView = findViewById(R.id.textView2)
        val textView2: TextView = findViewById(R.id.textView3)

        //getting information from Intent from MainActivity
        val extras = intent.extras
        val email = extras?.getString("email").toString()
        val password = extras?.getString("password").toString()
        val res = extras?.getString("res").toString()

        //log for making sure variables are not null
        Log.v("LOGG", "$email $password")

        //making request data using RequestModel and variables from Intent
        val requestModel = RequestModel(email, password)

        //retrofit 2 request
        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<ResponseModel> {
                //overriding response function of retrofit
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                    //variable that makes it easier to have access to token
                    val token = response.body()?.token.toString()

                    //displaying token in a toast
                    textView.text = "Token : $token, Res : $res"
                    Toast.makeText(
                        this@AdditionalActivity,
                        response.body()?.token.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                //overriding failure function of retrofit
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(this@AdditionalActivity, t.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )
    }
}

