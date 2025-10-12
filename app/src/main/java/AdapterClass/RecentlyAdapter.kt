package AdapterClass

import HelperClass.ReHelper
import HelperClass.recentlyorder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class RecentlyAdapter (private var recentHelperAd: List<recentlyorder>,
                       private val onAddToCart3: (recentlyorder) -> Unit,
                       private val onBuyNow3: (recentlyorder) -> Unit
) : RecyclerView.Adapter<RecentlyAdapter.revieholder>() {

    class revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgb: ImageView = itemView.findViewById(R.id.image2)
        val titl: TextView = itemView.findViewById(R.id.name2)
        val desc: TextView = itemView.findViewById(R.id.desc2)
        val price: TextView = itemView.findViewById(R.id.prod_prices)
        val btnAddToCart: Button = itemView.findViewById(R.id.Cart)
        val btnBuy: Button = itemView.findViewById(R.id.Buy2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): revieholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_header, parent, false)
        return revieholder(view)
    }

    override fun onBindViewHolder(holder: revieholder, position: Int) {
        val item = recentHelperAd[position]
        holder.titl.text = item.name
        holder.desc.text = item.description
        holder.price.text = "₹${item.price}"


        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.vl)
            .into(holder.imgb)
        // ✅ Handle clicks
        holder.btnAddToCart.setOnClickListener { onAddToCart3(item) }
        holder.btnBuy.setOnClickListener { onBuyNow3(item) }
    }

    override fun getItemCount(): Int = recentHelperAd.size}