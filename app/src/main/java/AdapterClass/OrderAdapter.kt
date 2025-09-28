package AdapterClass

import HelperClass.orderhelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class OrderAdapter( private val orderhelp : MutableList<orderhelper>) : RecyclerView.Adapter<OrderAdapter.recyclerHolder>()
{
    class recyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image_pro: ImageView = itemView.findViewById(R.id.prod_imageO)
        val name_pro: TextView = itemView.findViewById(R.id.prod_nameO)
        val desc_pro: TextView = itemView.findViewById(R.id.prod_descO)
        val price_pro: TextView = itemView.findViewById(R.id.prod_priceO)
        val buyer_name:TextView = itemView.findViewById(R.id.prod_buynameO)
        val prod_quantiyt:TextView= itemView.findViewById(R.id.product_quantityO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.orderlayout, parent, false)
        return recyclerHolder(view)
    }

    override fun onBindViewHolder(holder: recyclerHolder, position: Int) {
        val item = orderhelp[position]
        holder.name_pro.text = item.productName
        holder.desc_pro.text = item.productId
        holder.price_pro.text = "₹${item.totalAmount}"
        holder.buyer_name.text=item.buyerUid
        holder.prod_quantiyt.text=item.qty.toString()


        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.a)
            .into(holder.image_pro)

    }

    override fun getItemCount(): Int = orderhelp.size
}