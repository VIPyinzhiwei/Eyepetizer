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

package com.eyepetizer.android.ui.community.commend

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eyepetizer.android.BuildConfig
import com.eyepetizer.android.Const
import com.eyepetizer.android.R
import com.eyepetizer.android.extension.*
import com.eyepetizer.android.logic.model.CommunityRecommend
import com.eyepetizer.android.ui.common.holder.EmptyViewHolder
import com.eyepetizer.android.ui.home.daily.DailyAdapter
import com.eyepetizer.android.ui.ugcdetail.UgcDetailActivity
import com.eyepetizer.android.util.ActionUrlUtil
import com.eyepetizer.android.util.GlobalUtil
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder
import de.hdodenhof.circleimageview.CircleImageView

class CommendAdapter(val fragment: CommendFragment) : PagingDataAdapter<CommunityRecommend.Item, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item?.type) {
            STR_HORIZONTAL_SCROLLCARD_TYPE -> {
                when (item.data.dataType) {
                    STR_ITEM_COLLECTION_DATA_TYPE -> HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE
                    STR_HORIZONTAL_SCROLLCARD_DATA_TYPE -> HORIZONTAL_SCROLLCARD_TYPE
                    else -> Const.ItemViewType.UNKNOWN
                }
            }
            STR_COMMUNITY_COLUMNS_CARD -> {
                if (item.data.dataType == STR_FOLLOW_CARD_DATA_TYPE) FOLLOW_CARD_TYPE
                else Const.ItemViewType.UNKNOWN
            }
            else -> Const.ItemViewType.UNKNOWN
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE -> {
            HorizontalScrollcardItemCollectionViewHolder(R.layout.item_community_horizontal_scrollcard_item_collection_type.inflate(parent))
        }
        HORIZONTAL_SCROLLCARD_TYPE -> {
            HorizontalScrollcardViewHolder(R.layout.item_community_horizontal_scrollcard_type.inflate(parent))
        }
        FOLLOW_CARD_TYPE -> {
            FollowCardViewHolder(R.layout.item_community_columns_card_follow_card_type.inflate(parent))
        }
        else -> {
            EmptyViewHolder(View(parent.context))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)!!
        when (holder) {
            is HorizontalScrollcardItemCollectionViewHolder -> {
                (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                holder.recyclerView.layoutManager = LinearLayoutManager(fragment.activity).apply { orientation = LinearLayoutManager.HORIZONTAL }
                if (holder.recyclerView.itemDecorationCount == 0) {
                    holder.recyclerView.addItemDecoration(SquareCardOfCommunityContentItemDecoration(fragment))
                }
                holder.recyclerView.adapter = SquareCardOfCommunityContentAdapter(fragment, item.data.itemList)
            }
            is HorizontalScrollcardViewHolder -> {
                (holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                holder.bannerViewPager.run {
                    setCanLoop(false)
                    setRoundCorner(dp2px(4f))
                    setRevealWidth(0, GlobalUtil.getDimension(R.dimen.listSpaceSize))
                    if (item.data.itemList.size == 1) setPageMargin(0) else setPageMargin(dp2px(4f))
                    setIndicatorVisibility(View.GONE)
                    removeDefaultPageTransformer()
                    setAdapter(BannerAdapter())
                    setOnPageClickListener { position ->
                        ActionUrlUtil.process(fragment, item.data.itemList[position].data.actionUrl, item.data.itemList[position].data.title)
                    }
                    create(item.data.itemList)
                }
            }
            is FollowCardViewHolder -> {
                holder.tvChoiceness.gone()
                holder.ivPlay.gone()
                holder.ivLayers.gone()

                if (item.data.content.data.library == DailyAdapter.DAILY_LIBRARY_TYPE) holder.tvChoiceness.visible()

                if ((item.data.header?.iconType ?: "".trim()) == "round") {
                    holder.ivAvatar.invisible()
                    holder.ivRoundAvatar.visible()
                    holder.ivRoundAvatar.load(item.data.content.data.owner.avatar)
                } else {
                    holder.ivRoundAvatar.gone()
                    holder.ivAvatar.visible()
                    holder.ivAvatar.load(item.data.content.data.owner.avatar)
                }

                holder.ivBgPicture.run {
                    val imageHeight = calculateImageHeight(item.data.content.data.width, item.data.content.data.height)
                    layoutParams.width = fragment.maxImageWidth
                    layoutParams.height = imageHeight
                    this.load(item.data.content.data.cover.feed, 4f)
                }
                holder.tvCollectionCount.text = item.data.content.data.consumption.collectionCount.toString()
                val drawable = ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_favorite_border_black_20dp)
                holder.tvCollectionCount.setDrawable(drawable, 17f, 17f, 2)
                holder.tvDescription.text = item.data.content.data.description
                holder.tvNickName.text = item.data.content.data.owner.nickname

                when (item.data.content.type) {
                    STR_VIDEO_TYPE -> {
                        holder.ivPlay.visible()
                        holder.itemView.setOnClickListener {
                            val items = snapshot().filter { it!!.type == STR_COMMUNITY_COLUMNS_CARD && it.data.dataType == STR_FOLLOW_CARD_DATA_TYPE }
                            UgcDetailActivity.start(fragment.activity, items.map { it!! }, item)
                        }
                    }
                    STR_UGC_PICTURE_TYPE -> {
                        if (!item.data.content.data.urls.isNullOrEmpty() && item.data.content.data.urls.size > 1) holder.ivLayers.visible()
                        holder.itemView.setOnClickListener {
                            val items = snapshot().filter { it!!.type == STR_COMMUNITY_COLUMNS_CARD && it.data.dataType == STR_FOLLOW_CARD_DATA_TYPE }
                            UgcDetailActivity.start(fragment.activity, items.map { it!! }, item)
                        }
                    }
                }
            }
            else -> {
                holder.itemView.gone()
                if (BuildConfig.DEBUG) "${TAG}:${Const.Toast.BIND_VIEWHOLDER_TYPE_WARN}\n${holder}".showToast()
            }
        }
    }

    /**
     * 根据屏幕比例计算图片高
     *
     * @param originalWidth   服务器图片原始尺寸：宽
     * @param originalHeight  服务器图片原始尺寸：高
     * @return 根据比例缩放后的图片高
     */
    private fun calculateImageHeight(originalWidth: Int, originalHeight: Int): Int {
        //服务器数据异常处理
        if (originalWidth == 0 || originalHeight == 0) {
            logW(TAG, GlobalUtil.getString(R.string.image_size_error))
            return fragment.maxImageWidth
        }
        return fragment.maxImageWidth * originalHeight / originalWidth
    }

    /**
     * 主题创作广场+话题讨论大厅……
     */
    class HorizontalScrollcardItemCollectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
    }

    class SquareCardOfCommunityContentAdapter(val fragment: CommendFragment, var dataList: List<CommunityRecommend.ItemX>) :
        RecyclerView.Adapter<SquareCardOfCommunityContentAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivBgPicture: ImageView = view.findViewById(R.id.ivBgPicture)
            val tvTitle: TextView = view.findViewById(R.id.tvTitle)
            val tvSubTitle: TextView = view.findViewById(R.id.tvSubTitle)
        }

        override fun getItemCount() = dataList.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareCardOfCommunityContentAdapter.ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_community_horizontal_scroll_card_itemcollection_item_type, parent, false))
        }

        override fun onBindViewHolder(holder: SquareCardOfCommunityContentAdapter.ViewHolder, position: Int) {
            val item = dataList[position]
            holder.ivBgPicture.layoutParams.width = fragment.maxImageWidth
            holder.ivBgPicture.load(item.data.bgPicture)
            holder.tvTitle.text = item.data.title
            holder.tvSubTitle.text = item.data.subTitle
            holder.itemView.setOnClickListener { ActionUrlUtil.process(fragment, item.data.actionUrl, item.data.title) }
        }
    }

    class SquareCardOfCommunityContentItemDecoration(val fragment: CommendFragment) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view) // item position
            val count = parent.adapter?.itemCount?.minus(1) //item count

            when (position) {
                0 -> {
                    /*outRect.left = fragment.bothSideSpace*/
                    outRect.right = fragment.middleSpace
                }
                count -> {
                    outRect.left = fragment.middleSpace
                    /*outRect.right = fragment.bothSideSpace*/
                }
                else -> {
                    outRect.left = fragment.middleSpace
                    outRect.right = fragment.middleSpace
                }
            }
        }
    }

    class HorizontalScrollcardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bannerViewPager = view.findViewById<BannerViewPager<CommunityRecommend.ItemX, BannerAdapter.ViewHolder>>(R.id.bannerViewPager)
    }

    class BannerAdapter : BaseBannerAdapter<CommunityRecommend.ItemX, BannerAdapter.ViewHolder>() {

        class ViewHolder(val view: View) : BaseViewHolder<CommunityRecommend.ItemX>(view) {

            override fun bindData(item: CommunityRecommend.ItemX, position: Int, pageSize: Int) {
                val ivPicture = findView<ImageView>(R.id.ivPicture)
                val tvLabel = findView<TextView>(R.id.tvLabel)
                if (item.data.label?.text.isNullOrEmpty()) tvLabel.invisible() else tvLabel.visible()
                tvLabel.text = item.data.label?.text ?: ""
                ivPicture.load(item.data.image, 4f)
            }
        }

        override fun getLayoutId(viewType: Int): Int {
            return R.layout.item_banner_item_type
        }

        override fun createViewHolder(view: View, viewType: Int): ViewHolder {
            return ViewHolder(view)
        }

        override fun onBind(holder: ViewHolder, data: CommunityRecommend.ItemX, position: Int, pageSize: Int) {
            holder.bindData(data, position, pageSize)
        }
    }

    class FollowCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBgPicture: ImageView = view.findViewById(R.id.ivBgPicture)
        val tvChoiceness: TextView = view.findViewById(R.id.tvChoiceness)
        val ivLayers: ImageView = view.findViewById(R.id.ivLayers)
        val ivPlay: ImageView = view.findViewById(R.id.ivPlay)
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val ivAvatar: ImageView = view.findViewById(R.id.ivAvatar)
        val ivRoundAvatar: CircleImageView = view.findViewById(R.id.ivRoundAvatar)
        val tvNickName: TextView = view.findViewById(R.id.tvNickName)
        val tvCollectionCount: TextView = view.findViewById(R.id.tvCollectionCount)
    }

    /**
     * 社区整个垂直列表的间隙
     */
    class ItemDecoration(val fragment: CommendFragment) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex

            outRect.top = fragment.bothSideSpace

            when (spanIndex) {
                0 -> {
                    outRect.left = fragment.bothSideSpace
                    outRect.right = fragment.middleSpace
                }
                else -> {
                    outRect.left = fragment.middleSpace
                    outRect.right = fragment.bothSideSpace
                }
            }
        }
    }

    companion object {

        const val TAG = "CommendAdapter"

        const val STR_HORIZONTAL_SCROLLCARD_TYPE = "horizontalScrollCard"
        const val STR_COMMUNITY_COLUMNS_CARD = "communityColumnsCard"

        const val STR_HORIZONTAL_SCROLLCARD_DATA_TYPE = "HorizontalScrollCard"
        const val STR_ITEM_COLLECTION_DATA_TYPE = "ItemCollection"
        const val STR_FOLLOW_CARD_DATA_TYPE = "FollowCard"

        const val STR_VIDEO_TYPE = "video"
        const val STR_UGC_PICTURE_TYPE = "ugcPicture"

        const val HORIZONTAL_SCROLLCARD_ITEM_COLLECTION_TYPE = 1   //type:horizontalScrollCard -> dataType:ItemCollection
        const val HORIZONTAL_SCROLLCARD_TYPE = 2                   //type:horizontalScrollCard -> dataType:HorizontalScrollCard
        const val FOLLOW_CARD_TYPE = 3                             //type:communityColumnsCard -> dataType:FollowCard

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommunityRecommend.Item>() {
            override fun areItemsTheSame(oldItem: CommunityRecommend.Item, newItem: CommunityRecommend.Item): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CommunityRecommend.Item, newItem: CommunityRecommend.Item): Boolean {
                return oldItem == newItem
            }
        }

    }
}



