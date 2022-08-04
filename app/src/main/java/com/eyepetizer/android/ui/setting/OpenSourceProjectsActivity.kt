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

package com.eyepetizer.android.ui.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivityOpenSourceProjectsBinding
import com.eyepetizer.android.extension.dp2px
import com.eyepetizer.android.extension.inflate
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.common.ui.WebViewActivity
import com.eyepetizer.android.ui.common.view.SimpleDividerDecoration
import com.eyepetizer.android.util.GlobalUtil

/**
 * 开源项目列表界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/24
 */
class OpenSourceProjectsActivity : BaseActivity() {

    private var _binding: ActivityOpenSourceProjectsBinding? = null

    private val binding: ActivityOpenSourceProjectsBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOpenSourceProjectsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setupViews() {
        super.setupViews()
        binding.titleBar.tvTitle.text = GlobalUtil.getString(R.string.open_source_project_list)
        val layoutManager = LinearLayoutManager(this)
        val adapter = OpenSourceProjectsAdapter(this, getProjectList())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addItemDecoration(SimpleDividerDecoration(this, dp2px(0.5f), ContextCompat.getColor(this, R.color.gray)))
    }

    private fun getProjectList() = ArrayList<OpenSourceProject>().apply {
        add(OpenSourceProject("Retrofit", "https://github.com/square/retrofit"))
        add(OpenSourceProject("Glide", "https://github.com/bumptech/glide"))
        add(OpenSourceProject("OkHttp", "https://github.com/square/okhttp"))
        add(OpenSourceProject("Gson", "https://github.com/google/gson"))
        add(OpenSourceProject("Glide Transformations", "https://github.com/wasabeef/glide-transformations"))
        add(OpenSourceProject("Eventbus", "https://github.com/greenrobot/EventBus"))
        add(OpenSourceProject("Permissionx", "https://github.com/guolindev/PermissionX"))
        add(OpenSourceProject("FlycoTabLayout", "https://github.com/H07000223/FlycoTabLayout"))
        add(OpenSourceProject("SmartRefreshLayout", "https://github.com/scwang90/SmartRefreshLayout"))
        add(OpenSourceProject("BannerViewPager", "https://github.com/zhpanvip/BannerViewPager"))
        add(OpenSourceProject("Immersionbar", "https://github.com/gyf-dev/ImmersionBar"))
        add(OpenSourceProject("PhotoView", "https://github.com/chrisbanes/PhotoView"))
        add(OpenSourceProject("Circleimageview", "https://github.com/hdodenhof/CircleImageView"))
        add(OpenSourceProject("GSYVideoPlayer", "https://github.com/CarGuo/GSYVideoPlayer"))
        add(OpenSourceProject("VasSonic", "https://github.com/Tencent/VasSonic"))
        add(OpenSourceProject("Leakcanary", "https://github.com/square/leakcanary"))
        add(OpenSourceProject("Kotlinx Coroutines", "https://github.com/Kotlin/kotlinx.coroutines"))
    }

    class OpenSourceProjectsAdapter(val activity: Activity, private val projectList: List<OpenSourceProject>) :
        RecyclerView.Adapter<OpenSourceProjectsAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = R.layout.item_open_source_project.inflate(parent)
            val holder = ViewHolder(view)
            holder.itemView.setOnClickListener {
                val position = holder.bindingAdapterPosition
                val project = projectList[position]
                WebViewActivity.start(activity, project.name, project.url, true)
            }
            return holder
        }

        override fun getItemCount(): Int = projectList.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = projectList[position]
            holder.name.text = item.name
            holder.url.text = item.url
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            var name: TextView = view.findViewById(R.id.name)

            var url: TextView = view.findViewById(R.id.url)

        }
    }

    data class OpenSourceProject(val name: String, val url: String)

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, OpenSourceProjectsActivity::class.java)
            context.startActivity(intent)
        }

    }

}