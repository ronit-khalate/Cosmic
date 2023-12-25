package com.ronit.cosmic.feature_search.data.repository


import com.ronit.cosmic.core.data.remote_source.api.NasaImageApi
import com.ronit.cosmic.feature_search.domain.model.ImageInfo
import com.ronit.cosmic.feature_search.domain.repository.SearchScreenRepository
import javax.inject.Inject

class SearchScreenRepositoryImpl @Inject constructor(
        private val nasaImageApi: NasaImageApi
) :SearchScreenRepository {
    override suspend fun searchImageInfoByKeyWords(keyword: String):List<ImageInfo> {

        val collection = nasaImageApi.getImageInfoByKeywords(keyword=keyword)
        val items = collection.collection.items
        return nasaImageApi.getImageInfoByKeywords(keyword=keyword).collection.items.map {item->

            item.data.map {data->

                ImageInfo(
                        title = data.title,
                        nasa_id = data.nasa_id,
                        item.links[0].href
                )
            }
        }.flatten()

    }
}