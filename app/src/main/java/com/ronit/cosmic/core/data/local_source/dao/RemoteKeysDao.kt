package com.ronit.cosmic.core.data.local_source.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ronit.cosmic.core.data.local_source.model.RemotePageKeysEntity
import com.ronit.cosmic.core.utility.Constants.REMOTE_PAGE_KEYS_TABLE

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM $REMOTE_PAGE_KEYS_TABLE WHERE id =:id")
    suspend fun getRemoteKeys(id:Int):RemotePageKeysEntity

    @Insert
    suspend fun addAllRemoteKeys(remoteKeys:List<RemotePageKeysEntity>)

    @Query("SELECT * FROM $REMOTE_PAGE_KEYS_TABLE ORDER BY previousPage DESC LIMIT 1  ")
    suspend fun getFirstRemoteKey():RemotePageKeysEntity

    @Query("DELETE  FROM $REMOTE_PAGE_KEYS_TABLE")
    suspend fun deleteAllRemoteKeys()
}