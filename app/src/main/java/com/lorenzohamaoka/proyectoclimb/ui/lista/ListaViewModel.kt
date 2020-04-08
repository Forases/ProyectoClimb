package com.lorenzohamaoka.proyectoclimb.ui.lista

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is lista Fragment"
    }
    val text: LiveData<String> = _text
}