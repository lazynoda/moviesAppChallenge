package de.mytoysgroup.movies.challenge.presentation.list

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.mytoysgroup.movies.challenge.R
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.load
import kotlinx.android.synthetic.main.cell_list.view.*

typealias OnSelectMovieListener = ((Movie) -> Unit)?
typealias OnChangeWishlistListener = ((Movie) -> Unit)?

class ListAdapter(private val selectMovieListener: OnSelectMovieListener,
                  private val changeWishlistListener: OnChangeWishlistListener) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private var items: MutableList<Movie> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    fun updateItems(newItems: List<Movie>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(parent.inflate(R.layout.cell_list))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(items[position], selectMovieListener, changeWishlistListener)

    override fun getItemId(position: Int) = items[position].id.hashCode().toLong()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie,
                 selectMovieListener: OnSelectMovieListener,
                 changeWishlistListener: OnChangeWishlistListener) = with(itemView) {
            posterImage.load(movie.poster)
            titleLabel.text = movie.title
            with(wishlistButton) {
                setImageResource(if (movie.wishlist) R.drawable.ic_star else R.drawable.ic_star_border)
                setOnClickListener { changeWishlistListener?.invoke(movie) }
            }

            setOnClickListener { selectMovieListener?.invoke(movie) }
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutResId: Int) =
            LayoutInflater.from(context).inflate(layoutResId, this, false)
}