package com.thirumaran.superhero.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thirumaran.superhero.service.model.ErrorDetailPojo
import com.thirumaran.superhero.service.model.ResultsItem
import com.thirumaran.superhero.service.repo.SearchApi.getHeroAPI

class HeroListViewModel : ViewModel() {

    val herodetail = MutableLiveData<List<ResultsItem>>()
    val errorDetail = MutableLiveData<ErrorDetailPojo>()

    val filterdetail = MutableLiveData<List<ResultsItem>>()


    fun sendSearch(value: String) {
        getHeroAPI(value, herodetail, errorDetail)
    }

}