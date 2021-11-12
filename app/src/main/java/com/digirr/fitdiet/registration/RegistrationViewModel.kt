package com.digirr.fitdiet.registration

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.data.User
import com.digirr.fitdiet.repo.FirebaseModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationViewModel : ViewModel() {

    private val fbRepository = FirebaseModel()

    fun createNewUser(user : User) {
        fbRepository.createNewUser(user)
    }

}