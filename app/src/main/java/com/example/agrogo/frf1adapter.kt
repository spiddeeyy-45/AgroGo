package com.example.agrogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import frf1helper

class frf1adapter (private var frfHelperAd: List<frf1helper>)  : RecyclerView.Adapter<frf1adapter.frf1revieholder>() {


        class frf1revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val frimgb: ImageView = itemView.findViewById(R.id.ff1recy)
            val frtitl: TextView = itemView.findViewById(R.id.ff1titre)
            val frdesc: TextView = itemView.findViewById(R.id.ff1descre)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): frf1revieholder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.fruitfeturedrecy1, parent, false)
            return frf1revieholder(view)
        }

        override fun onBindViewHolder(holder: frf1revieholder, position: Int) {
            val item = frfHelperAd[position]
            holder.frimgb.setImageResource(item.ffimage)
            holder.frtitl.text = item.fftitle
            holder.frdesc.text = item.ffdesc
        }
        override fun getItemCount(): Int {
            return frfHelperAd.size
        }


    }



