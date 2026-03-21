package edu.nd.pmcburne.hello

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import edu.nd.pmcburne.hello.data.AppDatabase
import edu.nd.pmcburne.hello.data.LocationRepository
import edu.nd.pmcburne.hello.data.RetrofitInstance
import edu.nd.pmcburne.hello.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme() {
                MapApp()
            }
        }
    }

    @Composable
    fun MapApp() {
        val context = LocalContext.current.applicationContext

        val viewModel: MapViewModel by viewModels {
            MapViewModelFactory(context)
        }

        MapScreen(viewModel = viewModel)
    }
}

class MapViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            val database = Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "locations_db"
            ).build()

            val repository = LocationRepository(
                api = RetrofitInstance.api,
                dao = database.locationDao()
            )

            @Suppress("UNCHECKED_CAST")
            return MapViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}