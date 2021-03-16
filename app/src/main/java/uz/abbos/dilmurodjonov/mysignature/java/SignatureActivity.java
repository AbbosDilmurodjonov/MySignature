package uz.abbos.dilmurodjonov.mysignature.java;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

import tech.picnic.fingerpaintview.FingerPaintImageView;
import uz.abbos.dilmurodjonov.mysignature.R;

import static uz.abbos.dilmurodjonov.mysignature.java.MainActivity.PREF_NAME;
import static uz.abbos.dilmurodjonov.mysignature.java.MainActivity.SIGNATURE;

public class SignatureActivity extends AppCompatActivity {
    private static final String PERSON_FIO_ARG = "PERSON_FIO";

    public static void start(Context context, String personFio) {
        Intent starter = new Intent(context, SignatureActivity.class);

        starter.putExtra(PERSON_FIO_ARG, personFio);

        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        TextView fio = findViewById(R.id.fio);
        ImageView back = findViewById(R.id.backImage);
        ImageView save = findViewById(R.id.save);
        ImageView clear = findViewById(R.id.clear);
        FingerPaintImageView layoutMySignature = findViewById(R.id.finger);

        fio.setText(getIntent().getStringExtra(PERSON_FIO_ARG));

        back.setOnClickListener(v -> finish());
        clear.setOnClickListener(v -> layoutMySignature.clear());

        layoutMySignature.setStrokeColor(ContextCompat.getColor(this, R.color.sign_color));

        save.setOnClickListener(v -> {
            Drawable d = layoutMySignature.getDrawable();
            if (d instanceof BitmapDrawable) {
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

                String encodeBase64 = encodeBase64(bitmap);

                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                sharedPreferences.edit().putString(SIGNATURE, encodeBase64).apply();
                finish();
            }
        });

    }

    public String encodeBase64(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

}