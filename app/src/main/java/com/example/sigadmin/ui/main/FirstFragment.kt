package com.example.sigadmin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sigadmin.R
import com.example.sigadmin.MainActivity


class FirstFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = this.arguments
        val myValue = bundle?.getString("message")
        val tv = view?.findViewById<TextView>(R.id.tv_fnama_lapangan)

        tv?.text = myValue

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val tvName = root.findViewById<TextView>(R.id.tv_fnama_lapangan)
        val tvFacility = root.findViewById<TextView>(R.id.tv_ffasilitas)
        val tvJamBuka = root.findViewById<TextView>(R.id.tv_fjam_buka)
        val tvJamTutup = root.findViewById<TextView>(R.id.tv_fjam_tutup)
        val tvNoTelp = root.findViewById<TextView>(R.id.tv_fno_telepon)
        val tvLat = root.findViewById<TextView>(R.id.tv_flatitude)
        val tvLong = root.findViewById<TextView>(R.id.tv_flongitude)
        val tvAlamat = root.findViewById<TextView>(R.id.tv_falamat)

        val activity = activity as MainActivity

        val results = activity.getMyData()
        val name = results.getString("name")
        val facility = results.getString("facility")
        val jamBuka = results.getString("jamBuka")
        val jamTutup = results.getString("jamTutup")
        val noTelp = results.getString("noTelp")
        val lat = results.getString("lat")
        val lng = results.getString("long")
        val addr = results.getString("alamat")

        tvName.text = name
        tvFacility.text = facility
        tvJamBuka.text = jamBuka
        tvJamTutup.text = jamTutup
        tvNoTelp.text = noTelp
        tvLat.text = lat
        tvLong.text = lng
        tvAlamat.text = addr

        return root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        const val names = "name"

        @JvmStatic
        fun newInstance(name: String): FirstFragment {
            return FirstFragment().apply {
                arguments = Bundle().apply {
                    getString(names, name)
                }
            }
        }
    }
}