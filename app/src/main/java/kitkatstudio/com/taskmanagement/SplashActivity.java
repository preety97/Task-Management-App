package kitkatstudio.com.taskmanagement;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        if (getSupportActionBar()!=null)
            getSupportActionBar().hide();

        //TextView textView = findViewById(R.id.tv_app_name);
        final ImageView imageView = findViewById(R.id.iv_logo);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Courgette-Regular.ttf");
        //textView.setTypeface(typeface);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        imageView.startAnimation(animation);

        final Intent intent = new Intent(SplashActivity.this, TypeActivity.class);
        final ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(SplashActivity.this, android.R.anim.fade_in, android.R.anim.fade_out);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(intent, activityOptions.toBundle());
                    finish();
                }
            }
        });
        t.start();
    }
}
