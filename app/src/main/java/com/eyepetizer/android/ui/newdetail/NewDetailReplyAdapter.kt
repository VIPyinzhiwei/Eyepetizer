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

package com.eyepetizer.android.ui.newdetail

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.eyepetizer.android.BuildConfig
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.*
import com.eyepetizer.android.logic.model.VideoReplies
import com.eyepetizer.android.ui.common.holder.RecyclerViewHelp
import com.eyepetizer.android.ui.common.holder.TextCardViewHeader4ViewHolder
import com.eyepetizer.android.util.DateUtil
import com.eyepetizer.android.util.GlobalUtil

/**
 * 视频详情-评论列表绑定的适配器
 *
 * @author vipyinzhiwei
 * @since  2020/5/14
 */
class NewDetailReplyAdapter(val activity: NewDetailActivity, val dataList: List<VideoReplies.Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int) = when {
        dataList[position].type == "reply" && dataList[position].data.dataType == "ReplyBeanForClient" -> REPLY_BEAN_FOR_CLIENT_TYPE
        else -> RecyclerViewHelp.getItemViewType(dataList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        REPLY_BEAN_FOR_CLIENT_TYPE -> ReplyViewHolder(R.layout.item_new_detail_reply_type.inflate(parent))
        else -> RecyclerViewHelp.getViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataList[position]
        when (holder) {
            is TextCardViewHeader4ViewHolder -> {
                holder.tvTitle4.text = item.data.text
                if (item.data.actionUrl != null && item.data.actionUrl.startsWith(Const.ActionUrl.REPLIES_HOT)) {
                    //热门评论
                    holder.ivInto4.visible()
                    holder.tvTitle4.layoutParams = (holder.tvTitle4.layoutParams as LinearLayout.LayoutParams).apply { setMargins(0, dp2px(30f), 0, dp2px(6f)) }
                    setOnClickListener(holder.tvTitle4, holder.ivInto4) { R.string.currently_not_supported.showToast() }
                } else {
                    //相关推荐、最新评论
                    holder.tvTitle4.layoutParams = (holder.tvTitle4.layoutParams as LinearLayout.LayoutParams).apply { setMargins(0, dp2px(24f), 0, dp2px(6f)) }
                    holder.ivInto4.gone()
                }
            }
            is ReplyViewHolder -> {
                holder.groupReply.gone()
                holder.ivAvatar.load(item.data.user?.avatar ?: "")
                holder.tvNickName.text = item.data.user?.nickname
                holder.tvLikeCount.text = if (item.data.likeCount == 0) "" else item.data.likeCount.toString()
                holder.tvMessage.text = item.data.message
                holder.tvTime.text = getTimeReply(item.data.createTime)
                holder.ivLike.setOnClickListener { R.string.currently_not_supported.showToast() }

                item.data.parentReply?.run {
                    holder.groupReply.visible()
                    holder.tvReplyUser.text = String.format(GlobalUtil.getString(R.string.reply_target), user?.nickname)
                    holder.ivReplyAvatar.load(user?.avatar ?: "")
                    holder.tvReplyNickName.text = user?.nickname
                    holder.tvReplyMessage.text = message
                    holder.tvShowConversation.setOnClickListener { R.string.currently_not_supported.showToast() }
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    private fun getTimeReply(dateMillis: Long): String {
        val currentMillis = System.currentTimeMillis()
        val timePast = currentMillis - dateMillis
        if (timePast > -DateUtil.MINUTE) {
            when {
                timePast < DateUtil.DAY -> {
                    return DateUtil.getDate(dateMillis, "HH:mm")
                }
                else -> {
                    return DateUtil.getDate(dateMillis, "yyyy/MM/dd")
                }
            }
        } else {
            return DateUtil.getDate(dateMillis, "yyyy/MM/dd HH:mm")
        }
    }

    class ReplyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
        val tvNickName = view.findViewById<TextView>(R.id.tvNickName)
        val ivLike = view.findViewById<ImageView>(R.id.ivLike)
        val tvLikeCount = view.findViewById<TextView>(R.id.tvLikeCount)
        val tvMessage = view.findViewById<TextView>(R.id.tvMessage)
        val tvTime = view.findViewById<TextView>(R.id.tvTime)

        val groupReply = view.findViewById<Group>(R.id.groupReply)
        val tvReplyUser = view.findViewById<TextView>(R.id.tvReplyUser)
        val ivReplyAvatar = view.findViewById<ImageView>(R.id.ivReplyAvatar)
        val tvReplyNickName = view.findViewById<TextView>(R.id.tvReplyNickName)
        val tvReplyMessage = view.findViewById<TextView>(R.id.tvReplyMessage)
        val tvShowConversation = view.findViewById<TextView>(R.id.tvShowConversation)

    }

    companion object {
        const val TAG = "NewDetailReplyAdapter"
        const val REPLY_BEAN_FOR_CLIENT_TYPE = Const.ItemViewType.MAX
    }
}