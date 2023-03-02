package kr.co.bsd.stockgame

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    var isIdTested : Boolean = false
    var isEmailTested : Boolean = false
    var isPasswordTested : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idEditText.addTextChangedListener {
            isIdTested = false
            binding.checkId.setImageResource(R.drawable.cross)
        }

        binding.emailEditText.addTextChangedListener {
            val email = binding.emailEditText.text.toString()

            if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.checkEmail.setImageResource(R.drawable.circle)
                isEmailTested = true
            }
            else {
                binding.checkEmail.setImageResource(R.drawable.cross)
                isEmailTested = false
            }
        }

        binding.passwordEditText.addTextChangedListener {
            val password = binding.passwordEditText.text.toString()

            if(isPasswordFormat(password)){
                binding.checkPassword.setImageResource(R.drawable.circle)
                isPasswordTested = true
            }
            else {
                binding.checkPassword.setImageResource(R.drawable.cross)
                isPasswordTested = false
            }
        }

        binding.idTestButton.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val database =
                Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
            val usersRef = database.getReference("users")

            if (id.isNotEmpty()) {
                usersRef.child(id).get().addOnSuccessListener {
                    if (it.exists()) {
                        Toast.makeText(
                            baseContext,
                            "중복되는 아이디가 있습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else {
                        Toast.makeText(
                            baseContext,
                            "사용 가능한 아이디입니다.",
                            Toast.LENGTH_LONG
                        ).show()
                        isIdTested = true
                        binding.checkId.setImageResource(R.drawable.circle)
                    }
                }
            }
        }
        binding.registerButton.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            val database =
                Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
            val usersRef = database.getReference("users")
            if (id.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if(isIdTested && isEmailTested && isPasswordTested) {
                    usersRef.orderByChild("email").equalTo(email).limitToFirst(200).get().addOnSuccessListener {
                        if(!it.exists()) {
                            val intent = Intent(this, EmailVerifyActivity::class.java)
                            intent.putExtra("email", email)
                            intent.putExtra("id", id)
                            intent.putExtra("password", password)
                            intent.putExtra("purpose", "register")
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(
                                baseContext,
                                "이미 등록된 이메일입니다.",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
                }
            }
        }
    }
    fun isPasswordFormat(password: String): Boolean {
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$".toRegex())
    }

}