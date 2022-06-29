package com.setruth.bookkeeping.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.setruth.bookkeeping.dao.entity.ExpenditureEntity
import com.setruth.bookkeeping.dao.entity.IncomeEntity
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.service.dao.AppDatabase
import com.setruth.bookkeeping.service.model.BookkeepingInfo
import com.setruth.bookkeeping.utils.DateTool
import com.setruth.bookkeeping.utils.SpUtils
import java.util.*

/**
 * @author  :Setruth
 * time     :2022/5/27 22:14
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class BookkeepingViewModel(application: Application) : AndroidViewModel(application) {
    //所有账单改变标识
    val allBillChangeTag by lazy {
        MutableLiveData<Boolean>(false)
    }
    // 消费账单是否改变标识
    val expenditureBillChangeTag by lazy {
        MutableLiveData(false)
    }
    //收入账单是否变化标识
    val incomeBillChangeTag by lazy {
        MutableLiveData(false)
    }
    @SuppressLint("StaticFieldLeak")
    val context: Context
    val TAG = "BookkeepingViewModel"
    //shared preferences工具
    val spUtils = SpUtils(application, Constant.SHARED_PREFERENCES_NAME)

    //分类信息列表
    lateinit var classificationList: MutableLiveData<MutableSet<String>>


    //账单信息
    val model: MutableLiveData<BookkeepingInfo> by lazy {
        MutableLiveData<BookkeepingInfo>(BookkeepingInfo())
    }
    init {
        context = application
        //消费账单改变观察
        expenditureBillChangeTag.observeForever {
            //如果改变与另一个账单信息进行与或判断所有账单是否改变
            allBillChangeTag.value= it&&incomeBillChangeTag.value!!
        }
        //支出账单改变观察
        incomeBillChangeTag.observeForever {
            //如果改变与另一个账单信息进行与或判断所有账单是否改变
            allBillChangeTag.value= it&&expenditureBillChangeTag.value!!
        }
    }

    /**
     * TODO 获取分类的内容
     *
     */
    fun getClassification() {
        if (model.value!!.billMode == Constant.EXPENDITURE_PATTERN_TAG) {
            classificationList=MutableLiveData(spUtils.getItem(Constant.SP_CLASSIFICATION_EXPENDITURE_KEY,
                Constant.DEFAULT_EXPENDITURE_CLASSIFICATION) as MutableSet<String>?)
        } else {
            classificationList=MutableLiveData(spUtils.getItem(Constant.SP_CLASSIFICATION_INCOME_KEY,
                Constant.DEFAULT_INCOME_CLASSIFICATION) as MutableSet<String>?)
        }
        Log.e(TAG, "getClassification: ${classificationList.value!!.size}")
    }

    /**
     * TODO 添加新的分类
     *
     * @param newClassification 分类内容
     */
    fun addClassification(newClassification:String){
        classificationList.value!!.add(newClassification)
        //添加/覆盖shared preference中的分类
        if (model.value!!.billMode== Constant.EXPENDITURE_PATTERN_TAG){

            spUtils.setItem(Constant.SP_CLASSIFICATION_EXPENDITURE_KEY,classificationList.value!!)
        }else{
            spUtils.setItem(Constant.SP_CLASSIFICATION_INCOME_KEY,classificationList.value!!)
        }
    }
    /**
     * TODO 保存账单
     *
     * @param context
     * @param res
     */
    suspend fun saveBill(res: (resTag: Int, message: String) -> Unit) {
        val data = model.value!!
        val appDatabase = AppDatabase.getDb(context)
        if (data.billMode == Constant.EXPENDITURE_PATTERN_TAG) {
            val expenditureDaoImpl = appDatabase.getExpenditureDao()
            try {
                val money = data.money.toFloat()
                expenditureDaoImpl.insert(ExpenditureEntity(money,data.remark,DateTool.getTodayDate(),System.currentTimeMillis(),data.classification))
                res(1, "保存成功")
            } catch (e: Exception) {
                res(0, "您的金额格式有误，请检查")
            }
        }else{
            val expenditureDaoImpl = appDatabase.getIncomeDao()
            try {
                val money = data.money.toFloat()
                expenditureDaoImpl.addNewBill(IncomeEntity(money,data.remark,DateTool.getTodayDate(),System.currentTimeMillis(),data.classification))
                res(1, "保存成功")
            } catch (e: Exception) {
                res(0, "您的金额格式有误，请检查")
            }
        }
    }

    /**
     * TODO 更新账单的模式
     *
     * @param mode 传入的模式
     */
    fun updateMode(pattern: Int) {
        model.value!!.billMode = pattern
        model.value!!.classification="分类"
    }
}