package com.digirr.fitdiet.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.repo.FirebaseModel

class ProfileViewModel(application: Application) : AndroidViewModel(application){

    private val fbRepo = FirebaseModel()

    val user = fbRepo.getUserData()

    fun editProfileData(map: Map<String, String>) {
        fbRepo.updateProfileValues(map)
    }

}