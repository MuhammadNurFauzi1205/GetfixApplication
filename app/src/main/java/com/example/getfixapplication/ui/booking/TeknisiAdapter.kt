package com.example.getfixapplication.ui.booking

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubsocial.util.AutoUpdatableAdapter
import com.example.getfixapplication.R
import com.example.getfixapplication.data.model.Teknisi
import com.example.getfixapplication.databinding.ItemTeknisiBinding
import kotlin.properties.Delegates

class TeknisiAdapter :
    RecyclerView.Adapter<TeknisiAdapter.ListViewHolder>(), AutoUpdatableAdapter {

    private var selectedItemPosition: Int = 0
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    var items: List<Teknisi> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.username == n.username }
    }

    class ListViewHolder(var binding: ItemTeknisiBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemTeknisiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (nama, alamat, id, avatar, rating) = items[holder.adapterPosition]
        Glide.with(holder.itemView.context)
            .load(avatar)
            .circleCrop()
            .into(holder.binding.imgItemPhoto)

        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.adapterPosition
            onItemClickCallback.onItemClicked(items[holder.adapterPosition])
            notifyDataSetChanged()
        }

        holder.binding.apply {
            tvItemTeknisi.text = nama
            tvItemId.text = StringBuilder("ID : ").append(id.toString())
            tvItemArea.text = alamat
            ratingTeknisi.rating = rating.toFloat()
            if(selectedItemPosition == holder.adapterPosition) {
                cvTeknisi.setCardBackgroundColor(Color.parseColor("#AB6DFD"))
                imgItemPhoto.background = AppCompatResources.getDrawable(holder.itemView.context, R.drawable.roundedborder)
            } else {
                cvTeknisi.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                imgItemPhoto.background = null
            }

        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Teknisi)
    }
}