package com.malikali.rxjava.repository

import com.malikali.rxjava.data.ApiService
import com.malikali.rxjava.models.Animal
import com.malikali.rxjava.utils.BaseUnitTest
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.observers.TestObserver
import org.junit.Assert.*

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AnimalRepositoryTest : BaseUnitTest() {

    private val apiService :ApiService = mock()
    private val animal:TestObserver<Animal> = mock()
    private lateinit var animalRepository: AnimalRepository

    @Test
    fun getRandomAnimal() {
        animalRepository = AnimalRepository(apiService)


    }
}