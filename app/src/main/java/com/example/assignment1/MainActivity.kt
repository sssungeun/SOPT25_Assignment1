package com.example.assignment1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data: Uri
    var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        // 갤러리로부터 이미지 갖고올 때 사용하는 오버라이딩 메소드
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            if (requestCode == REQ_CODE_SELECT_IMAGE) {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        this.data = data!!.data!!
                        imageUri = data!!.data


                        // 선택한 이미지를 해당 이미지뷰에 적용
                        Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(img_edit_photo)

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
        }
        // 이미지 파일을 확장자까지 표시해주는 메소드
        fun getRealPathFromURI(context: Context, contentUri: Uri): String {
            var cursor: Cursor? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, proj, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                return cursor.getString(column_index)
            } finally {
                cursor?.close()
            }
        }
        img_act_profile.setOnClickListener {
            changeImage()
        }

    }

    // 방 배경 이미지 변경
    fun changeImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE)
    }
}

