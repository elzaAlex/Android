package com.example.lab1_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_0.setOnClickListener{ setTextFields("0") }
        btn_1.setOnClickListener{ setTextFields("1") }
        btn_2.setOnClickListener{ setTextFields("2") }
        btn_3.setOnClickListener{ setTextFields("3") }
        btn_4.setOnClickListener{ setTextFields("4") }
        btn_5.setOnClickListener{ setTextFields("5") }
        btn_6.setOnClickListener{ setTextFields("6") }
        btn_7.setOnClickListener{ setTextFields("7") }
        btn_8.setOnClickListener{ setTextFields("8") }
        btn_9.setOnClickListener{ setTextFields("9") }
        btn_dote.setOnClickListener{ setTextFields(".") }
        btn_open_br.setOnClickListener{ setTextFields("(") }
        btn_close_br.setOnClickListener{ setTextFields(")") }

        btn_minus.setOnClickListener{ setOperation('-') }
        btn_plus.setOnClickListener{ setOperation('+') }
        btn_divide.setOnClickListener{ setOperation('/') }
        btn_mult.setOnClickListener{ setOperation('*') }


        btn_percent.setOnClickListener{ setTextFields("*0.01") }
        btn_fraction.setOnClickListener{ setTextFields("^(-1)") }
        btn_pi.setOnClickListener{ setTextFields("π") }

        btn_sqr.setOnClickListener{ setTextFields("^(2)") }
        btn_cube.setOnClickListener{ setTextFields("^(3)") }
        btn_f.setOnClickListener{ setTextFields("!") }

        btn_root.setOnClickListener{ setTextFields("√") }
        btn_m_power.setOnClickListener{ setTextFields("^(1/") }
        btn_power.setOnClickListener{ setTextFields("^(") }

        btn_e.setOnClickListener{ setTextFields("e") }
        btn_ln.setOnClickListener{ setTextFields("ln(") }
        btn_log.setOnClickListener{ setTextFields("log(") }

        btn_sin.setOnClickListener{ setTextFields("sin(") }
        btn_cos.setOnClickListener{ setTextFields("cos(") }
        btn_tan.setOnClickListener{ setTextFields("tan(") }


        btn_clear.setOnClickListener {
            expression.text = ""
            result.text = ""
        }

        btn_back.setOnClickListener {
            if (expression.text.toString().equals("Ошибка"))
                expression.text = ""
            else {
                val str = expression.text.toString()
                if (str.isNotEmpty())
                    expression.text = str.substring(0, str.length - 1)
                result.text = ""
            }
        }

        btn_get.setOnClickListener {
            getResult()
            expression.text = result.text
            result.text = ""
        }
    }
    fun setTextFields(str: String){
        if (expression.text.toString().equals("Ошибка"))
            expression.text = ""
        expression.append(str)
        getResult()
    }

    fun getResult(){
        try {
            val ex = ExpressionBuilder(expression.text.toString()).build()
            val res = ex.evaluate()

            val longRes = res.toLong()
            if (res == longRes.toDouble())
                result.text = longRes.toString()
            else
                result.text = res.toString()
        } catch (e:Exception){

            val mathStr = expression.text
            val str = mathStr[mathStr.length - 1]
            if ((str != '-') and (str != '+') and (str != '*') and (str != '/')) {
                result.text = "Ошибка"
            }
        }
    }

    fun setOperation(str_next: Char){
        if (expression.text.isNotEmpty() and (expression.text.toString() != "Ошибка")) {
            val mathStr = expression.text
            val str = mathStr[mathStr.length - 1]

            if ((str != '(')) {
                if (str.equals('-') or str.equals('+') or str.equals('*') or str.equals('/')) {
                    val math_string = expression.text.toString()
                    expression.text = math_string.substring(0, math_string.length - 1)
                }
                setTextFields(str_next.toString())
            }
        }
    }
}