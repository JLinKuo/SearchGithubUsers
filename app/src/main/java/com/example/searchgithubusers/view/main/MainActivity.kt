package com.example.searchgithubusers.view.main

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.searchgithubusers.R
import com.example.searchgithubusers.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var oneButtonNoTitleDialog: Dialog? = null

    private val progressDialog by lazy {
        AlertDialog.Builder(this).setView(R.layout.view_progress_dialog).create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        Toast.makeText(this, "${binding.mainHello.text}", Toast.LENGTH_SHORT).show();
    }

    override fun onStop() {
        dismissAllDialog()
        super.onStop()
    }

    private fun dismissAllDialog() {
        dismissOneButtonNoTitleDialog()
    }

    private fun dismissOneButtonNoTitleDialog() {
        oneButtonNoTitleDialog?.let {
            if(it.isShowing) {
                it.dismiss()
            }
        }
    }

    fun showOneButtonNoTitleDialog(message: String, listener: DialogInterface.OnClickListener?) {
        oneButtonNoTitleDialog = AlertDialog.Builder(this)
            .setCancelable(false).setMessage(message)
            .setPositiveButton(getString(R.string.dialog_confirm), listener).show()
    }

    fun showProgressBar(isCancelable: Boolean) {
        progressDialog.setCancelable(isCancelable)
        progressDialog.show()
    }

    fun dismissProgressBar() {
        if(progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }
}