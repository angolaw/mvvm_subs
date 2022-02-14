package com.fourdeux.subs.infra

import androidx.room.*
import com.fourdeux.subs.data.model.Subscriber

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

}