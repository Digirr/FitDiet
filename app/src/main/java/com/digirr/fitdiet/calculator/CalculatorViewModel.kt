package com.digirr.fitdiet.calculator

import android.app.Application
import android.icu.number.IntegerWidth
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.R
import com.digirr.fitdiet.repo.FirebaseModel
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlin.math.ceil

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    private val fbRepository = FirebaseModel()

    val INCORRECT_AGE = getStringFromResources(R.string.incorrect_age)
    val BELOW_VALUE_AGE = getStringFromResources(R.string.below_value_age)
    val AT_LEAST_VALUE_AGE = getStringFromResources(R.string.at_least_value_age)

    val INCORRECT_HEIGHT = getStringFromResources(R.string.incorrect_height)
    val BELOW_VALUE_HEIGHT = getStringFromResources(R.string.incorrect_height)
    val AT_LEAST_VALUE_HEIGHT = getStringFromResources(R.string.incorrect_height)

    val INCORRECT_WEIGHT = getStringFromResources(R.string.incorrect_weight)
    val BELOW_VALUE_WEIGHT = getStringFromResources(R.string.incorrect_weight)
    val AT_LEAST_VALUE_WEIGHT = getStringFromResources(R.string.incorrect_weight)

    fun assignFieldsToTheUser(
        view: View,
        age: EditText,
        height: EditText,
        weight: EditText,
        spinnerActivityLvl: Spinner,
        spinnerGoal: Spinner,
        genderRG : RadioGroup
    ) : Boolean {

        val ageResult = validateBodyParameters(age, 16, 120, INCORRECT_AGE, BELOW_VALUE_AGE, AT_LEAST_VALUE_AGE)
        val heightResult = validateBodyParameters(height, 120, 220, INCORRECT_HEIGHT, BELOW_VALUE_HEIGHT, AT_LEAST_VALUE_HEIGHT)
        val weightResult = validateBodyParameters(weight, 40, 800, INCORRECT_WEIGHT, BELOW_VALUE_WEIGHT, AT_LEAST_VALUE_WEIGHT)

        if(ageResult && heightResult && weightResult) {
            return calculateKcalAndGrams(
                getEditTextAsInt(age),
                getEditTextAsInt(height),
                getEditTextAsInt(weight),
                getSpinnerValue(spinnerActivityLvl),
                getSpinnerValue(spinnerGoal),
                getRadioGroupValue(view, genderRG)
            )
        }
        return false
    }

    private fun calculateKcalAndGrams(age: Int, height: Int, weight: Int, spinnerActivityLvl: String, spinnerGoal: String, gender: String) : Boolean {
        val female: String = getApplication<Application>().resources.getString(R.string.female)

        val activityLvLs = getApplication<Application>().resources.getStringArray(R.array.activityLvl_array).toList()
        val activityLvlsValues = arrayOf(1.2, 1.35, 1.55, 1.55, 1.75, 2.0)
        val mapActivitiesToValues: Map<String, Double> = activityLvLs.zip(activityLvlsValues).toMap()

        val goals = getApplication<Application>().resources.getStringArray(R.array.goal_array).toList()
        val goalsValues = arrayOf(-300, 300, 0, -150)
        val mapGoalsToValues: Map<String, Int> = goals.zip(goalsValues).toMap()

        //Protein - 4kcal | Carbohydrates - 4kcal | Fat - 9kcal
        val goalsPercents : Array<List<Double>> = arrayOf(listOf(0.2, 0.5, 0.3), listOf(0.15, 0.55, 0.3), listOf(0.15, 0.55, 0.3), listOf(0.2, 0.5, 0.3))
        val mapGoalsPercentsToValues: Map<String, List<Double>> = goals.zip(goalsPercents).toMap()

        //Mifflin's formula
        var formulaValue : Double = 10*weight + 6.25*height - 5*age

        if(gender == female) {
            formulaValue -= 161
        } else {
            formulaValue += 5
        }

        formulaValue *= mapActivitiesToValues[spinnerActivityLvl]!!
        formulaValue += mapGoalsToValues[spinnerGoal]!!

        val values = mapGoalsPercentsToValues[spinnerGoal]
        val maxKcal = ceil(formulaValue).toInt()

        val kcalValues = mapOf(
            "maxKcal" to maxKcal,
            "maxProtein" to ceil(values!![0] * maxKcal).toInt(),
            "maxCarbohydrates" to ceil(values!![1] * maxKcal).toInt(),
            "maxFat" to ceil(values!![2] * maxKcal).toInt()
        )

        return fbRepository.updateProfileValues(kcalValues)
    }

    private fun validateBodyParameters(parameter : EditText, lowerArg : Int, topArgument : Int, incorrectTypingMess : String, incorretLargeArgMess : String, incorrectLowArgMess : String): Boolean {
        var parameterValue : Int

        try {
            parameterValue = getEditTextAsInt(parameter)
        } catch (e: NumberFormatException) {
            Toast.makeText(getApplication(), incorrectTypingMess, Toast.LENGTH_SHORT).show()
            return false
        }

        return if (parameterValue in lowerArg..topArgument) {
            true
        } else if (parameterValue > topArgument) {
            Toast.makeText(getApplication(), incorretLargeArgMess, Toast.LENGTH_SHORT).show()
            false
        } else {
            Toast.makeText(getApplication(), incorrectLowArgMess, Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun getStringFromResources(incomingId : Int) : String {
        return getApplication<Application>().resources.getString(incomingId)
    }

    private fun getEditTextAsInt(editText: EditText) : Int {
        return Integer.parseInt(editText.text.toString())
    }

    private fun getSpinnerValue(spinner: Spinner) : String {
        return spinner.selectedItem.toString()
    }

    private fun getRadioGroupValue(view: View, genderRG: RadioGroup) : String {
        var selectedRadioButton : RadioButton
        val selectedRadioButtonId = genderRG.checkedRadioButtonId
        selectedRadioButton = view.findViewById(selectedRadioButtonId)
        return selectedRadioButton.text.toString()
    }

}