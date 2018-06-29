package com.sugaryple.pobcast.ui.result

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sugaryple.pobcast.R
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import com.sugaryple.pobcast.BR
import com.sugaryple.pobcast.data.vo.Podcast


class ResultAdapter(
        private val viewModel: ResultViewModel
) : PagedListAdapter<Podcast, ResultAdapter.ViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        val listener = object: PodcastItemClickListener {
            override fun onClick(podcast: Podcast) {
                viewModel.goWebsite(podcast)
            }
        }
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(getItemViewType(position) == R.layout.podcast_item) {
            holder.bind(getItem(position - getHeaderCount()))
        }
    }

    private fun getHeaderCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < getHeaderCount() -> R.layout.podcast_header_item
            else -> R.layout.podcast_item
        }
    }

    class ViewHolder(
            private val binding : ViewDataBinding,
            private val listener: PodcastItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Any?) {
            if(data is Podcast) {
                binding.setVariable(BR.data, data)
                binding.setVariable(BR.listener, listener)
                binding.executePendingBindings()
            }
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Podcast>() {
            override fun areContentsTheSame(oldItem: Podcast, newItem: Podcast): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Podcast, newItem: Podcast): Boolean =
                    oldItem.id == newItem.id
        }
    }
}