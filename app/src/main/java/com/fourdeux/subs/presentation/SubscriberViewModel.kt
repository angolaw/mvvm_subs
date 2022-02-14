package com.fourdeux.subs.presentation

import android.util.Patterns
import androidx.annotation.Keep
import androidx.lifecycle.*
import com.fourdeux.subs.Event
import com.fourdeux.subs.data.model.Subscriber
import com.fourdeux.subs.data.repository.SubscriberRepository
import kotlinx.coroutines.flow.collect
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
    private lateinit var subscriberToUpdateOrDelete: Subscriber
    private var isUpdateOrDelete:Boolean = false
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
            if(isUpdateOrDelete){
                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!
                updateSubscriber(subscriberToUpdateOrDelete)
            }
            val name = inputName.value!!
            val email = inputEmail.value!!
            val sub = Subscriber(0,name, email)
            insertSubscriber(sub)
            inputEmail.value = ""
            inputName.value = ""
        }


    }
    fun clearOrDeleteAll(){
        if(isUpdateOrDelete){
            deleteSubscriber(subscriberToUpdateOrDelete)
        }else{
            clearAll()

        }
    }
    private fun deleteSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val numberOfRows = repository.delete(subscriber)
        if(numberOfRows > 0){
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Limpar tudo"
            statusMessage.value = Event("$numberOfRows registros deletados")
        }else{
            statusMessage.value= Event("Um erro ocorreu")
        }
    }
    private fun updateSubscriber(subscriber: Subscriber) = viewModelScope.launch {
        val numberOfRows = repository.update(subscriber)
        if(numberOfRows > 0){
            inputName.value = ""
            inputEmail.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Salvar"
            clearAllOrDeleteButtonText.value = "Limpar tudo"
            statusMessage.value = Event("$numberOfRows registros atualizados com sucesso")
        }else{
            statusMessage.value = Event("Um erro ocorreu")
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
    fun getSavedSubscribers() = liveData {
        repository.subscribers.collect {
            emit(it)
        }
    }
    private fun clearAll() = viewModelScope.launch {
        val numberOfRowsAffected = repository.deleteAll()
        if(numberOfRowsAffected > 0){
            statusMessage.value  = Event("$numberOfRowsAffected subs deletados")
        }else{
            statusMessage.value = Event("Um erro ocorreu")
        }
    }
    //editing
    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email
        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber
        saveOrUpdateButtonText.value = "Atualizar"
        clearAllOrDeleteButtonText.value = "Deletar"

    }
}