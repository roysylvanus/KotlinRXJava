package com.malikali.rxjava.data

import com.malikali.rxjava.models.Animal
import com.malikali.rxjava.utils.Constants
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET(Constants.RANDOM_ANIMAL)
    fun getRandomAnimal(): Observable<Animal>

}