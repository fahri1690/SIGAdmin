//package com.example.sigadmin.adapter
//
//import android.content.Context
//import android.support.v7.widget.RecyclerView
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import com.example.sigadmin.R
//import com.example.sigadmin.models.Admin
//import com.google.firebase.firestore.FirebaseFirestore
//
//class AdminRegistrationAdapter(
//    private val notesList: MutableList<Admin>,
//    private val context: Context,
//    private val firestoreDB: FirebaseFirestore
//)
//    : RecyclerView.Adapter<AdminRegistrationAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.home_admin, parent, false)
//
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
//        val admin = notesList[position]
//
//        holder!!.name.text = admin.namaPengguna
//        holder.phone.text = admin.content
//
//        holder.email.setOnClickListener { updateNote(admin) }
//        holder.delete.setOnClickListener { deleteNote(admin.id!!, position) }
//    }
//
//    override fun getItemCount(): Int {
//        return notesList.size
//    }
//
//    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
//        internal var name: TextView
//        internal var phone: TextView
//        internal var email: TextView
//
//        internal var update: ImageView
//        internal var delete: ImageView
//
//        init {
//            name = view.findViewById(R.id.tvA)
//            phone = view.findViewById(R.id.tvContent)
//
//            email = view.findViewById(R.id.ivEdit)
//            delete = view.findViewById(R.id.ivDelete)
//        }
//    }
//
//    private fun updateNote(note: Note) {
//        val intent = Intent(context, NoteActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        intent.putExtra("UpdateNoteId", note.id)
//        intent.putExtra("UpdateNoteTitle", note.title)
//        intent.putExtra("UpdateNoteContent", note.content)
//        context.startActivity(intent)
//    }
//
//    private fun deleteNote(id: String, position: Int) {
//        firestoreDB.collection("notes")
//            .document(id)
//            .delete()
//            .addOnCompleteListener {
//                notesList.removeAt(position)
//                notifyItemRemoved(position)
//                notifyItemRangeChanged(position, notesList.size)
//                Toast.makeText(context, "Note has been deleted!", Toast.LENGTH_SHORT).show()
//            }
//    }
//}