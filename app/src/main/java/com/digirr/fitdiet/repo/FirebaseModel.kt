package com.digirr.fitdiet.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    fun getUserData(): LiveData<User> {
        val cloudResult = MutableLiveData<User>()
        val uid = fbAuth.currentUser?.uid   //Nullable

        fbCloud.collection("users") //Odnosze sie do kolekcji users
            .document(uid!!)    //Biore odpowiedzialnosc, wchodze do dokumentu o nazwie uid
            .get()  //Uzyskaj ten dokument
            .addOnSuccessListener {
                //Od razu mapuje dane, ktore przejda na odpowiedni obiekt
                val user = it.toObject(User::class.java)
                cloudResult.postValue(user)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return cloudResult
    }

    fun updateProfileValues(map: Map<String, Any>) : Boolean {
        val uid = fbAuth.currentUser?.uid
        var value = true
        fbCloud.collection("users")
            .document(uid!!)
            .update(map)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Zaaktualizowano dane!")
                value = true
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
                value = false
            }
        return value
    }
}