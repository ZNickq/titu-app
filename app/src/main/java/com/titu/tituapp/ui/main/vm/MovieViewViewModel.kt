package com.titu.tituapp.ui.main.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Album(val name: String, val count: Int = 0)

class MovieViewViewModel : ViewModel() {
    inner class LiveItem {
        val name = MutableLiveData<String>()
    }

    //var item: Album = Album(name="Original")
    val liveItem: LiveItem = LiveItem()

//    fun copyItemToLive(item: Album) {
//        // store data
//        this.item = item
//        // copy to liveItem
//        liveItem.apply {
//            name.value = item.name
//        }
//    }
//
//    fun save() {
//        // save item to database
//    }
}