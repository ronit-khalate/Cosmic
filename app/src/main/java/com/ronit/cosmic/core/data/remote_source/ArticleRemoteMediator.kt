package com.ronit.cosmic.core.data.remote_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ronit.cosmic.core.data.local_source.model.ArticleDatabase
import com.ronit.cosmic.core.data.local_source.model.CachedArticleEntity
import com.ronit.cosmic.core.data.local_source.model.RemotePageKeysEntity
import com.ronit.cosmic.core.data.mappers.toArticleEntity
import com.ronit.cosmic.core.data.remote_source.api.ArticleApi
import com.ronit.cosmic.core.utility.Constants.ARTICLES_PER_PAGE
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(

        private val articleDb:ArticleDatabase,
        private val articleApi: ArticleApi
): RemoteMediator<Int, CachedArticleEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedArticleEntity>
    ): MediatorResult {
        return try {

            val currentPage = when(loadType){
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.plus(ARTICLES_PER_PAGE)?:0
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val remoteKeys = articleDb.remotePageKeysDao.getFirstRemoteKey()
                    Log.d("response","${remoteKeys?.nextPage?:"null"}")
                    val nextPage = remoteKeys.nextPage
                        ?: return MediatorResult.Success(
                                endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = articleApi.getArticles(limit = ARTICLES_PER_PAGE, offset = currentPage).results.map {
                it.toArticleEntity()
            }



            Log.d("load type","${loadType.name} : ${currentPage}")

            val endOfPagination = response.isEmpty()

            var previousPage = if(currentPage ==0) null else currentPage

            val nextPage= if(endOfPagination) null else currentPage+ ARTICLES_PER_PAGE

            articleDb.withTransaction {

                if(loadType==LoadType.REFRESH){
                    articleDb.remotePageKeysDao.deleteAllRemoteKeys()
                    articleDb.cachedArticleDao.clearAll()
                }

                val keys = response.map { articleDto->

                    RemotePageKeysEntity(
                            id = articleDto.id,
                            previousPage=previousPage,
                            nextPage = nextPage
                    )
                }


                articleDb.remotePageKeysDao.addAllRemoteKeys(remoteKeys = keys)
                articleDb.cachedArticleDao.upsertAll(articles = response)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)

        } catch (e:IOException){
            MediatorResult.Error(e)
        }catch (e:HttpException){
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state:PagingState<Int,CachedArticleEntity>
    ):RemotePageKeysEntity?{

        return  state.anchorPosition?.let {position->
            state.closestItemToPosition(position)?.id?.let {id->
                articleDb.remotePageKeysDao.getRemoteKeys(id=id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, CachedArticleEntity>
    ):RemotePageKeysEntity?{

        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()
            ?.let {
                articleDb
                    .remotePageKeysDao
                    .getRemoteKeys(id=it.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, CachedArticleEntity>
    ):RemotePageKeysEntity?{
        val state=state
        val page = state.pages.firstOrNull { it.data.isNotEmpty() }
        val data = page?.data?.firstOrNull()
       val key= data ?.let {
            articleDb.remotePageKeysDao
                .getRemoteKeys(id=it.id)
        }

        return key
    }
}