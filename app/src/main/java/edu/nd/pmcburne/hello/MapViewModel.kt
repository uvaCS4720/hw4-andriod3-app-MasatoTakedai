package edu.nd.pmcburne.hello

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.nd.pmcburne.hello.data.LocationEntity
import edu.nd.pmcburne.hello.data.LocationRepository
import kotlinx.coroutines.launch

class MapViewModel(private val repo: LocationRepository) : ViewModel() {

    var selectedTag by mutableStateOf("core")
        private set

    var locations by mutableStateOf(listOf<LocationEntity>())
        private set

    var tags by mutableStateOf(listOf<String>())
        private set

    init {
        viewModelScope.launch {
            repo.syncLocations()
            val data = repo.getAllLocations()
            locations = data

            tags = data
                .flatMap { it.tags.split(",") }
                .distinct()
                .sorted()
        }
    }

    fun setTag(tag: String) {
        selectedTag = tag
    }

    fun filteredLocations(): List<LocationEntity> {
        return locations.filter {
            it.tags.split(",").contains(selectedTag)
        }
    }
}