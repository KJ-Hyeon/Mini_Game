package com.jeong.android.game

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Point
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.jeong.android.game.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var state: String = " "
    private lateinit var btn_list: ArrayList<ImageView>
    private var score: Int = 0
    var mediaplayer: MediaPlayer? = null
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var Timer: Int = 60
    private var isPlaying = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        pref = getPreferences(Context.MODE_PRIVATE)
        editor = pref.edit()

        initDrawable()
        binding.tvScore.text = score.toString()


        Thread {
            binding.beeAnimation.playAnimation()
            Thread.sleep(5000)
            for (i in 0..10) {
                Thread.sleep(5000)
                val randomX = (Random().nextInt(800) + 1).toFloat()
                val randomY = (Random().nextInt(1500) + 1).toFloat()
                binding.beeAnimation.translationX = randomX
                binding.beeAnimation.translationY = randomY
            }
        }.start()


        binding.seed.setOnClickListener {
            binding.seed.tag = "seed"
            state = "seed"
            changeBack(state)
        }

        binding.watering.setOnClickListener {
            binding.watering.tag = "watering"
            state = "watering"
            changeBack(state)
        }

        binding.sun.setOnClickListener {
            binding.sun.tag = "sun"
            state = "sun"
            changeBack(state)
        }

        binding.finger.setOnClickListener {
            binding.finger.tag = "finger"
            state = "finger"
            changeBack(state)
        }

        binding.space1.setOnClickListener {
            clickEvnet(binding.space1)
        }
        binding.space2.setOnClickListener {
            clickEvnet(binding.space2)
        }
        binding.space3.setOnClickListener {
            clickEvnet(binding.space3)
        }
        binding.space4.setOnClickListener {
            clickEvnet(binding.space4)
        }
        binding.space5.setOnClickListener {
            clickEvnet(binding.space5)
        }
        binding.space6.setOnClickListener {
            clickEvnet(binding.space6)
        }
        binding.space7.setOnClickListener {
            clickEvnet(binding.space7)
        }
        binding.space8.setOnClickListener {
            clickEvnet(binding.space8)
        }
        binding.space9.setOnClickListener {
            clickEvnet(binding.space9)
        }
    }

    override fun onStop() {
        super.onStop()
        mediaplayer?.release()
        isPlaying = false
        Log.e("onStop", "OnStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy", "onDestroy")
    }

    override fun onStart() {
        super.onStart()

        isPlaying = true
        TimerThread().start()

        mediaplayer = MediaPlayer.create(this, R.raw.bgm)
        mediaplayer?.start()
    }

    inner class TimerThread: Thread() {
        override fun run() {
            while (isPlaying) {
                sleep(1000)
                runOnUiThread {
                    binding.tvTimer.text = "${Timer}초"
                }
                Timer -= 1
                if (Timer == 0) {
                    runOnUiThread {
                        Toast.makeText(this@MainActivity, "${score}점 입니다.!!", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        }
    }

    inner class GrowThread(private val iv: ImageView) : Thread() {
        override fun run() {
            sleep(1000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_2)
                iv.tag = R.drawable.ic_tomato_2
            }
            sleep(1000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_3)
                iv.tag = R.drawable.ic_tomato_3
            }
        }
    }

    inner class GrowThread2(private val iv: ImageView) : Thread() {
        override fun run() {
            sleep(1000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_5)
                iv.tag = R.drawable.ic_tomato_5
            }
            sleep(1000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_6)
                iv.tag = R.drawable.ic_tomato_6
            }
            sleep(1000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_7)
                iv.tag = R.drawable.ic_tomato_7
            }
        }
    }

    inner class GrowThread3(private val iv: ImageView) : Thread() {
        override fun run() {
            sleep(2000)
            runOnUiThread {
                iv.setImageResource(R.drawable.ic_tomato_8)
                iv.tag = R.drawable.ic_tomato_8
            }
        }
    }

    private fun getDrawable(iv: ImageView): Int? {
        return iv.tag as Int?
    }

    private fun initDrawable() {
        btn_list = arrayListOf(
            binding.seed, binding.watering, binding.sun, binding.finger
        )

        val iv_list = arrayListOf<ImageView>(
            binding.space1, binding.space2, binding.space3, binding.space4,
            binding.space5, binding.space6, binding.space7, binding.space8, binding.space9,
        )
        for (iv in iv_list) {
            iv.tag = R.drawable.ic_ground
        }
    }

    private fun changeBack(item: String) {
        for (iv in btn_list) {
            if (iv.tag == item) {
                iv.setBackgroundColor(Color.parseColor("#00EF0A"))
            } else {
                iv.setBackgroundColor(Color.parseColor("#00FFFFFF"))
            }
        }
    }

    private fun clickEvnet(iv: ImageView) {
        val currentDrawable = getDrawable(iv)

        if (state == "seed" && currentDrawable == R.drawable.ic_ground) {
            iv.setImageResource(R.drawable.ic_tomato_1)
            iv.tag = R.drawable.ic_tomato_1
            GrowThread(iv).start()
        }
        if (state == "watering" && currentDrawable == R.drawable.ic_tomato_3) {
            iv.setImageResource(R.drawable.ic_tomato_4)
            iv.tag = R.drawable.ic_tomato_4
            GrowThread2(iv).start()
        }
        if (state == "sun" && currentDrawable == R.drawable.ic_tomato_7) {
            GrowThread3(iv).start()
        }
        if (state == "finger" && currentDrawable == R.drawable.ic_tomato_8) {
            score = Integer.parseInt(binding.tvScore.text.toString())
            score += 100
            binding.tvScore.text = score.toString()
            iv.setImageResource(R.drawable.ic_ground)
            iv.tag = R.drawable.ic_ground
        }
    }
}