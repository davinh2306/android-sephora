package fr.davinhdot.sephora.ui.catalog.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.davinhdot.sephora.R
import fr.davinhdot.sephora.domain.entity.Item
import timber.log.Timber

class ItemAdapter(
    private val items: List<Item>,
    private val clickListener: (Item) -> Unit
) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        Timber.d("onCreateViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return ItemViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Timber.d("onCreateViewHolder")
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(
        itemView: View,
        private val listener: (Item) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Item) {
            Timber.d("bind item $item")

            itemView.findViewById<TextView>(R.id.item_name).text = item.id

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }

    class MarginItemDecoration(private val listSpace: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            Timber.d("getItemOffsets")

            outRect.bottom = listSpace
        }
    }
}