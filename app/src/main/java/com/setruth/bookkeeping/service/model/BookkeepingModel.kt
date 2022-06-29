package com.setruth.bookkeeping.service.model


import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.setruth.bookkeeping.BR
import com.setruth.bookkeeping.pojo.constant.Constant


/**
 * @author  :Setruth
 * time     :2022/5/27 22:53
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class BookkeepingInfo : BaseObservable() {
    var billMode:Int= Constant.EXPENDITURE_PATTERN_TAG
    @get:Bindable
    var money:String = "0"
        set(value) {
            field=value
            notifyPropertyChanged(BR.money)
        }
    @get:Bindable
    var classification:String = "分类"
        set(value) {
            field=value
            notifyPropertyChanged(BR.classification)
        }
    @get:Bindable
    var remark:String = ""
        set(value) {
            field=value
            notifyPropertyChanged(BR.remark)
        }
}