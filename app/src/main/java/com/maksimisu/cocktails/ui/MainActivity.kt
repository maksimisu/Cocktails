package com.maksimisu.cocktails.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.maksimisu.cocktails.R
import com.maksimisu.cocktails.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        root = binding.root
        setContentView(root)
        val navMain = findViewById<View>(R.id.navMain)
        binding.bottomNavigationView.setupWithNavController(navMain.findNavController())
    }

}