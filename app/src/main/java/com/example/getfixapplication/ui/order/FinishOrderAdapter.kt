package com.example.getfixapplication.ui.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.githubsocial.util.AutoUpdatableAdapter
import com.example.getfixapplication.data.model.OrderListItem
import com.example.getfixapplication.databinding.ItemMyOrderBinding
import kotlin.properties.Delegates

class FinishOrderAdapter :
    RecyclerView.Adapter<FinishOrderAdapter.ListViewHolder>(), AutoUpdatableAdapter {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    var items: List<OrderListItem> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.orderId == n.orderId }
    }

    class ListViewHolder(var binding: ItemMyOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = ItemMyOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (orderId, jenisOrder, _, statusOrder, _) = items[holder.adapterPosition]
        holder.binding.apply {
            tvLabelInvoice.text = orderId
            tvLaptop.text = jenisOrder

            holder.binding.button2.setOnClickListener {
                onItemClickCallback.onItemClicked(items[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    interface OnItemClickCallback {
        fun onItemClicked(data: OrderListItem)
    }
}