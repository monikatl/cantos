package com.example.singandsongs.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesManager @Inject constructor(
  private val dataStore: DataStore<Preferences>
) {

  companion object {
    val ENABLE_QUEUE = booleanPreferencesKey("enable_queue")
  }

  val enableQueueFlow: Flow<Boolean> = dataStore.data.map { preferences ->
    preferences[ENABLE_QUEUE] ?: false
  }

  suspend fun setEnableQueue(enable: Boolean) {
    dataStore.edit { preferences ->
      preferences[ENABLE_QUEUE] = enable
    }
  }
}
