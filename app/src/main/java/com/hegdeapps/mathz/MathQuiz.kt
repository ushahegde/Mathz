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

class MathQuiz : AppCompatActivity(), View.OnClickListener, Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {

           }

    override fun onAnimationStart(animation: Animation?) {
      //  clearTextFields()
            }


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
    /*private lateinit var btnAns2: Button
    private lateinit var btnAns1: Button
    private lateinit var btnAns3: Button
    private lateinit var btnAns4: Button
    private lateinit var tvQuestion: TextView
    private lateinit var tvQuestionNum: TextView*/
    //  private lateinit var tvScore:TextView


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
        /*tvQuestionNum.typeface = messageTypeface
        //   tvScore.typeface = messageTypeface
        btnAns1.typeface = messageTypeface
        btnAns2.typeface = messageTypeface
        btnAns3.typeface = messageTypeface
        btnAns4.typeface = messageTypeface*/



        layout = findViewById<RelativeLayout>(R.id.mainlayout)
       /* if (savedInstanceState != null) {
            mLevel = savedInstanceState.getInt(LEVEL)
            mQuestionNum = savedInstanceState.getInt(QUESTION_NUM)
            var question: String = savedInstanceState.getString(QUESTION, "")
            var ans1: String = savedInstanceState.getString(ANSWER1, "")
            var ans2: String = savedInstanceState.getString(ANSWER2, "")
            var ans3: String = savedInstanceState.getString(ANSWER3, "")
            var ans4: String = savedInstanceState.getString(ANSWER4, "")
            mCorrectAnswerNum = savedInstanceState.getInt(CORRECT_ANSWER_NUMBER,0)
            mScore = savedInstanceState.getInt(SCORE,0)
            showSavedQuestion(question, ans1, ans2, ans3, ans4)
        } else {*/
            createQuestion()
      /*  }*/

    }
/*

    override fun onSaveInstanceState(outState: Bundle ) {

        outState?.run {
            putInt(QUESTION_NUM, mQuestionNum)
             putString(QUESTION, tvQuestion.getText().toString())
             putString(ANSWER1, btnAns1.getText().toString().trim() )
             putString(ANSWER2, btnAns2.getText().toString().trim())
             putString(ANSWER3, btnAns3.getText().toString().trim())
             putString(ANSWER4, btnAns4.getText().toString().trim())
            putInt(CORRECT_ANSWER_NUMBER, mCorrectAnswerNum)
            putInt(SCORE, mScore)
            putInt(LEVEL, mLevel)
        }



        super.onSaveInstanceState(outState)

    }
*/

  /*  private fun showSavedQuestion(question: String, ans1: String, ans2: String, ans3: String, ans4: String) {
        tvQuestion.text = question
        btnAns1.text = ans1.trim()
        btnAns2.text = ans2.trim()
        btnAns3.text = ans3.trim()
        btnAns4.text = ans4.trim()
        tvQuestionNum.text = "Question "+mQuestionNum.toString()+" of 10 "
    }
*/
    private fun createQuestion() {

     //   tvMessage.visibility = View.INVISIBLE
        mRandom = SecureRandom();
        mRandom.setSeed(Calendar.getInstance().timeInMillis)
        val qn  = quizViewModel.createQuestion()
        showQuestion(qn)
       // tvQuestionNum.text ="Question "+ mQuestionNum.toString() + " of 10"
        /*if (mLevel == 1) {
            createSimpleArithQuestion()
            // createWordQuestion()
        } else if (mLevel == 2) {
            createOptionedQuestion()
        } else if (mLevel == 3) {
            createFillInQuestion()
        } else if (mLevel == 4) {
            createLargestSmallestQuestion()
        } else if (mLevel == 5) {
            createWordQuestion()
        }*/

    }

    private fun showQuestion(qn: Question) {
        databinding.message.visibility = View.INVISIBLE
        databinding.qnNum.text = quizViewModel.mQuestionNum.toString()+"/"+quizViewModel.NUM_QNS.toString()
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

 /*   private fun createWordQuestion() {
    //    layout.setBackgroundResource(R.drawable.greengradient)
        var desArray: Array<String> = arrayOf("Half of a number plus ", "Thrice the number minus",
                "Twice the number plus half the number  ",
                "Twice the number plus ", "Square of the number minus ",
                "Number plus half of the number ")

        var m: Int = mRandom.nextInt(desArray.size)
        var num: Int = mRandom.nextInt(40) + 3
        var questionString: String = ""
        var answer: Int = 0
        var num2: Int = 0
        var qnPre: String = desArray[m]
        if (m == 0) {
            var num3 = mRandom.nextInt(40) + 5
            qnPre = qnPre + " " + num3.toString()
            if (num % 2 != 0) {
                num++
            }
            num2 = num / 2 + num3


            answer = num2
        } else if (m == 1) {
            num2 = num * 3
            var num3 = mRandom.nextInt(20) + 5
            if (num3 > num2)
                num3 = num3 / 2
            qnPre = qnPre + " " + num3.toString()
            answer = num2 - num3

        } else if (m == 2) {
            if (num % 2 != 0) {
                num++
            }
            num2 = num * 2 + num / 2
            answer = num2
        } else if (m == 3) {
            var num3 = mRandom.nextInt(40) + 5
            qnPre = qnPre + " " + num3.toString()
            num2 = num * 2 + num3
            answer = num2
        } else if (m == 4) {
            var num3 = mRandom.nextInt(30) + 6
            qnPre = qnPre + " " + num3.toString()
            num2 = num * num - num3
            answer = num2
        } else if (m == 5) {
            if (num % 2 == 1) {
                num++
            }
            num2 = num + num / 2
            answer = num2

        }
    //    clearScreen()
        questionString = "If " + qnPre + " is " + answer.toString() + " then the number is ?"
        tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        tvQuestion.text = questionString
        var a = num + 10
        var b = num - 1
        var c = num + 3
        var d = num + 2
        mCorrectAnswerNum = mRandom.nextInt(4)
      //  hideButtons(false)
        btnAns1.text = a.toString().trim()
        btnAns2.text = b.toString().trim()
        btnAns3.text = c.toString().trim()
        btnAns4.text = d.toString().trim()
        when (mCorrectAnswerNum) {
            0 -> btnAns1.text = num.toString().trim()
            1 -> btnAns2.text = num.toString().trim()
            2 -> btnAns3.text = num.toString().trim()
            3 -> btnAns4.text = num.toString().trim()
        }
        mAnswer = num


    }*/

   /* private fun createLargestSmallestQuestion() {
        //     setTheme(R.style.PurpleTheme)
    //    layout.setBackgroundResource(R.drawable.bluegradient)
        var operator: Int = mOprType[mRandom.nextInt(mOprType.size)]
        var expressions: Array<String> = arrayOf("", "", "", "")
        var answers: Array<Int> = arrayOf(0, 0, 0, 0)
        for (i in 0..3) {
            var opr = mOprType[mRandom.nextInt(mOprType.size)]

            var a: Int = mRandom.nextInt(20) + 2
            var b: Int = mRandom.nextInt(20) + 3
            var c: Int = mRandom.nextInt(20) + 4
            var d: Int = 1
            var exp: String = ""
            var operators: Array<String> = arrayOf("+", "+")

            when (opr) {
                PLUS_PLUS -> {
                    d = a + b + c;operators[0] = "+";operators[1] = "+"
                }
                PLUS_MINUS -> {
                    d = a + b - c; operators[0] = "+";operators[1] = "-"
                }
                PLUS_STAR -> {
                    d = a + b * c
                    operators[0] = "+"
                    operators[1] = "X"
                }
                STAR_MINUS -> {
                    d = a * b + c
                    operators[0] = "X"
                    operators[1] = "-"
                }
                STAR_PLUS -> {
                    d = a * b + c
                    operators[0] = "X"
                    operators[1] = "+"
                }
            }
            expressions[i] = a.toString() + operators[0] + b.toString() + operators[1] + c.toString()
            answers[i] = d
        }
       // clearScreen()
        var typeOfQuestion = mRandom.nextInt(2)
        if (typeOfQuestion == 0) {
            var largestIndex: Int = findLargest(answers)
            mCorrectAnswerNum = largestIndex
        } else {
            var smallestIndex: Int = findSmallestIndex(answers)
            mCorrectAnswerNum = smallestIndex
        }
       // hideButtons(false)
        btnAns1.text = expressions[0]
        btnAns2.text = expressions[1]
        btnAns3.text = expressions[2]
        btnAns4.text = expressions[3]
        if (typeOfQuestion == 0)
            tvQuestion.text = " Largest = ?"
        else
            tvQuestion.text = " Smallest = ?"


    }*/
/*
    private fun findSmallestIndex(num: Array<Int>): Int {
        var smallest: Int = 0;
        if (num[1] < num[smallest])
            smallest = 1
        if (num[2] < num[smallest])
            smallest = 2
        if (num[3] < num[smallest])
            smallest = 3
        return smallest
    }

    private fun findLargest(num: Array<Int>): Int {
        var large: Int = 0;
        if (num[1] > num[large])
            large = 1
        if (num[2] > num[large])
            large = 2
        if (num[3] > num[large])
            large = 3
        return large


    }*/
/*

    private fun createFillInQuestion() {
     //   layout.setBackgroundResource(R.drawable.orangegradient)
        //   setTheme(R.style.GreenTheme)
        var operatorIndex: Int = mRandom.nextInt(4)
        var operator: String = operatorArr[operatorIndex]
        var maxNum = 0
        var smallNum = 0
        if (operator == "+" || operator == "-") {
            maxNum = 100
            smallNum = 100
        } else {
            maxNum = 40
            smallNum = 25
        }
        var num1: Int = mRandom.nextInt(maxNum) + 3
        var num2: Int = mRandom.nextInt(smallNum) + 2


        if (num1 < num2) {
            var temp: Int = num1
            num1 = num2
            num2 = temp
        } else if (num1 == num2) {
            num1 += mRandom.nextInt(40) + 7
        }
        var answer: Int = 0
        when (operator) {
            "+" -> answer = num1 + num2
            "-" -> answer = num1 - num2
            "X" -> answer = num1 * num2
            "/" -> {
                num1 = num2 * (mRandom.nextInt(20) + 2)
                answer = num1 / num2
            }
        }
        var wrongAnswer1: Int = num2 + 2
        var wrongAnswer2: Int = num2 - 1
        var wrongAnswer3: Int = num2 + 5
        mAnswer = num2
     //   clearScreen()
        tvQuestion.setText(num1.toString() + " " + operator + " ?= " + answer.toString())
        // tvQuestionNum.setText("Qn#"+mQuestionNum.toString())
        showAnswers(wrongAnswer1, wrongAnswer2, wrongAnswer3)
    }

    private fun createOptionedQuestion() {

       // layout.setBackgroundResource(R.drawable.greengradient)
        //  setTheme(R.style.GreenTheme)
        mAnswer = mRandom.nextInt(60) + 15
        val typeOfQuestion = mRandom.nextInt(2)


        var num1: Int = mRandom.nextInt(mAnswer) + 4
        var numAns1: Int = 0
        var ansString: String = ""
        if (typeOfQuestion == 0) {
            numAns1 = mAnswer - num1
            ansString = num1.toString() + " + " + numAns1.toString()
        } else {
            var num2 = mRandom.nextInt(10) + 5
            mAnswer = num1 * num2
            ansString = num1.toString() + " X " + num2.toString()
        }
        */
/*  var numAns2 = numAns1+5
          var numAns3 = numAns1 -2
          var numAns4 = numAns1+3*//*


        var num2 = mRandom.nextInt(mAnswer) + 2
        var temp = mAnswer - num2 + 10
        var wrongAnswerString1: String = num2.toString() + " + " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 3
        temp = mAnswer - num2 + 4
        var wrongAnswerString2: String = num2.toString() + " + " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 4
        temp = mAnswer / num2 + 1
        var wrongAnswerString3: String = num2.toString() + " X " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 5
        temp = mAnswer - num2 - 1
        var wrongAnswerString4: String = num2.toString() + " + " + temp.toString()
     //   clearScreen()
        tvQuestion.setText(mAnswer.toString() + "=?")
        //  tvQuestionNum.text = "Qn#"+mQuestionNum.toString()
        showLongerAnswers(ansString, wrongAnswerString1, wrongAnswerString2, wrongAnswerString3, wrongAnswerString4)
    }

    private fun showLongerAnswers(ansString: String, wrongAnswerString1: String, wrongAnswerString2: String, wrongAnswerString3: String, wrongAnswerString4: String) {
     //   hideButtons(false)
        btnAns1.setText(wrongAnswerString1)
        btnAns2.setText(wrongAnswerString2)
        btnAns3.setText(wrongAnswerString3)
        btnAns4.setText(wrongAnswerString4)
        var correctAnswer: Int = mRandom.nextInt(4)
        mCorrectAnswerNum = correctAnswer
        when (correctAnswer) {
            0 -> btnAns1.text = ansString
            1 -> btnAns2.text = ansString
            2 -> btnAns3.text = ansString
            3 -> btnAns4.text = ansString
        }
        // tvQuestionNum.text = "Qn#" + mQuestionNum.toString()
    }

    private fun createSimpleArithQuestion() {
        //    setTheme(R.style.PurpleTheme)
      //  clearScreen()
        var operatorIndex: Int = mRandom.nextInt(4)
        var operator: String = operatorArr[operatorIndex]
        var maxNum = 0
        var smallNum = 0

        var offset: Int = 0;
        if (operator == "+" || operator == "-") {
            maxNum = 100
            smallNum = 100
            offset = 45

        } else {
            maxNum = 40
            smallNum = 14
            offset = 6
        }
        var num1: Int = mRandom.nextInt(maxNum) + offset
        var num2: Int = mRandom.nextInt(smallNum) + offset
        if (num1 == num2) {
            num1 = num1 + mRandom.nextInt(10) + 5
        }
        if (num1 < num2) {
            var temp: Int = num1
            num1 = num2
            num2 = temp
        }
        when (operator) {
            "+" -> mAnswer = num1 + num2
            "-" -> mAnswer = num1 - num2
            "X" -> mAnswer = num1 * num2
            "/" -> {
                num1 = num2 * (mRandom.nextInt(20) + 2)
                mAnswer = num1 / num2
            }
        }
        var wrongAnswer1: Int = mAnswer + 3
        var wrongAnswer2: Int = mAnswer - 10
        if (wrongAnswer2 < 0) {
            wrongAnswer2 = mAnswer + 20
        }
        var wrongAnswer3: Int = mAnswer + 10
    //    clearScreen()
        showAnswers(wrongAnswer1, wrongAnswer2, wrongAnswer3)
        tvQuestion.setText(num1.toString() + " " + operator + " " + num2.toString() + "=?")


    }

    private fun showAnswers(wrongAnswer1: Int, wrongAnswer2: Int, wrongAnswer3: Int) {
        var wrongAnswer4: Int = wrongAnswer1 + 6
        //hideButtons(false)


        btnAns1.text = wrongAnswer1.toString().trim()
        btnAns2.text = wrongAnswer2.toString().trim()
        btnAns3.text = wrongAnswer3.toString().trim()
        btnAns4.text = wrongAnswer4.toString().trim()


        var correctAnswer: Int = mRandom.nextInt(4)
        mCorrectAnswerNum = correctAnswer
        when (correctAnswer) {
            0 -> btnAns1.text = mAnswer.toString().trim()
            1 -> btnAns2.text = mAnswer.toString().trim()
            2 -> btnAns3.text = mAnswer.toString().trim()
            3 -> btnAns4.text = mAnswer.toString().trim()
        }

    }
*/

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
              //  mScore = mScore + 1000
                //     tvScore.setText("Score:" + mScore.toString())
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
        showAnimation()
        val str = quizViewModel.startNextQuiz()
        when(str){
            quizViewModel.QUIZ_END->showQuizEndMessage()
            quizViewModel.LEVEL_NOT_CLEARED->showMessage(str)
            quizViewModel.LEVEL_CLEARED->showMessage(str)
            else->
                createQuestion()
        }

      /*  if (mQuestionNum > NUM_QNS && mLevel == 5) {
            showQuizEndMessage();
        } else {
            if (mQuestionNum > NUM_QNS) {

                if (mNumCorr < MIN_REQD) {
                    showMessage("Sorry. Level not cleared")
                    mQuestionNum = 1
                    mNumCorr = 0
                } else {
                    //  tvLevel
                    mLevel++;
                    showMessage("Level Cleared!!!")

                    mQuestionNum = 1;
                    mNumCorr = 0
                    // tvLevel.text = "L:"+mLevel.toString()
                }
               // clearScreen()
            } else {

                createQuestion()
            }*/
        }


    private fun showAnimation() {
        var anim: AlphaAnimation = AlphaAnimation(0.1f,1.0f)


               /* 0.1f,1.0f,0.1f,1.0f,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f)
*/

        anim.duration = 1700
        databinding.qnanswerLayout.animation = anim
    //    var qnAnswerLayout:LinearLayout = findViewById(R.id.qnanswerLayout)
   //     qnAnswerLayout.animation = anim


        anim.setAnimationListener(this)


        anim.start()

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
        val score = quizViewModel.mScore
        tv2.text = "Your score is $score"

        btn.typeface = mTypeface
        btn.setOnClickListener {
            d.dismiss()
            createQuestion()
        }

        d.show()
    }

   /* private fun clearScreen() {
        tvQuestion.text = ""
        tvQuestionNum.text = ""
        btnAns1.text = ""
        btnAns2.text = ""
        btnAns2.text = ""
        btnAns3.text = ""
        showAnimation()
        *//*  btnAns1.setText("")
           btnAns2.setText("")
           btnAns3.setText("")
           btnAns4.setText("")*//*
    }*/
    override fun onAnimationEnd(animation: Animation?) {
      //  clearTextFields()
            }

   /* private fun hideButtons(b: Boolean) {
        var visibility = if(b) View.INVISIBLE else View.VISIBLE

            btnAns1.visibility = visibility
            btnAns2.visibility = visibility
            btnAns3.visibility = visibility
            btnAns4.visibility = visibility


    }*/
  /*  private fun clearTextFields() {
        tvQuestion.text = ""
        tvQuestionNum.text = ""
      *//*  btnAns1.text = ""
        btnAns2.text = ""
        btnAns4.text = ""
        btnAns3.text = ""*//*
     //   hideButtons(true)

             }*/
}
