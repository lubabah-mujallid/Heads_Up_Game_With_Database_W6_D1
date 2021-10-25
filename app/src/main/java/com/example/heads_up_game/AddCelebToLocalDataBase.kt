package com.example.heads_up_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.heads_up_game.databinding.ActivityAddCelebToLocalDataBaseBinding

class AddCelebToLocalDataBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_celeb_to_local_data_base)
        val dbHelper = DataBase(this)
        initializeBinding()
        binding.buttonSubmit.setOnClickListener {
            val name = binding.etName.text.toString()
            val t1 = binding.etT1.text.toString()
            val t2 = binding.etT2.text.toString()
            val t3 = binding.etT3.text.toString()
            dbHelper.saveData(name, t1, t2, t3)
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_LONG).show()
        }
    }
    private lateinit var binding: ActivityAddCelebToLocalDataBaseBinding
    private fun initializeBinding() {
        binding = ActivityAddCelebToLocalDataBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

class DataBase( context: Context?) : SQLiteOpenHelper(context, "details.db", null, 1) {
    val sqLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("Create table celebs (Name text, Taboo1 text, Taboo2 text, Taboo3 text)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun saveData(name: String, t1: String, t2: String, t3: String) {
        val cv = ContentValues()
        cv.put("Name", name)
        cv.put("Taboo1", t1)
        cv.put("Taboo2", t2)
        cv.put("Taboo3", t3)
        sqLiteDatabase.insert("celebs", null, cv)
    }
}