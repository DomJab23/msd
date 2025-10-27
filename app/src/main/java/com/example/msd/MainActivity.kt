package com.example.msd

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.msd.ui.theme.MsdTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MsdTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onLoginClicked = { navController.navigate("main") })
        }
        composable("main") {
            MainScreen(mainNavController = navController)
        }
        composable("calendar") {
            CalendarScreen(
                onBackPressed = { navController.popBackStack() },
                onSubmit = { date, pillName ->
                    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(date))
                    val formattedTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                    PillRepository.addPill(Pill(0, pillName, formattedDate, formattedTime))
                    Toast.makeText(context, "Pill '$pillName' saved!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            )
        }
        composable(
            "edit_pill/{pillId}",
            arguments = listOf(navArgument("pillId") { type = NavType.LongType })
        ) {
            val pillId = it.arguments?.getLong("pillId")
            val pill = PillRepository.getPillById(pillId!!)
            if (pill != null) {
                EditPillScreen(
                    pill = pill,
                    onPillUpdated = { updatedPill ->
                        PillRepository.updatePill(updatedPill)
                        Toast.makeText(context, "Pill '${updatedPill.name}' updated!", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    onBackPressed = { navController.popBackStack() }
                )
            }
        }
    }
}
