package com.example.singandsongs.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PreferencesManager @Inject constructor(
  @ApplicationContext private val context: Context,
  private val dataStore: DataStore<Preferences>
) {

  companion object {
    val ENABLE_QUEUE = booleanPreferencesKey("enable_feature_key")
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
