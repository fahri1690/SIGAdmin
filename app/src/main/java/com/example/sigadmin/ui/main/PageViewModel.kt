package com.example.sigadmin.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val intent = Intent()

    val name = intent.getStringExtra("name")

    val text: LiveData<String> = Transformations.map(_index) {
        name
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}