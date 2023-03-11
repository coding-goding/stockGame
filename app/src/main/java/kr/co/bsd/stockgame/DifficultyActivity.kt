package kr.co.bsd.stockgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kr.co.bsd.stockgame.databinding.ActivityDifficultyBinding
import kotlin.math.max

class DifficultyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDifficultyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDifficultyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var difficulty = 1

        val textArray = arrayOf("           ", "평화로운 세계", "일반적인 세계", "격동하는 세계", "멸망하는 세계", "           ")
        val minArray = arrayOf(100, 500, 1000, 10000)
        val maxArray = arrayOf(500, 1000, 5000, 100000000)
        val iconArray = arrayOf(R.drawable.icon_easy, R.drawable.icon_medium, R.drawable.icon_hard, R.drawable.icon_hardcore)

        binding.leftSelect.text = textArray[difficulty - 1]
        binding.midSelect.text = textArray[difficulty]
        binding.rightSelect.text = textArray[difficulty + 1]
        binding.difficultyIcon.setImageResource(iconArray[difficulty - 1])
        binding.moneyRange.text = "가용 자본: " + minArray[difficulty - 1] + "만원 ~ " + maxArray[difficulty - 1] + "만원"

        binding.leftButton.setOnClickListener {
            if(difficulty != 1) {
                difficulty -= 1
                binding.leftSelect.text = textArray[difficulty - 1]
                binding.midSelect.text = textArray[difficulty]
                binding.rightSelect.text = textArray[difficulty + 1]
                binding.difficultyIcon.setImageResource(iconArray[difficulty - 1])
                binding.moneyRange.text = "가용 자본: " + minArray[difficulty - 1] + "만원 ~ " + maxArray[difficulty - 1] + "만원"
                if(difficulty == 4) {
                    binding.moneyRange.text = "가용 자본: 1억 이상"
                }
            }
        }
        binding.rightButton.setOnClickListener {
            if(difficulty != 4) {
                difficulty += 1
                binding.leftSelect.text = textArray[difficulty - 1]
                binding.midSelect.text = textArray[difficulty]
                binding.rightSelect.text = textArray[difficulty + 1]
                binding.difficultyIcon.setImageResource(iconArray[difficulty - 1])
                binding.moneyRange.text = "가용 자본: " + minArray[difficulty - 1] + "만원 ~ " + maxArray[difficulty - 1] + "만원"
                if(difficulty == 4) {
                    binding.moneyRange.text = "가용 자본: 1억 이상"
                }
            }
        }


    }
}