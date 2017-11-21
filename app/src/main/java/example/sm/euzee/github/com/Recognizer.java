package example.sm.euzee.github.com;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.Toast;

class Recognizer {
    private static final String TAG = Recognizer.class.getSimpleName();
    private static final int NOTIFICATION_ID = 1001;
    private static final int MAX_RESULT_VALUE = 5;
    private static final String CONST_EANGLISH = "en-US";
    private SpeechRecognizer speech;
    private Context context;

    Recognizer(Context context) {
        this.context = context;
        speech = SpeechRecognizer.createSpeechRecognizer(context);
        speech.setRecognitionListener(getRecognitionListener());
    }

    void startRecognize() {
        try {
            speech.startListening(getRecognitionIntent());
        } catch (Exception a) {
            Log.e(TAG, "startRecognize", a);
            Toast.makeText(context,
                    "Sorry! Speech recognition is not supported in this device.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Intent getRecognitionIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, CONST_EANGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, MAX_RESULT_VALUE);
        intent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        return intent;
    }

    private RecognitionListener getRecognitionListener() {
        return ListenerBuilder.with(this::parseResult);
    }

    private void parseResult(String matched) {
        if ((matched.contains("hey") || matched.contains("Hey"))
                && (matched.contains("Friday") || matched.contains("friday"))) {
            showFridayNotification();
            Toast.makeText(context, matched, Toast.LENGTH_SHORT).show();
        }
    }

    private void showFridayNotification() {
        Log.e(TAG, "notification");
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, NotificationHelper.getNotification(context, context.getString(R.string.notification)));
        }
    }
}
