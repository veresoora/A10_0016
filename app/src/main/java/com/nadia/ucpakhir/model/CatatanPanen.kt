package com.nadia.ucpakhir.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AllPanenResponse(
    val status: Boolean,
    val message: String,
    val data: List<CatatanPanen>
)

@Serializable
data class PanenDetailResponse(
    val status: Boolean,
    val message: String,
    val data: CatatanPanen
)

@Serializable
data class CatatanPanen(
    @SerialName("id_panen")
    val idPanen: Int,
    @SerialName("id_tanaman")
    val idTanaman: Int,
    @SerialName("tanggal_panen")
    val tanggal_panen: String,
    @SerialName("jumlah_panen")
    val jumlah_panen: String,
    @SerialName("keterangan")
    val keterangan: String,
)