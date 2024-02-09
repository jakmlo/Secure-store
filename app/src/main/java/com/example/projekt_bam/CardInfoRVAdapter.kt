package com.example.projekt_bam


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CardInfoRVAdapter(
    val context: Context,
    val cardInfoClickDeleteInterface: CardInfoClickDeleteInterface,
    val cardInfoClickInterface: CardInfoClickInterface
) :
    RecyclerView.Adapter<CardInfoRVAdapter.ViewHolder>() {

    private val allCardInfo = ArrayList<CardInfo>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bankNameTV = itemView.findViewById<TextView>(R.id.idTVBankName)
        val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.card_info_rv_item,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bankNameTV.text = allCardInfo.get(position).bankName
        holder.deleteIV.setOnClickListener {
            cardInfoClickDeleteInterface.onDeleteIconClick(allCardInfo.get(position))
        }
        holder.itemView.setOnClickListener {
            cardInfoClickInterface.onCardInfoClick(allCardInfo.get(position))
        }
    }

    override fun getItemCount(): Int {
        return allCardInfo.size
    }

    fun updateList(newList: List<CardInfo>) {
        allCardInfo.clear()
        allCardInfo.addAll(newList)
        notifyDataSetChanged()
    }
}

interface CardInfoClickDeleteInterface {
    fun onDeleteIconClick(cardInfo: CardInfo)
}

interface CardInfoClickInterface {
    fun onCardInfoClick(cardInfo: CardInfo)
}