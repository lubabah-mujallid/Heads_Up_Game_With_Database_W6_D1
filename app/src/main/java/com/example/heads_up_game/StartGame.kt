package com.example.heads_up_game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.internal.ParcelableSparseArray
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import java.io.Serializable

class StartGame : AppCompatActivity() {
    lateinit var myList: ArrayList<Celeb.Details>
    lateinit var StartButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_game)

        myList = ArrayList()
        StartButton = findViewById(R.id.startButton)

        requestAPI()

        StartButton.setOnClickListener { startGame() }
        var addButton = findViewById<Button>(R.id.addCelebButton)
        addButton.setOnClickListener { addCeleb() }


    }
    private fun requestAPI() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("MAIN", "fetch data")
            async { fetchData() }.await()
            if (myList.isNotEmpty()) {
                Log.d("MAIN", "Successfully got all data")
            } else {
                Log.d("MAIN", "Unable to get data")
            }
        }
    }

    private suspend fun fetchData() {
        Log.d("MAIN", "went inside fetch")
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<ArrayList<Celeb.Details>> = apiInterface!!.getPerson()
        val response: Response<ArrayList<Celeb.Details>>

        try {

            response = call.execute()
            withContext(Dispatchers.Main) {
                Log.d("MAIN", "fetch successful")
                for (Celeb in response.body()!!) {
                    Log.d("MAIN", "added person: $Celeb")
                    updateTextView(Celeb)
                }
            }
        } catch (e: Exception) {
            Log.d("MAIN", "ISSUE: $e")
        }

    }

    private fun updateTextView(person: Celeb.Details) {
        myList.add(person)
    }

    private fun startGame() {
        Log.d("MAIN", "going to game activity")
        intent = Intent(applicationContext, MainGame::class.java)
        //var bundle: Bundle = Bundle()
        //bundle.putSerializable("list", myList)
        //intent.putExtra("BUNDLE", bundle)
        intent.putExtra("list", myList)
        startActivity(intent)

    }

    private fun addCeleb() {
        Log.d("MAIN", "going to Add Celeb activity")
        intent = Intent(applicationContext, AddCelebToLocalDataBase::class.java)
        startActivity(intent)
    }


    //API --> arraylist

}

/*
Our version should have the following functionality:
- Use a start button to initiate the game
- Once the game starts, a 60 second timer should be displayed and count down
- The phone should display a Celebrity name and the three taboo words
- Rotating the device should move to the next celebrity

Activity 1:
    - Start Button
    - when pressed the game begins --> move to next activity

    - load API to a list of class Celeb

Activity 2:
    - start timer 60 seconds + count down by second
    - on portrait mode: rotate device
    - on landscape mode: display celeb
    - save data on changing device rotation
    - if timer finishes ---> alert dialog/new view --> print Good Job!

*/