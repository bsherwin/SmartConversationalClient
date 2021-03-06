package com.microsoft.pct.smartconversationalclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.microsoft.pct.smartconversationalclient.common.IQueryResult;
import com.microsoft.pct.smartconversationalclient.controller.SmartConversationalController;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    static final String LOG_TAG = "MainActivity";

    private SpeechRecognizer _speechRecognizer;
    private RecognitionListener _recognitionListener;
    private SmartConversationalController _controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init smart conversational controller
        _controller = new SmartConversationalController(this);
        //init speech to text google recognizer
        _recognitionListener = new RecognitionListener() {
            //speech recognizer offline partial global
            private String _offlineResult;

            @Override
            public void onReadyForSpeech(Bundle params) {}

            @Override
            public void onBeginningOfSpeech() {
                _offlineResult = null;
            }

            @Override
            public void onRmsChanged(float rmsdB) {}

            @Override
            public void onBufferReceived(byte[] buffer) {}

            @Override
            public void onEndOfSpeech() { }

            @Override
            public void onEvent(int eventType, Bundle params) {}

            @Override
            public void onError(int error) {
                //handle google offline bug see http://stackoverflow.com/questions/30654191/speechrecognizer-offline-error-no-match
                if (error == 7){
                    queryLuisAndShowResult(_offlineResult);
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                //handle offline unstable state based on http://stackoverflow.com/questions/30654191/speechrecognizer-offline-error-no-match
                ArrayList<String> data = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                ArrayList<String> unstableData = partialResults.getStringArrayList("android.speech.extra.UNSTABLE_TEXT");
                _offlineResult = data.get(0) + unstableData.get(0);
            }

            public void onResults(Bundle results) {
                //extractIntent from closest recognized string
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                EditText control = (EditText)findViewById(R.id.editText);
                String query = matches.get(0);
                control.setText(query);
                queryLuisAndShowResult(query);
            }
        };

        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        _speechRecognizer.setRecognitionListener(_recognitionListener);
    }

    private void queryLuisAndShowResult(final String query) {
        new AsyncTask<String, Void, IQueryResult>() {
            @Override
            // The first item in the pair indicaes wheter  the result was obtained from the cache or not
            protected IQueryResult doInBackground( final String ... params ) {
                String queryText = params[0];

                try {
                    return _controller.query(queryText);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute( final IQueryResult result ) {
                TextView control = (TextView) findViewById(R.id.resultText);
                if (result == null || result.getQueryIntents().length == 0) {
                    control.setText("Error occured during request to LUIS");
                    return;
                }

                String intent = result.getQueryIntents()[0];
                String displayText = "Intent: " + intent;
                if (result.getQueryEntities().length > 0){
                    displayText += "\n\nEntities: ";
                    for (String entity : result.getQueryEntities()){
                        displayText += entity + ", ";
                    }
                    displayText = displayText.substring(0, displayText.length()-2);
                }
                control.setText(displayText);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
    }

    public void textQuery(View view) {
        // get text from text field:
        EditText control = (EditText)findViewById(R.id.editText);
        String queryText = control.getText().toString();
        queryLuisAndShowResult(queryText);
    }

    void voiceQuery(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS,true);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        _speechRecognizer.startListening(intent);
    }

    public void clear (View view) {
        try {
            _controller.clearCache();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        TextView control = (TextView) findViewById(R.id.resultText);
        control.setText("Cache Cleared!");
    }
}