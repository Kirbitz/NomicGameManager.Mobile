package nomic.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mobile.game.manager.nomic.R
import nomic.ui.activities.IntialPlayersListAdapter

class GameConfigurationActivity :AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var btnAddPlayer: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: IntialPlayersListAdapter
    private val textList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_configuration_page)
        editText = findViewById(R.id.etIntialPlayers)
        recyclerView = findViewById(R.id.rvIntialPlayers)
        btnAddPlayer = findViewById(R.id.btnAddPlayer)
        adapter = IntialPlayersListAdapter(textList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        btnAddPlayer.setOnClickListener {
            val text = editText.text.toString()
            textList.add(text)
            adapter.notifyItemInserted(textList.size - 1)
            editText.text.clear()
        }
    }


}