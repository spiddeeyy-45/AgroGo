package AdapterClass

import HelperClass.onboardslide
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.agrogo.R

class onboardslideadapter(private val pages: List<onboardslide>) :
    RecyclerView.Adapter<onboardslideadapter.PageViewHolder>() {

    inner class PageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val animationView: LottieAnimationView = view.findViewById(R.id.slide1)
        val heading: TextView = view.findViewById(R.id.headingslide1)
        val description: TextView = view.findViewById(R.id.slided1escription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboard_slide, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val item = pages[position]
        holder.animationView.setAnimation(item.animationFile)  // Correct for R.raw.xxx
        holder.animationView.playAnimation()
        holder.heading.text = item.heading
        holder.description.text = item.description
    }
    override fun getItemCount(): Int = pages.size
}