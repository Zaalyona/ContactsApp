package com.example.contactsapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.ext.focusAndShowKeyboard
import com.example.contactsapp.ext.hideKeyboard

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ContactsAdapter()

        viewModel.allContacts.observe(this) {
            adapter.setData(it)
        }

        binding.rvContacts.adapter = adapter

        //Насильно перемещает фокус в EditText и вызывает клавиатуру
        //binding.etSoftInput.requestFocus()

        binding.fabAddContact.setOnClickListener {
            hideKeyboard()
            startActivity(Intent(this, AddContactActivity::class.java))
        }

        binding.fabEditContact.setOnClickListener {

            if (adapter.contactNameSelected != "" && adapter.contactSurnameSelected != "") {

                //нужно ли использовать интент, если я могу получить эти переменные из адаптера?
                val intent = Intent(this@MainActivity, EditContactActivity::class.java)

                intent.putExtra("contactId", adapter.contactIdSelected)
                intent.putExtra("contactName", adapter.contactNameSelected)
                intent.putExtra("contactSurname", adapter.contactSurnameSelected)
                intent.putExtra("contactNumber", adapter.contactNumberSelected)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Для редактирования выберите нужный контакт",
                    Toast.LENGTH_SHORT)
            }
        }

        //Работа с полем ввода типа "Пароль", открытие и закрытие видимости текста,
        // смена иконки с глазом
        /*binding.ivEye.setOnClickListener {
            with(binding) {
                if (etSoftInput.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                    etSoftInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    ivEye.setImageResource(R.drawable.baseline_remove_red_eye_24)
                } else {
                    etSoftInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    ivEye.setImageResource(R.drawable.baseline_visibility_off_24)
                }
                etSoftInput.setSelection(binding.etSoftInput.length())
            }
        }*/
    }
}