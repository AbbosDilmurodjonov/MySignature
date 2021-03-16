package uz.abbos.dilmurodjonov.mysignature.kotlin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import tech.picnic.fingerpaintview.FingerPaintImageView
import uz.abbos.dilmurodjonov.mysignature.R
import uz.abbos.dilmurodjonov.mysignature.kotlin.MainActivity.Companion.PREF_NAME
import uz.abbos.dilmurodjonov.mysignature.kotlin.MainActivity.Companion.SIGNATURE
import java.io.ByteArrayOutputStream

class SignatureActivity : AppCompatActivity() {
    companion object {
        private const val PERSON_FIO_ARG = "PERSON_FIO"

        fun start(context: Context, personFio: String) {
            val starter = Intent(
                context,
                SignatureActivity::class.java
            )
            starter.putExtra(PERSON_FIO_ARG, personFio)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signature)


        val fio = findViewById<TextView>(R.id.fio)
        val back = findViewById<ImageView>(R.id.backImage)
        val save = findViewById<ImageView>(R.id.save)
        val clear = findViewById<ImageView>(R.id.clear)
        val layoutMySignature = findViewById<FingerPaintImageView>(R.id.finger)

        fio.text = intent.getStringExtra(PERSON_FIO_ARG)

        back.setOnClickListener { finish() }
        clear.setOnClickListener { layoutMySignature.clear() }

        layoutMySignature.strokeColor = ContextCompat.getColor(this, R.color.sign_color)

        save.setOnClickListener {
            val d: Drawable? = layoutMySignature.drawable
            if (d is BitmapDrawable) {
                val bitmap = d.bitmap
                val encodeBase64: String? = encodeBase64(bitmap)

                val sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
                sharedPreferences.edit().putString(SIGNATURE, encodeBase64).apply()
                finish()
            }
        }

    }

    private fun encodeBase64(bitmap: Bitmap): String? {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }

}