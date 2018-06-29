package com.sugaryple.pobcast.ui.home

import android.arch.paging.PagedListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sugaryple.pobcast.R
import com.sugaryple.pobcast.data.vo.Channel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import com.sugaryple.pobcast.BR


class HomeAdapter(
        private val viewModel: HomeViewModel
) : PagedListAdapter<Channel, HomeAdapter.ViewHolder>(POST_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        val listener = object: HomeItemClickListener {
            override fun onClick(channel: Channel) {
                viewModel.goWebsite(channel)
            }
        }
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(getItemViewType(position) == R.layout.channel_item) {
            holder.bind(getItem(position - getHeaderCount()))
        }
    }

    private fun getHeaderCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position < getHeaderCount() -> R.layout.channel_header_item
            else -> R.layout.channel_item
        }
    }

    class ViewHolder(
            private val binding : ViewDataBinding,
            private val listener: HomeItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Any?) {
            if(data is Channel) {
                binding.setVariable(BR.data, data)
                binding.setVariable(BR.listener, listener)
                binding.executePendingBindings()
            }
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Channel>() {
            override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean =
                    oldItem.itunesId == newItem.itunesId
        }
    }
}