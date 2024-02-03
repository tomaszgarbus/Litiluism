package com.tgarbus.litiluism.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.data.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsViewModel(): ViewModel() {
    fun getInputMethodAsFlow(context: Context): Flow<InputMethod> {
        return SettingsRepository(context).inputMethodAsFlow()
    }

    suspend fun setInputMethod(context: Context, inputMethod: InputMethod) {
        SettingsRepository(context).setInputMethod(inputMethod)
    }
}