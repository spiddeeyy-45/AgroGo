package AdapterClass

import HelperClass.ReHelper
import HelperClass.remHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R
class mostordedre (
    private var remHelperAd: MutableList<remHelper>,
    private val onAddToCart: (remHelper) -> Unit,
    private val onBuyNow: (remHelper) -> Unit
) : RecyclerView.Adapter<mostordedre.recyclerHolder>() {

    class recyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_pro: ImageView = itemView.findViewById(R.id.prod_image2)
        val name_pro: TextView = itemView.findViewById(R.id.prod_name2)
        val desc_pro: TextView = itemView.findViewById(R.id.prod_desc2)
        val price_pro: TextView = itemView.findViewById(R.id.prod_prices)
        val btnAddToCart: Button = itemView.findViewById(R.id.btnCart)
        val btnBuy: Button = itemView.findViewById(R.id.btnBuy2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mostordered, parent, false)
        return recyclerHolder(view)
    }

    override fun onBindViewHolder(holder: recyclerHolder, position: Int) {
        val item = remHelperAd[position]
        holder.name_pro.text = item.name
        holder.desc_pro.text = item.description
        holder.price_pro.text = "₹${item.price}"


        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.a)
            .into(holder.image_pro)
        // ✅ Handle clicks
        holder.btnAddToCart.setOnClickListener { onAddToCart(item) }
        holder.btnBuy.setOnClickListener { onBuyNow(item) }
    }

    override fun getItemCount(): Int = remHelperAd.size

}




