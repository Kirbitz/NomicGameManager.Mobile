package nomic.domain.MicIntegration.androidVoiceCommands

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import nomic.mobile.databinding.ActivityMainBinding
import java.util.*

/**
 * Define the MicrophoneHandler class that extends the AppCompatActivity class
 */
class MicrophoneHandler : AppCompatActivity() {

    /**
     * Define the request code to use when launching the speech recognition activity
     */
    companion object {
        private const val REQUEST_CODE_STT = 1
    }

    private var ttsInitialized = false
    private val queuedTTS = PriorityQueue<String>()

    // source is used to know where speechRecognizer.listen() was called
    // so processResponse can handle it properly
    private var source = 0

    /**
     * Define a TextToSpeech engine to use for text-to-speech
     */
    private lateinit var textToSpeechEngine: TextToSpeech

    private lateinit var speechRecognizer: SpeechRecognizer

    /**
     * Binding for activity_main xml
     */
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    /**
     * This method is called when the activity is created
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        speechRecognizer.setRecognitionListener(MyRecognitionListener())

        // Add a click listener to the "Speak" button
        binding.btnStt.setOnClickListener {
            // Create an intent to start the speech recognition activity
            Log.i("Button Click", "Start")
            speechRecognizer.startListening(createSpeechIntent())
            Log.i("Button Click", "Finished")
        }

        textToSpeechEngine = TextToSpeech(this) { status ->
            Log.i("Text to speech", "complete: $status")
            if (status == TextToSpeech.SUCCESS) {
                ttsInitialized = true
                textToSpeechEngine.language = Locale.US
            }
        }
        Log.i("tts", "Just created")

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(applicationContext)
        speechRecognizer.setRecognitionListener(SpeechRecognizer(this))

        if (checkCallingOrSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(RECORD_AUDIO), 101)
        }
    }

    /**
     * Creates the intent to begin requesting user to speak
     */
    private fun createSpeechIntent(): Intent {
        val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

        sttIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")

        return sttIntent
    }

    /**
     * Change the return type of getTextFromUser() to String
     */
    private fun getTextFromUser(): String {
        return ""
    }

    /**
     * Confirm that the user said "yes" before running a spoken action
     */
    fun processResponseText(recognizedText: String): Boolean {
        if (recognizedText.contains("yes", true)) {
            Log.i("processResponseText", "Sheila works")
            return true
        }
        return false
    }

    /**
     * Runs when speech matches commands
     */
    fun processRecognizedText(recognizedText: String, rule: String): Boolean {
        if (recognizedText.contains(rule, true)) {
            Log.i("recognizedText", "Does contain $recognizedText")
            val confirm = "Did you say '$rule'?"
            textToSpeech(confirm) {}
            return true
        }
        return false
    }

    /**
     * Runs if TTS is activated
     * Checks the global source variable to see where the call is from. If source is 0 it is
     * listening for one of the commands in the first switch case. If source is not 0
     * it is checking for confirmation before executing the desired command
     */
    fun processSpeech(data: Bundle) {
        speechRecognizer.stopListening()
        val result = data.getStringArrayList(android.speech.SpeechRecognizer.RESULTS_RECOGNITION)
        Log.i("processSpeech", "Result: ${result == null}")

        result?.let {
            val recognizedText = it[0]
            Log.i("processSpeech", recognizedText)

            if (source == 0) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
                }
                when {
                    processRecognizedText(recognizedText, "Search rule") -> {
                        source = 1
                        speechRecognizer.startListening(intent)
                    }
                    processRecognizedText(recognizedText, "New rule") -> {
                        source = 2
                        speechRecognizer.startListening(intent)
                    }
                    processRecognizedText(recognizedText, "Delete rule") -> {
                        source = 3
                        speechRecognizer.startListening(intent)
                    }
                    processRecognizedText(recognizedText, "Edit rule") -> {
                        source = 4
                        speechRecognizer.startListening(intent)
                    }
                    else -> {
                        textToSpeech("Sorry, Please try again.") {}
                    }
                }
            } else {
                // Run confirmation
                if (processResponseText(recognizedText)) {
                    when (source) {
                        1 -> {
                            // Search Rule
                            Log.i("debug", "1")
                        }
                        2 -> {
                            // New Rule
                            Log.i("debug", "2")
                        }
                        3 -> {
                            // Delete Rule
                            Log.i("debug", "3")
                        }
                        4 -> {
                            // Edit Rule
                            Log.i("debug", "4")
                        }
                    }
                }
                source = 0
            }
        }
    }

    /**
     * Takes text and turns it to speech using google accessibility
     */
    private fun textToSpeech(textToSpeak: String, callback: (Boolean) -> Unit) {
        val utteranceId = hashCode().toString()

        if (ttsInitialized) {
            Log.i("textToSpeech", "IsInitialized")
            textToSpeechEngine.stop()
            textToSpeechEngine.speak(textToSpeak, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
            Log.i("textToSpeech", "Completed ")
        } else {
            Log.i("textToSpeech", "Not isInitialized")
            queuedTTS.add(textToSpeak)
        }
    }

    /**
     * If text engine needs to be paused stops it
     */
    override fun onPause() {
        if (ttsInitialized) {
            textToSpeechEngine.stop()
        }

        super.onPause()
    }

    /**
     * Shuts the text engine down on finish
     */
    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}

/**
* Creates logs for where process is currently at
*/
class SpeechRecognizer(private val activity: MicrophoneHandler) : RecognitionListener {
    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i("Speech Recognizer", "On Ready")
    }

    override fun onBeginningOfSpeech() {
        Log.i("Speech Recognizer", "On Beginning")
    }

    override fun onRmsChanged(p0: Float) {
        Log.i("Speech Recognizer", "On RMS Changed")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        Log.i("Speech Recognizer", "On Buffer Received")
    }

    override fun onEndOfSpeech() {
        Log.i("Speech Recognizer", "On End of Speech")
    }

    override fun onError(p0: Int) {
        Log.i("Speech Recognizer", "On Error: $p0")
    }

    override fun onResults(bundle: Bundle?) {
        Log.i("Speech Recognizer", "On Results")
        if (bundle == null) return
        Log.i("debug", bundle.toString())
        Log.i("onResults", "call processSpeech")
        activity.processSpeech(bundle)
    }

    override fun onPartialResults(p0: Bundle?) {
        Log.i("Speech Recognizer", "On Partial Results")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i("Speech Recognizer", "On Event")
    }
}

class MyRecognitionListener : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {
        Log.i("SpeechRecognizer", "onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i("SpeechRecognizer", "onBeginningOfSpeech")
    }

    override fun onRmsChanged(rmsdB: Float) {
        Log.i("SpeechRecognizer", "onRmsChanged: $rmsdB")
    }

    override fun onBufferReceived(buffer: ByteArray?) {
        Log.i("SpeechRecognizer", "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.i("SpeechRecognizer", "onEndOfSpeech")
    }

    override fun onError(error: Int) {
        Log.i("SpeechRecognizer", "onError: $error")
    }

    override fun onResults(results: Bundle?) {
        Log.i("SpeechRecognizer", "onResults")
    }

    override fun onPartialResults(partialResults: Bundle?) {
        Log.i("SpeechRecognizer", "onPartialResults")
    }

    override fun onEvent(eventType: Int, params: Bundle?) {
        Log.i("SpeechRecognizer", "onEvent")
    }
}
