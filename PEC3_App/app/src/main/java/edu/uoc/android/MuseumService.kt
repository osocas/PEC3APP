package edu.uoc.android

//import android.telecom.Call
//import edu.uoc.android.models.Museums
import edu.uoc.android.models.Museums
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Path


interface MuseumService {

    //@GET( "/api/dataset/museus/format/json/pag−ini/{pagIni}/pag−fi/{pagFi}")
    //fun museums(@Query("pagIni") pagIni : String, @Query("pagFi") pagFi : String)//: Call<Post> es una clase el Post

    @GET("/api/dataset/museus/format/json/pag-ini/{pagIni}/pag-fi/{pagFi}")
    fun museums(@Path("pagIni") pagIni: String, @Path("pagFi") pagFi: String): Call<Museums>
   // @GET( "/api/dataset/museus/format/json/pag?ini/{pagIni}/pag?fi/{pagFi}")
   // fun museums(@Query("pagIni") pagIni : String, @Query("pagFi") pagFi : String)

}

