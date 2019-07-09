package com.example.sigadmin.models

data class SubField(
    val name: String ="",
    val hargaSiang: String ="",
    val hargaMalam: String ="",
    val jenis: String =""
)

class DataSubFieldByName(
    val name: String = ""
)