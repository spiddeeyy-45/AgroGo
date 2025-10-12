package AdapterClass

import HelperClass.orderhelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class OrderAdapter(private val orderList: MutableList<orderhelper>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePro: ImageView = itemView.findViewById(R.id.orderProductImage)
        val namePro: TextView = itemView.findViewById(R.id.orderProductName)
        val qtyPrice: TextView = itemView.findViewById(R.id.orderQtyPrice)
        val farmerUId: TextView = itemView.findViewById(R.id.orderDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_layout, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val item = orderList[position]

        // Product name
        holder.namePro.text = item.productName

        // Quantity x Price formatting
        val quantity = item.qty ?: 1
        val price = item.totalAmount ?: 0.0
        holder.qtyPrice.text = "Qty: $quantity | ₹$price"

        // Order date
        holder.farmerUId.text = item.farmerUID ?: "Date not available"

        // Load product image
        Glide.with(holder.itemView.context)
            .load(item.imageUrl)
            .placeholder(R.drawable.a)
            .into(holder.imagePro)
    }

    override fun getItemCount(): Int = orderList.size
}
