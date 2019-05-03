package com.example.sigadmin.models

data class Admin(
    val namaPengguna: String,
    val noTelepon: String,
    val kataSandi: String) {
    constructor() : this("", "", "") {

    }
}