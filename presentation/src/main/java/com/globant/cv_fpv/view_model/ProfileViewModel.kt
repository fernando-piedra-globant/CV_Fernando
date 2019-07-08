package com.globant.cv_fpv.view_model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;
import com.globant.cv_fpv.model.Profile

class ProfileViewModel : ViewModel() {
     val profile = MutableLiveData<Profile>()

     fun loadProfile() {
        profile.value =  Profile(
            "Fernando",
            "Ingeniero en sistemas",
            "Azcapotzalco, CDMX",
            "fernando.piedra@globant.com",
            "5555555555"
        )
        // Do an asynchronous operation to fetch users.
    }
}
