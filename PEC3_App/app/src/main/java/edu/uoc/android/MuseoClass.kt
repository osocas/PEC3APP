package edu.uoc.android

import android.media.ThumbnailUtils

class  MuseoClass{
    var name:String =""
    var thumbnail:String=""
    var lat:Double=0.0
    var lng:Double=0.0

    constructor(name:String, thumbnail:String, lat:Double, lng:Double){
        this.name=name
        this.thumbnail=thumbnail
        this.lat=lat
        this.lng=lng

    }

    fun getname(): String {
        return name
    }

    fun getlat(): Double {
        return this.lat
    }
}