package com.marcelodonato.desafiomblabs.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.toast
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        getOnDatabase()



        binding.iconShopList.setOnClickListener {

        }

    }

    override fun onRestart() {
        super.onRestart()

    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.root
    }

    private fun setupRecycler(list: MutableList<MblabsEvents>) {
        binding.rvHome.adapter = HomeAdapter(list) { item, _ ->
            val dialog = CustomDialogBuyTicket(item)
            dialog.show(supportFragmentManager, dialog.tag)
        }

    }

    private fun getOnDatabase() {
        val ref = FirebaseDatabase.getInstance().getReference("/event")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList: MutableList<MblabsEvents> = mutableListOf()
                snapshot.children.mapNotNullTo(todoList) { dataSnapshot ->
                    dataSnapshot.getValue(MblabsEvents::class.java)
                }.let { list ->
                    if (list.size != 0) {
                        toast(list.size.toString())
                        setupRecycler(list)
                    }
                }
            }
        })
    }
}