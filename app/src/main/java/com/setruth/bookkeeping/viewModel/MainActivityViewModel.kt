package com.setruth.bookkeeping.viewModel

import android.app.Application
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.setruth.bookkeeping.dao.entity.ExpenditureEntity
import com.setruth.bookkeeping.dao.entity.IncomeEntity
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.utils.BillUtil
import com.setruth.bookkeeping.utils.DateTool
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.ColumnChartData
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue

/**
 * @author  :Setruth
 * time     :2022/6/7 9:15
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val billUtil = BillUtil(application)


    // 当前页面标识
    val nowPageTag: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(Constant.NOW_IS_HOME_TAG)
    }

    // 支出得金额
    val expenditureMoney by lazy {
        MutableLiveData(0f)
    }

    // 收入得金额
    val incomeMoney by lazy {
        MutableLiveData(0f)
    }

    // 消费总计
    val buyCountMoney by lazy {
        MutableLiveData(0f)
    }

    // 今天的日期
    var nowDate = DateTool.getTodayDate()

    //今日的消费账单列表
    val expenditureBIllList by lazy {
        MutableLiveData<MutableList<ExpenditureEntity>>()
    }

    //今日的收入账单列表
    val incomeBIllList by lazy {
        MutableLiveData<MutableList<IncomeEntity>>()
    }

    //消费账单信息
    val buyBillInfo by lazy {
        MutableLiveData<Map<String, Float>>()
    }
    /**
     * TODO 设置收入账单列表
     *
     * @param date
     */
    fun setIncomeBillList(date: String) {
        incomeBIllList.postValue(billUtil.getIncomeBill(date))
    }

    /**
     * TODO 设置支出账单列表
     *
     * @param date 日期
     */
    fun setExpenditureBillList(date: String) {
        expenditureBIllList.postValue(billUtil.getExpenditureBill(date))
    }
    /**
     * TODO 设置账单的信息
     *
     * @param date 日期
     */
    fun setBillCountInfo(date: String) {
        buyBillInfo.postValue(billUtil.getBuyHistory(date))
    }

    /**
     * TODO 今日的消费
     * @param date 日期
     */
    @DelicateCoroutinesApi
    fun initBIllData(date: String) {
        //在携程中进行数据库操作
        GlobalScope.launch {
            setBillCountInfo(date)
            setExpenditureBillList(date)
            setIncomeBillList(date)
        }
    }

    /**
     * TODO 初始化饼图的配置
     * @return 返回数据对象
     */
    private fun initMoneyProportionPieChartViewConfig():PieChartData{
        // 饼状图数据对象
        val pieChartViewDataPoj = PieChartData(getMoneyProportionPieChartViewData())
        pieChartViewDataPoj.setHasLabels(true)
        pieChartViewDataPoj.setHasLabelsOutside(false);//设置饼图外面是否显示值
        pieChartViewDataPoj.setHasCenterCircle(true);//设置饼图中间是否有第二个圈
//        pieChartViewData.setCenterCircleColor();//设置饼图中间圈的颜色
//        pieChartViewDataPoj.centerCircleScale = 2f;////设置第二个圈的大小比例
        pieChartViewDataPoj.centerText1 = "今日消费";//设置文本
        pieChartViewDataPoj.centerText1Color = Color.parseColor("#FDD5C8");//设置文本颜色
        pieChartViewDataPoj.centerText1FontSize = 18;//设置文本大小
        pieChartViewDataPoj.centerText2 = "总计${buyCountMoney.value}"//设置第二个圈文本
        pieChartViewDataPoj.centerText2FontSize = 12
        pieChartViewDataPoj.centerText2Color = Color.parseColor("#FDD5C8")
//        pieChartViewData.setCenterText2Color(int centerText2Color);//设置第二个圈文本颜色
//        pieChartViewDataPoj.setValueLabelsTextColor(Color.parseColor("#ffffff"));//设置显示值的字体颜色
        pieChartViewDataPoj.slicesSpacing = 2;//设置数据间的间隙
        pieChartViewDataPoj.setHasLabelsOnlyForSelected(false);//设置当值被选中才显示
        pieChartViewDataPoj.isValueLabelBackgroundEnabled=false
        return  pieChartViewDataPoj
    }
    /**
     * TODO 初始化金额占比饼图的数据
     * @return 返回数据源
     */
    private fun getMoneyProportionPieChartViewData():MutableList<SliceValue>{
        val data= mutableListOf<SliceValue>()
        data.add(SliceValue(expenditureMoney.value!!.toFloat(),Color.parseColor("#FF8A65")))
        data.add(SliceValue(incomeMoney.value!!.toFloat(), Color.parseColor("#FF018786")))
        return data
    }
    /**
     * TODO 获取饼图数据对象
     *
     * @return 返回图标的数据对象
     */
    fun getMoneyProportionPieCHartViewDataPoj()=initMoneyProportionPieChartViewConfig()

    /**
     * TODO 初始化所有天账单的柱状图配置
     *
     */
    fun getColumnChartDataPoj(){
        val columnChartData: ColumnChartData
    }

    /**
     * TODO 获取Y轴数据
     *
     * @return返回X轴数据
     */
    fun getAxisYData():Axis{
        return Axis().setHasLines(true)
    }

    /**
     * TODO 获取X轴数据
     *
     * @return返回X轴数据
     */
    fun getAxisXData():Axis{
        val axis=Axis()
        axis.name="日期"
        return axis
    }
}