package AdapterClass

import HelperClass.hoardingHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.example.agrogo.R

class hoardingSlide( private val pages:List<hoardingHelper>) : RecyclerView.Adapter<hoardingSlide.PageViewHolder>(){
    inner class PageViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val animation: LottieAnimationView = view.findViewById(R.id.slide)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):PageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_hoarding_slide, parent, false)
        return PageViewHolder(view)
    }

    override fun onBindViewHolder(holder:PageViewHolder, position: Int) {
        val item = pages[position]
        holder.animation.setAnimation(item.animationFile)  // Correct for R.raw.xxx
        holder.animation.playAnimation()
    }
    override fun getItemCount(): Int = pages.size
}