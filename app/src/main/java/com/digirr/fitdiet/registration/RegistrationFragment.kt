package com.digirr.fitdiet.registration

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.*


class RegistrationFragment : AbstractFragment() {

    private val fbAuth = FirebaseAuth.getInstance()
    private val REG_DEBUG = "REG_DEBUG"
    private val regViewModel by viewModels<RegistrationViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginRegButton.setOnClickListener {
            val nick = nick_reg_editText.text?.trim().toString()
            val email = email_reg_editText.text?.trim().toString()
            val password = pass_reg_editText.text?.trim().toString()
            val repPassword = repPass_reg_editText.text?.trim().toString()


            if(nick != "" || email != "" || password != "" || repPassword != ""){
                if(password == repPassword) {
                    fbAuth.createUserWithEmailAndPassword(email, password) //Utworzenie konta w zakladce Authentification
                        .addOnSuccessListener { authRes ->
                            if(authRes.user != null) {
                                val user = com.digirr.fitdiet.data.User(
                                    authRes.user!!.uid,
                                    nick,
                                    authRes.user!!.email)
                                regViewModel.createNewUser(user)    //Utworzenie powiazanego konta w bazie danych
                                findNavController().navigate(RegistrationFragmentDirections.actionRegistrationToCalculator().actionId)
//                                startMainViewApp()
                            }
                        }
                        .addOnFailureListener {
                            Snackbar.make(requireView(), "Something went wrong...", Snackbar.LENGTH_SHORT)
                                .show()
                            Log.d(REG_DEBUG, it.message.toString())
                        }
                }
            } else {
                Snackbar.make(requireView(), "Fields cannot be empty...", Snackbar.LENGTH_SHORT)
                    .show()
            }


        }
    }

}