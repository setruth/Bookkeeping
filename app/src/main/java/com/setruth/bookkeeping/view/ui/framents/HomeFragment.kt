package com.setruth.bookkeeping.view.ui.framents

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.setruth.bookkeeping.R
import com.setruth.bookkeeping.adapter.HomeListViewAdapter
import com.setruth.bookkeeping.adapter.HomeViewPageAdapter
import com.setruth.bookkeeping.dao.entity.BaseEntity
import com.setruth.bookkeeping.databinding.FragmentHomeBinding
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.utils.DateTool
import com.setruth.bookkeeping.viewModel.MainActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    //页面的bindingData
    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    //view model
    private lateinit var viewModel: MainActivityViewModel


    //viewPage的消费页面
    private lateinit var viewPageExpenditureView: View

    //viewPage的收入页面
    private lateinit var viewPageIncomeView: View

    // 账单变化广播接收者
    lateinit var billChangeBroadcastReceiver: BillChangeBroadcastReceiver

    //消费页面的列表
    lateinit var expenditureListView: ListView

    //收入页面中的列表
    lateinit var incomeListView: ListView

    // 消费列表适配器
    lateinit var expenditureListViewAdapter: HomeListViewAdapter

    // 收入列表适配器
    lateinit var incomeListViewAdapter: HomeListViewAdapter


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        billChangeBroadcastReceiver = BillChangeBroadcastReceiver()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Constant.BILL_CHANGE_BROADCAST)
        }
        activity!!.registerReceiver(billChangeBroadcastReceiver, filter)

        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        fragmentHomeBinding.viewModel = viewModel
        fragmentHomeBinding.timeInfo = DateTool
        //异步协程获取今天的账单
        viewModel.initBIllData(DateTool.getTodayDate())
        initUI()
        initListener()
        runObserver()
        return fragmentHomeBinding.root
    }

    /**
     * TODO 初始化监听
     *
     */
    fun initListener() {

    }

    /**
     * TODO 启动viewModel中的数据观察者
     *
     */
    fun runObserver(){
        val TAG="runObserver"
        viewModel.expenditureBIllList.observe(requireActivity()) {
            renderingExpenditureList()
        }
        viewModel.incomeBIllList.observe(requireActivity()) {
            renderingIncomeList()
        }
        viewModel.buyBillInfo.observe(requireActivity()) {
            updateToDayBillInfo()
        }
    }

    /**
     * TODO 更新今日的账单信息
     *
     */
    fun updateToDayBillInfo(){
        viewModel.expenditureMoney.value= viewModel.buyBillInfo.value!!["expenditure"]!!
        viewModel.incomeMoney.value= viewModel.buyBillInfo.value!!["income"]!!
        viewModel.buyCountMoney.value= viewModel.incomeMoney.value!!- viewModel.expenditureMoney.value!!
        fragmentHomeBinding.lifecycleOwner=this //添加这句才会在观察的时候设置数据时视图也更新数据
    }
    /**
     * TODO 渲染消费列表
     *
     */
    fun renderingExpenditureList(){
        expenditureListViewAdapter = HomeListViewAdapter(expenditureListView.context,
            R.layout.home_list_view_item,
            viewModel.expenditureBIllList.value as MutableList<BaseEntity>)
        expenditureListView.adapter = expenditureListViewAdapter
    }

    /**
     * TODO 渲染收入列表
     *
     */
    fun renderingIncomeList(){
        incomeListViewAdapter = HomeListViewAdapter(viewPageIncomeView.context,
            R.layout.home_list_view_item,
            viewModel.incomeBIllList.value as MutableList<BaseEntity>)
        incomeListView.adapter = incomeListViewAdapter
    }
    /**
     * TODO 初始化ui
     *
     */
    @SuppressLint("InflateParams")
    fun initUI() {
        viewPageExpenditureView =
            layoutInflater.inflate(R.layout.home_tab_expentiture_content_layout, null, true)
        viewPageIncomeView =
            layoutInflater.inflate(R.layout.home_tab_income_content_layout, null, true)
        val list = mutableListOf(viewPageExpenditureView, viewPageIncomeView)
        val homeViewPageAdapter = HomeViewPageAdapter(list)
        fragmentHomeBinding.homeViewPage.adapter = homeViewPageAdapter
        //把viewpage关联到tabLayout
        fragmentHomeBinding.homeTab.setupWithViewPager(fragmentHomeBinding.homeViewPage)
        /*
        * setupWithViewPager会把原有的标题全部移除
        * 所以我们要在关联后设置标题，以免被移除
        * */
        for (i in 0 until fragmentHomeBinding.homeTab.tabCount) {
            fragmentHomeBinding.homeTab.getTabAt(i)!!.text = Constant.HOME_TAB_ITEM_TITLE_ARRAY[i]
        }
        expenditureListView = viewPageExpenditureView.findViewById<ListView>(R.id.expenditure_list_view)
        incomeListView = viewPageIncomeView.findViewById<ListView>(R.id.income_list_view)
    }
    //账单变化广播
    inner class BillChangeBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            var date=DateTool.getTodayDate()
            GlobalScope.launch {
                viewModel.setBillCountInfo(date)
                when (p1!!.getIntExtra(Constant.BILL_CHANGE_BROADCAST_DATA_KEY, 0)) {
                    Constant.INCOME_BILL_CHANGE -> {
                        viewModel.setIncomeBillList(date)
                        expenditureListViewAdapter.notifyDataSetChanged()
                    }
                    Constant.EXPENDITURE_BILL_CHANGE -> {
                        viewModel.setExpenditureBillList(date)
                        expenditureListViewAdapter.notifyDataSetChanged()
                    }
                    Constant.ALL_BILL_CHANGE -> {
                        viewModel.setExpenditureBillList(date)
                        viewModel.setIncomeBillList(date)
                        expenditureListViewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}