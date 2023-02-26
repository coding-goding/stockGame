package kr.co.bsd.stockgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kr.co.bsd.stockgame.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // 사용자가 입력한 이메일과 비밀번호 가져오기
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            // 이후 코드 추가...


            Toast.makeText(this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}