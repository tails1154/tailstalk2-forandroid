package com.tails1154.tailstalk2.persistence

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.tails1154.tailstalk2.StoatApplication

object SqlStorage {
    val driver: SqlDriver = AndroidSqliteDriver(
        Database.Schema,
        StoatApplication.instance.applicationContext,
        "revolt.db"
    )
}