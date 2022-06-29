package com.setruth.bookkeeping.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author  :Setruth
 * time     :2022/5/16 11:38
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

object DateTool {
    /**
     * TODO 获取今天的时间
     *
     * @return 返回yy/mm/dd格式的日期
     */
    @SuppressLint("SimpleDateFormat")
    fun getTodayDate(): String {
        // 设置默认时区为东八区
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        val dateLong = System.currentTimeMillis()
        val formatter = SimpleDateFormat("yyyy/MM/dd")
        //东八区比董酒区多一天 现在用的是东八区得时间
        //val formatter = SimpleDateFormat("yyyy/MM/dd").parse("1991-07-24 00:00:00") 91年以及之前是获取东九区
        //val formatter = SimpleDateFormat("yyyy/MM/dd").parse("1992-07-24 00:00:00") 92年以及之前是获取东八区
        val date = Date(dateLong)
        return formatter.format(date)
    }

    /**
     * TODO获取今天的时间戳
     *
     * @return
     */
    fun getTodayTimeStamp()=System.currentTimeMillis()
    /**
     * TODO 时间戳转换为yy/mm/dd日期的时间
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    fun timeStampToSimpleDate(time:Long):String{
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date(time)
        return formatter.format(date)
    }
    /**
     * TODO 获取今天的星期几
     *
     * @return
     */
    fun getViewNowWeekIndex(): String {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            1 -> "一"
            2 -> "二"
            3 -> "三"
            4 -> "四"
            5 -> "五"
            6 -> "六"
            else -> {
                ""
            }
        }
    }
}