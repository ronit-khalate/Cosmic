package com.ronit.cosmic.core.data.local_source.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ronit.cosmic.core.data.local_source.model.RemotePageKeys
import com.ronit.cosmic.core.utility.Constants.REMOTE_PAGE_KEYS_TABLE

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM $REMOTE_PAGE_KEYS_TABLE WHERE id =:id")
    suspend fun getRemoteKeys(id:Int):RemotePageKeys

    @Insert
    suspend fun addAllRemoteKeys(remoteKeys:List<RemotePageKeys>)

    @Query("SELECT * FROM $REMOTE_PAGE_KEYS_TABLE ORDER BY previousPage DESC LIMIT 1  ")
    suspend fun getFirstRemoteKey():RemotePageKeys

    @Query("DELETE  FROM $REMOTE_PAGE_KEYS_TABLE")
    suspend fun deleteAllRemoteKeys()
}