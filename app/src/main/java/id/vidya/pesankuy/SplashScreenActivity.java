package id.vidya.pesankuy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 1700;

    Animation fadeinAnim;
    ImageView ivLogoApp;
    TextView tvJudulModul, tvSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        fadeinAnim = AnimationUtils.loadAnimation(this, R.anim.fadein);

        ivLogoApp = findViewById(R.id.ivLogoApp);
        tvJudulModul = findViewById(R.id.tvJudulModul);
        tvSlogan = findViewById(R.id.tvSlogan);

        ivLogoApp.setAnimation(fadeinAnim);
        tvJudulModul.setAnimation(fadeinAnim);
        tvSlogan.setAnimation(fadeinAnim);

        new Handler().postDelayed(() -> {
            Intent toMainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(toMainIntent);
            finish();
        }, SPLASH_SCREEN);
    }
}
