package com.setruth.bookkeeping.dao.impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.setruth.bookkeeping.dao.entity.BaseEntity
import com.setruth.bookkeeping.dao.entity.ExpenditureEntity

/**
 * @author  :Setruth
 * time     :2022/5/27 11:25
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */
@Dao
interface ExpenditureDao {
    @Insert
    suspend fun insert(expenditureEntity: ExpenditureEntity)

    @Query("select * from expenditure")
    suspend fun getAll():MutableList<ExpenditureEntity>

    @Query("select * from expenditure where date = :time ")
    fun getToDayBill(time:String):MutableList<ExpenditureEntity>
}