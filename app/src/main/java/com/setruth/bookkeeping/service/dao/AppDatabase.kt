package com.setruth.bookkeeping.service.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.setruth.bookkeeping.dao.impl.ExpenditureDao
import com.setruth.bookkeeping.dao.impl.IncomeDao
import com.setruth.bookkeeping.dao.entity.ExpenditureEntity
import com.setruth.bookkeeping.dao.entity.IncomeEntity

/**
 * @author  :Setruth
 * time     :2022/5/27 11:00
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */
@Database(
    version = 1,
    entities = [ExpenditureEntity::class, IncomeEntity::class],
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private val databaseName: String = "bookkeeping"
        private var appDatabase: AppDatabase? = null

        fun getDb(context: Context): AppDatabase = if (appDatabase == null) {
            appDatabase =
                Room.databaseBuilder(context, AppDatabase::class.java, databaseName).build()
            appDatabase!!
        } else {
            appDatabase!!
        }
    }

    abstract fun getExpenditureDao(): ExpenditureDao
    abstract fun getIncomeDao(): IncomeDao
}