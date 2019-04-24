package com.example.sigadmin.models

import com.example.sigadmin.R

data class FieldModelGetAll(
    val id: Int,
    val fieldName: String,
    val facility: String,
    val openHour: String,
    val closeHour: String,
    val phoneNumber: String,
    val address: String,
    val lat: String,
    val lon: String,
    val image: Int
)

data class FieldModelGetSubField(
    val id: Int,
    val fieldType: String,
    val dayPrice: String,
    val nightPrice: String,
    val image: Int
)

data class FieldGetByName(
    val id: Int,
    val name: String
)

object DataField {

    var fields = listOf(
        FieldModelGetAll(
            0,
            "Tifosi Futsal",
            "Mushola, Kamar Mandi, MiniMarket",
            "08:00",
            "23:00",
            "081234567890",
            "Jl. Pahlawan",
            "",
            "",
            R.drawable.tifosi
        )
    );

    var subField = listOf(
        FieldModelGetSubField(
            0,
            "Vinyl",
            "80000",
            "120000",
            R.drawable.tifosi
        )
    )

    var getFieldByName = listOf(
        FieldGetByName(
            0,
            "Lapangan"
        )
    )
}

