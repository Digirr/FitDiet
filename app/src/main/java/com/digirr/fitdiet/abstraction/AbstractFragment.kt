package com.digirr.fitdiet.abstraction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.digirr.fitdiet.activities.MainActivity
import com.digirr.fitdiet.activities.StartActivity

abstract class AbstractFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val transInflater = TransitionInflater.from(requireContext())
//        enterTransition = transInflater.inflateTransition(R.transition.slide_right)
//
//        exitTransition = transInflater.inflateTransition(R.transition.fade_out)
    }


    protected fun startMainViewApp() {
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

    protected fun startStartViewApp() {
        val intent = Intent(requireContext(), StartActivity::class.java).apply {
            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(intent)
    }

}