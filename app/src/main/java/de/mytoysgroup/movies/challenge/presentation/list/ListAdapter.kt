package de.mytoysgroup.movies.challenge.presentation.list

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.load
import kotlinx.android.synthetic.main.cell_search.view.*

typealias OnSelectMovieListener = ((Movie) -> Unit)?

class SearchAdapter(private val listener: OnSelectMovieListener) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

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
            ViewHolder(parent.inflate(R.layout.cell_search))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], listener)

    override fun getItemId(position: Int) = items[position].id.hashCode().toLong()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie, listener: OnSelectMovieListener) = with(itemView) {
            posterImage.load(movie.poster)
            titleLabel.text = movie.title

            setOnClickListener { listener?.invoke(movie) }
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutResId: Int) =
            LayoutInflater.from(context).inflate(layoutResId, this, false)
}