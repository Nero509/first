package com.nero.esdcare.cart

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.cspl.nuucare.utils.Logging
import com.nero.esdcare.R
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity : AppCompatActivity() {

    lateinit var adapter: CartItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val backgroundDrawable = ContextCompat.getDrawable(this@CartActivity, android.R.drawable.editbox_dropdown_light_frame)
        backgroundDrawable?.colorFilter = PorterDuffColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.OVERLAY)
        //xxx.background = backgroundDrawable

        continue_order.setOnClickListener {
            Logging.showUnderDevelopment(this@CartActivity)
        }

        val x=ArrayList<String>()
        x.add("Item-1")
        x.add("Item-2")
        x.add("Item-3")
        x.add("Item-4")
        x.add("Item-5")



        
        val margin= object : RecyclerView.ItemDecoration() {
            private val spaceHeight=15
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                with(outRect) {
                    /*if (parent.getChildAdapterPosition(view) == 0) {
                        top = spaceHeight
                    }*/
                    //left =  spaceHeight
                    //right = spaceHeight
                    bottom = spaceHeight
                }
            }
        }
        
        cart_item_list.setHasFixedSize(true)
        //cart_item_list.addItemDecoration(margin)

        cart_item_list.layoutManager=LinearLayoutManager(this)
        adapter=CartItemAdapter(this@CartActivity,x,backgroundDrawable)
        cart_item_list.adapter=adapter


        try {
             price_view.setOnClickListener { scrollToPriceDetail() }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun scrollToPriceDetail(){

        cart_item_list.post {
            cart_item_list.smoothScrollToPosition(5)



            /*Thread.sleep(500)

            val lastCardView=cart_item_list.findViewHolderForAdapterPosition(5).itemView
            val priceTitle=lastCardView.findViewById<TextView>(R.id.price_details_title)

            val colorAnim=ObjectAnimator.ofInt(priceTitle,"textColor",
                    Color.parseColor("#ff2874f0"),Color.GRAY).setDuration(2000)
            colorAnim.setEvaluator(ArgbEvaluator())
            colorAnim.start()

            val animShake = AnimationUtils.loadAnimation(this, R.anim.shake)
            priceTitle.startAnimation(animShake)*/


        }
        //adapter.notifyItemChanged(5,true)
    }

}