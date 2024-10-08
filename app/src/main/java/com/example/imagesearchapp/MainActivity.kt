package com.example.imagesearchapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagesearchapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ViewPager 관련 메소드 호출
        setViewPager()
    }

    private fun setViewPager() {
        val items = tabText()

        with(binding) {
            viewPager.adapter = ViewPagerAdapter(this@MainActivity)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = items[position]
                tab.icon = when (position) {
                    0 -> ContextCompat.getDrawable(this@MainActivity, R.drawable.home)
                    else -> ContextCompat.getDrawable(this@MainActivity, R.drawable.choice_icon)
                }
            }.attach()
        }
    }

    @SuppressLint("RestrictedApi")
    private fun tabText(): List<String> {
        val tabText = MenuBuilder(this)
        menuInflater.inflate(R.menu.tab_layout_text, tabText)

        val showTab = mutableListOf<String>()

        for (i in 0 until tabText.size()) {
            val tabTitle = tabText.getItem(i).title.toString()
            showTab.add(tabTitle)
        }

        return showTab
    }
}
