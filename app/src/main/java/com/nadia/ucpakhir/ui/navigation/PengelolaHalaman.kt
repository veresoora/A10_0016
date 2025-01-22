package com.nadia.ucpakhir.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiEntryTanaman
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiHomeTanaman
import com.nadia.ucpakhir.ui.tanaman.view.EntryTnmnScreen
import com.nadia.ucpakhir.ui.tanaman.view.HomeTanamanScreen

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
                onDetailClick = { //nim ->
//                    navController.navigate("${DestinasiDetail.route}/$nim")
//                    println("PengelolaHalaman: nim = $nim")
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
    }
}
