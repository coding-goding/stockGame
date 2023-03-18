package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val database =
            Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
        val usersRef = database.getReference("users")

        if (id != null) {
            usersRef.child(id).get().addOnSuccessListener {
                if (it.exists()) {
                    it.getValue(User::class.java)?.let { user ->
                        binding.nickname.text = "- 이름 : " + user.nickname
                        binding.email2.text = user.email

                        var userId = user.id
                        var chars = userId.toCharArray()
                        if (userId.length <= 3) {
                            for (i in 1..userId.length-1) {
                                chars[i] = '*'
                            }
                        }
                        else {
                            for (i in 3..userId.length-1) {
                                chars[i] = '*'
                            }
                        }
                        userId = String(chars)

                        var userPassword = user.password
                        chars = userPassword.toCharArray()

                        for (i in 3..userPassword.length-1) {
                            chars[i] = '*'
                        }
                        userPassword = String(chars)

                        binding.id.text = "- 아이디 : " + userId
                        binding.password.text = "- 비밀번호 : " + userPassword

                        binding.money.text = "- 현금 : " + user.money + "원"
                    }
                }
            }
        }
        binding.passwordSetButton.setOnClickListener {
            if (id != null) {
                usersRef.child(id).get().addOnSuccessListener {
                    if (it.exists()) {
                        it.getValue(User::class.java)?.let { user ->
                            val intent = Intent(this, EmailVerifyActivity::class.java)
                            intent.putExtra("email", user.email)
                            intent.putExtra("purpose", "findPassword")
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        }
    }
}