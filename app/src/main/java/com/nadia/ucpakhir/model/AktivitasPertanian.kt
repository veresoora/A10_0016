package com.nadia.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AllAktivitasResponse(
    val status: Boolean,
    val message: String,
    val data: List<Aktivitas>
)

@Serializable
data class AktivitasDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Aktivitas
)

@Serializable
data class Aktivitas(
    @SerialName("id_aktivitas")
    val idAktivitas: Int,
    @SerialName("id_tanaman")
    val idTanaman: Int,
    @SerialName("id_pekerja")
    val idPekerja: Int,
    @SerialName("tanggal_aktivitas")
    val tanggalAktivitas: String,
    @SerialName("deskripsi_aktivitas")
    val deskripsiAktivitas: String,
)