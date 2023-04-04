package nomic.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import mobile.game.manager.nomic.R

class GameConfigurationActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_configuration_page)
    }
}