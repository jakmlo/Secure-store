package com.example.projekt_bam

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity(), CardInfoClickInterface, CardInfoClickDeleteInterface {
    private val TAG = "MAIN_ACTIVITY"
    lateinit var viewModel: CardInfoViewModel
    lateinit var cardInfoRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardInfoRV = findViewById(R.id.cardInfoRV)
        addFAB = findViewById(R.id.idFAB)
        cardInfoRV.layoutManager = LinearLayoutManager(this)


        val cardInfoRVAdapter = CardInfoRVAdapter(this, this, this)

        cardInfoRV.adapter = cardInfoRVAdapter

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CardInfoViewModel::class.java)

        viewModel.allCardInfo.observe(this, Observer { list ->
            list?.let {
                cardInfoRVAdapter.updateList(it)
            }
        })
        addFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, AddEditUserActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        val sharedPreferences = getSharedPreferences("RememberMePrefs", Context.MODE_PRIVATE)

        val btn_logout: Button = findViewById(R.id.btn_logout)

        btn_logout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val editor = sharedPreferences.edit()
            editor.putBoolean("rememberMe", false)
            editor.apply()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
    override fun onCardInfoClick(cardInfo: CardInfo) {
        val intent = Intent(this@MainActivity, AddEditUserActivity::class.java)
        intent.putExtra("mutationType", "Edit")
        intent.putExtra("bankName", cardInfo.bankName)
        intent.putExtra("cardNumber", cardInfo.cardNumber)
        intent.putExtra("noteId", cardInfo.id)
//        Log.d("MAIN_ACTIVITY", cardInfo.bankName)
//        cardInfo.cardNumber?.let { Log.d("MAIN_ACTIVITY", it) }
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(cardInfo: CardInfo) {
        viewModel.deleteCardInfo(cardInfo)
        Toast.makeText(this, "${cardInfo.bankName} Deleted", Toast.LENGTH_LONG).show()
    }


}