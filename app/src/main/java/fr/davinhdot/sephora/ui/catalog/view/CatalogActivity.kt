package fr.davinhdot.sephora.ui.catalog.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fr.davinhdot.sephora.R
import fr.davinhdot.sephora.databinding.ActivityCatalogBinding
import fr.davinhdot.sephora.domain.entity.HttpCallFailureException
import fr.davinhdot.sephora.domain.entity.NoNetworkException
import fr.davinhdot.sephora.domain.entity.ServerUnreachableException
import fr.davinhdot.sephora.ui.catalog.adapter.ItemAdapter
import fr.davinhdot.sephora.ui.catalog.viewmodel.CatalogViewModel
import fr.davinhdot.sephora.ui.detail.view.BottomSheetDetailFragment
import timber.log.Timber

@AndroidEntryPoint
class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding

    private val viewModel: CatalogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.mLiveDataNetworkError.observe(this@CatalogActivity, { error ->
            showError(error)
        })

        viewModel.mLiveDataItems.observe(this@CatalogActivity, { items ->
            binding.catalogList.apply {
                adapter = ItemAdapter(items) {
                    BottomSheetDetailFragment.newInstance(it)
                        .show(
                            supportFragmentManager,
                            BottomSheetDetailFragment::class.java.simpleName
                        )
                }
                layoutManager = LinearLayoutManager(this@CatalogActivity)
                addItemDecoration(
                    ItemAdapter.MarginItemDecoration(
                        resources.getDimension(R.dimen.item_list_margin_decoration).toInt()
                    )
                )
            }
        })

        viewModel.getAllItemsFromApi()
    }

    private fun showError(error: Throwable) {
        Timber.d("showError")

        val message = when (error) {
            is NoNetworkException -> resources.getString(R.string.error_network)
            is ServerUnreachableException -> resources.getString(R.string.error_network)
            is HttpCallFailureException -> resources.getString(R.string.error_unknown)
            else -> resources.getString(R.string.error_unknown)
        }

        Toast.makeText(this@CatalogActivity, message, Toast.LENGTH_SHORT)
            .show()
    }
}