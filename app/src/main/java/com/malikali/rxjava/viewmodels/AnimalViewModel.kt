package com.malikali.rxjava.viewmodels
import android.util.Log
import io.reactivex.Observer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.malikali.rxjava.models.Animal
import com.malikali.rxjava.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AnimalViewModel @Inject constructor(private val repository: AnimalRepository):ViewModel() {

    private val _animal = MutableLiveData<Animal>()
    val animal:LiveData<Animal> get() = _animal

    private val _isLoading = MutableLiveData(false)
    val isLoading:LiveData<Boolean> get() = _isLoading

    private val compositeDisposable = CompositeDisposable()


    fun getARandomAnimal(){

        getRandomAnimal()

    }

    private fun getRandomAnimal() {

         repository.getRandomAnimal().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getRandomAnimalObserver())

    }

    private fun getRandomAnimalObserver():Observer<Animal>{
        return object : Observer<Animal>{
            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                _isLoading.postValue(true)
            }

            override fun onNext(t: Animal) {
              _animal.postValue(t)
                _isLoading.postValue(false)
            }

            override fun onError(e: Throwable) {
                _isLoading.postValue(false)
                Log.e("ERRor",e.message.toString())
            }

            override fun onComplete() {
                _isLoading.postValue(false)
            }

        }
    }

    fun clear(){
        compositeDisposable.clear()
    }

}