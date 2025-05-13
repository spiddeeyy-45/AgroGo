import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrogo.R
import com.example.agrogo.vegmost

class vmostadapter (private val vegmoHelperAd: List<vegmost>)  : RecyclerView.Adapter<vmostadapter.vremvieholder>() {

    class vremvieholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vmimgb: ImageView = itemView.findViewById(R.id.vegmostord)
        val vmtitl: TextView = itemView.findViewById(R.id.vegmostt)
        val vmdesc: TextView = itemView.findViewById(R.id.vegmostdesc)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vremvieholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vegmostrecy, parent, false)
        return vremvieholder(view)
    }

    override fun onBindViewHolder(holder: vremvieholder, position: Int) {
        val item = vegmoHelperAd[position]
        holder.vmimgb.setImageResource(item.vmimage)
        holder.vmtitl.text = item.vmtitle
        holder.vmdesc.text = item.vmdesc
    }
    override fun getItemCount(): Int {
        return vegmoHelperAd.size
    }


}
