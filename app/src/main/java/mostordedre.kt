import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrogo.R
class mostordedre (private val remHelperAd: List<remHelper>)  : RecyclerView.Adapter<mostordedre.remvieholder>() {

        class remvieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val mimgb: ImageView = itemView.findViewById(R.id.moapp)
            val mtitl: TextView = itemView.findViewById(R.id.motit)
            val mdesc: TextView = itemView.findViewById(R.id.modes)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): remvieholder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.mostordered, parent, false)
            return remvieholder(view)
        }

        override fun onBindViewHolder(holder: remvieholder, position: Int) {
            val item = remHelperAd[position]
            holder.mimgb.setImageResource(item.mimage)
            holder.mtitl.text = item.mtitle
            holder.mdesc.text = item.mdesc
        }
        override fun getItemCount(): Int {
            return remHelperAd.size
        }


    }
