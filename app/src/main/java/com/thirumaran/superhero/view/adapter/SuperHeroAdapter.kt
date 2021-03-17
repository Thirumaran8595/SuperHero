package com.thirumaran.superhero.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.thirumaran.superhero.BR
import com.thirumaran.superhero.R
import com.thirumaran.superhero.databinding.HeroListInflatorBinding
import com.thirumaran.superhero.service.model.ResultsItem

class SuperHeroAdapter :
    RecyclerView.Adapter<SuperHeroAdapter.Heroholder>() {

    var results: MutableList<ResultsItem?> = mutableListOf()

    class Heroholder(private val binding: HeroListInflatorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ResultsItem) {
            binding.setVariable(BR.hero, result)
            binding.executePendingBindings()

            binding.constHero.setOnClickListener {
                val args = Bundle()
                args.putSerializable("result",result)
                Navigation.findNavController(it).navigate(R.id.detailFragment,args)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Heroholder {
        val binding =
            HeroListInflatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Heroholder(binding)
    }

    override fun onBindViewHolder(holder: Heroholder, position: Int) {
        val result: ResultsItem = results[position]!!
        holder.bind(result)
    }

    override fun getItemCount(): Int = results.size

}