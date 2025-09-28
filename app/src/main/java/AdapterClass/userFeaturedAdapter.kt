package AdapterClass

import HelperClass.ReHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class adapterre(
    private var reHelperAd: List<ReHelper>,
    private val onAddToCart: (ReHelper) -> Unit,
    private val onBuyNow: (ReHelper) -> Unit
) : RecyclerView.Adapter<adapterre.revieholder>() {

    class revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgb: ImageView = itemView.findViewById(R.id.image_prod)
        val titl: TextView = itemView.findViewById(R.id.name_prod)
        val desc: TextView = itemView.findViewById(R.id.des_prod)
        val price: TextView = itemView.findViewById(R.id.price_prod)
        val btnAddToCart: Button = itemView.findViewById(R.id.btnaddtocart)
        val btnBuy: Button = itemView.findViewById(R.id.btnbuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): revieholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.featuredrecylcer, parent, false)
        return revieholder(view)
    }

    override fun onBindViewHolder(holder: revieholder, position: Int) {
        val item = reHelperAd[position]
        holder.titl.text = item.name
        holder.desc.text = item.description
        holder.price.text = "₹${item.price}"


        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.vl)
            .into(holder.imgb)
        // ✅ Handle clicks
        holder.btnAddToCart.setOnClickListener { onAddToCart(item) }
        holder.btnBuy.setOnClickListener { onBuyNow(item) }
    }

    override fun getItemCount(): Int = reHelperAd.size

}



