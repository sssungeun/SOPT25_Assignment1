package com.example.assignment1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private var edtLoginId: EditText? = null
    private var edtLoginPw: EditText? = null
    private var btnSignIn: Button? = null
    private var btnLogIn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        makeController()
    }

    // 사용자 입력을 받아서 프로그램을 실행하는 것(Controller)을 초기화 하는 것
    private fun makeController() {
        // layout 파일의 객체의 참조 얻어오기
        edtLoginId = findViewById(R.id.edtLoginId)
        edtLoginPw = findViewById(R.id.edtLoginPw)
        btnSignIn = findViewById(R.id.btnSignIn)
        btnLogIn = findViewById(R.id.btnLogin)

        // 익명 클래스를 사용한 click 이벤트 구현
        btnSignIn?.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(p0: View?) {  // 파라미터는 클린 view의 참조 (https://developer.android.com/reference/android/view/View.OnClickListener)
                    // 회원가입 페이지로 이동해야 한다.
                    val intent = Intent(this@LoginActivity, SignupActivity::class.java)

                    startActivity(intent)
                }
            }
        )

        // kotlin의 람다식을 사용한 click 이벤트 구현
        btnLogIn?.setOnClickListener {
            // ID, PW 둘중 하나라도 공백이면 눌리지 않는다.
            val id = edtLoginId?.text.toString()
            val pw = edtLoginPw?.text.toString()

            if (id.isEmpty() || pw.isEmpty()) {
                // 사용자에게 간단한 text 정보를 알려주기 위해 Toast를 띄워준다.
                Toast.makeText(this, "아이디나 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 입력한 ID, 입력한 PW를 가지고 로그인 요청을 한다.
            val response = requestLogin(id, pw)
            if (response) {
                val intent = Intent(this, MainActivity::class.java)
                // 로그인에 성공한 아이디를 넘겨주자.
                intent.putExtra("login", id)

                startActivity(intent)
            }
            else {
                // 로그인이 실패했으면 Toast를 사용해 로그인이 실패했다고 알려주고 아이디 혹은 비밀번호를 다시 입력하게 포커스를 이동시켜주자.
                Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                edtLoginId?.requestFocus()
            }
        }
    }

    private fun requestLogin(id: String, pw: String): Boolean {
        return true
    }
}
