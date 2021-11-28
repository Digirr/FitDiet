package com.digirr.fitdiet.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : AbstractFragment() {


    private val fbAuth = FirebaseAuth.getInstance()
    private val LOG_DEBUG = "LOG_DEBUG"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoginClick()
        setupRegistrationClick()
    }
    private fun setupRegistrationClick() {
        registerButton.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections
                .actionLoginToRegistration().actionId)
        }
    }

    override fun onStart() {
        super.onStart()
        isCurrentUser()
    }

    private fun isCurrentUser() {
        fbAuth.currentUser?.let {
            startMainViewApp()
        }
    }

    private fun setupLoginClick() {
        loginLogButton.setOnClickListener {
            val email = email_log_editText.text?.trim().toString()
            val password = pass_log_editText.text?.trim().toString()

            //Przeniesc ta logike do ViewModel
            if(email != "" || password != ""){
                fbAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener { authRes ->
                        if(authRes.user != null)
                            startMainViewApp()
                        }
                    .addOnFailureListener {
                        Snackbar.make(requireView(), "Something went wrong...", Snackbar.LENGTH_SHORT)
                            .show()
                        Log.d(LOG_DEBUG, it.message.toString())

                    }
            } else {
                Snackbar.make(requireView(), "Fields cannot be empty...", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

}