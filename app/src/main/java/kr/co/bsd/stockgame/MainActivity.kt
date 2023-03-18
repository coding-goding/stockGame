package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.bsd.stockgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var backKeyPressTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")

        binding.accountButton.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressTime + 2500) {
            Toast.makeText(
                baseContext,
                "뒤로가기를 한번 더 누르면 종료됩니다.",
                Toast.LENGTH_SHORT
            ).show()
            backKeyPressTime = System.currentTimeMillis()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressTime + 2500) {
            finish()
        }
    }
}