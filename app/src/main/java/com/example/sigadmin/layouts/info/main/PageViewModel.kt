package com.example.sigadmin.layouts.info.main

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

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