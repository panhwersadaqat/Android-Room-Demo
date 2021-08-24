package com.panhwersadaqat.roomsample.ui

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.roomwordssample.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.panhwersadaqat.roomsample.R
import com.panhwersadaqat.roomsample.WordsApplication
import com.panhwersadaqat.roomsample.room.Word
import com.panhwersadaqat.roomsample.room.WordListAdapter

class MainActivity : AppCompatActivity() {

private val newWordActivityRequestCode = 1
    private val wordViewModel: WordViewModel by viewModels {
        WordViewModelFactory((application as WordsApplication).repository)
    }

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
    val adapter = WordListAdapter()
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(this)

    val fab = findViewById<FloatingActionButton>(R.id.fab)
    fab.setOnClickListener {
        val intent = Intent(this@MainActivity, NewWordActivity::class.java)
        startActivityForResult(intent, newWordActivityRequestCode)
    }

    wordViewModel.allWords.observe(owner = this) { words ->
        // Update the cached copy of the words in the adapter.
        words.let { adapter.submitList(it) }
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
    super.onActivityResult(requestCode, resultCode, intentData)

    if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
        intentData?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let { reply ->
            val word = Word(reply)
            wordViewModel.insert(word)
        }
    }/* else {
        Toast.makeText(
            applicationContext,
            R.string.empty_not_saved,
            Toast.LENGTH_LONG
        ).show()
    }*/
}
}