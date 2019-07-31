package com.example.sigadmin.layouts.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sigadmin.R
import com.example.sigadmin.layouts.info.main.MainFragmentActivity
import com.example.sigadmin.layouts.info.place.CreatePlaceActivity
import com.example.sigadmin.models.PlaceModel
import com.example.sigadmin.services.db.GetDb
import com.example.sigadmin.services.db.GetImage
import com.example.sigadmin.services.db.OrderBy
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.item_place.view.*
import java.util.ArrayList

class HomeAdminActivity : AppCompatActivity() {

    inner class FieldViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setFieldName(fieldName: String) {
            val textView = view.findViewById<TextView>(R.id.txtFieldName)
            textView.text = fieldName
        }
    }

    inner class FieldFireStoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<PlaceModel>) :
        FirestoreRecyclerAdapter<PlaceModel, FieldViewHolder>(options) {
        override fun onBindViewHolder(
                fieldViewHolder: FieldViewHolder,
                position: Int,
                fieldModel: PlaceModel
        ) {
            fieldViewHolder.setFieldName(fieldModel.name)

            fieldViewHolder.itemView.setOnClickListener {

                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val list = GetDb().collection.document(snapshot.id).collection("listLapangan").document()
                list.id
                val intent = Intent(this@HomeAdminActivity, MainFragmentActivity::class.java)
                intent.putExtra("placeId", snapshot.id)
                intent.putExtra("name", fieldModel.name)
                intent.putExtra("facility", fieldModel.facility)
                intent.putExtra("alamat", fieldModel.alamat)
                intent.putExtra("jamBuka", fieldModel.jamBuka)
                intent.putExtra("jamTutup", fieldModel.jamTutup)
                intent.putExtra("lat", fieldModel.lat)
                intent.putExtra("long", fieldModel.long)
                intent.putExtra("noTelp", fieldModel.noTelp.toString())
                intent.putStringArrayListExtra("images", fieldModel.images)
                startActivity(intent)
            }

            fieldViewHolder.itemView.del_btn.setOnClickListener {

                val builder = AlertDialog.Builder(this@HomeAdminActivity)
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id

                val ids = snapshot.id

                builder.setTitle("Hapus Lapangan")

                builder.setMessage("Apakah kamu yakin?")

                builder.setPositiveButton("Ya") { _, _ ->
                    GetDb().collection.document(ids).delete()
                    Toast.makeText(applicationContext, "Lapangan berhasil dihapus.", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("Tidak") { _, _ ->

                }

                val dialog: AlertDialog = builder.create()

                dialog.show()

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
            return FieldViewHolder(view)
        }
    }

    private var adapter: FieldFireStoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        rvMain.layoutManager = LinearLayoutManager(this)

        val query = OrderBy().ascendingName
        val options = GetDb().recyclerOption.setQuery(query, PlaceModel::class.java).build()

        adapter = FieldFireStoreRecyclerAdapter(options)

        rvMain.adapter = adapter

        ib_add_new_place.setOnClickListener {
            val intent = Intent (this, CreatePlaceActivity::class.java)
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