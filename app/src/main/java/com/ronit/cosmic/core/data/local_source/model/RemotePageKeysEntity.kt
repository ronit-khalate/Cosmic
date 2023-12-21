package com.ronit.cosmic.core.data.local_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ronit.cosmic.core.utility.Constants

@Entity(tableName = Constants.REMOTE_PAGE_KEYS_TABLE)
data class RemotePageKeysEntity(
        @PrimaryKey(autoGenerate = false)
        val id:Int,
        val previousPage:Int?,
        val nextPage:Int?
)