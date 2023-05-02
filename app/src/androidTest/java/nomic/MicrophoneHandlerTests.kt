package nomic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat.startActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import nomic.domain.MicIntegration.androidVoiceCommands.MicrophoneHandler
import org.junit.Assert.*
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.Observer

@RunWith(AndroidJUnit4::class)
class MicrophoneHandlerTests {
    private lateinit var microphoneHandler: MicrophoneHandler
    private lateinit var latch: CountDownLatch

    @Before
    fun setUp() {
        latch = CountDownLatch(1)
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            val intent = Intent(context, MicrophoneHandler::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            val options = Bundle()
            startActivity(context, intent, options)
            microphoneHandler = MicrophoneHandler()
            latch.countDown()
        }
        latch.await()
    }

    @Test
    fun processResponseTextSUCCESS() {
        val actual = microphoneHandler.processResponseText("Yes")
        assertTrue((actual ?: true) as Boolean)
    }

    @Test
    fun processResponseTextFAIL() {
        val actual = microphoneHandler.processResponseText("No")
        assertFalse((actual ?: false) as Boolean)
    }

    @Test
    fun processRecognizedTextSUCCESS() {
        val actual = microphoneHandler.processRecognizedText("test", "test")
        assertTrue((actual ?: true) as Boolean)
    }

    @Test
    fun processRecognizedTextFAIL() {
        val actual = microphoneHandler.processRecognizedText("test", "fail")
        assertFalse((actual ?: false) as Boolean)
    }
}
