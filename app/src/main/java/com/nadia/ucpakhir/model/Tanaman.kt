package com.nadia.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AllTanamanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Tanaman>
)

@Serializable
data class TanamanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Tanaman
)

@Serializable
data class Tanaman(
    @SerialName("id_tanaman")
    val idTanaman: Int,
    @SerialName("nama_tanaman")
    val namaTanaman: String,
    @SerialName("periode_tanam")
    val periodeTanam: String,
    @SerialName("deskripsi_tanaman")
    val deskripsiTanaman: String,
)
