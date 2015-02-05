package io.github.dector.simpleprogressdemo;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.dector.simpleprogress.ProgressView;

public class MainActivity extends ActionBarActivity {

    private Button startButton;
    private Button resetButton;
    private Button plusButton;
    private Button minusButton;
    private TextView progressValue;
    private ProgressView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.start_button);
        resetButton = (Button) findViewById(R.id.reset_button);
        plusButton = (Button) findViewById(R.id.plus_button);
        minusButton = (Button) findViewById(R.id.minus_button);
        progressValue = (TextView) findViewById(R.id.value);
        progress = (ProgressView) findViewById(R.id.progress);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ProgressAsyncTask().execute();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgress(0);
            }
        });
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgress(progress.getValue()+1);
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProgress(progress.getValue()-1);
            }
        });

        updateProgress(progress.getValue());
    }

    private void updateProgress(int value) {
        progress.setValue(value);
        progressValue.setText(Integer.toString(progress.getValue()));
    }

    private void setControlsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        resetButton.setEnabled(enabled);
        plusButton.setEnabled(enabled);
        minusButton.setEnabled(enabled);
    }

    private class ProgressAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            for (int i = 0; i < 100; i++) {
                publishProgress(i);

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            updateProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setControlsEnabled(false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateProgress(100);
            setControlsEnabled(true);
        }
    }
}
