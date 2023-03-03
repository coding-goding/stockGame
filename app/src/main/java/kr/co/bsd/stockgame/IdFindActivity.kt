package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kr.co.bsd.stockgame.databinding.ActivityIdFindBinding
import kotlin.reflect.typeOf

class IdFindActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIdFindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIdFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database =
            Firebase.database("https://stockgame-e7286-default-rtdb.asia-southeast1.firebasedatabase.app")
        val usersRef = database.getReference("users")

        val email = intent.getStringExtra("email")
        val query = usersRef.orderByChild("email").equalTo(email)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val id = snapshot.child("id").value.toString()
                    binding.idText.text = id
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException())
            }
        })
        binding.returnButton.setOnClickListener {
            finish()
        }
    }
}