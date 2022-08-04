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

package com.eyepetizer.android.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.eyepetizer.android.R
import com.eyepetizer.android.databinding.ActivityMainBinding
import com.eyepetizer.android.event.MessageEvent
import com.eyepetizer.android.event.RefreshEvent
import com.eyepetizer.android.event.SwitchPagesEvent
import com.eyepetizer.android.extension.setOnClickListener
import com.eyepetizer.android.extension.showToast
import com.eyepetizer.android.ui.common.ui.BaseActivity
import com.eyepetizer.android.ui.community.CommunityFragment
import com.eyepetizer.android.ui.community.commend.CommendFragment
import com.eyepetizer.android.ui.home.HomePageFragment
import com.eyepetizer.android.ui.login.LoginActivity
import com.eyepetizer.android.ui.mine.MineFragment
import com.eyepetizer.android.ui.notification.NotificationFragment
import com.eyepetizer.android.util.DialogAppraiseTipsWorker
import com.eyepetizer.android.util.GlobalUtil
import org.greenrobot.eventbus.EventBus

/**
 * Eyepetizer的主界面。
 *
 * @author vipyinzhiwei
 * @since  2020/5/29
 */
class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null

    private val binding: ActivityMainBinding
        get() = _binding!!

    private var backPressTime = 0L

    private var homePageFragment: HomePageFragment? = null

    private var communityFragment: CommunityFragment? = null

    private var notificationFragment: NotificationFragment? = null

    private var mineFragment: MineFragment? = null

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun setupViews() {
        observe()
        setOnClickListener(
            binding.navigationBar.btnHomePage, binding.navigationBar.btnCommunity, binding.navigationBar.btnNotification, binding.navigationBar.ivRelease,
            binding.navigationBar.btnMine
        ) {
            when (this) {
                binding.navigationBar.btnHomePage -> {
                    notificationUiRefresh(0)
                    setTabSelection(0)
                }
                binding.navigationBar.btnCommunity -> {
                    notificationUiRefresh(1)
                    setTabSelection(1)
                }
                binding.navigationBar.btnNotification -> {
                    notificationUiRefresh(2)
                    setTabSelection(2)
                }
                binding.navigationBar.ivRelease -> {
                    LoginActivity.start(this@MainActivity)
                }
                binding.navigationBar.btnMine -> {
                    notificationUiRefresh(3)
                    setTabSelection(3)
                }
            }
        }
        setTabSelection(0)
    }

    override fun onMessageEvent(messageEvent: MessageEvent) {
        super.onMessageEvent(messageEvent)
        when {
            messageEvent is SwitchPagesEvent && CommendFragment::class.java == messageEvent.activityClass -> {
                binding.navigationBar.btnCommunity.performClick()
            }
            else -> {
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            processBackPressed()
        }
    }

    private fun processBackPressed() {
        val now = System.currentTimeMillis()
        if (now - backPressTime > 2000) {
            String.format(GlobalUtil.getString(R.string.press_again_to_exit), GlobalUtil.appName).showToast()
            backPressTime = now
        } else {
            super.onBackPressed()
        }
    }

    private fun setTabSelection(index: Int) {
        clearAllSelected()
        fragmentManager.beginTransaction().apply {
            hideFragments(this)
            when (index) {
                0 -> {
                    binding.navigationBar.ivHomePage.isSelected = true
                    binding.navigationBar.tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
                1 -> {
                    binding.navigationBar.ivCommunity.isSelected = true
                    binding.navigationBar.tvCommunity.isSelected = true
                    if (communityFragment == null) {
                        communityFragment = CommunityFragment.newInstance()
                        add(R.id.homeActivityFragContainer, communityFragment!!)
                    } else {
                        show(communityFragment!!)
                    }
                }
                2 -> {
                    binding.navigationBar.ivNotification.isSelected = true
                    binding.navigationBar.tvNotification.isSelected = true
                    if (notificationFragment == null) {
                        notificationFragment = NotificationFragment.newInstance()
                        add(R.id.homeActivityFragContainer, notificationFragment!!)
                    } else {
                        show(notificationFragment!!)
                    }
                }
                3 -> {
                    binding.navigationBar.ivMine.isSelected = true
                    binding.navigationBar.tvMine.isSelected = true
                    if (mineFragment == null) {
                        mineFragment = MineFragment.newInstance()
                        add(R.id.homeActivityFragContainer, mineFragment!!)
                    } else {
                        show(mineFragment!!)
                    }
                }
                else -> {
                    binding.navigationBar.ivHomePage.isSelected = true
                    binding.navigationBar.tvHomePage.isSelected = true
                    if (homePageFragment == null) {
                        homePageFragment = HomePageFragment.newInstance()
                        add(R.id.homeActivityFragContainer, homePageFragment!!)
                    } else {
                        show(homePageFragment!!)
                    }
                }
            }
        }.commitAllowingStateLoss()
    }

    private fun clearAllSelected() {
        binding.navigationBar.ivHomePage.isSelected = false
        binding.navigationBar.tvHomePage.isSelected = false
        binding.navigationBar.ivCommunity.isSelected = false
        binding.navigationBar.tvCommunity.isSelected = false
        binding.navigationBar.ivNotification.isSelected = false
        binding.navigationBar.tvNotification.isSelected = false
        binding.navigationBar.ivMine.isSelected = false
        binding.navigationBar.tvMine.isSelected = false
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        transaction.run {
            if (homePageFragment != null) hide(homePageFragment!!)
            if (communityFragment != null) hide(communityFragment!!)
            if (notificationFragment != null) hide(notificationFragment!!)
            if (mineFragment != null) hide(mineFragment!!)
        }
    }

    private fun notificationUiRefresh(selectionIndex: Int) {
        when (selectionIndex) {
            0 -> {
                if (binding.navigationBar.ivHomePage.isSelected) EventBus.getDefault().post(RefreshEvent(HomePageFragment::class.java))
            }
            1 -> {
                if (binding.navigationBar.ivCommunity.isSelected) EventBus.getDefault().post(RefreshEvent(CommunityFragment::class.java))
            }
            2 -> {
                if (binding.navigationBar.ivNotification.isSelected) EventBus.getDefault().post(RefreshEvent(NotificationFragment::class.java))
            }
            3 -> {
                if (binding.navigationBar.ivMine.isSelected) EventBus.getDefault().post(RefreshEvent(MineFragment::class.java))
            }
        }
    }

    private fun observe() {
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(DialogAppraiseTipsWorker.showDialogWorkRequest.id).observe(this, { workInfo ->
            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                WorkManager.getInstance(this).cancelAllWork()
            } else if (workInfo.state == WorkInfo.State.RUNNING) {
                if (isActive) {
                    DialogAppraiseTipsWorker.showDialog(this)
                    WorkManager.getInstance(this).cancelAllWork()
                }
            }
        })
    }

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}