package com.example.sigadmin.models

class Admin {

    var id: String? = null
    var namaPengguna: String? = null
    var noTelp: String? = null
    var email: String? = null

    constructor() {}

    constructor(id: String, namaPengguna: String, noTelp: String, email: String) {
        this.id = id
        this.namaPengguna = namaPengguna
        this.noTelp = noTelp
        this.email = email
    }

    constructor(namaPengguna: String, noTelp: String, email: String) {
        this.namaPengguna = namaPengguna
        this.noTelp = noTelp
        this.email = email
    }

    fun toMap(): Map<String, Any> {

        val result = HashMap<String, Any>()
        result.put("namaPengguna", namaPengguna!!)
        result.put("noTelp", noTelp!!)
        result.put("email", email!!)

        return result
    }
}