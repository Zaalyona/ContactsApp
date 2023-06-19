package com.example.contactsapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.databinding.ActivityMainBinding
import com.example.contactsapp.databinding.ItemContactBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ContactsAdapter() :
    ListAdapter<Contact, ContactsAdapter.MyViewHolder>(MyDiffUtil) {

    public var contactNameSelected: String = ""
    public var contactSurnameSelected: String = ""
    public var contactNumberSelected: String = ""
    public var contactIdSelected: String = ""

    object MyDiffUtil : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact?) {
            binding.tvNameAndSurname.text = "${contact?.name} ${contact?.surname}"
            binding.tvNumber.text = contact?.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemContactBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val note = getItem(position)
        holder.bind(note)

        holder.itemView.setOnClickListener {
            //holder.itemView.setBackgroundColor(Color.GRAY)

            contactIdSelected = getItem(position).id
            contactNameSelected = getItem(position).name.toString()
            contactSurnameSelected = getItem(position).surname.toString()
            contactNumberSelected = getItem(position).number.toString()
        }
    }

    fun setData(allContacts: List<Contact>) {
        this.submitList(allContacts)
        notifyDataSetChanged()
    }
}