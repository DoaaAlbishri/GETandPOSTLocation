package com.example.getandpostlocation

import com.google.gson.annotations.SerializedName

class UserDetails {

    var data: List<Datum>? = null

    class Datum {
        @SerializedName("pk")
        var pk: Int? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        constructor(pk:Int? , name: String? ,location: String?) {
            this.name = name
            this.pk = pk
            this.location = location
        }
    }
}