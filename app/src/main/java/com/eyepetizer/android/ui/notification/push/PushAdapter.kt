/*
 * Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.eyepetizer.android.ui.notification.push

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.inflate
import com.eyepetizer.android.extension.load
import com.eyepetizer.android.logic.model.PushMessage
import com.eyepetizer.android.util.ActionUrlUtil
import com.eyepetizer.android.util.DateUtil

class PushAdapter(val fragment: PushFragment) : PagingDataAdapter<PushMessage.Message, PushAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = ViewHolder(R.layout.item_notification_push.inflate(parent))
        holder.itemView.setOnClickListener {
            getItem(holder.bindingAdapterPosition)?.let {
                ActionUrlUtil.process(fragment, it.actionUrl, it.title ?: "")
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.run {
            holder.ivAvatar.load(icon ?: "") { error(R.mipmap.ic_launcher) }
            holder.tvTitle.text = title
            holder.tvTime.text = DateUtil.getConvertedDate(date)
            holder.tvContent.text = content
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val tvContent: TextView = view.findViewById(R.id.tvContent)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PushMessage.Message>() {

            override fun areItemsTheSame(oldItem: PushMessage.Message, newItem: PushMessage.Message) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PushMessage.Message, newItem: PushMessage.Message) = oldItem == newItem
        }

    }

}