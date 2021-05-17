package com.eniola.albumlistapp.ui.album

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eniola.albumlistapp.R
import com.eniola.albumlistapp.repository.AlbumData
import kotlinx.android.synthetic.main.item_albums_list.view.*

class AlbumListAdapter
    : RecyclerView.Adapter<AlbumListAdapter.TransactionViewHolder>() {

    private var albumList = mutableListOf<AlbumData>()

    fun setListItem(albumData: List<AlbumData>){
        albumList.clear()
        albumList.addAll(albumData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_albums_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = albumList[position]
        holder.albumTitle.text = item.title.toUpperCase()
    }

    class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val albumTitle: TextView = view.album_title
    }
}