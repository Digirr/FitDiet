package com.digirr.fitdiet.calculator

import android.app.Application
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel

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


    fun assignFieldsToTheUser(age: EditText, height: EditText, weight: EditText, spinnerActivityLvl: Spinner, spinnerGoal: Spinner) : Boolean {

        val ageResult = validateBodyParameters(age, 16, 120, INCORRECT_AGE, BELOW_VALUE_AGE, AT_LEAST_VALUE_AGE)
        val heightResult = validateBodyParameters(height, 120, 220, INCORRECT_HEIGHT, BELOW_VALUE_HEIGHT, AT_LEAST_VALUE_HEIGHT)
        val weightResult = validateBodyParameters(weight, 40, 800, INCORRECT_WEIGHT, BELOW_VALUE_WEIGHT, AT_LEAST_VALUE_WEIGHT)

        if(ageResult && heightResult && weightResult) {
            //Zapisz dane do modelu
            //Odpal aplikacje
            return true
        }
        return false
    }


    private fun validateBodyParameters(parameter : EditText, lowerArg : Int, topArgument : Int, incorrectTypingMess : String, incorretLargeArgMess : String, incorrectLowArgMess : String): Boolean {
        var parameterValue : Int

        try {
            parameterValue = Integer.parseInt(parameter.text.toString())
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