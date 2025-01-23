package com.nadia.ucpakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
                oClickAktivitasPertanian = {},
                oClickCatatanPanen = {},
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


    }
}
