package com.marcelodonato.desafiomblabs.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        getOnDatabase()
        setupRecycler()
    }

    override fun onRestart() {
        super.onRestart()

    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.root
    }

    private fun setupRecycler() {
        binding.homeRv.adapter = HomeAdapter(mock())
    }

    private fun mock(): MutableList<MblabsEvents> = mutableListOf(
        MblabsEvents(
            uid = "",
            name = "evento 1",
            desc = "qualquer coisa",
            price = 25.50,
            uri = "https://tradaq.com.br/wp-content/uploads/2018/08/MEDIDAS-IMAGENS-converted.jpg",
        ),
        MblabsEvents(
            uid = "",
            name = "evento 2",
            desc = "qualquer coisa",
            price = 25.50,
            uri = "https://tradaq.com.br/wp-content/uploads/2018/08/MEDIDAS-IMAGENS-converted.jpg",
        ),
        MblabsEvents(
            uid = "",
            name = "evento 3",
            desc = "qualquer coisa",
            price = 25.50,
            uri = "https://tradaq.com.br/wp-content/uploads/2018/08/MEDIDAS-IMAGENS-converted.jpg",
        ),
        MblabsEvents(
            uid = "",
            name = "evento 4",
            desc = "qualquer coisa",
            price = 25.50,
            uri = "https://tradaq.com.br/wp-content/uploads/2018/08/MEDIDAS-IMAGENS-converted.jpg",
        ),
    )

    private fun getOnDatabase() {
        val ref = FirebaseDatabase.getInstance().getReference("/event")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
//                val todoList: MutableList<MblabsEvents> = mutableListOf()
//                snapshot.children.mapNotNullTo(todoList) { dataSnapshot ->
//                    dataSnapshot.getValue(MblabsEvents::class.java)
//                }.let { list ->
//                    if (list.size != 0) {
//                       toast(list.size.toString())
//                        setupRecycler(list)
//                    }
//                }
            }
        })
    }
}