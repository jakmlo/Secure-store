package com.example.projekt_bam

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


class AddEditUserActivity : AppCompatActivity() {

    lateinit var bankNameEdit: EditText
    lateinit var cardNumberEdit: EditText
    lateinit var saveBtn: Button

    lateinit var viewModal: CardInfoViewModel
    var noteID = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_user)

        val alias = "secret_key"
        val cryptoUtils = CryptoUtils()

        cryptoUtils.getKey()

        viewModal = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(CardInfoViewModel::class.java)

        bankNameEdit = findViewById(R.id.idEdtBankName)
        cardNumberEdit = findViewById(R.id.idEdtCardNumber)
        saveBtn = findViewById(R.id.idBtn)


        val mutationType = intent.getStringExtra("mutationType")
        if (mutationType.equals("Edit")) {

            val bankName = intent.getStringExtra("bankName")
//            Log.d("ADD_USER_CARD_NUMBER", "bankName: $bankName")
            val cardNumber = intent.getStringExtra("cardNumber")
//            Log.d("ADD_USER_CARD_NUMBER", "encrypted cardNumber: $cardNumber")
            noteID = intent.getIntExtra("noteId", -1)
            saveBtn.setText("Update Card Number")
            bankNameEdit.setText(bankName)
            cardNumber?.let {
                val clearText = cryptoUtils.decrypt(alias, it)
//                Log.d("ADD_USER_CARD_NUMBER", "decrypted cardNumber: $clearText")
                cardNumberEdit.setText(clearText)
            }
        } else {
            saveBtn.setText("Save Card Number")
        }


        saveBtn.setOnClickListener {

            val bankName = bankNameEdit.text.toString()
//            Log.d("ADD_USER_ACTIVITY", "bankName: $bankName")
            val cardNumber = cardNumberEdit.text.toString()
//            Log.d("ADD_USER_ACTIVITY", "cardNumber: $cardNumber")

            val encryptedCardName = cryptoUtils.encrypt(alias, plainText = cardNumber)

//            Log.d("ADD_USER_ACTIVITY", "encrypted cardNumber: $encryptedCardName")

            if (mutationType.equals("Edit")) {
                if (bankName.isNotEmpty() && cardNumber.isNotEmpty()) {

                    val updatedData = CardInfo(Firebase.auth.currentUser?.email!!
                        , bankName
                        , encryptedCardName
                    )
//                    Log.d("ADD_USER_ACTIVITY", "User data: $updatedData")
                    updatedData.id = noteID
                    viewModal.updateCardInfo(updatedData)
                    Toast.makeText(this, "Card Number Updated..", Toast.LENGTH_LONG).show()
                }
            } else {
                if (bankName.isNotEmpty() && cardNumber.isNotEmpty()) {


                    viewModal.addCardInfo(CardInfo(Firebase.auth.currentUser?.email!!
                        , bankName
                        , encryptedCardName))
                    Toast.makeText(this, "$bankName Added", Toast.LENGTH_LONG).show()
                }
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()
        }
    }
}