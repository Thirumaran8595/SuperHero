package com.thirumaran.superhero.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thirumaran.superhero.service.model.ResultsItem

class DetailViewmodel :ViewModel() {
    val detailViewmodel = MutableLiveData<ResultsItem>()
}