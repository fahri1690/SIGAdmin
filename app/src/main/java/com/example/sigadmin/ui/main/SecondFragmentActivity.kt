package com.example.sigadmin.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sigadmin.FragmentMainActivity
import com.example.sigadmin.R
import com.example.sigadmin.layouts.FieldDetailActivity
import com.example.sigadmin.models.Field
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.item_place.view.*
import kotlinx.android.synthetic.main.activity_fragment_sec.view.*

class SecondFragmentActivity : Fragment() {

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
            fieldViewHolder.setFieldName(fieldModel.name)

            val db = FirebaseFirestore.getInstance()

            fieldViewHolder.itemView.setOnClickListener {
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val sid = snapshots.getSnapshot(position).reference
                sid.id
                val intent = Intent(context, FieldDetailActivity::class.java)
                intent.putExtra("sid", sid.id)
                intent.putExtra("id", snapshot.id)
                intent.putExtra("name", fieldModel.name)
                intent.putExtra("jenis", fieldModel.jenis)
                intent.putExtra("hargaSiang", fieldModel.hargaSiang)
                intent.putExtra("hargaMalam", fieldModel.hargaMalam)
                Log.d("MESSSSS", sid.id)
                Log.d("MESSSSS", snapshot.id)
                startActivity(intent)
            }

            fieldViewHolder.itemView.del_btn.setOnClickListener {

                val builder = AlertDialog.Builder(context)
                val snapshot = snapshots.getSnapshot(position).reference
                snapshot.id

                val ids = snapshot.id

                builder.setTitle("Hapus Lapangan")

                builder.setMessage("Apakah kamu yakin?")

                builder.setPositiveButton("Ya") { _, _ ->
                    db.collection("Lapangan").document().collection("listLapangan").document(ids).delete()
                    Toast.makeText(context, "Lapangan berhasil dihapus.", Toast.LENGTH_SHORT).show()
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

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.activity_fragment_sec, container, false)

        val activity = activity as FragmentMainActivity

        val results = activity.getList()
        val listId = results.getString("id")

        root.rvMMain.layoutManager = LinearLayoutManager(context)

        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("Lapangan").document(listId).collection("listLapangan").orderBy("name", Query.Direction.ASCENDING)
        val options =
                FirestoreRecyclerOptions.Builder<Field>().setQuery(query, Field::class.java).build()

        adapter = FieldFireStoreRecyclerAdapter(options)

        root.rvMMain.adapter = adapter

        root.ibb_add_new_place.setOnClickListener {

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