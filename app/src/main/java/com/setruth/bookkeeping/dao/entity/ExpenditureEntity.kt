package com.setruth.bookkeeping.dao.entity


import androidx.room.Entity

/**
 * @author  :Setruth
 * time     :2022/5/27 15:17
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */


@Entity(tableName = "expenditure")
class ExpenditureEntity(money: Float, remark: String,date:String,detailsDate: Long,classification: String) : BaseEntity(money, remark, date,detailsDate,classification) {
}