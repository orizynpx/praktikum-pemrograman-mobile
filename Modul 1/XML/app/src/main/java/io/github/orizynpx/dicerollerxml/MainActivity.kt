package io.github.orizynpx.dicerollerxml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var imgDice1: ImageView
    private lateinit var imgDice2: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        imgDice1 = findViewById(R.id.imgDice1)
        imgDice1.setImageResource(R.drawable.dice_0)

        imgDice2 = findViewById(R.id.imgDice2)
        imgDice2.setImageResource(R.drawable.dice_0)

        val btnRoll: Button = findViewById(R.id.btnRoll)
        btnRoll.setOnClickListener { rollDice() }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun rollDice() {
        val result1: Int = (1..6).random()
        val result2: Int = (1..6).random()

        val imageResource1 = when (result1) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
        imgDice1.setImageResource(imageResource1)
        imgDice1.contentDescription = result1.toString()

        val imageResource2 = when (result2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
        imgDice2.setImageResource(imageResource2)
        imgDice2.contentDescription = result2.toString()

        val layout: ConstraintLayout = findViewById(R.id.main)
        val diceMessage = if (result1 == result2) getString(R.string.dice_win_msg) else getString(R.string.dice_lose_msg)

        val snackbar = Snackbar.make(layout, diceMessage, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
}