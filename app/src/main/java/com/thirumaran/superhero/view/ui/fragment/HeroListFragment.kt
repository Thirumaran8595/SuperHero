package com.thirumaran.superhero.view.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.thirumaran.superhero.R
import com.thirumaran.superhero.databinding.FragmentHeroListBinding
import com.thirumaran.superhero.service.model.ResultsItem
import com.thirumaran.superhero.view.adapter.SuperHeroAdapter
import com.thirumaran.superhero.viewmodel.HeroListViewModel


class HeroListFragment : Fragment(),
    SeekBar.OnSeekBarChangeListener {

    private var heroListViewModel: HeroListViewModel? = null
    private lateinit var fragmentHeroListBinding: FragmentHeroListBinding
    private var superHeroAdapter: SuperHeroAdapter = SuperHeroAdapter()
    var bottomSheetDialog: BottomSheetDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //view binding
        fragmentHeroListBinding = FragmentHeroListBinding.inflate(inflater, container, false)

        //ViewModel
        heroListViewModel = ViewModelProvider(this).get(HeroListViewModel::class.java)

        //recycler adapter
        fragmentHeroListBinding.recyclerHero.adapter = superHeroAdapter

        fragmentHeroListBinding.lifecycleOwner = this
        fragmentHeroListBinding.viewmodel = heroListViewModel


        //Search text watcher
        fragmentHeroListBinding.edtSrch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().length > 2) {
                    heroListViewModel?.sendSearch(s.toString())
                }
            }
        })

        //error observer
        heroListViewModel?.errorDetail?.observe(viewLifecycleOwner, { error ->
            fragmentHeroListBinding.recyclerHero.visibility = View.GONE
            fragmentHeroListBinding.txtError.visibility = View.VISIBLE
            fragmentHeroListBinding.txtError.text = error.error
        })


        //recycler observer
        heroListViewModel?.herodetail?.observe(viewLifecycleOwner, { response ->

            fragmentHeroListBinding.txtError.visibility = View.GONE
            fragmentHeroListBinding.recyclerHero.visibility = View.VISIBLE
            updateAdapter(response)

        })

        //filter observer
        heroListViewModel?.filterdetail?.observe(viewLifecycleOwner, { response ->

            fragmentHeroListBinding.txtError.visibility = View.GONE
            fragmentHeroListBinding.recyclerHero.visibility = View.VISIBLE

            updateAdapter(response)

        })


        //filter options
        fragmentHeroListBinding.txtFilter.setOnClickListener {
            if (heroListViewModel?.herodetail?.value != null)
                showBottomsheet()
        }

        //clear
        fragmentHeroListBinding.txtClear.setOnClickListener {
            fragmentHeroListBinding.edtSrch.text = null
        }


        return fragmentHeroListBinding.root
    }


    //Recycler Update
    private fun updateAdapter(response: List<ResultsItem>) {
        superHeroAdapter.run {
            results.clear()
            results.addAll(response)
            notifyDataSetChanged()
        }

    }

    //Filter Bottom Sheet
    private fun showBottomsheet() {
        val view = layoutInflater.inflate(R.layout.filter_bottomsheet, null)

        bottomSheetDialog?.dismiss()
        bottomSheetDialog = BottomSheetDialog(fragmentHeroListBinding.txtFilter.context)
        bottomSheetDialog?.setContentView(view)
        bottomSheetDialog?.setCancelable(true)

        val intelligence = view.findViewById<SeekBar>(R.id.seekbar_inteli)
        val strength = view.findViewById<SeekBar>(R.id.seekbar_strength)
        val speed = view.findViewById<SeekBar>(R.id.seekbar_speed)
        val durability = view.findViewById<SeekBar>(R.id.seekbar_durability)
        val power = view.findViewById<SeekBar>(R.id.seekbar_power)
        val combat = view.findViewById<SeekBar>(R.id.seekbar_combat)

        intelligence.setOnSeekBarChangeListener(this)
        strength.setOnSeekBarChangeListener(this)
        speed.setOnSeekBarChangeListener(this)
        durability.setOnSeekBarChangeListener(this)
        power.setOnSeekBarChangeListener(this)
        combat.setOnSeekBarChangeListener(this)

        bottomSheetDialog?.show()


    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        when (seekBar?.id) {
            R.id.seekbar_inteli -> {
                val result = heroListViewModel?.herodetail?.value?.filter { intelligence ->
                    if (seekBar.progress != 0) intelligence.powerstats?.intelligence != "null" && intelligence.powerstats?.intelligence?.toInt()!! >= seekBar.progress
                    else intelligence.powerstats?.intelligence != null
                }
                heroListViewModel?.filterdetail?.postValue(result)
            }
            R.id.seekbar_strength -> {
                val result = heroListViewModel?.herodetail?.value?.filter { strength ->
                    if (seekBar.progress != 0) strength.powerstats?.strength != "null" && strength.powerstats?.strength?.toInt()!! >= seekBar.progress
                    else strength.powerstats?.strength != null
                }
                heroListViewModel?.filterdetail?.postValue(result)
            }

            R.id.seekbar_speed -> {
                val result = heroListViewModel?.herodetail?.value?.filter { speed ->
                    if (seekBar.progress != 0) speed.powerstats?.speed != "null" && speed.powerstats?.speed?.toInt()!! >= seekBar.progress
                    else speed.powerstats?.speed != null
                }
                heroListViewModel?.filterdetail?.postValue(result)

            }
            R.id.seekbar_durability -> {
                val result = heroListViewModel?.herodetail?.value?.filter { durability ->
                    if (seekBar.progress != 0) durability.powerstats?.durability != "null" && durability.powerstats?.durability?.toInt()!! >= seekBar.progress
                    else durability.powerstats?.durability != null
                }
                heroListViewModel?.filterdetail?.postValue(result)
            }
            R.id.seekbar_power -> {
                val result = heroListViewModel?.herodetail?.value?.filter { power ->
                    if (seekBar.progress != 0) power.powerstats?.power != "null" && power.powerstats?.power?.toInt()!! >= seekBar.progress
                    else power.powerstats?.power != null
                }
                heroListViewModel?.filterdetail?.postValue(result)
            }
            R.id.seekbar_combat -> {
                val result = heroListViewModel?.herodetail?.value?.filter { combat ->
                    if (seekBar.progress != 0) combat.powerstats?.combat != "null" && combat.powerstats?.combat?.toInt()!! >= seekBar.progress
                    else combat.powerstats?.combat != null
                }
                heroListViewModel?.filterdetail?.postValue(result)
            }

        }

    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }
}