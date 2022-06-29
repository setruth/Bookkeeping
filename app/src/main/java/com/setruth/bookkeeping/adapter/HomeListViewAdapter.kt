package com.setruth.bookkeeping.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.setruth.bookkeeping.R
import com.setruth.bookkeeping.dao.entity.BaseEntity
import com.setruth.bookkeeping.databinding.HomeListViewItemBinding
import com.setruth.bookkeeping.utils.DateTool

/**
 * @author  :Setruth
 * time     :2022/6/11 13:14
 * e-mail   :1607908758@qq.com
 * remark   :Bookkeeping
 */

class HomeListViewAdapter(context: Context,id:Int,var dataList:MutableList<BaseEntity>):ArrayAdapter<BaseEntity>(context,id,dataList){
    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder:ViewHolder
        val view:View
        if (convertView==null){
            view =LayoutInflater.from(context).inflate(R.layout.home_list_view_item,parent,false)
            viewHolder= ViewHolder(view.findViewById(R.id.list_view_item_money),view.findViewById(R.id.list_view_item_remark),view.findViewById(R.id.list_view_item_date))
            view.tag = viewHolder
        }else{
            view=convertView
            viewHolder= view.tag as ViewHolder
        }
        viewHolder.date.text=DateTool.timeStampToSimpleDate(dataList[position].detailsDate)
        viewHolder.money.text="${dataList[position].money}￥"
        viewHolder.remark.text="备注:${dataList[position].remark}"
        return view
    }
    data class ViewHolder(
         var money:TextView,
         var remark:TextView,
         var date:TextView,
    )
}