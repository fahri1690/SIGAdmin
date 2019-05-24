package com.example.sigadmin.models

data class DataField(
    val namaLapangan: String,
    val alamat: String,
    val jamBuka: String,
    val jamTutup: String,
    val fasilitas: String,
    val noTelepon: String,
    val latitude: String,
    val longitude: String
)

data class DataFieldByName(
        val namaLapangan: String
)
