package app.netlify.dev4rju9.spotifyclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.netlify.dev4rju9.spotifyclone.data.entities.Song
import app.netlify.dev4rju9.spotifyclone.databinding.ListItemBinding
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    class SongViewHolder (val view: ListItemBinding) : RecyclerView.ViewHolder(view.root)

    private val differCallback = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.mediaId == newItem.mediaId
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var songs: List<Song>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs.get(position)
        holder.view.apply {
            tvPrimary.text = song.title
            tvSecondary.text = song.subtitle
            glide.load(song.image).into(ivItemImage)
            root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

    private var onItemClickListener : ((Song) -> Unit)? = null

    fun setOnItemClickListener (listener: (Song) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = songs.size
}