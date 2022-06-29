package com.setruth.bookkeeping.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author  :Setruth
 * time     :2022/5/12 17:01
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

@RequiresApi(Build.VERSION_CODES.P)
class DetabaseHelper(
    context: Context?,
    name: String?,
    version: Int,
    openParams: SQLiteDatabase.OpenParams
) : SQLiteOpenHelper(context, name, version, openParams) {
    override fun onCreate(p0: SQLiteDatabase?) {

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}