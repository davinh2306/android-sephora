package fr.davinhdot.sephora.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import fr.davinhdot.sephora.R
import timber.log.Timber

object ImageHelper {

    fun displayImageFromUrl(
        context: Context,
        image: Any? = null,
        imageView: ImageView? = null
    ) {
        Timber.d("displayImageFromUrl")

        val options = RequestOptions()
            .placeholder(R.drawable.item_background)

        imageView?.let { view ->
            Glide.with(context)
                .load(image)
                .apply(options)
                .into(view)
        }

    }
}