package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityAddContactBinding
import com.example.contactsapp.ext.focusAndShowKeyboard

class AddContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNameAdd.focusAndShowKeyboard()

        binding.btnSaveAdd.setOnClickListener {
            with(binding) {
                viewModel.addContact(
                    name = etNameAdd.text.toString(),
                    surname = etSurnameAdd.text.toString(),
                    number = etNumberAdd.text.toString()
                )
                startActivity(Intent(this@AddContactActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}