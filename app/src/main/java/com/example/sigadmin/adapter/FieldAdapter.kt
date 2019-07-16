//package com.example.sigadmin.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.sigadmin.R
//import com.example.sigadmin.models.DataFieldByName
//import kotlinx.android.synthetic.main.item_place.view.*
//
//class FieldAdapter(private val dataFields: List<DataFieldByName>) : RecyclerView.Adapter<FieldHolder>() {
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): FieldHolder {
//        return FieldHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_place, viewGroup, false))
//    }
//
//    override fun getItemCount(): Int = dataFields.size
//
//    override fun onBindViewHolder(holder: FieldHolder, position: Int) {
//        holder.bindHero(dataFields[position])
//    }
//}
//
//class FieldHolder(view: View) : RecyclerView.ViewHolder(view) {
//    private val tvFieldName = view.txtFieldName
//
//    fun bindHero(fieldByName: DataFieldByName) {
//        tvFieldName.text = fieldByName.name
//    }
//}
//
