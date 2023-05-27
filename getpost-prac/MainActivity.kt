package com.example.bustame_example

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.bustame_example.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val api = APIS.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnGet.setOnClickListener {
            api.get_users().enqueue(object : Callback<HTTP_GET_Model> {
                override fun onResponse(call: Call<HTTP_GET_Model>, response: Response<HTTP_GET_Model>) {
                    Log.d("log",response.toString())
                    Log.d("log", response.body().toString())
                    if(!response.body().toString().isEmpty())
                        binding.Text.setText(response.body().toString());
                }

                override fun onFailure(call: Call<HTTP_GET_Model>, t: Throwable) {
                    // 실패
                    Log.d("log",t.message.toString())
                    Log.d("log","fail")
                }
            })
        }

        binding.btnPost.setOnClickListener {
            val data = PostModel(binding.etId.text.toString(),binding.etBusstopid.text.toString(),binding.etMessage.text.toString())
            api.post_users(data).enqueue(object : Callback<PostResult> {
                override fun onResponse(call: Call<PostResult>, response: Response<PostResult>) {
                    Log.d("log",response.toString())
                    Log.d("log", response.body().toString())
                    if(!response.body().toString().isEmpty())
                        binding.Text.setText(response.body().toString());
                }

                override fun onFailure(call: Call<PostResult>, t: Throwable) {
                    // 실패
                    Log.d("log",t.message.toString())
                    Log.d("log","fail")
                }
            })
        }

    }
}
