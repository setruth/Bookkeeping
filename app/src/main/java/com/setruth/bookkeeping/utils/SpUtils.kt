package com.setruth.bookkeeping.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.setruth.bookkeeping.pojo.constant.Constant

/**
 * @author  :Setruth
 * time     :2022/5/12 17:36
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class SpUtils(context: Context,fileName:String) {
    private var sharedPreferences:SharedPreferences
    init {
        sharedPreferences=context.getSharedPreferences(fileName,Context.MODE_PRIVATE)
    }
    /**
     * TODO 设置内容
     *
     * @param key
     * @param any 值
     */
    @SuppressLint("CommitPrefEdits")
    fun setItem(key:String, any: Any){
        val edit=sharedPreferences.edit()
        when(any){
            Int->{
                edit.putInt(key,any as Int)
            }
            Float->{
                edit.putFloat(key,any as Float)
            }
            Boolean->{
                edit.putBoolean(key,any as Boolean)
            }
            Long->{
                edit.putLong(key,any as Long)
            }
             String->{
                edit.putString(key,any as String)
            }
        }
        //判断保存的key是不是分类的保存到分类的键
        if (key== Constant.SP_CLASSIFICATION_EXPENDITURE_KEY||key== Constant.SP_CLASSIFICATION_INCOME_KEY){
            edit.putStringSet(key,any as MutableSet<String>)
        }
        edit.commit()
    }

    /**
     * TODO 获取item项的值
     *
     * @param key
     * @param defaultValue 如果值不存在 返回的默认值
     * @return 返回为null的时候就是没有defaultValue这种类型的get
     */
    fun getItem(key:String,defaultValue:Any?):Any? = when (defaultValue) {
        is Int -> {
            sharedPreferences.getInt(key,defaultValue )
        }
        is Float -> {
            sharedPreferences.getFloat(key,defaultValue )
        }
        is Boolean -> {
            sharedPreferences.getBoolean(key,defaultValue )
        }
        is Long -> {
            sharedPreferences.getLong(key,defaultValue )
        }
        is String -> {
            sharedPreferences.getString(key,defaultValue )
        }
        is MutableSet<*> -> {
            sharedPreferences.getStringSet(key, defaultValue as MutableSet<String>?)
        }
        else -> {
            null
        }
    }
}