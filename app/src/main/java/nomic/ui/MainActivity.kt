package nomic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import nomic.mobile.databinding.ActivityMainBinding
import nomic.ui.fragments.CreateAmendmentFragment
import nomic.ui.fragments.CreateRuleFragment
import nomic.ui.fragments.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.createRule.setOnClickListener{
            CreateRuleFragment().show(supportFragmentManager, "newRuleTag")

        }

        binding.createAmend.setOnClickListener{
            CreateAmendmentFragment().show(supportFragmentManager, "newAmendTag")

        }
    }

}