package com.example.sigadmin.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sigadmin.R
import kotlinx.android.synthetic.main.fragment_main.*

class SecondFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            arguments?.getString("name")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.sec_fragment, container, false)
        pageViewModel.text.observe(this, Observer<String> {

        })

        return root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        const val names = "name"

        @JvmStatic
        fun newInstance(name: String): SecondFragment {
            return SecondFragment().apply {
                arguments = Bundle().apply {
                    getString(names, name)
                }
            }
        }
    }
}