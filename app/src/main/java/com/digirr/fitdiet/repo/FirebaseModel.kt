package com.digirr.fitdiet.repo

import com.digirr.fitdiet.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseModel {

    private val REPO_DEBUG = "REPO_DEBUG"

    private val fbAuth = FirebaseAuth.getInstance()
    private val fbStorage = FirebaseStorage.getInstance()
    private val fbCloud = FirebaseFirestore.getInstance()


    fun createNewUser(user: User) {
        fbCloud.collection("users")
            .document(user.uid!!)
            .set(user)
    }
}