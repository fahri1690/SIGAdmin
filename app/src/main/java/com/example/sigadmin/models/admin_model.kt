package com.example.sigadmin.models

import android.provider.ContactsContract

data class Admin(val name: String, val phone: String, val password: String) {
    constructor() : this("", "", "") {

    }
}