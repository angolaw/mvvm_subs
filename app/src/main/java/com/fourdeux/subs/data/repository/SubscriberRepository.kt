package com.fourdeux.subs.data.repository

import com.fourdeux.subs.data.SubscriberDAO
import com.fourdeux.subs.data.model.Subscriber

class SubscriberRepository(private val dao: SubscriberDAO) {
    val subscribers = dao.getAllSubscribers()

    suspend fun  insert(subscriber: Subscriber):Long{
        return dao.insert(subscriber)
    }
    suspend fun update(subscriber: Subscriber):Int{
        return dao.update(subscriber)
    }
    suspend fun  delete(subscriber: Subscriber):Int{
        return dao.delete(subscriber)
    }
    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }
}