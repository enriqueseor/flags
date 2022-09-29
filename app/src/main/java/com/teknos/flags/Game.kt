package com.teknos.flags

import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Button
import android.widget.TextView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.teknos.flags.menu.WidthHeight
import com.teknos.flags.database.SetQuestion
import com.teknos.flags.database.DataRetriever
import java.util.ArrayList
import com.teknos.flags.database.Country
import android.view.View
import com.teknos.flags.database.AnswerChecker
import com.teknos.flags.singleton.Singleton
import com.teknos.flags.database.MakeQuestion
import com.teknos.flags.R.drawable
import java.lang.Exception

class Game : AppCompatActivity() {
    private var mode: String? = null
    private var makeQuestion: MakeQuestion? = null
    private var flag: ImageView? = null
    private var choice1: Button? = null
    private var choice2: Button? = null
    private var choice3: Button? = null
    private var choice4: Button? = null
    private var right: TextView? = null
    private var wrong: TextView? = null
    private var choices: LinearLayout? = null
    private var won: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        mode = Singleton.type
        assignVars()
        WidthHeight(this, 355, 40, 700, 60, choice1!!, choice2!!, choice3!!, choice4!!)
        makeQuestion = MakeQuestion(mode!!, "Game")
        SetQuestion(this, flag!!, choice1!!, choice2!!, choice3!!, choice4!!, makeQuestion!!)
    }

    private fun next() {
        Handler(Looper.getMainLooper()).postDelayed(Runnable@{
            if (finished()) return@Runnable
            makeQuestion = MakeQuestion(mode!!, "Game")
            SetQuestion(
                this@Game,
                flag!!,
                choice1!!,
                choice2!!,
                choice3!!,
                choice4!!,
                makeQuestion!!
            )
        }, 1000)
    }

    private fun assignVars() {
        val dataRetriever = DataRetriever(this, mode!!)
        data = dataRetriever.data
        fullData = ArrayList()
        (fullData as ArrayList<Country>).addAll(data!!)
        flag = findViewById(R.id.FlagImage)
        choice1 = findViewById(R.id.c1)
        choice2 = findViewById(R.id.c2)
        choice3 = findViewById(R.id.c3)
        choice4 = findViewById(R.id.c4)
        right = findViewById(R.id.rightCount)
        wrong = findViewById(R.id.wrongCount)
        choices = findViewById(R.id.choicesLayout)
        won = findViewById(R.id.wonLayout)
    }

    private fun finished(): Boolean {
        if (data!!.isEmpty()) {
            choices!!.visibility = View.INVISIBLE
            won!!.visibility = View.VISIBLE
            flag!!.visibility = View.INVISIBLE
            return true
        }
        return false
    }

    fun choice1(view: View?) {
        AnswerChecker(this, choice1, choice2, choice3, choice4, makeQuestion!!, 0, right!!, wrong!!)
        next()
    }

    fun choice2(view: View?) {
        AnswerChecker(this, choice1, choice2, choice3, choice4, makeQuestion!!, 1, right!!, wrong!!)
        next()
    }

    fun choice3(view: View?) {
        AnswerChecker(this, choice1, choice2, choice3, choice4, makeQuestion!!, 2, right!!, wrong!!)
        next()
    }

    fun choice4(view: View?) {
        AnswerChecker(this, choice1, choice2, choice3, choice4, makeQuestion!!, 3, right!!, wrong!!)
        next()
    }

    companion object {
        var data: List<Country>? = null
        var fullData: MutableList<Country>? = null
        fun getResId(resName: String?): Int {
            return try {
                val idField = drawable::class.java.getDeclaredField(resName.toString())
                idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        }
    }
}