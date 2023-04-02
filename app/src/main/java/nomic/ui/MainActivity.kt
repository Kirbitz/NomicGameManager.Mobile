package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.toolbox.Volley
import mobile.game.manager.nomic.databinding.ActivityMainBinding
import nomic.data.services.NomicApiService

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.container, MainFragment.newInstance())
//                .commitNow()
//        }

        val queue = Volley.newRequestQueue(applicationContext)

        binding.button.setOnClickListener {

            val nomicApiService = NomicApiService()
            queue.add(nomicApiService.getRulesAmendmentsList())
        }
    }
}