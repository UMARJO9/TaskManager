package com.umar.taskmanager.data.session

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umar.taskmanager.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")
class SessionManager(
    private val dataStore: DataStore<Preferences>
) : SessionRepository {
    private val userIdKey = longPreferencesKey("user_id")

    override fun observeUserId(): Flow<Long?> =
        dataStore.data.map { prefs -> prefs[userIdKey] }

    override suspend fun saveUserId(userId: Long) {
        dataStore.edit { prefs -> prefs[userIdKey] = userId }
    }

    override suspend fun clear() {
        dataStore.edit { prefs -> prefs.remove(userIdKey) }
    }
}