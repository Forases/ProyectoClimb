package com.lorenzohamaoka.proyectoclimb.ui.filtro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FiltroViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is filtro Fragment"
    }
    val text: LiveData<String> = _text
}