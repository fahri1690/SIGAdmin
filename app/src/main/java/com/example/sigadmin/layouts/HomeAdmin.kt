package com.example.sigadmin.layouts

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sigadmin.R
import com.example.sigadmin.models.DataField
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.field_detail.*
import kotlinx.android.synthetic.main.home_admin.*
import kotlinx.android.synthetic.main.item_field.view.*


class HomeAdmin : AppCompatActivity() {

    inner class FieldViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setFieldName(fieldName: String) {
            val textView = view.findViewById<TextView>(R.id.txtFieldName)
            textView.text = fieldName
        }
    }

    inner class FieldFireStoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<DataField>) :
        FirestoreRecyclerAdapter<DataField, FieldViewHolder>(options) {
        override fun onBindViewHolder(
            fieldViewHolder: FieldViewHolder,
            position: Int,
            fieldModel: DataField
        ) {
            fieldViewHolder.setFieldName(fieldModel.name)

            val db = FirebaseFirestore.getInstance()

            fieldViewHolder.itemView.setOnClickListener {
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val intent = Intent(this@HomeAdmin, FieldDetail::class.java)
                intent.putExtra("id", snapshot.id)
                intent.putExtra("name", fieldModel.name)
                intent.putExtra("facility", fieldModel.facility)
                intent.putExtra("alamat", fieldModel.alamat)
                intent.putExtra("jamBuka", fieldModel.jamBuka)
                intent.putExtra("jamTutup", fieldModel.jamTutup)
                intent.putExtra("lat", fieldModel.lat)
                intent.putExtra("long", fieldModel.long)
                intent.putExtra("noTelp", fieldModel.noTelp)
                startActivity(intent)
            }

            fieldViewHolder.itemView.del_btn.setOnClickListener {

                val builder = AlertDialog.Builder(this@HomeAdmin)
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id

                val ids = snapshot.id

                builder.setTitle("Hapus Lapangan")

                builder.setMessage("Apakah kamu yakin?")

                builder.setPositiveButton("Ya") { _, which ->
                    db.collection("Lapangan").document(ids).delete()
                    Toast.makeText(applicationContext, "Lapangan berhasil dihapus.", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("Tidak") { _, which ->

                }

                val dialog: AlertDialog = builder.create()

                dialog.show()

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_field, parent, false)
            return FieldViewHolder(view)
        }
    }

    private var adapter: FieldFireStoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_admin)

        rvMain.layoutManager = LinearLayoutManager(this)

        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("Lapangan").orderBy("name", Query.Direction.ASCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<DataField>().setQuery(query, DataField::class.java).build()

        adapter = FieldFireStoreRecyclerAdapter(options)

        rvMain.adapter = adapter

        floatacbut_add_new_place.setOnClickListener {
            val intent = Intent (this, InputNewField::class.java)
            startActivity(intent)
        }

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

