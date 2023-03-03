package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityRegisterFinishBinding

class RegisterFinishActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database =
            Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
        val usersRef = database.getReference("users")

        val email = intent.getStringExtra("email")
        val id = intent.getStringExtra("id")
        val password = intent.getStringExtra("password")

        binding.finishButton.setOnClickListener {
            val nickname = binding.nickNameEditText.text.toString()
            if(nickname.isNotEmpty()) {
                if (email != null && id != null && password != null) {
                    val user = User(email, id, password)
                    usersRef.child(id).setValue(user)
                    usersRef.child(id).child("nickname").setValue(nickname)
                    usersRef.child(id).child("money").setValue(1000000)
                    Toast.makeText(
                        baseContext,
                        "회원가입 완료, stocker:주식게임에 오신 걸 환영합니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
        }
    }
}
