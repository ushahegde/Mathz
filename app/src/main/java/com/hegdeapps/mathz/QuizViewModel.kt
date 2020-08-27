package com.hegdeapps.mathz

import android.widget.TextView
import androidx.lifecycle.ViewModel
import java.security.SecureRandom
import java.util.*

class QuizViewModel:ViewModel(){
    val LEVEL_CLEARED: String="Level Completed"
    val QUIZ_END: String="Quiz completed"
    val LEVEL_NOT_CLEARED: String="Sorry. Level not cleared"
    val CORRECT_STRING: String="Correct"
    val WRONG_STRING = "Wrong"
    private val MIN_REQD=5
     val NUM_QNS=10
    private var mNumCorr: Int = 0
    private var mRandom: SecureRandom
    var operatorArr: Array<String> = arrayOf("+", "-", "X", "/")

    private val PLUS_PLUS = 1
    private val PLUS_MINUS = 2
    private val PLUS_STAR = 3
    private val STAR_PLUS = 4
    private val MINUS_MINUS = 5
    private val STAR_MINUS = 6
    val mOprType: Array<Int> = arrayOf(PLUS_PLUS, PLUS_MINUS, MINUS_MINUS, PLUS_STAR, STAR_PLUS, STAR_MINUS)

    private var mLevel = 1
    internal var mScore = 0

    var mAnswer: Int = 0
    var mQuestionNum = 1;

    init{
        mRandom = SecureRandom();
        mRandom.setSeed(Calendar.getInstance().timeInMillis)
    }
     fun createQuestion():Question {

       // tvQuestionNum.text ="Question "+ mQuestionNum.toString() + " of 10"
        if (mLevel == 1) {
            return createSimpleArithQuestion()
            // createWordQuestion()
        } else if (mLevel == 2) {
            return createOptionedQuestion()
        } else if (mLevel == 3) {
            return createFillInQuestion()
        } else if (mLevel == 4) {
            return createLargestSmallestQuestion()
        } else    {
            return createWordQuestion()
        }

    }

   /* private fun showQuizEndMessage() {
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
*/
    private fun restartGame() {
        mLevel = 1
        mScore = 0
        mQuestionNum = 1
        createQuestion()
    }

      fun createWordQuestion():Question{
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

       // tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
       // tvQuestion.text = questionString
        var a = num + 10
        var b = num - 1
        var c = num + 3
        var d = num + 2
        val correctAnswerNum = mRandom.nextInt(4)+1
        when(correctAnswerNum){
            1->a=num
            2->b=num
            3->c = num
            4->d = num
        }
       val qn:Question = Question(questionString,a.toString(),b.toString(),c.toString(),
               d.toString(),correctAnswerNum)
       return qn
        //  hideButtons(false)
      /*  btnAns1.text = a.toString().trim()
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
*/

    }

    private fun createLargestSmallestQuestion():Question {

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
        var correctAnswerNum:Int = 0
        var typeOfQuestion = mRandom.nextInt(2)
        if (typeOfQuestion == 0) {
            var largestIndex: Int = findLargest(answers)
            correctAnswerNum = largestIndex
        } else {
            var smallestIndex: Int = findSmallestIndex(answers)
            correctAnswerNum = smallestIndex
        }
        // hideButtons(false)
        /*btnAns1.text = expressions[0]
        btnAns2.text = expressions[1]
        btnAns3.text = expressions[2]
        btnAns4.text = expressions[3]*/
        val questionText = when(typeOfQuestion) {
            0 ->
                " Largest = ?"
            1 ->
                 " Smallest = ?"
            else ->""
        }
        val qn:Question = Question(questionText, expressions[0],expressions[1],expressions[2],
                expressions[3],correctAnswerNum+1)
        return qn
    }

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


    }

    private fun createFillInQuestion() :Question{
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
        var answer1: Int = num2 + 2
        var answer2: Int = num2 - 1
        var answer3: Int = num2 + 5
        var answer4 = num2-5
        mAnswer = num2
        //   clearScreen()
        val questionText = (num1.toString() + " " + operator + " ?= " + answer.toString())
        var correctAnswer: Int = mRandom.nextInt(4)+1
   //     mCorrectAnswerNum = correctAnswer
        when (correctAnswer) {
            1 -> answer1 = mAnswer
            2 -> answer2= mAnswer
            3 -> answer3 = mAnswer
            4 -> answer4 = mAnswer
        }
        val qn:Question = Question(questionText,answer1.toString(),
                answer2.toString(),answer3.toString(),answer4.toString(),
                correctAnswer)
        return qn
        // tvQuestionNum.setText("Qn#"+mQuestionNum.toString())
      //  showAnswers(wrongAnswer1, wrongAnswer2, wrongAnswer3)
    }

    private fun createOptionedQuestion():Question {

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
        /*  var numAns2 = numAns1+5
          var numAns3 = numAns1 -2
          var numAns4 = numAns1+3*/

        var num2 = mRandom.nextInt(mAnswer) + 2
        var temp = mAnswer - num2 + 10
        var answer1: String = num2.toString() + " + " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 3
        temp = mAnswer - num2 + 4
        var answer2: String = num2.toString() + " + " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 4
        temp = mAnswer / num2 + 1
        var answer3: String = num2.toString() + " X " + temp.toString()
        num2 = mRandom.nextInt(mAnswer) + 5
        temp = mAnswer - num2 - 1
        var answer4: String = num2.toString() + " + " + temp.toString()
        //   clearScreen()
        val questionText = (mAnswer.toString() + "=?")
        var correctAnswer: Int = mRandom.nextInt(4)+1
       // mCorrectAnswerNum = correctAnswer
        when (correctAnswer) {
            1-> answer1= ansString
            2 -> answer2 = ansString
            3 -> answer3 = ansString
            4 -> answer4= ansString
        }
        val qn:Question = Question(questionText,answer1,
                answer2,answer3,answer4,
                correctAnswer)
        return qn
        //  tvQuestionNum.text = "Qn#"+mQuestionNum.toString()
       // showLongerAnswers(ansString, wrongAnswerString1, wrongAnswerString2, wrongAnswerString3, wrongAnswerString4)
    }

   /* private fun showLongerAnswers(ansString: String, wrongAnswerString1: String, wrongAnswerString2: String, wrongAnswerString3: String, wrongAnswerString4: String) {
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
*/
    private fun createSimpleArithQuestion():Question {
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
            maxNum = 25
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
        var wrongAnswer4 = mAnswer-3
        //    clearScreen()
      //  showAnswers(wrongAnswer1, wrongAnswer2, wrongAnswer3)
        val questionText = (num1.toString() + " " + operator + " " + num2.toString() + "=?")
       var correctAnswer: Int = mRandom.nextInt(4)+1
       //     mCorrectAnswerNum = correctAnswer
       when (correctAnswer) {
           1 -> wrongAnswer1 = mAnswer
           2 -> wrongAnswer2= mAnswer
           3 -> wrongAnswer3 = mAnswer
           4 -> wrongAnswer4 = mAnswer
       }
       val qn:Question = Question(questionText,wrongAnswer1.toString(),
               wrongAnswer2.toString(),wrongAnswer3.toString(),wrongAnswer4.toString(),
               correctAnswer)
       return qn

    }

   /* private fun showAnswers(wrongAnswer1: Int, wrongAnswer2: Int, wrongAnswer3: Int) {
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
  /*  override fun onClick(view: View) {
        var id: Int = view.id;
        when (id) {
            R.id.ans1,
            R.id.ans2,
            R.id.ans3,
            R.id.ans4 -> checkAnswer(view as TextView)

        }
    }*/


     fun checkAnswer(view: TextView,correctAnswerNum:Int):String {
        var id: Int = view.id
        var correctAnswer: Boolean = false
        if (id == R.id.ans1 && correctAnswerNum == 1)
            correctAnswer = true
        else if (id == R.id.ans2 && correctAnswerNum == 2)
            correctAnswer = true
        else if (id == R.id.ans3 && correctAnswerNum == 3) {
            correctAnswer = true
        } else if (id == R.id.ans4 && correctAnswerNum == 4)
            correctAnswer = true
        val message:String
        if (correctAnswer) {
            mNumCorr++
           // tvMessage.visibility = View.VISIBLE
            message = CORRECT_STRING//"Correct !!!"
          /*  var green = resources.getColor(R.color.green)
            tvMessage.setTextColor(green)*/
            mScore = mScore + 1000
            //     tvScore.setText("Score:" + mScore.toString())
        } else {
            /*tvMessage.visibility = View.VISIBLE*/
            message=WRONG_STRING// "Wrong!"
          /*  var red = resources.getColor(R.color.red)
            tvMessage.setTextColor(red)

            tvMessage.setTextColor(Color.RED)*/
            /* }*/
        }
        return message


       // createDelay()
    }

   /* private fun createDelay2() {

        Thread {
            Thread.sleep(500)

        }.start()

    }*/

   /* private fun checkLongAnswers(view: TextView) {
        var str = view.text.toString()
        var ans = evaluate(str)
        if (ans == mAnswer) {
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = "Correct!"
            mNumCorr++
            mScore += 1000
        } else {
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = "Wrong!"
        }


    }*/

    //TODO split the array into sub elements and evaluate
   /* private fun evaluate(str: String): Int {
        var opList: List<String> = str.split(" ")
        var n1 = opList.get(0).toInt()
        var n2 = opList.get(2).toInt()
        var operator = opList.get(1)
        var answer: Int = 0
        when (operator) {
            "+" -> answer = n1 + n2
            "-" -> answer = n1 - n2
            "X" -> answer = n1 * n2
        }
        return answer


    }
*/
  /*  private fun createDelay() {

        Thread {
            Thread.sleep(1000)
            runOnUiThread {
                startNextQuiz()
            }
        }.start()
    }*/

      fun startNextQuiz():String {
       // showAnimation()
        mQuestionNum++
        if (mQuestionNum > NUM_QNS && mLevel == 5) {
            return QUIZ_END//"QuizEnd"//showQuizEndMessage();
        } else {
            if (mQuestionNum > NUM_QNS) {

                if (mNumCorr < MIN_REQD) {
                    mQuestionNum = 1
                    mNumCorr = 0
                    return LEVEL_NOT_CLEARED// "Sorry. Level not cleared"

                } else {
                    //  tvLevel
                    mLevel++;


                    mQuestionNum = 1;
                    mNumCorr = 0
                    return   LEVEL_CLEARED// "Level Cleared!!!"
                    // tvLevel.text = "L:"+mLevel.toString()
                }
                // clearScreen()
            } else {
                return "NextQn"
                //createQuestion()
            }
        }
    }

    fun clearCounters() {
        mLevel = 1
        mScore = 0
        mQuestionNum = 1
     }
}