package com.setruth.bookkeeping.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * @author  :Setruth
 * time     :2022/6/10 10:45
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class HomeViewPageAdapter(var viewList: MutableList<View>) : PagerAdapter() {
    override fun getCount(): Int = viewList.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewList[position])
        return viewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewList[position])
    }
}