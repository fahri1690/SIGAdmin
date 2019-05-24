package com.example.sigadmin.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.sigadmin.models.Users

public class FieldAdapter(context: Context):BaseAdapter(){
    private val mCtx : Context

    init {
        mCtx = context
    }

    override fun getCount(): Int {
        val count = 20
        return count
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return Users(String()).name
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val fieldName = TextView(mCtx)
        fieldName.text = getItem(position).toString() + " " + getItemId(position)
        return fieldName
    }
}