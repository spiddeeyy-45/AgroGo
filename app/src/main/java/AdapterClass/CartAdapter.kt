package AdapterClass

import HelperClass.carthelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class CartAdapter(private var carthelper: List<carthelper>,
                  private val onRemoveBTN: (carthelper) -> Unit,
                  private val onBuyBTN: (carthelper) -> Unit
) : RecyclerView.Adapter<CartAdapter.recyclerHolder>() {

    class recyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_pro: ImageView = itemView.findViewById(R.id.prod_imageCart)
        val name_pro: TextView = itemView.findViewById(R.id.prod_nameCart)
        val desc_pro: TextView = itemView.findViewById(R.id.prod_descCart)
        val price_pro: TextView = itemView.findViewById(R.id.prod_priceCart)
        val btnRemove: Button = itemView.findViewById(R.id.removeBTNCart)
        val btnBuy: Button = itemView.findViewById(R.id.buyBTNCart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_layout, parent, false)
        return recyclerHolder(view)
    }

    override fun onBindViewHolder(holder: recyclerHolder, position: Int) {
        val item = carthelper[position]
        holder.name_pro.text = item.name
        holder.desc_pro.text = item.description
        holder.price_pro.text = "₹${item.price}"


        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.a)
            .into(holder.image_pro)
        //  Handle clicks
        holder.btnRemove.setOnClickListener { onRemoveBTN(item) }
        holder.btnBuy.setOnClickListener { onBuyBTN(item) }
    }

    override fun getItemCount(): Int = carthelper.size

}