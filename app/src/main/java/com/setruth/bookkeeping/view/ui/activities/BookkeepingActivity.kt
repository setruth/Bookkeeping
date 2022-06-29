package com.setruth.bookkeeping.view.ui.activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.transition.Explode
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.lifecycle.ViewModelProvider
import com.setruth.bookkeeping.databinding.ActivityBookkeepingLayoutBinding
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.utils.DateTool
import com.setruth.bookkeeping.view.ui.dialogs.ClassificationDialog
import com.setruth.bookkeeping.view.ui.dialogs.ConfirmDialog
import com.setruth.bookkeeping.viewModel.BookkeepingViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
* 忽略类型转换的警告 32排
* */
@Suppress("UNCHECKED_CAST")
class BookkeepingActivity : AppCompatActivity() {
    private var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                Constant.DAO_MESSAGE -> {
                    var msgData = msg.data
                    Toast.makeText(applicationContext,
                        "${msgData.getString(Constant.DAO_MESSAGE_KEY)}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //ViewModel
    private lateinit var bookkeepingViewModel: BookkeepingViewModel

    //分类弹窗
    private lateinit var classificationDialog: ClassificationDialog

    //数据layout绑定视图
    private lateinit var binding: ActivityBookkeepingLayoutBinding

    //模式的按钮列表
    private var modeBtnList: ArrayList<Button> = arrayListOf()

    //actionbar
    private lateinit var actionBar: ActionBar
    override fun onDestroy() {
        val billChangeTag = if (bookkeepingViewModel.allBillChangeTag.value!!) {
            Constant.ALL_BILL_CHANGE
        } else if (bookkeepingViewModel.expenditureBillChangeTag.value!!) {
            Constant.EXPENDITURE_BILL_CHANGE
        } else if (bookkeepingViewModel.incomeBillChangeTag.value!!) {
            Constant.INCOME_BILL_CHANGE
        } else {
            0 //没有变化
        }
        //有改变的时候发送广播
        if (billChangeTag != 0) {
            Intent().also {
                it.action= Constant.BILL_CHANGE_BROADCAST
                it.putExtra(Constant.BILL_CHANGE_BROADCAST_DATA_KEY,billChangeTag)
                sendBroadcast(it)
            }
        }
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.enterTransition = Explode()
        super.onCreate(savedInstanceState)
        binding = ActivityBookkeepingLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bookkeepingViewModel = ViewModelProvider(this)[BookkeepingViewModel::class.java]
        binding.bill = bookkeepingViewModel
        actionBar = supportActionBar!!
        initData()
        setListen()
        //默认账单是支出
        clickModeBtn(0)
    }

    /**
     * TODO 初始化基础数据
     *
     */
    private fun initData() {
        //装载两个模式按钮
        modeBtnList.add(binding.expenditureBtn)
        modeBtnList.add(binding.incomeBtn)
        actionBar.hide()
        //显示actionbar的返回键
        actionBar.setDisplayHomeAsUpEnabled(true)
        binding.nowDate.text = DateTool.getTodayDate()
    }

    /**
     * TODO 控件设置监听
     *
     */
    private fun setListen() {
        //模式按钮的监听
        modeBtnList.forEachIndexed { index, button ->
            button.setOnClickListener {
                clickModeBtn(index)
            }
        }
        //返回按钮的监听
        binding.backBtn.setOnClickListener {
            if(bookkeepingViewModel.model.value!!.remark!=""){
                backTip()
            }else{
                finish()
            }
        }
        //分类按钮的监听
        binding.classificationBtn.setOnClickListener {
            classificationDialog = ClassificationDialog(this, bookkeepingViewModel)
            classificationDialog.show()
        }
        //输入框中的监听
        binding.inputGridLayout.children.forEach { it ->
            it.setOnClickListener {
                updateMoney(it as Button)
            }
        }
    }

    /**
     * TODO 返回提示
     *
     */
    fun backTip(){
        var dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("返回")
            setMessage("返回会造成当前记录的数据丢失，你确定要返回嘛")
            setNegativeButton("确定", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    finish()
                }
            })
            show()
        }
    }
    /**
     * TODO 更新账单金额
     *
     * @param button 点击的按钮
     */
    @OptIn(DelicateCoroutinesApi::class)
    fun updateMoney(button: Button) {
        val model = bookkeepingViewModel.model.value!!
        when (button.text) {
            "删除" -> {
                if (model.money != "0") {
                    model.money =
                        model.money.removeRange(model.money.length - 1, model.money.length)
                    if (model.money.length == 0) {
                        model.money = "0"
                    }
                }
            }
            "清空" -> {
                model.money = "0"
                //更新model中的金额
            }
            "确认" -> {
                //判断是否有选择分类
                if (model.classification == "分类") {
                    AlertDialog.Builder(this).apply {
                        setTitle("没有选择分类")
                        setMessage("你没有选择分类，默认会是其他分类")
                        setNegativeButton("确认") { _, _ ->
                            model.classification = "其他"
                            showConfirmDialog()
                        }
                        show()
                    }
                } else {
                    showConfirmDialog()
                }
            }
            else -> {
                if (model.money == "0") {
                    model.money = ""
                }
                model.money += button.text
            }
        }
    }

    /**
     * TODO 监听返回按钮
     *
     */
    override fun onBackPressed() {
        if(bookkeepingViewModel.model.value!!.remark!=""){
            backTip()
        }else{
            super.onBackPressed()
        }
    }
    /**
     * TODO 更改记账模式
     *  @param index 点击的是第几个按钮
     */
    private fun clickModeBtn(index: Int) {
        if (index == 0) {
            modeBtnList[0].setBackgroundColor(Color.parseColor("#6AF9476C"))
            modeBtnList[1].setBackgroundColor(Color.parseColor("#FFF9476C"))
            binding.billModeTitle.text = "记账(支出)"
            bookkeepingViewModel.updateMode(Constant.EXPENDITURE_PATTERN_TAG)
        } else {
            modeBtnList[0].setBackgroundColor(Color.parseColor("#FFF9476C"))
            modeBtnList[1].setBackgroundColor(Color.parseColor("#6AF9476C"))
            binding.billModeTitle.text = "记账(收入)"
            bookkeepingViewModel.updateMode(Constant.INCOME_PATTERN_TAG)
        }
    }

    /**
     * TODO 显示确认弹窗
     *
     */
    private fun showConfirmDialog() {
        val confirmDialog =
            ConfirmDialog(this, bookkeepingViewModel.model.value!!, object : ConfirmDialog.Confirm {
                override fun confirm() {
                    GlobalScope.launch {
                        bookkeepingViewModel.saveBill { resTag, msg ->
                            val mBundle = Bundle()
                            if (resTag == 1) {
                                mBundle.putString(Constant.DAO_MESSAGE_KEY, msg)
                            } else {
                                mBundle.putString(Constant.DAO_MESSAGE_KEY, msg)
                            }
                            //发送一个带有数据的message去处理存储账单数据
                            handler.sendMessage(Message.obtain().apply {
                                what = Constant.DAO_MESSAGE
                                data = mBundle
                            })

                        }
                    }
                    //判断是哪一个账单变化了，设置账单改变标识
                    if (bookkeepingViewModel.model.value!!.billMode== Constant.EXPENDITURE_PATTERN_TAG){
                        bookkeepingViewModel.expenditureBillChangeTag.value=true
                    }else if(bookkeepingViewModel.model.value!!.billMode== Constant.INCOME_PATTERN_TAG){
                        bookkeepingViewModel.incomeBillChangeTag.value=true
                    }
                }
            })
        confirmDialog.show()
    }
}