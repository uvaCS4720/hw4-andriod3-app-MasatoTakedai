package edu.nd.pmcburne.hello.data

class LocationRepository(
    private val dao: LocationDao,
    private val api: LocationApi
) {
    suspend fun syncLocations() {
        val apiData = api.getLocations()

        val entities = apiData.map {
            LocationEntity(
                id = it.id,
                name = it.name,
                description = it.description,
                latitude = it.visual_center.latitude,
                longitude = it.visual_center.longitude,
                tags = it.tag_list.joinToString(",")
            )
        }

        dao.insertAll(entities) // IGNORE prevents duplicates
    }

    suspend fun getAllLocations(): List<LocationEntity> {
        return dao.getAll()
    }
}
