package com.digirr.fitdiet.registration

import androidx.lifecycle.ViewModel
import com.digirr.fitdiet.data.User
import com.digirr.fitdiet.repo.FirebaseModel

class RegistrationViewModel : ViewModel() {

    private val fbRepository = FirebaseModel()

    fun createNewUser(user : User) {
        fbRepository.createNewUser(user)
    }
}