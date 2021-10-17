package com.example.heads_up_game

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Surface
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.net.URL

class MainGame : AppCompatActivity() {
    private lateinit var llTop: LinearLayout
    private lateinit var llCelebrity: LinearLayout
    private lateinit var tvTime: TextView

    private lateinit var tvName: TextView
    private lateinit var tvTaboo1: TextView
    private lateinit var tvTaboo2: TextView
    private lateinit var tvTaboo3: TextView

    private lateinit var celebrities: ArrayList<Celeb.Details>
    private var celeb = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        llTop = findViewById(R.id.llTop)
        llCelebrity = findViewById(R.id.llCelebrity)

        tvTime = findViewById(R.id.tvTime)

        tvName = findViewById(R.id.tvName)
        tvTaboo1 = findViewById(R.id.tvTaboo1)
        tvTaboo2 = findViewById(R.id.tvTaboo2)
        tvTaboo3 = findViewById(R.id.tvTaboo3)


        //var intent: Intent = getIntent()
        //var bundle: Bundle? = intent.getBundleExtra("BUNDLE")
        //celebrities = bundle?.getSerializable("list") as ArrayList<Celeb>
        celebrities = intent.getSerializableExtra("list") as ArrayList<Celeb.Details>
        Log.d("GAME", "retrieved data succefully" + celebrities)

        newTimer()

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        val rotation = windowManager.defaultDisplay.rotation
        if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                celeb++
                newCelebrity(celeb)
                updateStatus(false)
        }else{
                updateStatus(true)
        }
    }

    private fun newTimer(){
            val rotation = windowManager.defaultDisplay.rotation
            if(rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180){
                updateStatus(false)
            }else{
                updateStatus(true)
            }

            object : CountDownTimer(60000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    tvTime.text = "Time: ${millisUntilFinished / 1000}"
                }

                override fun onFinish() {
                    tvTime.text = "Time: --"
                    updateStatus(false)
                    backToMain()
                }
            }.start()
        }


    private fun newCelebrity(id: Int){
        if(id < celebrities.size){
            tvName.text = celebrities[id].name
            tvTaboo1.text = celebrities[id].taboo1
            tvTaboo2.text = celebrities[id].taboo2
            tvTaboo3.text = celebrities[id].taboo3
        }
    }

    private fun updateStatus(showCelebrity: Boolean){
        llCelebrity.isVisible = showCelebrity
    }

    private fun backToMain() {
        Log.d("MAIN", "going to start activity")
        intent = Intent(applicationContext, StartGame::class.java)
        startActivity(intent)
    }

}