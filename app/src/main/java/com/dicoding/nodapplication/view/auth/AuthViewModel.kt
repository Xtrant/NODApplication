package com.dicoding.nodapplication.view.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.nodapplication.data.UserRepository
import com.dicoding.nodapplication.data.pref.UserPreferences
import com.dicoding.nodapplication.data.response.LoginResponse
import com.dicoding.nodapplication.data.response.MessageResponse
import com.dicoding.nodapplication.result.Result
import kotlinx.coroutines.launch

class AuthViewModel(
    private val userRepository: UserRepository,
    private val pref: UserPreferences
) : ViewModel() {

    private val _resultLogin = MutableLiveData<Result<LoginResponse>>()
    val resultLogin: LiveData<Result<LoginResponse>> = _resultLogin

    private val _resultMessage = MutableLiveData<Result<MessageResponse>>()
    val resultMessage: LiveData<Result<MessageResponse>> = _resultMessage



    fun login(email: String, password: String) {
        viewModelScope.launch {
            _resultLogin.value = Result.Loading
            _resultLogin.value = userRepository.login(email, password)
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _resultMessage.value = Result.Loading
            _resultMessage.value = userRepository.register(email, password)
        }
    }

    fun logout() {
        viewModelScope.launch {
            _resultMessage.value = Result.Loading
            _resultMessage.value = userRepository.logout()
        }
    }


    fun isLogin(): LiveData<Boolean> {
        return pref.isLogin().asLiveData()
    }

    fun getEmail(): LiveData<String> {
        return pref.getEmail().asLiveData()
    }

    fun saveSession(email: String, isLogin: Boolean, token: String) {
        viewModelScope.launch {
            pref.saveSession(email, isLogin, token)
        }
    }

    fun getToken(): LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun clearSession() {
        viewModelScope.launch {
            pref.clearSession()
        }
    }
}