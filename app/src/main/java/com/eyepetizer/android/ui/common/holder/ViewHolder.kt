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

package com.eyepetizer.android.ui.common.holder

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eyepetizer.android.Const.ItemViewType.Companion.AUTO_PLAY_VIDEO_AD
import com.eyepetizer.android.Const.ItemViewType.Companion.BANNER
import com.eyepetizer.android.Const.ItemViewType.Companion.BANNER3
import com.eyepetizer.android.Const.ItemViewType.Companion.COLUMN_CARD_LIST
import com.eyepetizer.android.Const.ItemViewType.Companion.FOLLOW_CARD
import com.eyepetizer.android.Const.ItemViewType.Companion.HORIZONTAL_SCROLL_CARD
import com.eyepetizer.android.Const.ItemViewType.Companion.INFORMATION_CARD
import com.eyepetizer.android.Const.ItemViewType.Companion.SPECIAL_SQUARE_CARD_COLLECTION
import com.eyepetizer.android.Const.ItemViewType.Companion.TAG_BRIEFCARD
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_FOOTER2
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_FOOTER3
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_HEADER4
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_HEADER5
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_HEADER7
import com.eyepetizer.android.Const.ItemViewType.Companion.TEXT_CARD_HEADER8
import com.eyepetizer.android.Const.ItemViewType.Companion.TOPIC_BRIEFCARD
import com.eyepetizer.android.Const.ItemViewType.Companion.UGC_SELECTED_CARD_COLLECTION
import com.eyepetizer.android.Const.ItemViewType.Companion.UNKNOWN
import com.eyepetizer.android.Const.ItemViewType.Companion.VIDEO_SMALL_CARD
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.inflate
import com.eyepetizer.android.logic.model.*
import com.eyepetizer.android.ui.home.discovery.DiscoveryAdapter
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer
import com.zhpan.bannerview.BannerViewPager

/**
 * 项目通用ViewHolder，集中统一管理。
 *
 * @author vipyinzhiwei
 * @since  2020/5/1
 */

/**
 * 未知类型，占位进行容错处理。
 */
class EmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

class TextCardViewHeader4ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle4 = view.findViewById<TextView>(R.id.tvTitle4)
    val ivInto4 = view.findViewById<ImageView>(R.id.ivInto4)
}

class TextCardViewHeader5ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle5 = view.findViewById<TextView>(R.id.tvTitle5)
    val tvFollow = view.findViewById<TextView>(R.id.tvFollow)
    val ivInto5 = view.findViewById<ImageView>(R.id.ivInto5)
}

class TextCardViewHeader7ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle7 = view.findViewById<TextView>(R.id.tvTitle7)
    val tvRightText7 = view.findViewById<TextView>(R.id.tvRightText7)
    val ivInto7 = view.findViewById<ImageView>(R.id.ivInto7)
}

class TextCardViewHeader8ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle8 = view.findViewById<TextView>(R.id.tvTitle8)
    val tvRightText8 = view.findViewById<TextView>(R.id.tvRightText8)
    val ivInto8 = view.findViewById<ImageView>(R.id.ivInto8)
}

class TextCardViewFooter2ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvFooterRightText2 = view.findViewById<TextView>(R.id.tvFooterRightText2)
    val ivTooterInto2 = view.findViewById<ImageView>(R.id.ivTooterInto2)
}

class TextCardViewFooter3ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvRefresh = view.findViewById<TextView>(R.id.tvRefresh)
    val tvFooterRightText3 = view.findViewById<TextView>(R.id.tvFooterRightText3)
    val ivTooterInto3 = view.findViewById<ImageView>(R.id.ivTooterInto3)
}

class HorizontalScrollCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val bannerViewPager: BannerViewPager<Discovery.ItemX, DiscoveryAdapter.HorizontalScrollCardAdapter.ViewHolder> = view.findViewById(R.id.bannerViewPager)
}

class SpecialSquareCardCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivInto = view.findViewById<ImageView>(R.id.ivInto)
}

class ColumnCardListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivInto = view.findViewById<ImageView>(R.id.ivInto)
}

class FollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivVideo = view.findViewById<ImageView>(R.id.ivVideo)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val ivShare = view.findViewById<ImageView>(R.id.ivShare)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivChoiceness = view.findViewById<ImageView>(R.id.ivChoiceness)
}

class Banner3ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
}

class VideoSmallCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvVideoDuration = view.findViewById<TextView>(R.id.tvVideoDuration)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val ivShare = view.findViewById<ImageView>(R.id.ivShare)
}

class TagBriefCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val tvFollow = view.findViewById<TextView>(R.id.tvFollow)
}

class TopicBriefCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
}

class InformationCardFollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivCover = view.findViewById<ImageView>(R.id.ivCover)
    val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
}

class AutoPlayVideoAdViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvLabel = view.findViewById<TextView>(R.id.tvLabel)
    val ivAvatar = view.findViewById<ImageView>(R.id.ivAvatar)
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
    val videoPlayer: GSYVideoPlayer = view.findViewById(R.id.videoPlayer)
}

class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
}

class UgcSelectedCardCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
    val tvRightText = view.findViewById<TextView>(R.id.tvRightText)
    val ivCoverLeft = view.findViewById<ImageView>(R.id.ivCoverLeft)
    val ivLayersLeft = view.findViewById<ImageView>(R.id.ivLayersLeft)
    val ivAvatarLeft = view.findViewById<ImageView>(R.id.ivAvatarLeft)
    val tvNicknameLeft = view.findViewById<TextView>(R.id.tvNicknameLeft)
    val ivCoverRightTop = view.findViewById<ImageView>(R.id.ivCoverRightTop)
    val ivLayersRightTop = view.findViewById<ImageView>(R.id.ivLayersRightTop)
    val ivAvatarRightTop = view.findViewById<ImageView>(R.id.ivAvatarRightTop)
    val tvNicknameRightTop = view.findViewById<TextView>(R.id.tvNicknameRightTop)
    val ivCoverRightBottom = view.findViewById<ImageView>(R.id.ivCoverRightBottom)
    val ivLayersRightBottom = view.findViewById<ImageView>(R.id.ivLayersRightBottom)
    val ivAvatarRightBottom = view.findViewById<ImageView>(R.id.ivAvatarRightBottom)
    val tvNicknameRightBottom = view.findViewById<TextView>(R.id.tvNicknameRightBottom)
}

/**
 * RecyclerView帮助类，获取通用ViewHolder或ItemViewType。
 */
object RecyclerViewHelp {

    fun getViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {

        TEXT_CARD_HEADER4 -> TextCardViewHeader4ViewHolder(R.layout.item_text_card_type_header_four.inflate(parent))

        TEXT_CARD_HEADER5 -> TextCardViewHeader5ViewHolder(R.layout.item_text_card_type_header_five.inflate(parent))

        TEXT_CARD_HEADER7 -> TextCardViewHeader7ViewHolder(R.layout.item_text_card_type_header_seven.inflate(parent))

        TEXT_CARD_HEADER8 -> TextCardViewHeader8ViewHolder(R.layout.item_text_card_type_header_eight.inflate(parent))

        TEXT_CARD_FOOTER2 -> TextCardViewFooter2ViewHolder(R.layout.item_text_card_type_footer_two.inflate(parent))

        TEXT_CARD_FOOTER3 -> TextCardViewFooter3ViewHolder(R.layout.item_text_card_type_footer_three.inflate(parent))

        HORIZONTAL_SCROLL_CARD -> HorizontalScrollCardViewHolder(R.layout.item_horizontal_scroll_card_type.inflate(parent))

        SPECIAL_SQUARE_CARD_COLLECTION -> SpecialSquareCardCollectionViewHolder(R.layout.item_special_square_card_collection_type.inflate(parent))

        COLUMN_CARD_LIST -> ColumnCardListViewHolder(R.layout.item_column_card_list_type.inflate(parent))

        BANNER -> BannerViewHolder(R.layout.item_banner_type.inflate(parent))

        BANNER3 -> Banner3ViewHolder(R.layout.item_banner_three_type.inflate(parent))

        VIDEO_SMALL_CARD -> VideoSmallCardViewHolder(R.layout.item_video_small_card_type.inflate(parent))

        TAG_BRIEFCARD -> TagBriefCardViewHolder(R.layout.item_brief_card_tag_brief_card_type.inflate(parent))

        TOPIC_BRIEFCARD -> TopicBriefCardViewHolder(R.layout.item_brief_card_topic_brief_card_type.inflate(parent))

        FOLLOW_CARD -> FollowCardViewHolder(R.layout.item_follow_card_type.inflate(parent))

        INFORMATION_CARD -> InformationCardFollowCardViewHolder(R.layout.item_information_card_type.inflate(parent))

        UGC_SELECTED_CARD_COLLECTION -> UgcSelectedCardCollectionViewHolder(R.layout.item_ugc_selected_card_collection_type.inflate(parent))

        AUTO_PLAY_VIDEO_AD -> AutoPlayVideoAdViewHolder(R.layout.item_auto_play_video_ad.inflate(parent))

        else -> EmptyViewHolder(View(parent.context))
    }

    fun getItemViewType(type: String, dataType: String) = when (type) {

        "horizontalScrollCard" -> {
            when (dataType) {
                "HorizontalScrollCard" -> HORIZONTAL_SCROLL_CARD
                else -> UNKNOWN
            }
        }
        "specialSquareCardCollection" -> {
            when (dataType) {
                "ItemCollection" -> SPECIAL_SQUARE_CARD_COLLECTION
                else -> UNKNOWN
            }
        }
        "columnCardList" -> {
            when (dataType) {
                "ItemCollection" -> COLUMN_CARD_LIST
                else -> UNKNOWN
            }
        }
        /*"textCard" -> {
            when (item.data.type) {
                "header5" -> TEXT_CARD_HEADER5
                "header7" -> TEXT_CARD_HEADER7
                "header8" -> TEXT_CARD_HEADER8
                "footer2" -> TEXT_CARD_FOOTER2
                "footer3" -> TEXT_CARD_FOOTER3
                else -> UNKNOWN
            }
        }*/
        "banner" -> {
            when (dataType) {
                "Banner" -> BANNER
                else -> UNKNOWN
            }
        }
        "banner3" -> {
            when (dataType) {
                "Banner" -> BANNER3
                else -> UNKNOWN
            }
        }
        "videoSmallCard" -> {
            when (dataType) {
                "VideoBeanForClient" -> VIDEO_SMALL_CARD
                else -> UNKNOWN
            }
        }
        "briefCard" -> {
            when (dataType) {
                "TagBriefCard" -> TAG_BRIEFCARD
                "TopicBriefCard" -> TOPIC_BRIEFCARD
                else -> UNKNOWN
            }
        }
        "followCard" -> {
            when (dataType) {
                "FollowCard" -> FOLLOW_CARD
                else -> UNKNOWN
            }
        }
        "informationCard" -> {
            when (dataType) {
                "InformationCard" -> INFORMATION_CARD
                else -> UNKNOWN
            }
        }
        "ugcSelectedCardCollection" -> {
            when (dataType) {
                "ItemCollection" -> UGC_SELECTED_CARD_COLLECTION
                else -> UNKNOWN
            }
        }
        "autoPlayVideoAd" -> {
            when (dataType) {
                "AutoPlayVideoAdDetail" -> AUTO_PLAY_VIDEO_AD
                else -> UNKNOWN
            }
        }
        else -> UNKNOWN
    }

    private fun getTextCardType(type: String) = when (type) {
        "header4" -> TEXT_CARD_HEADER4
        "header5" -> TEXT_CARD_HEADER5
        "header7" -> TEXT_CARD_HEADER7
        "header8" -> TEXT_CARD_HEADER8
        "footer2" -> TEXT_CARD_FOOTER2
        "footer3" -> TEXT_CARD_FOOTER3
        else -> UNKNOWN
    }

    fun getItemViewType(item: Discovery.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

    fun getItemViewType(item: HomePageRecommend.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

    fun getItemViewType(item: Daily.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

    fun getItemViewType(item: Follow.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

    fun getItemViewType(item: VideoRelated.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

    fun getItemViewType(item: VideoReplies.Item): Int {
        return if (item.type == "textCard") getTextCardType(item.data.type) else getItemViewType(item.type, item.data.dataType)
    }

}