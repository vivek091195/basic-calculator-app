package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var resultsTextView: TextView? = null
    var isDotPresent: Boolean = false
    var isLastNumeric: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)

        resultsTextView = findViewById(R.id.tvResults)

        val clearBtn: Button = findViewById(R.id.btnClear)
        clearBtn.setOnClickListener {
            clearBtnClickHandler()
        }
    }

    fun dotClickHandler(view: View) {
        if(isLastNumeric && !isDotPresent) {
            resultsTextView?.append((view as Button).text)
            isDotPresent = true
            isLastNumeric = false
        }
    }

    fun operatorClickHandler(view: View) {
        resultsTextView?.text.let{
            if(isLastNumeric && !checkExistingOperators(it.toString())) {
                resultsTextView?.append((view as Button).text)
                isLastNumeric = false
            }
        }
    }

    fun equalsClickHandler(view: View) {
        if(isLastNumeric) {
            var prefix = ""
            var firstValue: String = ""
            var secondValue: String = ""
            var result: Double = 0.0
            var tvValue: String = resultsTextView?.text.toString()
            try {
                if(tvValue.startsWith('-')) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains('-')) {
                    val splitValue = tvValue.split('-')
                    firstValue = if(prefix.isNotEmpty()) { prefix + splitValue[0] } else { splitValue[0] }
                    secondValue = splitValue[1]
                    result = firstValue.toDouble() - secondValue.toDouble()
                } else if(tvValue.contains('+')) {
                    val splitValue = tvValue.split('+')
                    firstValue = splitValue[0]
                    secondValue = splitValue[1]
                    result = firstValue.toDouble() + secondValue.toDouble()
                } else if(tvValue.contains('*')) {
                    val splitValue = tvValue.split('*')
                    firstValue = splitValue[0]
                    secondValue = splitValue[1]
                    result = firstValue.toDouble() * secondValue.toDouble()
                } else {
                    val splitValue = tvValue.split('/')
                    firstValue = splitValue[0]
                    secondValue = splitValue[1]
                    result = firstValue.toDouble() / secondValue.toDouble()
                }

                val finalResult = removeUnnecessaryZeroFromEnd(result.toString())
                resultsTextView?.text = finalResult
            } catch(e: ArithmeticException) {
                e.printStackTrace()
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun removeUnnecessaryZeroFromEnd(result: String): String {
        return if(result.endsWith(".0")) {
            result.substring(0, result.length - 2)
        } else {
            result
        }
    }

    private fun checkExistingOperators(str: String): Boolean {
        return if(str.startsWith('-')) {
            false
        } else {
            str.contains('-') || str.contains('+') || str.contains('/') || str.contains('*')
        }
    }

    fun btnClickHandler(view: View) {
        resultsTextView?.append((view as Button).text)
        isLastNumeric = true
        isDotPresent = false
    }

    private fun clearBtnClickHandler() {
        resultsTextView?.text = ""
    }
}