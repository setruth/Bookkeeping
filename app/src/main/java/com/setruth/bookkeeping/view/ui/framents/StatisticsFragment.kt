package com.setruth.bookkeeping.view.ui.framents

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.setruth.bookkeeping.databinding.FragmentStatisticsBinding
import com.setruth.bookkeeping.viewModel.MainActivityViewModel

class StatisticsFragment : Fragment() {
    lateinit var fragmentStatisticsBinding:FragmentStatisticsBinding
    lateinit var mainActivityViewModel: MainActivityViewModel
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        fragmentStatisticsBinding= FragmentStatisticsBinding.inflate(inflater)
        mainActivityViewModel= ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        initUI()
        return fragmentStatisticsBinding.root
    }
    /**
     * TODO 初始化ui
     *
     */
    fun initUI(){
        // 初始化金额占比图
        fragmentStatisticsBinding.todayMoneyProportionChart.isViewportCalculationEnabled=true //饼图自适应
        fragmentStatisticsBinding.todayMoneyProportionChart.isChartRotationEnabled=true //旋转
        fragmentStatisticsBinding.todayMoneyProportionChart.isValueSelectionEnabled=false //点击时变大
        fragmentStatisticsBinding.todayMoneyProportionChart.circleFillRatio=0.9f //设置图标大肖
        fragmentStatisticsBinding.todayMoneyProportionChart.pieChartData=mainActivityViewModel.getMoneyProportionPieCHartViewDataPoj()
    }
}