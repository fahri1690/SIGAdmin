package com.example.sigadmin.models

data class DataPlace(
    val name: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val facility: String = "",
    val noTelp: String = "",
    val lat: String = "",
    val long: String = ""
)

class DataFieldByName(
    val name: String = ""
)