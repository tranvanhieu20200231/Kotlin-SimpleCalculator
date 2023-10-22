package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.calculator.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var currentInput: String = ""
    private var currentOperator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Khi click số
        fun addToInputText(btnValue: String): String {
            return "${binding.tvInput.text}$btnValue"
        }

        binding.btn0.setOnClickListener {
            binding.tvInput.text = addToInputText("0")
        }
        binding.btn1.setOnClickListener {
            binding.tvInput.text = addToInputText("1")
        }
        binding.btn2.setOnClickListener {
            binding.tvInput.text = addToInputText("2")
        }
        binding.btn3.setOnClickListener {
            binding.tvInput.text = addToInputText("3")
        }
        binding.btn4.setOnClickListener {
            binding.tvInput.text = addToInputText("4")
        }
        binding.btn5.setOnClickListener {
            binding.tvInput.text = addToInputText("5")
        }
        binding.btn6.setOnClickListener {
            binding.tvInput.text = addToInputText("6")
        }
        binding.btn7.setOnClickListener {
            binding.tvInput.text = addToInputText("7")
        }
        binding.btn8.setOnClickListener {
            binding.tvInput.text = addToInputText("8")
        }
        binding.btn9.setOnClickListener {
            binding.tvInput.text = addToInputText("9")
        }
        binding.btnCong.setOnClickListener {
            binding.tvInput.text = addToInputText("+")
        }
        binding.btnTru.setOnClickListener {
            binding.tvInput.text = addToInputText("-")
        }
        binding.btnNhan.setOnClickListener {
            binding.tvInput.text = addToInputText("x")
        }
        binding.btnChia.setOnClickListener {
            binding.tvInput.text = addToInputText("/")
        }
        binding.btnBang.setOnClickListener {
            showResult()
        }
        binding.btnCE.setOnClickListener {
            currentInput = "0"
            updateInputDisplay()
        }
        binding.btnC.setOnClickListener {
            currentInput = ""
            currentOperator = null
            updateInputDisplay()
        }
        binding.btnBS.setOnClickListener {
            if (binding.tvInput.text.isNotEmpty()) {
                val currentText = binding.tvInput.text.toString()
                val modifiedText = if (currentText.length > 1) {
                    currentText.substring(0, currentText.length - 1)
                } else {
                    "0"
                }
                binding.tvInput.text = modifiedText
            }
        }
    }

    fun updateInputDisplay() {
        binding.tvInput.text = currentInput
    }

    private fun showResult() {
        // Lấy chuỗi biểu thức nhập từ người dùng
        val expression = binding.tvInput.text.toString()

        try {
            val result = evaluateExpression(expression)
            binding.tvInput.text = result.toString()
        } catch (e: Exception) {
            binding.tvInput.text = "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        // Tạo danh sách lưu trữ số và toán tử
        val numbers = mutableListOf<Double>()
        val operators = mutableListOf<Char>()

        var currentNumber = StringBuilder()

        for (char in expression) {
            if (char.isDigit() || char == '.') {
                currentNumber.append(char)
            } else if (char == '+' || char == '-') {
                if (currentNumber.isNotEmpty()) {
                    numbers.add(currentNumber.toString().toDouble())
                    currentNumber.clear()
                }
                while (operators.isNotEmpty() && (operators.last() == '+' || operators.last() == '-' || operators.last() == 'x' || operators.last() == '/')) {
                    performOperation(numbers, operators)
                }
                operators.add(char)
            } else if (char == 'x' || char == '/') {
                if (currentNumber.isNotEmpty()) {
                    numbers.add(currentNumber.toString().toDouble())
                    currentNumber.clear()
                }
                while (operators.isNotEmpty() && (operators.last() == 'x' || operators.last() == '/')) {
                    performOperation(numbers, operators)
                }
                operators.add(char)
            }
        }

        if (currentNumber.isNotEmpty()) {
            numbers.add(currentNumber.toString().toDouble())
        }

        while (operators.isNotEmpty()) {
            performOperation(numbers, operators)
        }

        return if (numbers.isNotEmpty()) numbers[0] else 0.0
    }

    private fun performOperation(numbers: MutableList<Double>, operators: MutableList<Char>) {
        val operator = operators.removeAt(operators.size - 1)
        val number2 = numbers.removeAt(numbers.size - 1)
        val number1 = numbers.removeAt(numbers.size - 1)
        when (operator) {
            '+' -> numbers.add(number1 + number2)
            '-' -> numbers.add(number1 - number2)
            'x' -> numbers.add(number1 * number2)
            '/' -> {
                if (number2 != 0.0) {
                    numbers.add(number1 / number2)
                } else {
                    throw ArithmeticException("Division by zero")
                }
            }
        }
    }
}