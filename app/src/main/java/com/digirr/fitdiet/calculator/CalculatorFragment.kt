package com.digirr.fitdiet.calculator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import kotlinx.android.synthetic.main.fragment_calculator.*

class CalculatorFragment : AbstractFragment() {

    private val calcVM by viewModels<CalculatorViewModel>()


    //Tworzenie elementow
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSpinners()

        saveCalculatorDataButton.setOnClickListener {
          val result = calcVM.assignFieldsToTheUser(view, ageET, heightET, giveWeightET, spinnerActivityLvl, spinnerGoal, genderRG)

          if(result) {
              startMainViewApp()
          }
        }
    }



    private fun createSpinners() {
        ArrayAdapter.createFromResource(requireContext(), R.array.activityLvl_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerActivityLvl.adapter = adapter
                }
        ArrayAdapter.createFromResource(requireContext(), R.array.goal_array, android.R.layout.simple_spinner_item)
                .also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerGoal.adapter = adapter
                }
    }

}