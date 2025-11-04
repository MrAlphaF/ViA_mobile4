package com.arturssilins.calc

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    val displayText = mutableStateOf("0")

    private var firstOperand: Double? = null
    private var currentOperator: String? = null
    private var shouldClearDisplay = false
    private var memory: Double = 0.0

    fun onNumberClick(number: String) {
        if (shouldClearDisplay) {
            displayText.value = "0"
            shouldClearDisplay = false
        }

        if (displayText.value == "0" && number != ".") {
            displayText.value = number
        } else if (number == "." && displayText.value.contains(".")) {
            // Do nothing if decimal point already exists
        } else {
            displayText.value += number
        }
    }

    fun onOperatorClick(operator: String) {
        val currentDisplayValue = displayText.value.toDoubleOrNull() ?: return

        if (firstOperand == null) {
            firstOperand = currentDisplayValue
        } else {
            calculate()
            firstOperand = displayText.value.toDoubleOrNull()
        }

        currentOperator = operator
        shouldClearDisplay = true
    }

    fun onEqualsClick() {
        calculate()
        firstOperand = null
        currentOperator = null
    }

    fun onClearClick() {
        displayText.value = "0"
        firstOperand = null
        currentOperator = null
        shouldClearDisplay = false
    }

    fun onMemorySave() {
        memory = displayText.value.toDoubleOrNull() ?: 0.0
    }

    fun onMemoryRead() {
        displayText.value = formatResult(memory)
        shouldClearDisplay = false
    }

    fun onMemoryClear() {
        memory = 0.0
    }

    private fun calculate() {
        val secondOperand = displayText.value.toDoubleOrNull()
        val op1 = firstOperand
        if (op1 != null && secondOperand != null && currentOperator != null) {
            val result = when (currentOperator) {
                "+" -> op1 + secondOperand
                "-" -> op1 - secondOperand
                "*" -> op1 * secondOperand
                "/" -> if (secondOperand != 0.0) op1 / secondOperand else Double.NaN
                else -> 0.0
            }

            if (result.isNaN()) {
                displayText.value = "Error"
            } else {
                displayText.value = formatResult(result)
            }
            shouldClearDisplay = true
        }
    }

    private fun formatResult(result: Double): String {
        return if (result % 1.0 == 0.0) {
            result.toLong().toString()
        } else {
            result.toString()
        }
    }
}