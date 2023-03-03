package kr.co.bsd.stockgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.bsd.stockgame.databinding.ActivityEmailVerifyBinding
import java.util.*
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class EmailVerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmailVerifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val purpose = intent.getStringExtra("purpose")

        val code = email?.let { sendEmail(it) }

        binding.emailText.text = email

        binding.verifyButton.setOnClickListener {
            if(binding.verifyEditText.text.toString() == code) {
                Toast.makeText(
                    baseContext,
                    "인증 성공!",
                    Toast.LENGTH_SHORT
                ).show()

                if(purpose == "register") {
                    val id = intent.getStringExtra("id")
                    val password = intent.getStringExtra("password")
                    val intent = Intent(this, RegisterFinishActivity::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("id", id)
                    intent.putExtra("password", password)
                    startActivity(intent)
                    finish()
                }
                else if(purpose == "findId") {
                    val intent = Intent(this, IdFindActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                }
                else if(purpose == "findPassword") {
                    val intent = Intent(this, PasswordFindActivity::class.java)
                    intent.putExtra("email", email)
                    startActivity(intent)
                    finish()
                }

            }
            else {
                Toast.makeText(
                    baseContext,
                    "인증 코드가 다릅니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun sendEmail(toEmail: String): String {

        val fromEmail = "bsddevelopment2023@gmail.com"
        val userName = "bsddevelopment2023@gmail.com"
        val password = "jylzvmnaihjdwpoe"
        val code = (100000..1000000).random().toString()

        CoroutineScope(Dispatchers.IO).launch {
            val props = Properties()

            props.setProperty("mail.transport.protocol", "smtp")
            props.setProperty("mail.host", "smtp.gmail.com")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.socketFactory.fallback", "false")
            props.put("mail.smtp.ssl.enable", "true")
            props.setProperty("mail.smtp.quitwait", "false")
            val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(userName, password)
                }
            })

            val message = MimeMessage(session)
            message.sender = InternetAddress(fromEmail)
            message.addRecipient(Message.RecipientType.TO, InternetAddress(toEmail))
            message.subject =
                "Stocker:주식게임 이메일 인증 코드"
            message.setText("안녕하세요.\n이 메일은 Stocker:주식게임 이메일 인증을 위해 발송되었습니다.\n\n아래 코드를 입력하여 인증해주세요.\n" + "<" + code + ">\n\n" + "만약 이 주소의 인증을 요청하지 않았다면 이 메일을 무시하셔도 좋습니다.\n\n감사합니다.\n\n-BSD development-")

            Transport.send(message)


        }
        return code
    }
}