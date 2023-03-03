package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityFindAccountBinding
import kr.co.bsd.stockgame.databinding.ActivityRegisterBinding

class FindAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            val database =
                Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
            val usersRef = database.getReference("users")

            if(email.isNotEmpty()) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usersRef.orderByChild("email").equalTo(email).limitToFirst(200).get().addOnSuccessListener {
                        if(!it.exists()) {
                            Toast.makeText(
                                baseContext,
                                "해당 이메일로 가입된 계정이 없습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        else {
                            val intent = Intent(this, EmailVerifyActivity::class.java)
                            intent.putExtra("email", email)
                            intent.putExtra("purpose", "findId")
                            startActivity(intent)
                        }
                    }
                }
                else {
                    Toast.makeText(
                        baseContext,
                        "이메일 형식이 아닙니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            else {
                Toast.makeText(
                    baseContext,
                    "이메일이 비어있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.passwordButton.setOnClickListener {

            val email = binding.emailEditText.text.toString()

            val database =
                Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
            val usersRef = database.getReference("users")

            if (email.isNotEmpty()) {
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usersRef.orderByChild("email").equalTo(email).limitToFirst(200).get()
                        .addOnSuccessListener {
                            if (!it.exists()) {
                                Toast.makeText(
                                    baseContext,
                                    "해당 이메일로 가입된 계정이 없습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val intent = Intent(this, EmailVerifyActivity::class.java)
                                intent.putExtra("email", email)
                                intent.putExtra("purpose", "findPassword")
                                startActivity(intent)
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext,
                        "이메일 형식이 아닙니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    baseContext,
                    "이메일이 비어있습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}