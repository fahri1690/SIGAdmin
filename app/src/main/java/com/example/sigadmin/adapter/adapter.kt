package com.example.sigadmin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

public class FieldAdapter(context: Context):BaseAdapter(){
    private val mCtx : Context

    init {
        mCtx = context
    }

    override fun getCount(): Int {
        return 10
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return "Test"
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val fieldName = TextView(mCtx)
        fieldName.text = "Lapangan"
        return fieldName
    }
}