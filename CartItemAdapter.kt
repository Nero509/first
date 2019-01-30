package com.nero.esdcare.cart

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.cspl.nuucare.utils.Logging
import com.nero.esdcare.R
import kotlinx.android.synthetic.main.cart_item_adapter.view.*
import kotlinx.android.synthetic.main.cart_total_price_layout.view.*

class CartItemAdapter(var activity:Activity, val list: ArrayList<String>,val drawable:Drawable?):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //val listener=activity as CartMediator
    private var init= false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType==2){
            val view=LayoutInflater.from(activity).inflate(R.layout.cart_item_adapter,parent,false)
            Item(view)
        }else{
            val view=LayoutInflater.from(activity).inflate(R.layout.cart_total_price_layout,parent,false)
            Item(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=list.size) {
            (holder as Item).bindData(list[position])
        }else{
            if(init){
               // (holder as Item).bindData2()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (!payloads.isEmpty() ) {
            //Logging.showShortToast(activity,"if")
            (holder as Item).bindData2()
        }else{
            super.onBindViewHolder(holder, position, payloads)
            //Logging.showShortToast(activity,"else")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(position==list.size){
            1
        }else{
            2
        }
    }

    override fun getItemCount() = (list.size+1)

    inner class Item(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindData(model:String){
            itemView.title_product_cart.text=model
            itemView.real_price_cart.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            itemView.xxx.background=drawable
        }

        fun bindData2(){

            val colorAnim= ObjectAnimator.ofInt(itemView.price_details_title,"textColor",
                    Color.parseColor("#ff2874f0"),Color.GRAY).setDuration(2000)
            colorAnim.setEvaluator(ArgbEvaluator())
            colorAnim.start()

            val animShake = AnimationUtils.loadAnimation(activity, R.anim.shake)
            itemView.price_details_title.startAnimation(animShake)
        }

    }
}