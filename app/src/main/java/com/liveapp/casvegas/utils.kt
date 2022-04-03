package com.liveapp.casvegas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

const val LINK = "https://wetyop.fun/j7s8fMKb"

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity, url:String) {
    val intent = Intent(this, activity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    intent.putExtra("url", url)
    startActivity(intent)
    this.finish()
}