package com.setruth.bookkeeping.view.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import com.setruth.bookkeeping.databinding.ClassificationDialogLayoutBinding
import com.setruth.bookkeeping.viewModel.BookkeepingViewModel

/**
 * @author  :Setruth
 * time     :2022/5/16 19:42
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */
class ClassificationDialog(context: Context, viewModel: BookkeepingViewModel) : Dialog(context) {

    private var binding: ClassificationDialogLayoutBinding =
        ClassificationDialogLayoutBinding.inflate(layoutInflater)
    var viewModel: BookkeepingViewModel

    init {
        setContentView(binding.root)
        this.viewModel = viewModel
        //获取分类内容
        viewModel.getClassification()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)
        binding.addNewClassificationBtn.setOnClickListener {
            AlertDialog.Builder(context).apply {
                val newClassificationContent = binding.newClassificationContent.text.toString()
                if (newClassificationContent == "") {
                    Toast.makeText(context, "自定义不能为空", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addClassification(newClassificationContent)
                    binding.classificationContentContainer.removeAllViews()
                    renderingClassificationContent()
                }
            }
        }
        //初始化渲染内容
        renderingClassificationContent()
    }

    /**
     * TODO 渲染分类内容
     *
     */
    fun renderingClassificationContent() {
         var lineLayout = LinearLayout(context)
        viewModel.classificationList.value!!.forEachIndexed{index,it->
            if (index%4==0){
                lineLayout = LinearLayout(context)
                lineLayout.orientation = LinearLayout.HORIZONTAL
                lineLayout.weightSum = 1f
                lineLayout.addView(getClassificationBtn(it))
                binding.classificationContentContainer.addView(lineLayout)
            }else{
                lineLayout.addView(getClassificationBtn(it))
            }
        }
    }

    /**
     * TODO 获取分类项按钮
     *
     * @param content 分类项名字
     * @return 返回按钮
     */
    fun getClassificationBtn(content: String): Button {
        var classification_item = Button(context)
        classification_item.text = content
        classification_item.setOnClickListener {
            viewModel.model.value!!.classification=content
            dismiss()
        }
        return classification_item
    }

}