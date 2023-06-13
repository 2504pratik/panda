package com.example.panda

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.panda.adapter.MessageAdapter
import com.example.panda.api.ApiUtilities
import com.example.panda.databinding.ActivityChatBinding
import com.example.panda.models.MessageModels
import com.example.panda.models.request.ChatGenerateRequest
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.RequestBody

class ChatActivity :AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding

    var list = ArrayList<MessageModels>()
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            finish()
        }
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.stackFromEnd = true
        adapter = MessageAdapter(list)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = mLayoutManager

        binding.sendbtn.setOnClickListener {
            if (binding.userMessage.text!!.isEmpty()) {
                Toast.makeText(this,"Enter Message",Toast.LENGTH_SHORT).show()
            } else {
                callApi()
            }
        }

    }

    private fun callApi() {
        list.add(MessageModels( true,false,binding.userMessage.text.toString()))
        adapter.notifyItemInserted(list.size - 1)
        binding.recyclerView.recycledViewPool.clear()
        binding.recyclerView.smoothScrollToPosition(list.size -1)

        val apiInterface = ApiUtilities.getApiInterface()
        val requestBody = RequestBody.create(MediaType.parse("application/json"),
        Gson().toJson(
            ChatGenerateRequest(
                250,
                "text-davinci-003",
                binding.userMessage.text.toString(),
                0.7
            )
        )
        )

        val contentType = "application/json"
        val authorization = "Bearer ${utils.API_KEY}"

        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = apiInterface.generatChat(
                    contentType, authorization, requestBody
                )
                val textResponse = response.choices.first().text
                list.add(MessageModels(false,false,textResponse))
                withContext(Dispatchers.Main) {
                    adapter.notifyItemInserted(list.size - 1)
                    binding.recyclerView.recycledViewPool.clear()
                    binding.recyclerView.smoothScrollToPosition(list.size -1)
                }
                binding.userMessage.text!!.clear()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ChatActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}