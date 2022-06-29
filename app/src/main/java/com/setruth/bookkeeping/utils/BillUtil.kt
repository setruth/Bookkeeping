package com.setruth.bookkeeping.utils

import android.content.Context
import android.util.Log
import com.setruth.bookkeeping.dao.entity.ExpenditureEntity
import com.setruth.bookkeeping.dao.entity.IncomeEntity
import com.setruth.bookkeeping.dao.impl.ExpenditureDao
import com.setruth.bookkeeping.dao.impl.IncomeDao
import com.setruth.bookkeeping.service.dao.AppDatabase

/**
 * @author  :Setruth
 * time     :2022/6/7 16:27
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class BillUtil(context: Context) {
    var appDatabase = AppDatabase.getDb(context)
    var expenditureDao: ExpenditureDao = appDatabase.getExpenditureDao()
    var incomeDao: IncomeDao = appDatabase.getIncomeDao()

    init {
    }

    /**
     * TODO 获取支出账单
     *
     * @param dateTime 哪天得账单
     * @return 返回账单列表
     */
    fun getExpenditureBill( dateTime: String): MutableList<ExpenditureEntity> = expenditureDao.getToDayBill(dateTime)
    /**
     * TODO 获取收入账单
     *
     * @param dateTime 哪天得账单
     * @return 返回账单列表
     */
    fun getIncomeBill( dateTime: String): MutableList<IncomeEntity> = incomeDao.getToDayBill(dateTime)

    /**
     * TODO 获取消费记录
     *
     * @param dateTime 哪天得消费记录
     * @return 返回消费总计 expenditure 支出金额，income 收入金额
     */
    fun getBuyHistory(dateTime: String): Map<String, Float> {
        var expenditureMoneyCount: Float = 0f
        var incomeMoneyCount: Float = 0f
        getExpenditureBill(dateTime).forEach {

            expenditureMoneyCount += it.money
        }
        getIncomeBill(dateTime).forEach {
            incomeMoneyCount += it.money
        }
        return mapOf("expenditure" to expenditureMoneyCount, "income" to incomeMoneyCount)
    }
}