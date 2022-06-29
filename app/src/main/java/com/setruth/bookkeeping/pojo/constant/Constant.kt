package com.setruth.bookkeeping.pojo.constant

/**
 * @author  :Setruth
 * time     :2022/5/11 20:33
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

object Constant {
    //Dao的handler消息key
    const val DAO_MESSAGE_KEY = "daoMessage"

    //Dao的消息what
    const val DAO_MESSAGE = 1

    //MainActivity的TAG
    const val LOG_TAG = "MainActivity"

    //SharedPreferences的文件名字
    const val SHARED_PREFERENCES_NAME = "config"

    // 支出分类的默认内容
    val DEFAULT_EXPENDITURE_CLASSIFICATION =
        hashSetOf("饮食", "出行", "住宿", "衣着", "运动", "医疗", "学习", "购物", "生活", "烟酒", "其他")

    //支出分类的默认分类
    val DEFAULT_INCOME_CLASSIFICATION = mutableSetOf("工资", "生活费", "奖金", "兼职")

    // 数据库名字
    const val DATABASE_NAME = "bookkeeping.db"

    // 数据类版本
    const val DATABASE_VERSION = 1

    //支出模式的标签
    const val EXPENDITURE_PATTERN_TAG = 1

    //收入模式的标签
    const val INCOME_PATTERN_TAG = 0

    //支出分类在shared preferences的key
    const val SP_CLASSIFICATION_EXPENDITURE_KEY = "expenditure_classification"

    //收入分类在shared preferences中的key
    const val SP_CLASSIFICATION_INCOME_KEY = "income_classification"

    //当前是主页面标识
    const val NOW_IS_HOME_TAG=1

    //当前是历史页面标识
    const val NOW_IS_HISTORY_TAG=0
    //主页的tab的选项
     val HOME_TAB_ITEM_TITLE_ARRAY= arrayOf("支出","收入")
    // 消费账单变化
    const val EXPENDITURE_BILL_CHANGE=3
    // 收入账单变化
    const val INCOME_BILL_CHANGE=2
    // 所有账单变化
    const val ALL_BILL_CHANGE=1
    // 账单变化广播
    const val BILL_CHANGE_BROADCAST="Bill_Change"
    //获取账单广播携带变化内容数据的Key
    const val BILL_CHANGE_BROADCAST_DATA_KEY="bill_change"
}