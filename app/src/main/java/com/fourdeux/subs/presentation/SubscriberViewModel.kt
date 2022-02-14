package com.fourdeux.subs.presentation

import android.util.Patterns
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fourdeux.subs.Event
import com.fourdeux.subs.data.model.Subscriber
import com.fourdeux.subs.data.repository.SubscriberRepository
import kotlinx.coroutines.launch

@Keep
class SubscriberViewModel(private val repository: SubscriberRepository):ViewModel() {
    val inputName = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    var saveOrUpdateButtonText = MutableLiveData<String>()
    var clearAllOrDeleteButtonText = MutableLiveData<String>()
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
    get() = statusMessage
    init {
      saveOrUpdateButtonText.value = "Salvar"
        clearAllOrDeleteButtonText.value = "Limpar tudo"

    }
    fun saveOrUpdate(){
        if(inputName.value == null){
            statusMessage.value = Event("Favor inserir o nome do sub")
        }else if(inputEmail.value == null){
            statusMessage.value = Event("Favor inserir o email do sub")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Favor inserir um email válido")
        }else {
            val name = inputName.value!!
            val email = inputEmail.value!!
            val sub = Subscriber(0,name, email)
            insertSubscriber(sub)
            inputEmail.value = ""
            inputName.value = ""
        }


    }
    private fun insertSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val newRowId = repository.insert(subscriber)
        if(newRowId > -1){
            statusMessage.value = Event("Subscriber cadastrado com id: $newRowId")
        }else{
            statusMessage.value = Event("Um erro ocorreu")
        }
    }
}