package uz.abbos.dilmurodjonov.mysignature.java;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import uz.abbos.dilmurodjonov.mysignature.R;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_NAME = "MY_SIGNATURE";
    public static final String SIGNATURE = "SIGN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textSigner = findViewById(R.id.textSigner);
        LinearLayout layoutSign = findViewById(R.id.layoutSign);
        layoutSign.setOnClickListener(v -> SignatureActivity.start(this, textSigner.getText().toString()));


    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView imageSignResult = findViewById(R.id.imageSignResult);

        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        try {
            imageSignResult.setImageBitmap(
                    decodeBase64(sharedPreferences.getString(SIGNATURE, ""))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap decodeBase64(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
