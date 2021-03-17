package com.thirumaran.superhero.service.model

import com.google.gson.annotations.SerializedName

data class ErrorDetailPojo(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("error")
	var error: String? = null
)
