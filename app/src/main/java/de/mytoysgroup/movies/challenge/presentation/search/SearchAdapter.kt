package de.mytoysgroup.movies.challenge.presentation.search

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import de.mytoysgroup.movies.challenge.domain.model.Movie

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    fun addNewItems(items: List<Movie>) {
        val updatedPosition = this.items.size
        this.items.addAll(items)
        notifyItemRangeInserted(updatedPosition, items.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(android.R.layout.simple_list_item_1))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemId(position: Int) = items[position].id.hashCode().toLong()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie) = with(itemView as TextView) {
            text = movie.title
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutResId: Int) =
            LayoutInflater.from(context).inflate(layoutResId, this, false)
}