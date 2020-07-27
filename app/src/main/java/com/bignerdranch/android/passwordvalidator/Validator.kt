package com.bignerdranch.android.passwordvalidator

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validator: TextWatcher {

    var toDisappear: MutableLiveData<Boolean> = MutableLiveData(false)

    var strengthLevel: MutableLiveData<String> = MutableLiveData()
    var strengthColor: MutableLiveData<Int> = MutableLiveData()

    var lowerCase: MutableLiveData<Int> = MutableLiveData(0)
    var upperCase: MutableLiveData<Int> = MutableLiveData(0)
    var digit: MutableLiveData<Int> = MutableLiveData(0)
    var specialChar: MutableLiveData<Int> = MutableLiveData(0)

    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        if (s != null){
            lowerCase.value = if (s.hasLowerCase()) 1 else 0
            upperCase.value = if (s.hasUpperCase()) 1 else 0
            digit.value = if (s.hasDigit()) 1 else 0
            specialChar.value = if (s.hasSpecialChar()) 1 else 0
            calculateStrength(s)
        }
    }

    private fun calculateStrength(password: CharSequence) {
        if (password.length in 0..7){
            strengthColor.value = R.color.weak
            strengthLevel.value = "WEAK"
        }else if (password.length in 8..10){
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1){
                strengthColor.value = R.color.medium
                strengthLevel.value = "MEDIUM"
            }
        } else if (password.length in 11..16){
            if (lowerCase.value == 1 || upperCase.value == 1 || digit.value == 1 || specialChar.value == 1){
                if (lowerCase.value == 1 || upperCase.value == 1){
                strengthColor.value = R.color.strong
                strengthLevel.value = "STRONG"
            }}
        } else if (password.length > 16){
            if (lowerCase.value == 1 && upperCase.value == 1 && digit.value == 1 && specialChar.value == 1){
                strengthColor.value = R.color.bulletproof
                strengthLevel.value = "BULLETPROOF"
            }
        }

        toDisappear.value = lowerCase.value == 1 && upperCase.value == 1 && digit.value == 1 && specialChar.value == 1
        }

    private fun CharSequence.hasLowerCase(): Boolean{
        val pattern: Pattern = Pattern.compile("[a-z]")
        val hasLowerCase: Matcher = pattern.matcher(this)
        return hasLowerCase.find()
    }

    private fun CharSequence.hasUpperCase(): Boolean{
        val pattern: Pattern = Pattern.compile("[A-Z]")
        val hasUpperCase: Matcher = pattern.matcher(this)
        return hasUpperCase.find()
    }

    private fun CharSequence.hasDigit(): Boolean{
        val pattern: Pattern = Pattern.compile("[0-9]")
        val hasDigit: Matcher = pattern.matcher(this)
        return hasDigit.find()
    }

    private fun CharSequence.hasSpecialChar(): Boolean{
        val pattern: Pattern = Pattern.compile("[!@#$%^&*()_+{}/.<>|\\[\\]~¿§«»ω⊙¤°℃℉€¥£¢¡®©0-]")
        val hasSpecialChar: Matcher = pattern.matcher(this)
        return hasSpecialChar.find()
    }


}