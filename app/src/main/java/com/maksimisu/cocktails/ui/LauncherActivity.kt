package com.maksimisu.cocktails.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.maksimisu.cocktails.databinding.ActivityLauncherBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLauncherBinding.inflate(layoutInflater)
        root = binding.root
        setContentView(root)

        GlobalScope.launch(Dispatchers.Main) {
            delay(2000L)
            startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
            finish()
        }

    }
}