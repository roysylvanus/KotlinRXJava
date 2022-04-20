package com.malikali.rxjava.repository

import com.malikali.rxjava.data.ApiService
import com.malikali.rxjava.models.Animal
import io.reactivex.Observable
import javax.inject.Inject

class AnimalRepository @Inject constructor(
    private val service: ApiService
) {

    fun getRandomAnimal(): Observable<Animal> = service.getRandomAnimal()
}