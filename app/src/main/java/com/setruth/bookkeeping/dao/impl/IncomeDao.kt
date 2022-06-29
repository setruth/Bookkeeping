package com.setruth.bookkeeping.dao.impl

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.setruth.bookkeeping.dao.entity.BaseEntity
import com.setruth.bookkeeping.dao.entity.IncomeEntity

/**
 * @author  :Setruth
 * time     :2022/5/27 11:25
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */
@Dao
interface IncomeDao {
    @Query("select * from income")
    fun getALl(): MutableList<IncomeEntity>
    @Insert
    fun addNewBill(incomeEntity: IncomeEntity)
    @Query("select * from income where date = :time ")
    fun getToDayBill(time:String):MutableList<IncomeEntity>
}