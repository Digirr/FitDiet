package com.digirr.fitdiet.loadingscreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenLoadFragment : AbstractFragment() { //Tu zmienione na abstract chwilowo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_screen_load, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.Main) {
            delay(2000L)
            findNavController().navigate(ScreenLoadFragmentDirections.actionScreenLoadFragmentToLogin())
            //startMainViewApp()
        }
    }


}