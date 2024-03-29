package com.digirr.fitdiet.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

    fun addFoodProduct(product : FoodProduct){
        fbCloud.collection("foodProducts")
            .document(product.id!!)
            .set(product)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Dodano nowy produkt!")
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    fun uploadFoodImage(bytes: ByteArray){
        fbStorage.getReference("foodProducts")
            .child("$bytes.jpg")
            .putBytes(bytes)
            .addOnCompleteListener {
                Log.d(REPO_DEBUG, "COMPLETE UPLOAD PHOTO")
            }
            .addOnSuccessListener {
                getPhotoDownloadUrl(it.storage, bytes.toString())
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    private fun getPhotoDownloadUrl(storage: StorageReference, bytesName : String) {
        storage.downloadUrl
            .addOnSuccessListener {
                updateProductPhoto(it.toString(), bytesName)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    private fun updateProductPhoto(url : String, bytesName: String) {
        fbCloud.collection("foodProducts")
            .document(bytesName)
            .update("image", url)
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "UPDATE USER PHOTO!")
            }
            .addOnFailureListener{
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    fun getProducts(): LiveData<List<FoodProduct>> {
        val cloudResult = MutableLiveData<List<FoodProduct>>()

        fbCloud.collection("foodProducts")
            .get()
            .addOnSuccessListener {
                val product = it.toObjects(FoodProduct::class.java)
                cloudResult.postValue(product)
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
        return cloudResult
    }

    fun addEatenProduct(product : FoodProduct) {
        fbCloud.collection("users")
            .document(fbAuth.currentUser?.uid!!)
            .update("eatenProducts", FieldValue.arrayUnion(product.id))
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Produkt dodany do spożytych")
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

    fun logoutUser() {
        fbAuth.signOut()
    }

    fun getEatenProducts(list : List<String>?) : LiveData<List<FoodProduct>> {
        val cloudResult = MutableLiveData<List<FoodProduct>>()

        if(!list.isNullOrEmpty()) {
            fbCloud.collection("foodProducts")
                .whereIn("id", list)
                .get()
                .addOnSuccessListener {
                    val resultList = it.toObjects(FoodProduct::class.java)
                    cloudResult.postValue(resultList)
                }
                .addOnFailureListener {
                    Log.d(REPO_DEBUG, it.message.toString())
                }
        }
        return cloudResult
    }

    fun removeEatenProduct(product : FoodProduct) {
        fbCloud.collection("users")
            .document(fbAuth.currentUser?.uid!!)
            .update("eatenProducts", FieldValue.arrayRemove(product.id))
            .addOnSuccessListener {
                Log.d(REPO_DEBUG, "Usunięto produkt z listy")
            }
            .addOnFailureListener {
                Log.d(REPO_DEBUG, it.message.toString())
            }
    }

}