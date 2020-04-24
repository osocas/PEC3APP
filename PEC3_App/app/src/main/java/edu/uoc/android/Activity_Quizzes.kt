package edu.uoc.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Activity_Quizzes : AppCompatActivity() {

    private  val lista_preguntas: ArrayList<Quizzes> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity__quizzes)

        val db : FirebaseFirestore= FirebaseFirestore.getInstance()

        db.collection("Quizzes").get().addOnCompleteListener{
            if(it.isSuccessful){
                lista_preguntas.clear()
                for(documentos in it.result!!){
                    val title = documentos.getString("title")
                    val rigthChoice:Long? = documentos.get("rigthChoice") as Long?
                    val image = documentos.getString("image")
                    val choice2 = documentos.getString("choice2")
                    val choice1 = documentos.getString("choice1")

                    Log.d("Etiqueta", image)

                    if (title != null && rigthChoice != null && image!=null && choice2 != null && choice1!=null){
                        lista_preguntas.add(Quizzes(title,rigthChoice,image,choice2,choice1))
                    }

                }
            }
        }
    }
}
