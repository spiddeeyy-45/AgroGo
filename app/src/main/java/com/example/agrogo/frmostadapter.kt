package com.example.agrogo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import frmost

class frmostadapter (private var frmHelperAd: List<frmost>)  : RecyclerView.Adapter<frmostadapter.frmostrevieholder>() {


    class frmostrevieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fmimgb: ImageView = itemView.findViewById(R.id.fmostord)
        val fmtitl: TextView = itemView.findViewById(R.id.fmostt)
        val fmdesc: TextView = itemView.findViewById(R.id.fmostdesc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): frmostrevieholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fruitmostrecy, parent, false)
        return frmostrevieholder(view)
    }

    override fun onBindViewHolder(holder: frmostrevieholder, position: Int) {
        val item = frmHelperAd[position]
        holder.fmimgb.setImageResource(item.fmimage)
        holder.fmtitl.text = item.fmtitle
        holder.fmdesc.text = item.fmdesc
    }
    override fun getItemCount(): Int {
        return frmHelperAd.size
    }


}

