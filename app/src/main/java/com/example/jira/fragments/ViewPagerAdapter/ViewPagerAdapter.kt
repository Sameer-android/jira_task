package com.example.jira.fragments.ViewPagerAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.jira.R

class ViewPagerAdapter(
    private val context: Context,
    private val imageList: List<Int>,
    private val textList: List<String>
) : PagerAdapter() {

    override fun getCount(): Int = imageList.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view === `object` as ConstraintLayout

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = inflater.inflate(R.layout.image_slider_item, container, false)

        // Set image for ImageView
        val imageView: ImageView = itemView.findViewById(R.id.idIVImage)
        imageView.setImageResource(imageList[position])

        // Set text for TextView
        val textView: TextView = itemView.findViewById(R.id.idTVText)
        textView.text = textList[position]

        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}
