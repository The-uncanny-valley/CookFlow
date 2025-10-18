package com.uncannyvalley.cookflow.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1,2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            "ALTER TABLE recipes ADD COLUMN dishTypes TEXT DEFAULT '' NOT NULL"
        )

        // try to migrate old data (if old column existed)
        try {
            db.execSQL(
                "UPDATE recipes SET dishTypes = dishType WHERE dishType IS NOT NULL"
            )
            android.util.Log.d("Migration_1_2", "Migrated dishType → dishTypes successfully.")
        } catch (e: Exception) {
            // safe fallback if the old column doesn't exist (e.g. clean install)
            android.util.Log.w(
                "Migration_1_2",
                "No old dishType column found — skipping data migration: ${e.message}"
            )
        }
    }
}