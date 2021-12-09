package fr.davinhdot.sephora.ui.catalog.adapter

import android.app.Activity
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.davinhdot.sephora.R
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.utils.ImageHelper
import timber.log.Timber

class ItemAdapter(
    private val activity: Activity,
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
        holder.bind(activity, items[position])
    }

    override fun getItemCount() = items.size

    class ItemViewHolder(
        itemView: View,
        private val listener: (Item) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(activity: Activity, item: Item) {
            Timber.d("bind item $item")

            ImageHelper.displayImageFromUrl(
                context = activity,
                image = item.image,
                imageView = itemView.findViewById(R.id.item_image)
            )

            itemView.setOnClickListener {
                listener(item)
            }
        }
    }

    class MarginItemDecoration(private val space: Int, private val size: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            Timber.d("getItemOffsets")

            val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewAdapterPosition

            if (itemPosition % size == 0) {
                outRect.right = space
            } else {
                outRect.left = space
            }
            outRect.top = space
            outRect.bottom = space
        }
    }
}