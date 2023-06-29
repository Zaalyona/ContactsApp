package com.example.contactsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.ext.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), LifecycleObserver {

    private lateinit var binding: ActivityMainBinding

    private val defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
            Log.d("Main", "DefaultLifecycleObserver - onCreate")
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            Log.d("Main", "DefaultLifecycleObserver - onStart")
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            Log.d("Main", "DefaultLifecycleObserver - onResume")
        }
    }

    private val viewModel : MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(defaultLifecycleObserver)

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

        binding.fabDeleteContact.setOnClickListener {
            if (adapter.contactNameSelected != "") {
                viewModel.deleteContact(adapter.contactIdSelected)
                finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Для удаления выберите нужный контакт",
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

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(defaultLifecycleObserver)
    }
}