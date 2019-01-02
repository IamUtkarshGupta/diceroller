package com.example.shubh.dice;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView dicep;
    Random r=new Random();
    SoundPool dice_sound;
    int sid;
    Handler handler;
    Timer timer =new Timer();
    boolean rolling=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitSound();
        dicep=(ImageView)findViewById(R.id.imageView);
        dicep.setOnClickListener(new HandleClick());

        handler=new Handler(callback);
    }

    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            if (!rolling) {
                rolling = true;
                //Show rolling image
                dicep.setImageResource(R.drawable.dice3droll);

                dice_sound.play(sid, 1.0f, 1.0f, 0, 0, 1.0f);

                timer.schedule(new Roll(), 400);
            }
        }
    }

    void InitSound() {
        AudioAttributes aa = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        dice_sound = new SoundPool.Builder().setAudioAttributes(aa).build();

        sid = dice_sound.load(this, R.raw.s, 1);
    }
    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }
    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            //Get roll result
            //Remember nextInt returns 0 to 5 for argument of 6
            //hence + 1
            switch(r.nextInt(6)+1) {
                case 1:
                    dicep.setImageResource(R.drawable.one);
                    break;
                case 2:
                    dicep.setImageResource(R.drawable.two);
                    break;
                case 3:
                    dicep.setImageResource(R.drawable.three);
                    break;
                case 4:
                    dicep.setImageResource(R.drawable.four);
                    break;
                case 5:
                    dicep.setImageResource(R.drawable.five);
                    break;
                case 6:
                    dicep.setImageResource(R.drawable.six);
                    break;
                default:
            }
            rolling=false;
            return true;
        }
    };

    //Clean up
    protected void onPause() {
        super.onPause();
        dice_sound.pause(sid);
    }
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
