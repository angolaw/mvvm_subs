package com.fourdeux.subs.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourdeux.subs.data.model.Subscriber
import com.fourdeux.subs.data.repository.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    var saveOrUpdateButtonText = MutableLiveData<String>()
    var clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
      saveOrUpdateButtonText.value = "Salvar"
        clearAllOrDeleteButtonText.value = "Limpar tudo"

    }
    fun saveOrUpdate(){
        val name = inputName.value!!
        val email = inputEmail.value!!
        val sub = Subscriber(0,name, email)
        insertSubscriber(sub)
        inputEmail.value = ""
        inputName.value = ""

    }
    suspend fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }
}