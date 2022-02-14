package com.fourdeux.subs.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fourdeux.subs.data.model.Subscriber
import kotlinx.coroutines.flow.Flow

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun  insert(subscriber: Subscriber):Long
    @Update
    suspend fun update(subscriber: Subscriber):Int
    @Delete
    suspend fun delete(subscriber: Subscriber):Int
    @Query("DELETE FROM subscriber_data_table")
    suspend fun  deleteAll():Int
    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers():Flow<List<Subscriber>>

}