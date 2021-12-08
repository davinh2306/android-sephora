package fr.davinhdot.sephora.ui.catalog.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.davinhdot.sephora.databinding.ActivityCatalogBinding

class CatalogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCatalogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCatalogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}