package com.digirr.fitdiet.calculator

import android.app.Application
import android.icu.number.IntegerWidth
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.R
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlin.math.ceil

class CalculatorViewModel(application: Application) : AndroidViewModel(application) {

    val INCORRECT_AGE = "Wiek pusty lub wprowadzony nieprawidłowo"
    val BELOW_VALUE_AGE = "Wiek musi być poniżej 120 lat"
    val AT_LEAST_VALUE_AGE = "Musisz mieć co najmniej 16 lat"

    val INCORRECT_HEIGHT = "Wzrost pusty lub wprowadzony nieprawidłowo"
    val BELOW_VALUE_HEIGHT = "Wzrost musi być wynosić poniżej 220 cm"
    val AT_LEAST_VALUE_HEIGHT = "Minimalny wzrost wynosi 120 cm"

    val INCORRECT_WEIGHT = "Waga pusta lub wprowadzona nieprawidłowo"
    val BELOW_VALUE_WEIGHT = "Waga musi być wynosić poniżej 800 kg"
    val AT_LEAST_VALUE_WEIGHT = "Minimalna waga wynosi 40 kg"


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
            calculateKcalAndGrams(
                getEditTextAsInt(age),
                getEditTextAsInt(height),
                getEditTextAsInt(weight),
                getSpinnerValue(spinnerActivityLvl),
                getSpinnerValue(spinnerGoal),
                getRadioGroupValue(view, genderRG)
            )
            //Zapisz dane do modelu
            //Odpal aplikacje
            return true
        }
        return false
    }

    private fun calculateKcalAndGrams(age: Int, height: Int, weight: Int, spinnerActivityLvl: String, spinnerGoal: String, gender: String) {
        val female: String = getApplication<Application>().resources.getString(R.string.female)
        val male: String = getApplication<Application>().resources.getString(R.string.male)
        val activityLvLs = getApplication<Application>().resources.getStringArray(R.array.activityLvl_array).toList()
        val activityLvlsValues = arrayOf(1.2, 1.35, 1.55, 1.55, 1.75, 2.0)
        val mapNamesToValues: Map<String, Double> = activityLvLs.zip(activityLvlsValues).toMap()


        //Mifflin's formula
        var formulaValue : Double = 10*weight + 6.25*height - 5*age

        if(gender == female) {
            formulaValue -= 161
            return
        }
        formulaValue += 5

        //Log.d("SIEMA", ceil(formulaValue).toString())

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

    private fun validateBodyParameters(parameter : EditText, lowerArg : Int, topArgument : Int, incorrectTypingMess : String, incorretLargeArgMess : String, incorrectLowArgMess : String): Boolean {
        var parameterValue : Int

        try {
            parameterValue = getEditTextAsInt(parameter)
        } catch (e: NumberFormatException) {
            Toast.makeText(getApplication(), incorrectTypingMess, Toast.LENGTH_SHORT).show()
            return false
        }

        return if (parameterValue >= lowerArg && parameterValue <= topArgument) {
            true
        } else if (parameterValue > topArgument) {
            Toast.makeText(getApplication(), incorretLargeArgMess, Toast.LENGTH_SHORT).show()
            false
        } else {
            Toast.makeText(getApplication(), incorrectLowArgMess, Toast.LENGTH_SHORT).show()
            false
        }
    }

}