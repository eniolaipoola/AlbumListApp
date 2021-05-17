package com.eniola.albumlistapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eniola.albumlistapp.R
import com.eniola.albumlistapp.base.BaseActivity
import com.eniola.albumlistapp.ui.album.AlbumListAdapter
import com.eniola.albumlistapp.ui.album.AlbumViewModel
import com.eniola.albumlistapp.ui.album.ViewState
import com.eniola.albumlistapp.utility.hide
import com.eniola.albumlistapp.utility.show
import com.eniola.albumlistapp.utility.toast
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<AlbumViewModel> { viewModelFactory }
    private val adapter by lazy { AlbumListAdapter() }


    override fun observeData() {
        viewModel.state.observe(this) { viewState ->
            when(viewState) {
                is ViewState.SUCCESS -> {
                    loader.hide()
                    //pass data list fetched from db to recyclerview
                    adapter.setListItem(viewState.data)
                    album_list_recyclerview.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL, false)
                    album_list_recyclerview.adapter = adapter
                }

                is ViewState.ERROR -> {
                    loader.hide()
                    this.toast(viewState.errorMessage)
                }

                is ViewState.LOADING -> {
                    if(viewState.loading){
                        loader.show()
                    } else {
                        loader.hide()
                    }
                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //make call to fetch album list from API
        viewModel.fetchAlbumsFromApi()

        //make call to fetch albums from database and populate in recyclerview
        viewModel.fetchAlbumsFromDatabase()

        observeData()
    }
}