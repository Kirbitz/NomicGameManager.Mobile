package com.example.pleasework

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
import mobile.game.manager.nomic.databinding.ActivityMainBinding
import java.util.*

/**
 * Define the MainActivity class that extends the AppCompatActivity class
 */
class MainActivity : AppCompatActivity() {

    /**
     * Define the request code to use when launching the speech recognition activity
     */
    companion object {
        private const val REQUEST_CODE_STT = 1
    }

    private var ttsInitialized = false
    private val queuedTTS = PriorityQueue<String>()

    /**
     * Define a TextToSpeech engine to use for text-to-speech
     */
    private lateinit var textToSpeechEngine: TextToSpeech

    private lateinit var speechRecognizer : SpeechRecognizer


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
            Log.i("Button Click","Start")
            speechRecognizer.startListening(createSpeechIntent())
            Log.i("Button Click","Finished")

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
            requestPermissions( arrayOf(RECORD_AUDIO), 101)
        }

    }

    private fun createSpeechIntent() : Intent {
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
    private fun processResponseText(recognizedText: String) : Boolean {
        if (recognizedText.contains("yes", true)) {
            Log.i("processResponseText", "Sheila works")
            return true
        }
        return false
    }

    /**
     * Runs when speech matches commands
     */
    private fun processRecognizedText(recognizedText: String, rule: String) : Boolean {
        if (recognizedText.contains(rule, true)) {
            Log.i("recognizedText", "Does contain $recognizedText")
            val confirm = "Did you say '$rule'?"
            textToSpeech(confirm) {}

            // Return here and break back to the processSpeech function
            /**
            val userResponse = getTextFromUser()
            if (userResponse.contains("yes", true)) {
                val confirm = "Running '$rule'"
                textToSpeech(confirm) {}
            */
                // newRule()

            return true
        }

        return false
    }

    /**
     * Runs if TTS is activated
     */
    fun processSpeech(data: Bundle) {
        if (data != null) {
            val result = data.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            Log.i("processSpeech", "Result: ${result == null}")
            result?.let {
                val recognizedText = it[0]
                Log.i("processSpeech", recognizedText)

                // Check if recognized text matches any command
                when {
                    processRecognizedText(recognizedText, "Search rule") -> {
                        val userResponse = getTextFromUser()
                        if (processResponseText(userResponse)) {
                            // user has confirmed the action, run command
                            Log.i("processSpeech", recognizedText)
                        }
                    }
                    processRecognizedText(recognizedText, "New rule") -> {
                        // Call the Google Cloud Speech-to-Text API to prompt the user for a response
                        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something")
                        }
                        speechRecognizer.startListening(intent)
                        Log.i("debug", intent.toString())
                        Log.i("debug", speechRecognizer.toString())
                        if (processResponseText(recognizedText)) {
                            // user has confirmed the action, run command
                        }
                    }
                    processRecognizedText(recognizedText, "Delete rule") -> {
                        if(processResponseText(recognizedText)) {
                           // user has confirmed the action, run command
                        }
                    }
                    processRecognizedText(recognizedText, "Edit rule") -> {
                        if (processResponseText(recognizedText)) {
                            // user has confirmed the action, run command
                            }
                        }
                    else -> {
                        textToSpeech("Sorry, Please try again.") {}
                    }
                }
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
        }
        else
        {
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

class SpeechRecognizer(private val activity: MainActivity) : RecognitionListener {
    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i("Speech Recognizer","On Ready")
    }

    override fun onBeginningOfSpeech() {
        Log.i("Speech Recognizer","On Beginning")
    }

    override fun onRmsChanged(p0: Float) {
        Log.i("Speech Recognizer","On RMS CHanged")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        Log.i("Speech Recognizer","On Buffer Received")
    }

    override fun onEndOfSpeech() {

        Log.i("Speech Recognizer","On End of Speech")
    }

    override fun onError(p0: Int) {
        Log.i("Speech Recognizer","On Error: $p0")
    }

    override fun onResults(bundle: Bundle?) {
        Log.i("Speech Recognizer","On Results")
        if (bundle == null) return
        activity.processSpeech(bundle)
    }

    override fun onPartialResults(p0: Bundle?) {
        Log.i("Speech Recognizer","On Partial Results")
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i("Speech Recognizer","On Event")
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
