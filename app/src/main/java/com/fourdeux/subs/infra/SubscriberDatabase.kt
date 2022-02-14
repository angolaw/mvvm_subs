package com.fourdeux.subs.infra

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fourdeux.subs.data.SubscriberDAO
import com.fourdeux.subs.data.model.Subscriber


@Database(entities = [Subscriber::class], version = 1)
abstract class SubscriberDatabase {
    abstract  val subscriberDAO: SubscriberDAO

    companion object {
        @Volatile
        private var INSTANCE: SubscriberDatabase? =null
        fun getInstance(context: Context): SubscriberDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SubscriberDatabase::class.java,
                    "subscriber_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}