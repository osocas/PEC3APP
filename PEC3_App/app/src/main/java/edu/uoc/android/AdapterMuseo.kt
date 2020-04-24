package edu.uoc.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AdapterMuseo(var list: ArrayList<MuseoClass>): RecyclerView.Adapter<AdapterMuseo.ViewHolder>(){

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        fun bindItems(data:MuseoClass){
            val name:TextView=itemView.findViewById(R.id.tv_museo)
            val thumbnail:ImageView=itemView.findViewById(R.id.iv_museo)

            name.text=data.name
            Glide.with(itemView.context).load(data.thumbnail).into(thumbnail)

            itemView.setOnClickListener{
                Toast.makeText(itemView.context, "Museo ${data.name}", Toast.LENGTH_LONG).show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.content_item,parent,false)

        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterMuseo.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }



}