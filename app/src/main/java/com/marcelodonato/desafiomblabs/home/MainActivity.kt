package com.marcelodonato.desafiomblabs.home

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.getEditText
import com.marcelodonato.desafiomblabs.common.extension.validate
import com.marcelodonato.desafiomblabs.common.model.Event
import com.marcelodonato.desafiomblabs.databinding.ActivityMainBinding
import java.net.URI

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var listToCountAndGetNameId: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        getOnDataBase()

    }

    private fun startBinding(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.root
    }

    private fun setupRecycler(list: MutableList<Event>){
        binding.homeRv.layoutManager = GridLayoutManager(this, 2)
        binding.homeRv.adapter = MainAdapter(list)
    }



    private fun getOnDataBase(){

        val ref = FirebaseDatabase.getInstance().getReference("/events")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList : MutableList<Event> = mutableListOf()
                snapshot.children.mapNotNullTo(todoList){ dataSnapshot ->
                    dataSnapshot.getValue(Event::class.java)
                }.let { list ->
                    if(list.size != 0){
                        listToCountAndGetNameId = list
                        setupRecycler(list)
                    }  
                }
            }

            override fun onCancelled(error: DatabaseError) {
                errorCase()
            }
        })

    }


    private fun errorCase() {
        Toast.makeText(this, R.string.unexpected_error,Toast.LENGTH_SHORT).show()

    }

}