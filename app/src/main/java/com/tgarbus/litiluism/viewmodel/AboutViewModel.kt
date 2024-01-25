package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tgarbus.litiluism.data.AboutRepository
import kotlinx.coroutines.launch

class AboutViewModel(): ViewModel() {
    fun markComplete(context: Context) {
        val aboutRepo = AboutRepository()
        viewModelScope.launch {
            aboutRepo.markCompleted(context)
        }
    }
}