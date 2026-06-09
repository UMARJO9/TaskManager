package com.umar.taskmanager.data.local

import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.umar.taskmanager.data.local.convertor.Converters
import com.umar.taskmanager.data.local.dao.CommentDao
import com.umar.taskmanager.data.local.dao.TaskDao
import com.umar.taskmanager.data.local.dao.UserDao
import com.umar.taskmanager.data.local.entity.CommentEntity
import com.umar.taskmanager.data.local.entity.TaskEntity
import com.umar.taskmanager.data.local.entity.UserEntity


@Database(
    entities = [UserEntity::class, TaskEntity::class, CommentEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
    abstract fun commentDao(): CommentDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE tasks ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
            }
        }
    }
}
