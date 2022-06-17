package com.marcelodonato.desafiomblabs.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.ActivityHomeBinding
import com.marcelodonato.desafiomblabs.login.LoginActivity
import com.marcelodonato.desafiomblabs.registerEvent.RegisterEventActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var user: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding()
        getOnDatabase()
        toolbar()

    }
    private fun toolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout -> logout()
            R.id.create_event -> goToRegisterEventActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onRestart() {
        super.onRestart()
        getOnDatabase()
    }

    private fun logout() {
        user = FirebaseAuth.getInstance()
            user.signOut()
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()

    }

    private fun startBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.root
    }

    private fun setupRecycler(list: MutableList<MblabsEvents>) {
        binding.progressHome.visibility = View.VISIBLE
        binding.rvHome.adapter = HomeAdapter(list.reversed().toMutableList()) { item, _ ->
            val dialog = CustomDialogBuyTicket(item)
            dialog.show(supportFragmentManager, dialog.tag)
        }
        binding.progressHome.visibility = View.GONE
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
                        setupRecycler(list)
                    }
                }
            }
        })
    }

    private fun goToRegisterEventActivity() {
        val loginIntent = Intent(this, RegisterEventActivity::class.java)
        startActivity(loginIntent)
    }
}