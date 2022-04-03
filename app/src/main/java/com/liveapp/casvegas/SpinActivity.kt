package com.liveapp.casvegas

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.liveapp.casvegas.databinding.ActivitySpinBinding

class SpinActivity : AppCompatActivity() {
    lateinit var binding: ActivitySpinBinding
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spin)

        binding = ActivitySpinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null){
            score = savedInstanceState.getInt("Score", 0)
            binding.scoreTextView.text = score.toString()
        }
        init()
    }

    private fun init() {
        binding.buttonSpin.setOnClickListener {
            binding.imageWheel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_center))
            score = randomScore()
            binding.scoreTextView.text = score.toString()
        }
    }

    private fun randomScore(): Int {
        when ((1 until 4).random()) {
            1 -> return 10
            2 -> return 15
            3 -> return 20
        }
        return 0
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("Score", score)
    }
}