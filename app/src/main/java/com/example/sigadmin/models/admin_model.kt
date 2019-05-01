package com.example.sigadmin.models

import android.provider.ContactsContract

data class Admin(val namaPengguna: String, val noTelepon: String, val kataSandi: String) {
    constructor() : this("", "", "") {

    }
}