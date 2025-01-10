package com.example.singandsongs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.singandsongs.data.DatabaseInit
import com.example.singandsongs.databinding.ActivityMainBinding
import com.example.singandsongs.ui.current_playlist.CurrentPlayListViewModel
import com.example.singandsongs.workmanager.scheduleDailyWork
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrentPlayListViewModel by viewModels()

    @Inject
    lateinit var databaseInitializer: DatabaseInit

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration : AppBarConfiguration

    private lateinit var sdCardReceiver: BroadcastReceiver

    private val PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        CoroutineScope(Dispatchers.IO).launch {
          databaseInitializer.initialize(applicationContext)
        }

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
              R.id.navigation_home,
              R.id.navigation_dashboard,
              R.id.navigation_calendar,
              R.id.navigation_notifications,
              R.id.currentPlayListFragment
            )
        )

      val targetFragment = intent.getStringExtra("TARGET_FRAGMENT")

      if (savedInstanceState == null) {
        when (targetFragment) {
          "NotificationsFragment" -> navigateToFragmentX()
          else -> navigateToDefaultFragment()
        }
      }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

      scheduleDailyWork(this)

        sdCardReceiver = object : BroadcastReceiver() {
          override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
              Intent.ACTION_MEDIA_MOUNTED -> {
                Toast.makeText(context, "SD Card Mounted", Toast.LENGTH_SHORT).show()
                viewModel.isActive()
              }
              Intent.ACTION_MEDIA_UNMOUNTED -> {
                Toast.makeText(context, "SD Card Unmounted", Toast.LENGTH_SHORT).show()
                viewModel.isInactive()
              }
            }
          }
        }

        val intentFilter = IntentFilter().apply {
          addAction(Intent.ACTION_MEDIA_MOUNTED)
          addAction(Intent.ACTION_MEDIA_UNMOUNTED)
          addDataScheme("file")
        }

        registerReceiver(sdCardReceiver, intentFilter)

    }

    private val databaseName = "CantoDatabase.db"

    private fun databaseExists(): Boolean {
        val databaseFile = File(this.getDatabasePath(databaseName).path)
        return databaseFile.exists()
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp(appBarConfiguration)
    }

    private fun navigateToFragmentX() {
      findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.navigation_notifications)
    }

    private fun navigateToDefaultFragment() {
      findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.currentPlayListFragment)
    }
 }
