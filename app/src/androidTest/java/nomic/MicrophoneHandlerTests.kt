package nomic

import androidx.test.ext.junit.runners.AndroidJUnit4
import nomic.domain.MicIntegration.androidVoiceCommands.MicrophoneHandler
import org.junit.Test
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MicrophoneHandlerTests {
    @get:Rule var activityScenarioRule = activityScenarioRule<MicrophoneHandler>()

    @Test
    fun processRecognizedTextSUCCESS() {
        assertTrue(MicrophoneHandler.processRecognizedText("test string", "test string"))
    }

    @Test
    fun processRecognizedTextFAIL() {

    }
}