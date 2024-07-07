package com.dicoding.nodapplication.view.home

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.nodapplication.data.UserRepository
import com.dicoding.nodapplication.data.response.DetailResponse
import com.dicoding.nodapplication.data.response.ListDestinationResponseItem
import com.dicoding.nodapplication.data.response.MessageResponse
import com.dicoding.nodapplication.result.Result
import com.dicoding.nodapplication.utils.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class DestinationViewModel(private val userRepository: UserRepository, context: Context) :
    ViewModel() {
    //for location
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    var previousLocation: Location? = null

    //for dropDown Button
    private val _isDropBtn1Click = MutableLiveData<Boolean>()
    val isDropBtn1Click: LiveData<Boolean> = _isDropBtn1Click

    private val _isDropBtn2Click = MutableLiveData<Boolean>()
    val isDropBtn2Click: LiveData<Boolean> = _isDropBtn2Click

    private val _isDropBtn3Click = MutableLiveData<Boolean>()
    val isDropBtn3Click: LiveData<Boolean> = _isDropBtn3Click

    private val _isCategory2 = MutableLiveData<Boolean>()
    val isCategory2: LiveData<Boolean> = _isCategory2

    private val _isCategory3 = MutableLiveData<Boolean>()
    val isCategory3: LiveData<Boolean> = _isCategory3

    private val _isCategory4 = MutableLiveData<Boolean>()
    val isCategory4: LiveData<Boolean> = _isCategory4

    private val _isCategory5 = MutableLiveData<Boolean>()
    val isCategory5: LiveData<Boolean> = _isCategory5

    private val _isCategory1 = MutableLiveData<Boolean>()
    val isCategory1: LiveData<Boolean> = _isCategory1




    //Nearby
    private val _listDestination = MutableLiveData<Result<List<ListDestinationResponseItem>>>()
    val listDestination: LiveData<Result<List<ListDestinationResponseItem>>> = _listDestination

    //most review
    private val _listDestinationReview =
        MutableLiveData<Result<List<ListDestinationResponseItem>>>()
    val listDestinationReview: LiveData<Result<List<ListDestinationResponseItem>>> =
        _listDestinationReview

    //category
    private val _listDestinationCategory =
        MutableLiveData<Result<List<ListDestinationResponseItem>>>()
    val listDestinationCategory: LiveData<Result<List<ListDestinationResponseItem>>> =
        _listDestinationCategory

    private val _resultDetailDestination = MutableLiveData<Result<DetailResponse>>()
    val resultDetailDestination: LiveData<Result<DetailResponse>> = _resultDetailDestination

    private val _resultMessage = MutableLiveData<Result<MessageResponse>>()
    val resultMessage: LiveData<Result<MessageResponse>> = _resultMessage

    init {
        _isDropBtn1Click.value = false
        _isDropBtn2Click.value = false

        _isCategory1.value = false
        _isCategory2.value = false
        _isCategory3.value = false
        _isCategory4.value = false
        _isCategory5.value = false
    }

    private fun getListDestination(lat: Double, lon: Double) {
        viewModelScope.launch {
            _listDestination.value = Result.Loading
            _listDestination.value = userRepository.listDestination(lat, lon)
        }
    }

    private fun getListCategoryDestination(category: String, lat: Double, lon: Double) {
        viewModelScope.launch {
            _listDestinationCategory.value = Result.Loading
            _listDestinationCategory.value =
                userRepository.listCategoryDestination(category, lat, lon)
        }
    }

    private fun getListReviewDestination(lat: Double, lon: Double) {
        viewModelScope.launch {
            _listDestinationReview.value = Result.Loading
            _listDestinationReview.value = userRepository.listReviewDestination(lat, lon)
        }
    }

    fun getReviewsItem(name: String) {
        viewModelScope.launch {
            _resultDetailDestination.value = Result.Loading
            _resultDetailDestination.value = userRepository.detailDestination(name)
        }
    }

    fun postReview(name: String, comment: String, rating: String, token: String) {
        viewModelScope.launch {
            _resultMessage.value = Result.Loading
            _resultMessage.value = userRepository.ratingDestination(name, rating, comment, token)
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentLocation(context: Context) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (previousLocation != null) {
                    val newLocation = Location("newLocation")
                    newLocation.latitude = location.latitude
                    newLocation.longitude = location.longitude

                    val distance = previousLocation?.distanceTo(newLocation)

                    // Memeriksa jika perubahan jarak lebih dari 50 meter
                    if (distance != null) {
                        if (distance > 50) {
                            previousLocation = Location("previousLocation")
                            previousLocation?.latitude = location.latitude
                            previousLocation?.longitude = location.longitude

                            getListDestination(location.latitude, location.longitude)
                        }
                    }
                } else {
                    previousLocation = Location("previousLocation")
                    previousLocation?.latitude = location.latitude
                    previousLocation?.longitude = location.longitude

                    getListDestination(location.latitude, location.longitude)
                }
            } else showToast(context, "Please Enable Location Settings.")
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentCategoryLocation(context: Context, category: String) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (previousLocation != null) {
                    val newLocation = Location("newLocation")
                    newLocation.latitude = location.latitude
                    newLocation.longitude = location.longitude

                    val distance = previousLocation!!.distanceTo(newLocation)

                    // Memeriksa jika perubahan jarak lebih dari 50 meter
                    if (distance > 50) {
                        previousLocation = Location("previousLocation")
                        previousLocation!!.latitude = location.latitude
                        previousLocation!!.longitude = location.longitude

                        getListCategoryDestination(category, location.latitude, location.longitude)
                    }
                } else {
                    previousLocation = Location("previousLocation")
                    previousLocation!!.latitude = location.latitude
                    previousLocation!!.longitude = location.longitude

                    getListCategoryDestination(category, location.latitude, location.longitude)
                }
            } else showToast(context, "Please Enable Location Settings.")
        }
    }

    @SuppressLint("MissingPermission")
    fun fetchCurrentReviewLocation(context: Context) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                if (previousLocation != null) {
                    val newLocation = Location("newLocation")
                    newLocation.latitude = location.latitude
                    newLocation.longitude = location.longitude

                    val distance = previousLocation!!.distanceTo(newLocation)

                    // Memeriksa jika perubahan jarak lebih dari 50 meter
                    if (distance > 50) {
                        previousLocation = Location("previousLocation")
                        previousLocation!!.latitude = location.latitude
                        previousLocation!!.longitude = location.longitude

                        getListReviewDestination(location.latitude, location.longitude)
                    }
                } else {
                    previousLocation = Location("previousLocation")
                    previousLocation!!.latitude = location.latitude
                    previousLocation!!.longitude = location.longitude

                    getListReviewDestination(location.latitude, location.longitude)
                }
            } else showToast(context, "Please Enable Location Settings.")
        }
    }

     fun dropDown1Click(isClick: Boolean) {
         _isDropBtn1Click.value = isClick
    }

     fun dropDown2Click(isClick: Boolean) {
         _isDropBtn2Click.value = isClick
    }

    fun dropDown3Click(isClick: Boolean) {
        _isDropBtn3Click.value = isClick
    }

     fun clickCategory1(isClick: Boolean) {
         _isCategory1.value = isClick
    }

     fun clickCategory2(isClick: Boolean) {
         _isCategory2.value = isClick
    }

     fun clickCategory3(isClick: Boolean) {
         _isCategory3.value = isClick
    }

     fun clickCategory4(isClick: Boolean) {
         _isCategory4.value = isClick
    }

     fun clickCategory5(isClick: Boolean) {
         _isCategory5.value = isClick
    }
}

