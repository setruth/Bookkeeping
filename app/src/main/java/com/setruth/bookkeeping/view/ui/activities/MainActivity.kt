package com.setruth.bookkeeping.view.ui.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.mxn.soul.flowingdrawer_core.ElasticDrawer
import com.mxn.soul.flowingdrawer_core.ElasticDrawer.OnDrawerStateChangeListener
import com.mxn.soul.flowingdrawer_core.FlowingDrawer
import com.setruth.bookkeeping.R
import com.setruth.bookkeeping.R.id.menu_icon
import com.setruth.bookkeeping.databinding.ActivityMainBinding
import com.setruth.bookkeeping.pojo.constant.Constant
import com.setruth.bookkeeping.utils.SpUtils
import com.setruth.bookkeeping.view.ui.framents.HomeFragment
import com.setruth.bookkeeping.view.ui.framents.StatisticsFragment
import com.setruth.bookkeeping.viewModel.MainActivityViewModel


class MainActivity : AppCompatActivity() {
    // 当前页面的标识，避免页面重复
    private var nowPage: Int = 0

    //创建mainActivity的布局对象
    private lateinit var binding:ActivityMainBinding
    //开启fragment管理器
    private lateinit var translationManager: FragmentTransaction

    // actionbar
    private lateinit var actionBar: ActionBar

    //菜单侧边栏
    private lateinit var flowingDrawer: FlowingDrawer

    //页面view model
    private lateinit var viewModel: MainActivityViewModel

    //sharedPreferences工具
    private lateinit var spUtils: SpUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //实例化视图资源对象
        binding = ActivityMainBinding.inflate(layoutInflater)
        //获取view model
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        setContentView(binding.root)
        actionBar = supportActionBar!!
        flowingDrawer = binding.drawerlayout
        //初始化监听
        initListen()
        //初始化页面
        changePage()
    }

    /**
     * TODO 初始化控件监听
     *
     */
    private fun initListen() {
        //初始化左边菜单监听
        flowingDrawer.setOnDrawerStateChangeListener(object : OnDrawerStateChangeListener {
            override fun onDrawerStateChange(oldState: Int, newState: Int) {
                if (newState == ElasticDrawer.STATE_CLOSED) {

                }
            }

            override fun onDrawerSlide(openRatio: Float, offsetPixels: Int) {
            }
        })
        //观察页面标识的改变切换页面
        viewModel.nowPageTag.observeForever {
            if (nowPage!=it){
                changePage()
                nowPage=it
            }

        }
        //初始化底部导航栏监听按钮
        binding.bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.bottom_home_icon -> {
                    actionBar.title = "首页"
                    binding.bottomAppBar.hideOnScroll = false
                    binding.bottomAppBar.hideOnScroll = true
                    viewModel.nowPageTag.value = Constant.NOW_IS_HOME_TAG

                }
                R.id.bottom_statistics_icon -> {
                    viewModel.nowPageTag.value = Constant.NOW_IS_HISTORY_TAG
                    actionBar.title = "统计"
                    binding.bottomAppBar.hideOnScroll = false
                    binding.bottomAppBar.hideOnScroll = true
                }
            }
            false
        }
        //进入添加账单页面
        binding.addBill.setOnClickListener {
            val intent = Intent(this, BookkeepingActivity::class.java)
            val options = ActivityOptions.makeSceneTransitionAnimation(this)
            startActivity(intent, options.toBundle())
        }
    }
    /**
     * TODO 初始化页面数据
     *
     */
    private fun initData() {
        nowPage=viewModel.nowPageTag.value!!
        //实例化fragment事务管理器对象
        translationManager = supportFragmentManager.run { beginTransaction() }
        //添加默认第一个home页面
        with(translationManager) {
            add(R.id.fragment_layout, HomeFragment()) //添加第一个默认界面
            commit() //提交
        }
        //设置默认actionbar标题
        actionBar.title = "首页"
        //关闭从边缘滑动打开菜单
        flowingDrawer.setTouchMode(ElasticDrawer.TOUCH_MODE_NONE)
        nowPage = viewModel.nowPageTag.value!!
    }

    /**
     * TODO 设置actionbar菜单
     *
     * @param menu
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //切换页面
    fun changePage() {
        // 在每次重新提交事务的时候要重新获取beginTransaction，否则会报错显示commit already called
        translationManager = supportFragmentManager.run { beginTransaction() }
        if (viewModel.nowPageTag.value == Constant.NOW_IS_HOME_TAG) {
            translationManager.apply {
                setCustomAnimations(R.anim.left_move_enter, R.anim.right_move_out)
                replace(R.id.fragment_layout, HomeFragment())
                commit()

            }
        } else {
            translationManager.apply {
                setCustomAnimations(R.anim.right_move_enter, R.anim.left_move_out)
                replace(R.id.fragment_layout, StatisticsFragment())
                commit()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }
    /**
     * TODO 设置actionbar菜单监听
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            menu_icon -> {
                flowingDrawer.toggleMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}