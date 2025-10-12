package AdapterClass

import HelperClass.farmerproductdataclass
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.agrogo.R

class FarmerHomeAdapter(
    private val farmeradapter: MutableList<farmerproductdataclass>,  // 👈 made mutable
    private val onEditClick: (farmerproductdataclass) -> Unit,
    private val onDeleteClick: (farmerproductdataclass) -> Unit
) : RecyclerView.Adapter<FarmerHomeAdapter.farmerviewholder>() {

    class farmerviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val prod_image: ImageView = itemView.findViewById(R.id.prod_image)
        val prod_name: TextView = itemView.findViewById(R.id.prod_name)
        val prod_desc: TextView = itemView.findViewById(R.id.prod_desc)
        val prod_priceperkg: TextView = itemView.findViewById(R.id.price)
        val prod_quantity: TextView = itemView.findViewById(R.id.quantity)
        val updatebtn: Button = itemView.findViewById(R.id.update)
        val deletebtn: Button = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): farmerviewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.farmer_product, parent, false)
        return farmerviewholder(view)
    }

    override fun onBindViewHolder(holder: farmerviewholder, position: Int) {
        val product = farmeradapter[position]
        holder.prod_name.text = product.name
        holder.prod_priceperkg.text = "₹${product.price}"
        holder.prod_quantity.text = "${product.quantity} kg"
        holder.prod_desc.text = product.description

        Glide.with(holder.itemView.context)
            .load(product.imageUrl)
            .into(holder.prod_image)

        holder.updatebtn.setOnClickListener { onEditClick(product) }
        holder.deletebtn.setOnClickListener { onDeleteClick(product) }
    }

    override fun getItemCount(): Int = farmeradapter.size

    // ✅ Add this method to update list dynamically
    fun updateList(newList: List<farmerproductdataclass>) {
        farmeradapter.clear()
        farmeradapter.addAll(newList)
        notifyDataSetChanged()
    }
}
