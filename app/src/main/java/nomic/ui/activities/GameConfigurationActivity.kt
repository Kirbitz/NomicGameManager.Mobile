package nomic.ui.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nomic.mobile.R
import nomic.ui.viewmodels.GameConfigurationViewModel

class GameConfigurationActivity :AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var btnAddPlayer: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IntialPlayersListAdapter
    private lateinit var editTitle: EditText
    private lateinit var btnGame : Button
    private lateinit var viewModel: GameConfigurationViewModel
    private val textList = mutableListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_configuration_page)
        editText = findViewById(R.id.etIntialPlayers)
        editTitle = findViewById(R.id.etGameTitle)
        recyclerView = findViewById(R.id.rvIntialPlayers)
        btnAddPlayer = findViewById(R.id.btnAddPlayer)
        btnGame = findViewById(R.id.btnStartGame)
        adapter = IntialPlayersListAdapter(textList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this).get(GameConfigurationViewModel::class.java)

        btnGame.setOnClickListener {
                val title = editTitle.text.toString()
                viewModel.submitGameTitle(title)
            }
        viewModel.gameTitle.observe(this) { title ->

            Toast.makeText(this, "title perfect: $title", Toast.LENGTH_SHORT).show()
        }

        btnAddPlayer.setOnClickListener {
                val text = editText.text.toString()
                textList.add(text)
                adapter.notifyItemInserted(textList.size - 1)
                editText.text.clear()
            }
        }

    }
