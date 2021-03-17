package com.thirumaran.superhero.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.thirumaran.superhero.databinding.FragmentDetailBinding
import com.thirumaran.superhero.service.model.ResultsItem
import com.thirumaran.superhero.viewmodel.DetailViewmodel


class DetailFragment : Fragment() {

    private lateinit var fragmentDetailBinding: FragmentDetailBinding
    private var detailViewmodel: DetailViewmodel? = null
    private var bundle: Bundle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)

        detailViewmodel = ViewModelProvider(this).get(DetailViewmodel::class.java)

        fragmentDetailBinding.lifecycleOwner = this
        fragmentDetailBinding.hero = detailViewmodel

        bundle = arguments

        val result = bundle?.getSerializable("result") as ResultsItem
        detailViewmodel?.detailViewmodel?.value = (result)

        detailViewmodel?.detailViewmodel?.observe(viewLifecycleOwner, {
            fragmentDetailBinding.executePendingBindings()
        })

        return fragmentDetailBinding.root
    }

}