package com.example.admin.applicationsante;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    public static final String PROGRESS = "PROGRESS";
    public static final int PROGRESSION = 1;
    ProgressBar bar;

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            //int progress=msg.getData().getInt(PROGRESS);
            //bar.incrementProgressBy(progress);
            if(msg.what == PROGRESSION) {
                bar.incrementProgressBy(1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = (ProgressBar) findViewById(R.id.progressBar);

        bar.setProgress(0);
        isRunning.set(true);
        isPausing.set(false);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bar.setMax(100);
            }
        });

        thread.start();
    }

    private AtomicBoolean isRunning = new AtomicBoolean(false);
    private AtomicBoolean isPausing = new AtomicBoolean(false);

    //Si on veut qu'il continue après avoir mis l'appli en arrière plan on lance le thread dans le onCreate
    /*@Override
    protected void onStart() {
        super.onStart();
        bar.setProgress(0);
        isRunning.set(true);
        isPausing.set(false);

        thread.start();
    }*/

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            Message message;
            Bundle messageBundle = new Bundle();

            try {
                for (int i=0; i<100 && isRunning.get(); i++) {

                    Thread.sleep(300);
                    message = handler.obtainMessage();
                    message.what = PROGRESSION;
                    messageBundle.putInt(PROGRESS, i);
                    message.setData(messageBundle);
                    handler.sendMessage(message);


                }
                isRunning.set(false);
                isPausing.set(true);

            } catch (Throwable t) {

            }
        }
    });

    @Override
    protected void onStop() {
        super.onStop();
        isPausing.set(true);
        isRunning.set(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        isPausing.set(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPausing.set(true);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        isPausing.set(true);
        isRunning.set(false);
    }
}
