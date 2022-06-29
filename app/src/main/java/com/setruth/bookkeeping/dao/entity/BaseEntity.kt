package com.setruth.bookkeeping.dao.entity

import android.util.Log
import android.view.textclassifier.TextClassification
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * @author  :Setruth
 * time     :2022/6/6 22:33
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

open class BaseEntity(
    @ColumnInfo(name = "money") var money: Float,//金额
    @ColumnInfo(name = "remark") var remark: String,// 备注
    @ColumnInfo(name = "date") var date: String,//日期
    @ColumnInfo(name = "details_date")var detailsDate:Long,//详细日期
    @ColumnInfo(name = "classification")var classification: String//分类
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    override fun toString(): String {
        return "BaseEntity(id=$id, money=$money, remark='$remark', date=$date)"
    }
}