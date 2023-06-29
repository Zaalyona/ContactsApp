package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityAddContactBinding
import com.example.contactsapp.ext.focusAndShowKeyboard
import com.example.contactsapp.presenter.MainAction
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddContactActivity : AppCompatActivity(), MainAction {

    private val viewModel : MainViewModel by viewModel()

    //private val presenter: Presenter by inject()

    private lateinit var binding: ActivityAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //presenter.initAction(this)

        binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etNameAdd.focusAndShowKeyboard()

        //Вариант с MVVM
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

        //Вариант с презентером MVP
        /*binding.btnSaveAdd.setOnClickListener {
            with(binding) {
                presenter.addContact(
                    name = etNameAdd.text.toString(),
                    surname = etSurnameAdd.text.toString(),
                    number = etNumberAdd.text.toString()
                )
            }
        }*/
    }

    override fun onAddContact(contacts: List<Contact>) {
        Toast.makeText(this, contacts.last().name, Toast.LENGTH_SHORT).show()
    }
}