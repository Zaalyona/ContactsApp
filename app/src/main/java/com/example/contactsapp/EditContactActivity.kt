package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityEditContactBinding
import com.example.contactsapp.ext.focusAndShowKeyboard

class EditContactActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityEditContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding = ActivityEditContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val contactId = intent.extras!!.getString("contactId")
        val contactName = intent.extras!!.getString("contactName")
        val contactSurname = intent.extras!!.getString("contactSurname")
        val contactNumber = intent.extras!!.getString("contactNumber")

        binding.etNameEdit.setText(contactName)
        binding.etSurnameEdit.setText(contactSurname)
        binding.etNumberEdit.setText(contactNumber)

        binding.etNameEdit.focusAndShowKeyboard()

        binding.btnSaveEdit.setOnClickListener {
            with(binding) {
                if (binding.etNameEdit.text == null ||
                    binding.etSurnameEdit.text == null ||
                    binding.etNumberEdit.text == null) {

                    Toast.makeText(applicationContext,
                        "Для редактирования контакта заполните все поля",
                        Toast.LENGTH_SHORT)

                } else if (contactId != null) {

                    viewModel.editContact(
                        contactId,
                        binding.etNameEdit.text.toString(),
                        binding.etSurnameEdit.text.toString(),
                        binding.etNumberEdit.text.toString()
                    )

                }
                startActivity(Intent(this@EditContactActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}