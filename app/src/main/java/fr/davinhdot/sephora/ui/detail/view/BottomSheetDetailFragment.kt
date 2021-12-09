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
import fr.davinhdot.sephora.domain.entity.Item
import timber.log.Timber

class BottomSheetDetailFragment : BottomSheetDialogFragment() {

    companion object {

        private const val DEFAULT_PERCENTAGE_BOTTOM_SHEET = 0.75

        private const val ARGS_ITEM = "args_item"

        fun newInstance(item: Item) =
            BottomSheetDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARGS_ITEM, item)
                }
            }
    }

    var mDisableDrag = false

    private lateinit var mParentActivity: AppCompatActivity

    private lateinit var mBottomSheetBehavior: BottomSheetBehavior<FrameLayout>

    private lateinit var mBottomSheetCallback: BottomSheetCallback


    override fun onAttach(context: Context) {
        Timber.d("onAttach")

        super.onAttach(context)

        if (context is AppCompatActivity) {
            @Suppress("UNCHECKED_CAST")
            this.mParentActivity = context
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

        // TODO setStyle(STYLE_NORMAL, R.style.DataHero_BottomSheetTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView")

        // get the views and attach the listener
        return inflater.inflate(R.layout.bottom_sheet_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBottomSheet()
    }

    override fun onDestroyView() {
        Timber.d("onDestroyView")

        super.onDestroyView()

        if (::mBottomSheetBehavior.isInitialized) {
            mBottomSheetBehavior.removeBottomSheetCallback(mBottomSheetCallback)
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

                mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
                mBottomSheetBehavior.peekHeight = params.height
                mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

                mBottomSheetCallback = object : BottomSheetCallback() {
                    override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                        if (mDisableDrag && newState == BottomSheetBehavior.STATE_DRAGGING) {
                            mBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                        } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                            dismissAllowingStateLoss()
                        }
                    }

                    override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                        // nothing
                    }
                }
                mBottomSheetBehavior.addBottomSheetCallback(mBottomSheetCallback)
            }
        }
    }
}