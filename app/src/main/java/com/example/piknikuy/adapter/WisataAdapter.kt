package com.example.piknikuy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.piknikuy.databinding.ItemListBinding
import com.example.piknikuy.model.ModelWisata

class WisataAdapter : RecyclerView.Adapter<WisataAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    var listWisata = arrayListOf<ModelWisata>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        listWisata.clear()
        listWisata.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding =
            ItemListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listWisata[position])
    }

    override fun getItemCount(): Int = listWisata.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataWisata: ModelWisata) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataWisata.picture)
                    .into(avatarFav)

                tvName.text = dataWisata.name

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataWisata) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelWisata)
    }
}