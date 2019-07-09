package com.example.sigadmin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.sigadmin.layouts.HomeAdmin
import com.example.sigadmin.layouts.InputNewField
import com.example.sigadmin.layouts.InputNewSubField
import com.example.sigadmin.models.DataField
import com.example.sigadmin.models.SubField
import com.example.sigadmin.ui.main.SectionsPagerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_admin.*
import kotlinx.android.synthetic.main.item_field.view.*
import kotlinx.android.synthetic.main.sec_fragment.view.*

class MainActivity : AppCompatActivity() {

    inner class SubFieldViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setSubFieldName(subFieldName: String) {
            val textView = view.findViewById<TextView>(R.id.txtSubFieldName)
            textView.text = subFieldName
        }
    }

    inner class SubFieldFireStoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<SubField>) :
        FirestoreRecyclerAdapter<SubField, SubFieldViewHolder>(options) {
        override fun onBindViewHolder(
            subFieldViewHolder: SubFieldViewHolder,
            position: Int,
            subFieldModel: SubField
        ) {
            subFieldViewHolder.setSubFieldName()(subFieldModel.name)

            val db = FirebaseFirestore.getInstance()

            subFieldViewHolder.itemView.setOnClickListener {
                val d = db.collection("Lapangan")
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val list = d.document(snapshot.id).collection("listLapangan").document(snapshot.id)
                list.id
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                intent.putExtra("id", snapshot.id)
                intent.putExtra("name", subFieldModel.name)
                intent.putExtra("hargaSiang", subFieldModel.hargaSiang)
                intent.putExtra("hargaMalam", subFieldModel.hargaMalam)
                intent.putExtra("jenis", subFieldModel.jenis)
                intent.putExtra("listId", list.id)
                startActivity(intent)
            }

            subFieldViewHolder.itemView.del_btn_subField.setOnClickListener {

                val builder = AlertDialog.Builder(this@MainActivity)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdmin.FieldViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_field, parent, false)
            return SubFieldViewHolder(view)
        }
    }

    private var adapter: MainActivity.SubFieldFireStoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val listId = intent.getStringExtra("listId")

        Log.d("ListID", "$listId")


        rvMain.layoutManager = LinearLayoutManager(this)

        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("Lapangan").orderBy("name", Query.Direction.ASCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<DataField>().setQuery(query, DataField::class.java).build()

        adapter = SubFieldFireStoreRecyclerAdapter(options)

        rvMain2.adapter = adapter

        ib_add_new_subField.setOnClickListener {
            val intent = Intent(this, InputNewSubField::class.java)
            startActivity(intent)
        }

    }

    private var adapter: HomeAdmin.FieldFireStoreRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain.layoutManager = LinearLayoutManager(this)

        val rootRef = FirebaseFirestore.getInstance()
        val query = rootRef.collection("Lapangan").orderBy("name", Query.Direction.ASCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<DataField>().setQuery(query, DataField::class.java).build()

        adapter = SubFieldFireStoreRecyclerAdapter(options)

        rvMain.adapter = adapter

        ib_add_new_place.setOnClickListener {
            val intent = Intent(this, InputNewField::class.java)
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

fun getMyData(): Bundle {

    val name = intent.getStringExtra("name")
    val facility = intent.getStringExtra("facility")
    val openHour = intent.getStringExtra("jamBuka")
    val closeHour = intent.getStringExtra("jamTutup")
    val phone = intent.getStringExtra("noTelp")
    val addr = intent.getStringExtra("alamat")
    val lat = intent.getStringExtra("lat")
    val lng = intent.getStringExtra("long")

    val b = Bundle()
    b.putString("name", name)
    b.putString("facility", facility)
    b.putString("jamBuka", openHour)
    b.putString("jamTutup", closeHour)
    b.putString("noTelp", phone)
    b.putString("alamat", addr)
    b.putString("lat", lat)
    b.putString("long", lng)

    return b

}
