package com.marcelodonato.desafiomblabs.presentation.profile.view

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcelodonato.desafiomblabs.common.extension.viewBinding
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.ActivityProfileBinding
import com.marcelodonato.desafiomblabs.presentation.base.BaseActivity
import com.marcelodonato.desafiomblabs.presentation.profile.presenter.ProfileViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : BaseActivity<ProfileViewModel>() {

    override val binding by viewBinding(ActivityProfileBinding::inflate)

    private var data: Bundle? = null
    private var events: MblabsEvents? = null
    private var list: MutableList<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupView()
        getOnDatabase()
    }

    private fun setupView() {
        binding.tvProfileName.text = "Marcelo"
        btn_back_home.setOnClickListener {
            finish()
        }
        if (intent.extras != null) {
            data = intent.extras
        }

        if (data != null) {
            events = data?.getSerializable("event") as MblabsEvents
            list = mutableListOf(events)
            var listEvents = list as MutableList<MblabsEvents?>
            setupRecycler(listEvents)
        }
    }

    private fun setupRecycler(listEvent: MutableList<MblabsEvents?>) {
        binding.progressHome.visibility = View.VISIBLE
        binding.rvProfile.adapter =
            ProfileAdapter(listEvent.reversed().toMutableList()) { item, _ ->
                removeOnDatabase(item.uid.toString())
            }
        binding.progressHome.visibility = View.GONE
    }

    override fun onRestart() {
        super.onRestart()
        getOnDatabase()
    }

    private fun getOnDatabase() {
        val ref = FirebaseDatabase.getInstance().getReference("/profile")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(snapshot: DataSnapshot) {
                val todoList: MutableList<MblabsEvents?> = mutableListOf()
                snapshot.children.mapNotNullTo(todoList) { dataSnapshot ->
                    dataSnapshot.getValue(MblabsEvents::class.java)
                }.let { list ->
                    if (list.size != 0) {
                        setupRecycler(list)
                    }else{
                        binding.rvProfile.visibility= GONE
                        binding.progressHome.visibility = GONE
                        binding.imgRv.visibility= VISIBLE
                    }
                }
            }
        })
    }

    private fun removeOnDatabase(id: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/profile/$id")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataSnapshot in dataSnapshot.children) {
                    dataSnapshot.ref.removeValue()
                    getOnDatabase()
                }
            }
        })
    }
}