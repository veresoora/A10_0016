package com.nadia.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AllPekerjaResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pekerja>
)

@Serializable
data class PekerjaDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pekerja
)

@Serializable
data class Pekerja(
    @SerialName("id_pekerja")
    val idPekerja: Int,
    @SerialName("nama_pekerja")
    val namaPekerja: String,
    @SerialName("jabatan")
    val jabatan: String,
    @SerialName("kontak_pekerja")
    val kontakPekerja: String,
)