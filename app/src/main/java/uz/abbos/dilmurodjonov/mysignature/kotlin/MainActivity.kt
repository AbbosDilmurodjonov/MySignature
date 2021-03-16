package uz.abbos.dilmurodjonov.mysignature.kotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import uz.abbos.dilmurodjonov.mysignature.R

class MainActivity : AppCompatActivity() {
    companion object {
        const val PREF_NAME = "MY_SIGNATURE"
        const val SIGNATURE = "SIGN"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textSigner = findViewById<TextView>(R.id.textSigner)
        val layoutSign = findViewById<LinearLayout>(R.id.layoutSign)
        layoutSign.setOnClickListener {
            SignatureActivity.start(this, textSigner.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        val imageSignResult = findViewById<ImageView>(R.id.imageSignResult)

        val sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        try {
            imageSignResult.setImageBitmap(
                sharedPreferences.getString(SIGNATURE, "")?.let { decodeBase64(it) }
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(IllegalArgumentException::class)
    fun decodeBase64(base64Str: String): Bitmap? {
        val decodedBytes = Base64.decode(
            base64Str.substring(base64Str.indexOf(",") + 1),
            Base64.DEFAULT
        )
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}