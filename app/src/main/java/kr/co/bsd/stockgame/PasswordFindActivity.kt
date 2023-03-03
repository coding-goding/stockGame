package kr.co.bsd.stockgame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityFindAccountBinding
import kr.co.bsd.stockgame.databinding.ActivityPasswordFindBinding

class PasswordFindActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordFindBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database =
            Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
        val usersRef = database.getReference("users")

        val email = intent.getStringExtra("email")
        val query = usersRef.orderByChild("email").equalTo(email)

        binding.settingButton.setOnClickListener {
            val id = binding.idEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        if(id == snapshot.child("id").value.toString()) {
                            if(isPasswordFormat(password)) {
                                Toast.makeText(
                                    baseContext,
                                    "재설정 완료!",
                                    Toast.LENGTH_SHORT
                                ).show()
                                usersRef.child(id).child("password").setValue(password)
                                finish()
                            }
                            else {
                                Toast.makeText(
                                    baseContext,
                                    "비밀번호는 숫자,영자,특수문자를 포함한 8~15자가 되어야 합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        else {
                            Toast.makeText(
                                baseContext,
                                "입력된 아이디가 계정 정보와 일치하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("TAG", "Error getting data", databaseError.toException())
                }
            })
        }
    }
    fun isPasswordFormat(password: String): Boolean {
        return password.matches("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&]).{8,15}.\$".toRegex())
    }
}