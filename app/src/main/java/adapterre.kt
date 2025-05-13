import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrogo.R

 class adapterre (private var reHelperAd: List<ReHelper>)  : RecyclerView.Adapter<adapterre.revieholder>() {


     class revieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val imgb: ImageView = itemView.findViewById(R.id.imrecy)
         val titl: TextView = itemView.findViewById(R.id.imtitre)
         val desc: TextView = itemView.findViewById(R.id.descre)

     }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): revieholder {
         val view = LayoutInflater.from(parent.context).inflate(R.layout.featuredrecylcer, parent, false)
         return revieholder(view)
     }

     override fun onBindViewHolder(holder: revieholder, position: Int) {
         val item = reHelperAd[position]
         holder.imgb.setImageResource(item.image)
         holder.titl.text = item.title
         holder.desc.text = item.desc
     }
     override fun getItemCount(): Int {
         return reHelperAd.size
     }


 }


