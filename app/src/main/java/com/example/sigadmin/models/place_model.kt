package com.example.sigadmin.models

data class PlaceModel(
    val placeId: String = "",
    val namaTempat: String = "",
    val alamat: String ="",
    val jamBuka: String = "",
    val jamTutup: String = "",
    val fasilitas: String = "",
    val noTelp: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val jenisLapangan: String = "",
    val hargaTerendah: Int = 0,
    val hargaTertinggi: Int = 0,
    val gambar: ArrayList<String> = arrayListOf(PlaceImages().gambar)
){

    companion object {

        const val PLACE_NAME = "namaTempat"
    }

    fun getName(): String {
        return  this.namaTempat
    }

}

data class PlaceImages(
    val gambar: String = ""
)
