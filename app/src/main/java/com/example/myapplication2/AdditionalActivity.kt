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
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additional)
        requestQueue = Volley.newRequestQueue(this)


        val textView: TextView = findViewById(R.id.textView2)
        val textView2: TextView = findViewById(R.id.textView3)

        val extras = intent.extras
        val email = extras?.getString("email").toString()
        val password = extras?.getString("password").toString()
        val res = extras?.getString("res").toString()

        Log.v("LOGG", "$email $password")

        val postParams = HashMap<String, String>()
        postParams["email"] = email
        postParams["password"] = password

        val requestModel = RequestModel(email, password)

        val response = ServiceBuilder.buildService(ApiInterface::class.java)
        response.sendReq(requestModel).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(
                    call: Call<ResponseModel>,
                    response: Response<ResponseModel>
                ) {
                   val token = response.body()?.token.toString()
                    textView.text = "Token : $token, Res : $res"
                    Toast.makeText(
                        this@AdditionalActivity,
                        response.body()?.token.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    Toast.makeText(this@AdditionalActivity, t.toString(), Toast.LENGTH_LONG).show()
                }

            }
        )
        val data = response.sendReq(requestModel)


    }
}

