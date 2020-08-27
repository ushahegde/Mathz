package com.hegdeapps.mathz


import android.app.Dialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hegdeapps.mathz.databinding.ActivityMathQuizBinding
import kotlinx.android.synthetic.main.activity_math_quiz.view.*
import java.security.SecureRandom
import java.util.*

class MathQuiz : AppCompatActivity(), View.OnClickListener {



    private lateinit var quizViewModel: QuizViewModel
    private lateinit var databinding: ActivityMathQuizBinding
    private val SCORE: String = "Score"
    private val CORRECT_ANSWER_NUMBER: String = "Correct answer number"
    private val ANSWER1: String = "Answer1"
    private val ANSWER2: String = "Answer2"
    private val ANSWER3: String = "Answer3"
    private val ANSWER4: String = "Answer4"
    private val QUESTION: String = "Question"
    private val QUESTION_NUM: String = "Question Number"
    private val LEVEL: String = "level"
    private val MIN_REQD = 3
    private val NUM_QNS = 10
    private lateinit var layout: RelativeLayout
    //  private lateinit var   tvLevel: TextView
    private var mNumCorr: Int = 0
  //  private lateinit var tvMessage: TextView

    private lateinit var mTypeface: Typeface
    private var mCorrectAnswerNum: Int = 0
    private lateinit var mRandom: SecureRandom


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this,R.layout.activity_math_quiz)
        quizViewModel = ViewModelProviders.of(this).get(QuizViewModel::class.java)

        //setContentView(R.layout.activity_math_quiz)
        /*tvQuestion = findViewById<TextView>(R.id.question)

        tvQuestionNum = findViewById(R.id.qnNum)*/
        //  tvScore = findViewById(R.id.score)
        //  tvLevel = findViewById(R.id.level)

      /*  btnAns1 = findViewById<Button>(R.id.ans1)
        btnAns2 = findViewById<Button>(R.id.ans2)
        btnAns3 = findViewById<Button>(R.id.ans3)
        btnAns4 = findViewById<Button>(R.id.ans4)*/
        mTypeface = Typeface.createFromAsset(this.assets, "poets.ttf")
        databinding.ans1.setOnClickListener(this)
        databinding.ans2.setOnClickListener(this)
        databinding.ans3.setOnClickListener(this)
        databinding.ans4.setOnClickListener(this)

        with(databinding){
            ans1.gravity = Gravity.CENTER
            ans1.typeface = mTypeface
            ans2.gravity = Gravity.CENTER
            ans2.typeface = mTypeface
            ans3.gravity = Gravity.CENTER
            ans3.typeface = mTypeface

            ans4.gravity = Gravity.CENTER
            ans4.typeface = mTypeface
        }
       /* btnAns1.setOnClickListener(this)
        btnAns2.setOnClickListener(this)
        btnAns3.setOnClickListener(this)
        btnAns4.setOnClickListener(this)*/

       /* with (btnAns1){gravity=Gravity.CENTER}
        with (btnAns2){gravity=Gravity.CENTER}
        with (btnAns3){gravity=Gravity.CENTER}
        with (btnAns4){gravity=Gravity.CENTER}
*/

      //  mTypeface = Typeface.createFromAsset(this.assets, "poets.ttf")

        databinding.question.typeface = mTypeface
       // tvQuestion.typeface = mTypeface

      //  var messageTypeface: Typeface = mTypeface
        // Typeface.createFromAsset(this.assets,"sanchez.otf")

        databinding.message.typeface = mTypeface
        databinding.message.visibility = View.INVISIBLE
       /* tvMessage = findViewById<TextView>(R.id.message)
        tvMessage.typeface = messageTypeface
        tvMessage.visibility = View.INVISIBLE*/


        databinding.qnNum.typeface = mTypeface



            createQuestion()


    }

    private fun createQuestion() {

     //   tvMessage.visibility = View.INVISIBLE
        mRandom = SecureRandom();
        mRandom.setSeed(Calendar.getInstance().timeInMillis)
        val qn  = quizViewModel.createQuestion()
        showQuestion(qn)


    }

    private fun showQuestion(qn: Question) {
        databinding.message.visibility = View.INVISIBLE
        databinding.qnNum.text = quizViewModel.mQuestionNum.value.toString()+"/"+quizViewModel.NUM_QNS.toString()
        databinding.question.text = qn.questionText
        databinding.ans1.text = qn.ans1
        databinding.ans2.text = qn.ans2
        databinding.ans3.text = qn.ans3
        databinding.ans4.text = qn.ans4
        mCorrectAnswerNum = qn.correctAnswerNum
    }

    private fun showQuizEndMessage() {
        var d: Dialog = Dialog(this)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        d.setContentView(R.layout.quizendlayout)

        var yesbtn = d.findViewById<Button>(R.id.yesbutton)
        var nobtn = d.findViewById<Button>(R.id.nobutton)

        var message = d.findViewById<TextView>(R.id.msg)
        message.typeface = mTypeface
        yesbtn.setOnClickListener() {
            d.dismiss()
            restartGame()
        }
        nobtn.setOnClickListener() {
            d.dismiss()
            finish()
        }
        d.show()
    }

    private fun restartGame() {
        quizViewModel.clearCounters()
        createQuestion()
    }



    override fun onClick(view: View) {
        var id: Int = view.id;
        when (id) {
            R.id.ans1,
            R.id.ans2,
            R.id.ans3,
            R.id.ans4 -> checkAnswer(view as TextView)

        }
    }


    private fun checkAnswer(view: TextView) {

              databinding.message.visibility = View.VISIBLE
            val str = quizViewModel.checkAnswer(view,mCorrectAnswerNum)
            if(str.equals(quizViewModel.CORRECT_STRING)){

                databinding.message.text = "Correct !!!"
                var green = resources.getColor(R.color.green)
                databinding.message.setTextColor(green)

            } else {

                databinding.message.text = "Wrong!"
                var red = resources.getColor(R.color.red)
                databinding.message.setTextColor(red)
            }

        createDelay()
    }




    private fun createDelay() {

        Thread {
            Thread.sleep(1000)
            runOnUiThread {
                startNextQuiz()
            }
        }.start()
    }

    private fun startNextQuiz() {

        val str = quizViewModel.startNextQuiz()
        when(str){
            quizViewModel.QUIZ_END->showQuizEndMessage()
            quizViewModel.LEVEL_NOT_CLEARED->showMessage(str)
            quizViewModel.LEVEL_CLEARED->showMessage(str)
            else->
                createQuestion()
        }


        }



    private fun showMessage(s: String) {
        var d = Dialog(this)
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)

        d.setContentView(R.layout.messagelayout)

        val tv: TextView = d.findViewById(R.id.message)
        val btn: Button = d.findViewById(R.id.okbutton)
        // d.setTitle("Mathz")
        tv.text = s
        tv.typeface = mTypeface

        val tv2: TextView = d.findViewById(R.id.yourscore)
        tv2.typeface = mTypeface
        val score = quizViewModel.mScore.value
        tv2.text = "Your score is $score"

        btn.typeface = mTypeface
        btn.setOnClickListener {
            d.dismiss()
            createQuestion()
        }

        d.show()
    }




}
