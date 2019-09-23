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
import com.example.sigadmin.layouts.admin.AdminInfoActivity
import com.example.sigadmin.layouts.admin.LoginActivity
import com.example.sigadmin.layouts.main.MainFragmentActivity
import com.example.sigadmin.layouts.place.CreatePlaceActivity
import com.example.sigadmin.models.PlaceModel
import com.example.sigadmin.services.db.GetDb
import com.example.sigadmin.services.db.OrderBy
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class HomeAdminActivity : AppCompatActivity() {

    inner class FieldViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view) {
        internal fun setFieldName(fieldName: String) {
            val textView = view.findViewById<TextView>(R.id.tv_name_item)
            textView.text = fieldName
        }
    }

    inner class FieldFireStoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<PlaceModel>) :
        FirestoreRecyclerAdapter<PlaceModel, FieldViewHolder>(options) {
        override fun onBindViewHolder(
            fieldViewHolder: FieldViewHolder,
            position: Int,
            placeModel: PlaceModel
        ) {
            fieldViewHolder.setFieldName(placeModel.namaTempat)

            fieldViewHolder.itemView.setOnClickListener {

                val snapshot = snapshots.getSnapshot(position)
                snapshot.id
                val list = GetDb().collection.document(snapshot.id).collection("listLapangan").document()
                list.id
                val intent = Intent(this@HomeAdminActivity, MainFragmentActivity::class.java)
                intent.putExtra("placeId", snapshot.id)
                intent.putExtra("namaTempat", placeModel.namaTempat)
                intent.putExtra("fasilitas", placeModel.fasilitas)
                intent.putExtra("alamat", placeModel.alamat)
                intent.putExtra("jamBuka", placeModel.jamBuka)
                intent.putExtra("jamTutup", placeModel.jamTutup)
                intent.putExtra("latitude", placeModel.latitude.toString())
                intent.putExtra("longitude", placeModel.longitude.toString())
                intent.putExtra("noTelp", placeModel.noTelp)
                intent.putStringArrayListExtra("gambar", placeModel.gambar)
                startActivity(intent)
            }

            fieldViewHolder.itemView.btn_delete.setOnClickListener {

                val builder = AlertDialog.Builder(this@HomeAdminActivity)
                val snapshot = snapshots.getSnapshot(position)
                snapshot.id

                val ids = snapshot.id

                builder.setTitle("Hapus Tempat Futsal")

                builder.setMessage("Apakah kamu yakin?")

                builder.setPositiveButton("Ya") { _, _ ->
                    GetDb().collection.document(ids).delete()
                    Toast.makeText(applicationContext, "${placeModel.namaTempat} berhasil dihapus.", Toast.LENGTH_SHORT).show()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        rvMain.layoutManager = LinearLayoutManager(this)

        val user = FirebaseAuth.getInstance().currentUser

        val query = OrderBy().ascendingName
        val options = GetDb().recyclerOption.setQuery(query, PlaceModel::class.java).build()

        adapter = FieldFireStoreRecyclerAdapter(options)

        rvMain.adapter = adapter

        ib_add_new_place.setOnClickListener {
            val intent = Intent (this, CreatePlaceActivity::class.java)
            startActivity(intent)
        }

        tv_admin.text = user?.email

        tv_admin.setOnClickListener {

            val db = FirebaseFirestore.getInstance()
            db.collection("Admin").whereEqualTo("email", user?.email).get().addOnSuccessListener{
                for(document in it.documents) {

                    val doc = document.data

                    val mail = doc?.get("email").toString()
                    val name = doc?.get("namaTempat").toString()
                    val phone = doc?.get("").toString()

                    val intent = Intent(this, AdminInfoActivity::class.java)
                    intent.putExtra(AdminInfoActivity.USER_EMAIL, mail)
                    intent.putExtra(AdminInfoActivity.USER_NAME, name)
                    intent.putExtra(AdminInfoActivity.PHONE, phone)
                    startActivity(intent)
                }
            }
        }

        tv_logOut.setOnClickListener {

            val builder = AlertDialog.Builder(this@HomeAdminActivity)

            builder.setMessage("Apakah kamu yakin akan keluar aplikasi?")

            builder.setPositiveButton("Ya") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                finish()
                startActivity(intent)
            }

            builder.setNegativeButton("Tidak") { _, _ ->

            }

            val dialog: AlertDialog = builder.create()

            dialog.show()

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

    override fun onBackPressed() {
        finishAffinity()
    }
}