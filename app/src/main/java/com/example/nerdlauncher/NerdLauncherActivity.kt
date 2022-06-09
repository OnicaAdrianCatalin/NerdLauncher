package com.example.nerdlauncher

import android.content.Intent
import android.content.Intent.ACTION_MAIN
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nerdlauncher.databinding.ActivityNerdLauncherBinding

class NerdLauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNerdLauncherBinding =
            ActivityNerdLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.appRecyclerView.layoutManager = LinearLayoutManager(this)
        setupAdapter(binding)
    }

    private fun setupAdapter(binding: ActivityNerdLauncherBinding) {
        val startupIntent = Intent(ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val activities = packageManager.queryIntentActivities(startupIntent, 0).map {
            Pair(it, it.loadLabel(packageManager).toString())
        }.sortedBy {
            it.second
        }

        Log.i(TAG, "found ${activities.size} activities")
        binding.appRecyclerView.adapter =
            ActivityAdapter(activities) { activityInfo ->
                val intent = Intent(ACTION_MAIN).apply {
                    setClassName(activityInfo.applicationInfo.packageName, activityInfo.name)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
            }
    }

    companion object {
        private const val TAG = "NerdLauncherActivity"
    }
}
