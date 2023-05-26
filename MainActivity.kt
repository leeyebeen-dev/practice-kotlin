package com.example.bustame

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bustame.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    val db = FirebaseFirestore.getInstance()
    val itemList = arrayListOf<ListLayout>()
    val adapter = ListAdapter(itemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rvList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvList.adapter = adapter

        binding.btnOutput.setOnClickListener {
            db.collection("Users")   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item = ListLayout(
                            document["busNumber"] as String,
                            document["busStopId"] as String,
                            document["customerType"] as String,
                            document["id"] as String
                        )
                        itemList.add(item)
                    }
                    adapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신
                }
                .addOnFailureListener { exception ->
                    // 실패할 경우
                    Log.w("MainActivity", "Error getting documents: $exception")
                }
        }

        //val busNumber: String, val busStopId: String, customerType: String, id: String
        binding.btnInput.setOnClickListener {
            // 대화상자 생성
            val builder = AlertDialog.Builder(this)

            // 대화상자에 텍스트 입력 필드 추가, 대충 만들었음
            val tv_busNumber = TextView(this)
            tv_busNumber.text = "버스 번호"
            val tv_busStopId = TextView(this)
            tv_busStopId.text = "버스 정류장 이름"
            val tv_customerType = TextView(this)
            tv_customerType.text = "손님 유형"
            val tv_id = TextView(this)
            tv_id.text = "사용자 아이디"

            val et_busNumber = EditText(this)
            et_busNumber.isSingleLine = true
            val et_busStopId = EditText(this)
            et_busStopId.isSingleLine = true
            val et_customerType = EditText(this)
            et_customerType.isSingleLine = true
            val et_id = EditText(this)
            et_id.isSingleLine = true


            val mLayout = LinearLayout(this)
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tv_busNumber)
            mLayout.addView(et_busNumber)
            mLayout.addView(tv_busStopId)
            mLayout.addView(et_busStopId)
            mLayout.addView(tv_customerType)
            mLayout.addView(et_customerType)
            mLayout.addView(tv_id)
            mLayout.addView(et_id)
            builder.setView(mLayout)

            //val busNumber: String, val busStopId: String, customerType: String, id: String
            builder.setTitle("데이터 입력")
            builder.setPositiveButton("입력") { dialog, which ->
                // EditText에서 문자열을 가져와 hashMap으로 만듦
                val data = hashMapOf(
                    "busNumber" to et_busNumber.text.toString(),
                    "busStopId" to et_busStopId.text.toString(),
                    "customerType" to et_customerType.text.toString(),
                    "id" to et_id.text.toString()
                )
                // Contacts 컬렉션에 data를 자동 이름으로 저장
                db.collection("Users")
                    .add(data)
                    .addOnSuccessListener {
                        // 성공할 경우
                        Toast.makeText(this, "데이터가 입력되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        // 실패할 경우
                        Log.w("MainActivity", "Error getting documents: $exception")
                    }
            }
            builder.setNegativeButton("취소") { dialog, which ->

            }
            builder.show()
        }
    }
}