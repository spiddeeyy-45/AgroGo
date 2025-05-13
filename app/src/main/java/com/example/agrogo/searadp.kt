package com.example.agrogo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

// The imports should be correct now, no broken ones like '\.LayoutInflater'
import ReHelper

class searadp(private var reHelperAd: List<ReHelper>) : RecyclerView.Adapter<searadp.revieholder>() {

    // Keep full copy of the list for search functionality
    private var fullList: List<ReHelper> = ArrayList(reHelperAd)

    // ViewHolder to hold the views for each item
    class revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgbs: ImageView = itemView.findViewById(R.id.search)
        val titls: TextView = itemView.findViewById(R.id.seachbt)
        val descs: TextView = itemView.findViewById(R.id.searchde)
    }

    // Creates the view holder for each item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): revieholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search, parent, false)
        return revieholder(view)
    }

    // Binds the data to the views
    override fun onBindViewHolder(holder: revieholder, position: Int) {
        val item = reHelperAd[position]
        holder.imgbs.setImageResource(item.image) // Set the image
        holder.titls.text = item.title // Set the title
        holder.descs.text = item.desc // Set the description
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return reHelperAd.size
    }

    // Function to filter the list based on search query
    fun filterList(query: String, context: Context) {
        val filteredList = mutableListOf<ReHelper>()

        // Check each item if it matches the query
        for (item in fullList) {
            if (item.title.contains(query, ignoreCase = true) ||
                item.desc.contains(query, ignoreCase = true)) {
                filteredList.add(item)
            }
        }

        // If no data found, show a Toast
        if (filteredList.isEmpty()) {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show()
        }

        // Update the list with filtered results
        updateList(filteredList)
    }

    // Updates the current list and refreshes the RecyclerView
    fun updateList(newList: List<ReHelper>) {
        reHelperAd = newList
        notifyDataSetChanged() // Refresh the RecyclerView
    }
}
