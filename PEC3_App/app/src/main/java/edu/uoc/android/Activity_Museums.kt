package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.android.models.Museums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class Activity_Museums : AppCompatActivity() {

    lateinit var museumService: MuseumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_museums)
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val prograssBar:ProgressBar=findViewById(R.id.id_progressBar)

        val recyclerView:RecyclerView=findViewById(R.id.recycler)
        recyclerView.layoutManager=LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        val museos=ArrayList<MuseoClass>()



        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://do.diba.cat")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        museumService = retrofit.create(MuseumService::class.java)

        //museums()
        museumService.museums("1","10").enqueue(object : Callback<Museums>{
            override fun onResponse(call: Call<Museums>, response: Response<Museums>){
                if (response.code() == 200) {
                    //
                    // showProgress( false );
                    //
                    //

                    //museos.add(MuseoClass("Este es el nombre",R.drawable.logo))




                    val museums = response.body()!!
                    // Adapter <<− museums // elements
                    val elemento = museums.getElements()
                    for (oneElement in elemento) {
                       // Log.d("Etiqueta", oneElement.adrecaNom)
                        museos.add(MuseoClass(oneElement.adrecaNom,oneElement.imatge[0], 0.0, 0.0))
                    }
                    val adapter=AdapterMuseo(museos)

                    if(prograssBar.visibility== View.VISIBLE){
                        prograssBar.visibility=View.INVISIBLE
                    }

                    if(recyclerView.visibility== View.INVISIBLE){
                        recyclerView.visibility=View.VISIBLE
                    }

                    recyclerView.adapter=adapter
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) { // Log.d(TAG, ””xxxxx”)
                Log.d("Etiqueta","Fallo en llamada",t)
            }
        })

    }

    fun museums() {

    }

   /* val museumAPI = RetrofitFactory.museumAPI
    val call = museumAPI.museums("1", "5")

    call.enqueue (object: Callback<Museums> {
        override fun onResponse (call: Call <Museums>, response: Response<Museums>){
            if (response.code()==200) {
                //
                //showProgress(false);
                //
                val museums = response.body()!!
                //Adapter <<- museums // elements
                val elemento = museums.getElements()
                for (oneElement in elemento) {
                    Log.d("Retrobit", oneElement.adrecaNom)
                }
            }
        }
        override fun onFailure(call: Call<Museums>, t: Throwable) {
            Log.d("Retrobit","Fallo en llamada")
        }
    })*/
    /*companion object {
        const val API_URL = "https://do.diba.cat"
    }*/
}
