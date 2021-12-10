package fr.davinhdot.sephora.ui.detail.view

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import fr.davinhdot.sephora.R
import fr.davinhdot.sephora.databinding.BottomSheetDetailBinding
import fr.davinhdot.sephora.domain.entity.Item
import fr.davinhdot.sephora.utils.ImageHelper
import timber.log.Timber


class BottomSheetDetailFragment : BottomSheetDialogFragment() {

    companion object {

        private const val DEFAULT_PERCENTAGE_BOTTOM_SHEET = 0.8

        private const val ARGS_ITEM = "args_item"

        fun newInstance(item: Item) =
            BottomSheetDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARGS_ITEM, item)
                }
            }
    }

    var disableDrag = false

    private var item: Item? = null

    private lateinit var binding: BottomSheetDetailBinding

    private lateinit var parentActivity: AppCompatActivity

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private lateinit var bottomSheetCallback: BottomSheetCallback


    override fun onAttach(context: Context) {
        Timber.d("onAttach")

        super.onAttach(context)

        if (context is AppCompatActivity) {
            @Suppress("UNCHECKED_CAST")
            this.parentActivity = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Timber.d("handleOnBackPressed")

                this@BottomSheetDetailFragment.dismiss()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setStyle(STYLE_NORMAL, R.style.Sephora_BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")

        binding = BottomSheetDetailBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parseArgs()

        initBottomSheet()

        initView()
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")

        super.onDestroyView()

        if (::bottomSheetBehavior.isInitialized) {
            bottomSheetBehavior.removeBottomSheetCallback(bottomSheetCallback)
        }
    }


    override fun show(manager: FragmentManager, tag: String?) {
        Timber.d("show")

        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commitAllowingStateLoss()
        } catch (illegalStateException: IllegalStateException) {
            Timber.e("show - exception $illegalStateException")
        }
    }

    private fun parseArgs() {
        Timber.d("parseArgs")

        arguments?.run {
            item = getParcelable(ARGS_ITEM)
        }
    }

    private fun initBottomSheet() {
        Timber.d("initBottomSheet")

        dialog?.setOnShowListener { dialog ->

            val bottomSheet = (dialog as BottomSheetDialog)
                .findViewById<View>(R.id.design_bottom_sheet) as FrameLayout

            context?.let { context ->
                val params = bottomSheet.layoutParams

                val displayMetrics = DisplayMetrics()
                (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

                params.height =
                    (displayMetrics.heightPixels * DEFAULT_PERCENTAGE_BOTTOM_SHEET).toInt()

                bottomSheet.layoutParams = params

                bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.peekHeight = params.height
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                bottomSheetCallback = object : BottomSheetCallback() {
                    override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                        if (disableDrag && newState == BottomSheetBehavior.STATE_DRAGGING) {
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismissAllowingStateLoss()
                        }
                    }

                    override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                        // nothing
                    }
                }
                bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)
            }
        }
    }

    private fun initView() {

        context?.let {
            ImageHelper.displayImageFromUrl(
                context = it,
                image = item?.image,
                imageView = binding.detailImage
            )
        }

        item?.let {
            binding.detailDescription.text = if (it.description.isEmpty()) {
                resources.getString(R.string.detail_empty_description)
            } else {
                it.description
            }
            binding.detailLocation.text = if (it.location.isEmpty()) {
                resources.getString(R.string.detail_empty_location)
            } else {
                it.location
            }
            binding.detailId.text = it.id
        }
    }
}