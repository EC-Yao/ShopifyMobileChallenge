package com.example.eddyyao.shopifymobilechallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.eddyyao.shopifymobilechallenge.R;

public class LoaderActivity extends AppCompatActivity {
    private TextView tv ;
    private ImageView iv ;
    public static ApiManager api;

    // Instances UI and plays animation
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = new ApiManager(this);


        // Connects the xml file with the Java file
        setContentView(R.layout.activity_buffer);

        // Initializes the text field and image view
        tv = findViewById(R.id.bufferText);
        iv = findViewById(R.id.bufferImage);

        // Begin animation
        Animation loadingAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        tv.startAnimation(loadingAnim);
        iv.startAnimation(loadingAnim);
        final Intent i = new Intent(this, MainActivity.class);

        // Keeps main thread inactive until animation can finish
        Thread timer = new Thread(){
            public void run (){
                try{
                    sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    // Launches login activity
                    startActivity(i);
                    finish();
                }
            }
        };
            timer.start();
    }

}
