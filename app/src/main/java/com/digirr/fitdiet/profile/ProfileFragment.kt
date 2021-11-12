package com.digirr.fitdiet.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.digirr.fitdiet.R
import com.digirr.fitdiet.data.User
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private val profileVm by viewModels<ProfileViewModel>()
    private val PROFILE_DEBUG = "PROFILE_DEBUG"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calcAgainData()
        setupSubmitDataClick()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        profileVm.user.observe(viewLifecycleOwner, { user ->
            bindUserData(user)
        })
    }

    private fun calcAgainData() {
        calc_again_button.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToCalculatorFragment())
        }
    }

    private fun setupSubmitDataClick() {
        save_new_profile_data_button.setOnClickListener {
            val nick = nick_change_editText.text?.trim().toString()
            val email = email_change_editText.text?.trim().toString()
            val map = mapOf("nick" to nick, "email" to email)
            profileVm.editProfileData(map)
        }
    }

    private fun bindUserData(user : User) {
        Log.d(PROFILE_DEBUG, user.toString())
        nick_change_editText.setText(user.nick)
        email_change_editText.setText(user.email)
    }

}