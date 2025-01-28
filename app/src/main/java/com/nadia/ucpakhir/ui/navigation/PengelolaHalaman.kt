package com.nadia.ucpakhir.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiAktivitasUpdate
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiDetailAktivitas
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiEntryAktivitas
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiHomeAktivitas
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DetailScreenAktivitas
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.EntryAktvtsScreen
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.HomeAktivitasScreen
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.UpdateScreenAktivitas
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiDetailPanen
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiEntryPanen
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiHomePanen
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiPanenUpdate
import com.nadia.ucpakhir.ui.catatanpanen.view.DetailScreenPanen
import com.nadia.ucpakhir.ui.catatanpanen.view.EntryPnnScreen
import com.nadia.ucpakhir.ui.catatanpanen.view.HomePanenScreen
import com.nadia.ucpakhir.ui.catatanpanen.view.UpdateScreenPanen
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiDetailPekerja
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiEntryPekerja
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiHomePekerja
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiPekerjaUpdate
import com.nadia.ucpakhir.ui.pekerja.view.DetailScreenPekerja
import com.nadia.ucpakhir.ui.pekerja.view.EntryPkrjScreen
import com.nadia.ucpakhir.ui.pekerja.view.HomePekerjaScreen
import com.nadia.ucpakhir.ui.pekerja.view.UpdateScreenPekerja
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiDetailTanaman
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiEntryTanaman
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiHomeTanaman
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiTanamanUpdate
import com.nadia.ucpakhir.ui.tanaman.view.DetailScreenTanaman
import com.nadia.ucpakhir.ui.tanaman.view.EntryTnmnScreen
import com.nadia.ucpakhir.ui.tanaman.view.HomeTanamanScreen
import com.nadia.ucpakhir.ui.tanaman.view.UpdateScreenTanaman

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PengelolaHalamanPertanian(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeTanaman.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomeTanaman.route){
            HomeTanamanScreen(
                navigateToltemEntry = {
                    navController.navigate(DestinasiEntryTanaman.route)
                },
                onDetailClick = { idtnmn ->
                    navController.navigate("${DestinasiDetailTanaman.route}/$idtnmn")
                    println("PengelolaHalaman: idTanaman = $idtnmn")
                },
                onEditClick = { idtnmn ->
                    navController.navigate("${DestinasiTanamanUpdate.route}/$idtnmn")
                    println("PengelolaHalaman: idTanaman = $idtnmn")
                },
                oClickAktivitasPertanian = {navController.navigate(DestinasiHomeAktivitas.route)},
                oClickCatatanPanen = {navController.navigate(DestinasiHomePanen.route)},
                onClickPekerja = {
                    navController.navigate(DestinasiHomePekerja.route)
                }
            )
        }

        composable(DestinasiEntryTanaman.route) {
            EntryTnmnScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            DestinasiDetailTanaman.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailTanaman.IDtnmn) {
                    type = NavType.IntType
                }
            )
        ) {
            val idtanaman = it.arguments?.getInt(DestinasiDetailTanaman.IDtnmn)

            idtanaman?.let { idtanaman ->
                DetailScreenTanaman(
                    navigateBack = {
                        navController.navigate(DestinasiHomeTanaman.route) {
                            popUpTo(DestinasiHomeTanaman.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEntryCatatanPanen = {
                        navController.navigate(DestinasiEntryPanen.route)
                    }
                )
            }
        }

        composable(
            DestinasiTanamanUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiTanamanUpdate.IDtnmn){
                    type = NavType.IntType
                }
            )
        ){
            val idtanaman = it.arguments?.getInt(DestinasiTanamanUpdate.IDtnmn)
            idtanaman?.let { idtanaman ->
                UpdateScreenTanaman(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(DestinasiHomePekerja.route){
            HomePekerjaScreen(
                navigateToltemEntry = {navController.navigate(DestinasiEntryPekerja.route) },
                onDetailClick = {idpkrj ->
                    navController.navigate("${DestinasiDetailPekerja.route}/$idpkrj")
                    println("PengelolaHalaman: idPekerja = $idpkrj")
                                },
                onEditClick = { idpkrj ->
                    navController.navigate("${DestinasiPekerjaUpdate.route}/$idpkrj")
                    println("PengelolaHalaman: idPekerja = $idpkrj")
                },
                oBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiEntryPekerja.route) {
            EntryPkrjScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            DestinasiDetailPekerja.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPekerja.IDpkrj) {
                    type = NavType.IntType
                }
            )
        ) {
            val idpekerja = it.arguments?.getInt(DestinasiDetailPekerja.IDpkrj)

            idpekerja?.let { idpekerja ->
                DetailScreenPekerja(
                    navigateBack = {
                        navController.navigate(DestinasiHomePekerja.route) {
                            popUpTo(DestinasiHomePekerja.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiPekerjaUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiPekerjaUpdate.IDpkrj){
                    type = NavType.IntType
                }
            )
        ){
            val idpekerja = it.arguments?.getInt(DestinasiPekerjaUpdate.IDpkrj)
            idpekerja?.let { idpekerja ->
                UpdateScreenPekerja(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(DestinasiHomeAktivitas.route){
            HomeAktivitasScreen(
                navigateToltemEntry = {
                    navController.navigate(DestinasiEntryAktivitas.route)
                                      },
                onEditClick = {idaktvts ->
                    navController.navigate("${DestinasiAktivitasUpdate.route}/$idaktvts")
                    println("PengelolaHalaman: idAktivitas = $idaktvts")
                },
                onDetailClick = { idaktvts ->
                    navController.navigate("${DestinasiDetailAktivitas.route}/$idaktvts")
                    println("PengelolaHalaman: idAktivitas = $idaktvts")
                },
                oBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiEntryAktivitas.route) {
            EntryAktvtsScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            DestinasiDetailAktivitas.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAktivitas.IDaktvts) {
                    type = NavType.IntType
                }
            )
        ) {
            val idAktivitas = it.arguments?.getInt(DestinasiDetailAktivitas.IDaktvts)

            idAktivitas?.let { idaktivitas ->
                DetailScreenAktivitas(
                    navigateBack = {
                        navController.navigate(DestinasiHomeAktivitas.route) {
                            popUpTo(DestinasiHomeAktivitas.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiAktivitasUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiAktivitasUpdate.IDaktvts){
                    type = NavType.IntType
                }
            )
        ){
            val idaktivitas = it.arguments?.getInt(DestinasiAktivitasUpdate.IDaktvts)
            idaktivitas?.let { idaktivitas ->
                UpdateScreenAktivitas(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(DestinasiHomePanen.route){
            HomePanenScreen(
                onEditClick = {idpnn ->
                    navController.navigate("${DestinasiPanenUpdate.route}/$idpnn")
                    println("PengelolaHalaman: idPanen = $idpnn")
                },
                onDetailClick = { idpnn ->
                    navController.navigate("${DestinasiDetailPanen.route}/$idpnn")
                    println("PengelolaHalaman: idPanen = $idpnn")
                },
                oBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(DestinasiEntryPanen.route) {
            EntryPnnScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable (
            DestinasiDetailPanen.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPanen.IDpnn) {
                    type = NavType.IntType
                }
            )
        ) {
            val idCatatanPanen = it.arguments?.getInt(DestinasiDetailPanen.IDpnn)

            idCatatanPanen?.let { idCatatanPanen ->
                DetailScreenPanen(
                    navigateBack = {
                        navController.navigate(DestinasiHomePanen.route) {
                            popUpTo(DestinasiHomePanen.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }

        composable(
            DestinasiPanenUpdate.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiPanenUpdate.IDpnn){
                    type = NavType.IntType
                }
            )
        ){
            val idpanen = it.arguments?.getInt(DestinasiPanenUpdate.IDpnn)
            idpanen?.let { idpanen ->
                UpdateScreenPanen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
