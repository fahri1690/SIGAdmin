package com.example.sigadmin.layouts.field

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sigadmin.R
import com.example.sigadmin.layouts.info.main.MainFragmentActivity
import com.example.sigadmin.models.Field
import com.example.sigadmin.services.db.GetDb
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_field_list.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class FieldListActivity : Fragment() {

    inner class FieldViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setFieldName(fieldName: String) {
            val textView = view.findViewById<TextView>(R.id.txtFieldName)
            textView.text = fieldName
        }
    }

    inner class FieldFireStoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<Field>) :
            FirestoreRecyclerAdapter<Field, FieldViewHolder>(options) {
        override fun onBindViewHolder(
                fieldViewHolder: FieldViewHolder,
                position: Int,
                fieldModel: Field
        ) {
            fieldViewHolder.setFieldName("Lapangan ${fieldModel.name}")

            val activity = activity as MainFragmentActivity

            val results = activity.getList()
            val placeId:String? = results.getString("placeId")

            fieldViewHolder.itemView.setOnClickListener {
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val intent = Intent(context, FieldDetailActivity::class.java)
                intent.putExtra("fieldId", snapshot.id)
                intent.putExtra("name", fieldModel.name)
                intent.putExtra("jenis", fieldModel.jenis)
                intent.putExtra("hargaSiang", fieldModel.hargaSiang.toString())
                intent.putExtra("hargaMalam", fieldModel.hargaMalam.toString())
                intent.putExtra("placeId", placeId)
                startActivity(intent)
            }

            fieldViewHolder.itemView.del_btn.setOnClickListener {

                val builder = AlertDialog.Builder(activity)
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id

                val fieldId = snapshot.id

                builder.setTitle("Hapus Lapangan")

                builder.setMessage("Apakah kamu yakin?")

                builder.setPositiveButton("Ya") { _, _ ->
                    GetDb().collection.document(placeId.toString()).collection("listLapangan").document(fieldId).delete()
                    Toast.makeText(activity, "Lapangan berhasil dihapus.", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("Tidak") { _, _ ->

                }

                val dialog: AlertDialog = builder.create()

                dialog.show()

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
            return FieldViewHolder(view)
        }
    }

    private var adapter: FieldFireStoreRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.activity_field_list, container, false)

        val activity = activity as MainFragmentActivity

        val results = activity.getList()
        val placeId:String? = results.getString("placeId")

        root.rvMMain.layoutManager = LinearLayoutManager(context)

        val query = GetDb().collection.document(placeId.toString()).collection("listLapangan").orderBy("name", Query.Direction.ASCENDING)
        val options =
                FirestoreRecyclerOptions.Builder<Field>().setQuery(query, Field::class.java).build()

        adapter = FieldFireStoreRecyclerAdapter(options)

        root.rvMMain.adapter = adapter

        root.ibb_add_new_field.setOnClickListener {
            val intent = Intent(getActivity(), CreateFieldActivity::class.java)
            intent.putExtra("placeId", placeId)
            startActivity(intent)
        }

        return root
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (adapter != null) {
            adapter!!.stopListening()
        }
    }
}