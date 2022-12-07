package com.example.mycalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.view.View
import android.widget.TextView
import com.example.mycalculator.R
import org.w3c.dom.Text
import java.lang.ArithmeticException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    private var tvInput: TextView? = null
    //var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
    //var tvInput = findViewById<TextView>(R.id.tvInput)
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
        /*This is implemented by android studio it self when we select the Basic Activity while creating the project.*/
        //setSupportActionBar(toolbar)
    }
    fun onDigit(view: View){
        //Toast.makeText(this,"Button clicked", Toast.LENGTH_LONG).show()
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput?.text=""
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result = one.toDouble() - two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result = one.toDouble() + two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result = one.toDouble() * two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    var result = one.toDouble() / two.toDouble()
                    tvInput?.text = removeZeroAfterDot(result.toString())
                }
            }catch(error: ArithmeticException){
                error.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String{
        var value = result
        if(result.endsWith(".0"))
            value = result.substring(0,result.length - 2)
        return value
    }
    private fun isOperatorAdded(value : String): Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }

}