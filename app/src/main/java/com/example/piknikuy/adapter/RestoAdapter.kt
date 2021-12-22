package com.example.piknikuy.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.piknikuy.databinding.ItemListBinding
import com.example.piknikuy.model.ModelResto

class RestoAdapter : RecyclerView.Adapter<RestoAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    var listResto = arrayListOf<ModelResto>()
    @SuppressLint("NotifyDataSetChanged")
    set(value) {
        listResto.clear()
        listResto.addAll(value)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listResto[position])
    }

    override fun getItemCount(): Int = listResto.size

    inner class ListViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataResto: ModelResto) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(dataResto.picture)
                    .into(avatarFav)
                tvName.text = dataResto.name

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(dataResto) }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ModelResto)
    }
}