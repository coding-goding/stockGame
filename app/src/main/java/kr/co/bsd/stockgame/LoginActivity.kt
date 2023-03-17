package kr.co.bsd.stockgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            // 사용자가 입력한 이메일과 비밀번호 가져오기
            val id = binding.idEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val database =
                Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
            val usersRef = database.getReference("users")

            if (id.isNotEmpty() && password.isNotEmpty()) {
                usersRef.child(id).get().addOnSuccessListener {
                    if (it.exists()) {
                        it.getValue(User::class.java)?.let { user ->
                            if (user.password == password) {
                                Toast.makeText(baseContext, "환영합니다, " + user.nickname + "님.", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                intent.putExtra("id", user.id)
                                finish()
                            }
                            else {
                                Toast.makeText(
                                    baseContext,
                                    "입력된 정보가 계정 정보와 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else {
                        Toast.makeText(
                            baseContext,
                            "입력된 정보가 계정 정보와 일치하지 않습니다.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            else {
                Toast.makeText(baseContext, "모든 정보가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }


        }
        binding.registerTextButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        binding.passwordTextButton.setOnClickListener {
            val intent = Intent(this,FindAccountActivity::class.java)
            startActivity(intent)
        }
    }
}