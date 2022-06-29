package com.setruth.bookkeeping.view.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import com.setruth.bookkeeping.databinding.ConfirmDialogLayoutBinding
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.service.model.BookkeepingInfo

/**
 * @author  :Setruth
 * time     :2022/5/16 12:02
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class ConfirmDialog(context: Context, data: BookkeepingInfo, confirmListen:Confirm) : Dialog(context) {
    interface Confirm {
        fun confirm()
    }
    private  var confirmListen:Confirm
    private  var binding:ConfirmDialogLayoutBinding
    init {
        this.confirmListen=confirmListen
        binding=ConfirmDialogLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.money.text = data.money
        if (data.remark==""){
            binding.remark.text ="暂无备注"
        }else
            binding.remark.text=data.remark

        binding.billMode.text=if(data.billMode== Constant.EXPENDITURE_PATTERN_TAG){
            "支出"
        }else{
            "收入"
        }
        binding.classification.text=data.classification
        binding.confirmBtn.setOnClickListener {
            confirmListen.confirm()
            dismiss()
        }
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
    }

}