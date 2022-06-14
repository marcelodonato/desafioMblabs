package com.marcelodonato.desafiomblabs.registerEvent

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.DataBindingUtil
import com.google.firebase.database.FirebaseDatabase
import com.marcelodonato.desafiomblabs.R
import com.marcelodonato.desafiomblabs.common.extension.getEditText
import com.marcelodonato.desafiomblabs.common.extension.toast
import com.marcelodonato.desafiomblabs.common.extension.validate
import com.marcelodonato.desafiomblabs.common.model.MblabsEvents
import com.marcelodonato.desafiomblabs.databinding.ActivityRegisterEventBinding
import java.util.*
import com.google.firebase.storage.FirebaseStorage

class RegisterEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterEventBinding
    private var imgEvent: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        starBinding()
        getImgGallery()
        registerEvent()
        goBackToHome()
    }

    private fun starBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_event)
        binding.root
    }

    private fun registerEvent() {
        binding.btnRegisterEvent.setOnClickListener {

            validateDataBaseFields()
        }
    }

    private fun validateDataBaseFields() {
        val name = binding.etRegisterEventName.validate()
        val desc = binding.etRegisterEventDesc.validate()
        val price = binding.etRegisterEventPrice.validate()
        val img = imgEvent == null

        when {
            img -> toast(getString(R.string.register_img_message_error))
            name -> binding.etRegisterEventName.error =
                getString(R.string.generic_error_edit_text, getString(R.string.register_event_name))
            desc -> binding.etRegisterEventDesc.error = getString(
                R.string.generic_error_edit_text,
                getString(R.string.register_description_event)
            )
            price -> binding.etRegisterEventPrice.error = getString(
                R.string.generic_error_edit_text,
                getString(R.string.register_price_event)
            )
            else -> {
                addImgEventToStore()
                binding.incLoader.frameLoadingBackground.visibility = VISIBLE
            }
        }
    }

    private fun addEventToDatabase(name: String, desc: String, price: Double, uri: String) {
        val id = UUID.randomUUID().toString()
        val ref = FirebaseDatabase.getInstance().getReference("/event/$id")
        val events = MblabsEvents(
            uid = id, name = name, desc = desc, price = price, uri = uri
        )
        ref.setValue(events)
            .addOnSuccessListener {
                hideLoading()
                finish()
            }
            .addOnFailureListener {
                hideLoading()
                toast(it.message.toString())
            }
    }

    private fun addImgEventToStore() {
        val id = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/event/$id")
        imgEvent?.let { img ->
            ref.putFile(img)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { responseImg ->
                        addEventToDatabase(
                            binding.etRegisterEventName.getEditText(),
                            binding.etRegisterEventDesc.getEditText(),
                            binding.etRegisterEventPrice.getEditText().toDouble(),
                            responseImg.toString()
                        )
                    }
                }
                .addOnFailureListener {
                    hideLoading()
                    toast(it.message.toString())
                }
        }
    }

    private fun getImgGallery() {
        binding.tvRegisterImg.setOnClickListener {
            Intent(Intent.ACTION_PICK).let {
                it.type = "image/*"
                startActivityForResult(it, 0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            try {
                imgEvent = data.data
                binding.ivRegisterEvent.setImageURI(imgEvent)
            } catch (e: java.lang.Exception) {
                toast(e.message.toString())
            }
        }
    }

    private fun hideLoading() {
        binding.incLoader.frameLoadingBackground.visibility = GONE
    }

    private fun goBackToHome() {
        binding.btnBackHome.setOnClickListener {
            finish()
        }
    }
}