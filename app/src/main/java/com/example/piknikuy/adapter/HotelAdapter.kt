package com.example.piknikuy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.piknikuy.databinding.ItemListBinding
import com.example.piknikuy.model.ModelHotel

class HotelAdapter : RecyclerView.Adapter<HotelAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    var listHotel = arrayListOf<ModelHotel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            listHotel.clear()
            listHotel.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listHotel[position])
    }

    override fun getItemCount(): Int = listHotel.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataHotel: ModelHotel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataHotel.picture)
                    .into(avatarFav)
                tvName.text = dataHotel.name

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataHotel) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelHotel)
    }
}