import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrogo.R
import vegf2Helper

class vf2adapter (private var vf2reHelperAd: List<vegf2Helper>)  : RecyclerView.Adapter<vf2adapter.vf2revieholder>() {


    class vf2revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vf2imgb: ImageView = itemView.findViewById(R.id.vf2recy)
        val vf2titl: TextView = itemView.findViewById(R.id.vegf2titre)
        val vf2desc: TextView = itemView.findViewById(R.id.vegf2descre)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vf2revieholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vegfeturedrecy2, parent, false)
        return vf2revieholder(view)
    }

    override fun onBindViewHolder(holder: vf2revieholder, position: Int) {
        val item = vf2reHelperAd[position]
        holder.vf2imgb.setImageResource(item.vf2image)
        holder.vf2titl.text = item.vf2title
        holder.vf2desc.text = item.vf2desc
    }
    override fun getItemCount(): Int {
        return vf2reHelperAd.size
    }


}


