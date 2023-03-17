package com.aoreo.coco.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aoreo.coco.R
import com.aoreo.coco.dataModel.CurrentPriceResult

class SelectRVAdapter(val context : Context, val coinPriceList : List<CurrentPriceResult>):RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

    val selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val coinName : TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImg : ImageView = view.findViewById(R.id.likeBtn)

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectRVAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectRVAdapter.ViewHolder, position: Int) {
    holder.coinName.text=coinPriceList[position].coinName
        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H

        if(fluctate_24H.contains("-")){
            holder.coinPriceUpDown.text="하락"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        }else{
            holder.coinPriceUpDown.text="상승"
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }
        val likeImage = holder.likeImg
        if(selectedCoinList.contains(coinPriceList[position].coinName)){
            likeImage.setImageResource(R.drawable.like_red)
        }else{
            likeImage.setImageResource(R.drawable.like_grey)
        } // recyclerview는 view를 재사용하기 때문에 스크롤을 내렸을 때 클릭하지않았음에도 클릭되어있는 것으로 뜨는 오류 방지
        
        likeImage.setOnClickListener {
            if(selectedCoinList.contains(coinPriceList[position].coinName)){
                likeImage.setImageResource(R.drawable.like_grey)
                selectedCoinList.remove(coinPriceList[position].coinName)
            }else{
                selectedCoinList.add(coinPriceList[position].coinName)
                likeImage.setImageResource(R.drawable.like_red)
            }

        }
    }

    override fun getItemCount(): Int {
       return coinPriceList.size
    }

}