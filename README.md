# Eyepetizer
基于 [Kotlin][1] 语言仿写「[开眼 Eyepetizer][2]」的一个短视频 Android 客户端项目，采用 [Jetpack][3] + [协程][30]实现的 MVVM 架构。

## 简介
这是一个高仿「[开眼 Eyepetizer][2]」的短视频 Android 客户端项目，个人觉得这款 App 的 UI 设计风格很好看，界面简洁清新，通过此项目，进行相关技术的学习与整合。

整个项目没有复杂的封装，同时项目开发规范参考了 [Kotlin 官方文档][4] 与第三方 [AndroidStandardDevelop][5]。个人认为代码还是比较容易阅读理解的，因此也非常适合新手入门 Kotlin 语言，同时更快地掌握 Jetpack 组件的使用。

另外值得一提的是，所有 UI 都是经过标注工具测量后的，无论是字体颜色、大小、间距等几乎都是**像素级**模仿的「开眼 Eyepetizer」Android 客户端 App，对应的 v6.3.1 版本（目前最新版）。

## 屏幕截图
<img src="screenshots/snapshot1.png" width="50%"/><img src="screenshots/snapshot2.png" width="50%"/>
<img src="screenshots/snapshot3.png" width="50%"/><img src="screenshots/snapshot4.png" width="50%"/>
<img src="screenshots/snapshot5.png" width="50%"/><img src="screenshots/snapshot6.png" width="50%"/>
<img src="screenshots/snapshot7.png" width="50%"/><img src="screenshots/snapshot8.png" width="50%"/>
<img src="screenshots/snapshot10.png" width="50%"/><img src="screenshots/snapshot11.png" width="50%"/>
<br></br>
<img src="screenshots/snapshot12.png"/>
<br></br>
<img src="screenshots/snapshot9.png" width="50%"/><img src="screenshots/snapshot13.png" width="50%"/>
<br></br>

图片加载不出来？附上[博客地址][6]
<br></br>

## 下载体验
- 扫描二维码安装：<br></br>
   <a href="https://www.pgyer.com/eyepetizer"><img src="eyepetizer.png"/></a>

- [点击下载 eyepetizer.apk][7]

## 主要功能
- 观看优质高清短视频与评论。
- 浏览社区图文与视频创作。
- 查看每日新鲜资讯与热搜关键词。
- 分享精彩短视频与新鲜资讯等。

## 使用工具
- [Vector Asset Studio][8] 图标制作
- [iconfont][9] 图标/设计
- [Postman][10] API 调试工具
- [Charles][11] API 数据抓包
- [PxCook][12] 标注工具
- [开发助手][13] 反编译应用、提取应用 Apk 等

## 关于我
- RealName : 殷志威
- NickName : vipyinzhiwei
- Email : vipyinzhiwei@gmail.com
- Blog : <https://www.vipyinzhiwei.com>

## 鼓励
通过这个项目希望能够帮助大家更好地学习 Jetpack 与 MVVM 架构。如果你喜欢 Eyepetizer 的设计，感觉本项目的源代码对你的学习有所帮助，可以点右上角 **"Star"** 支持一下，谢谢！^_^

## 致谢
- [Retrofit][14] 网络请求框架封装
- [Glide][15] 图片加载
- [OkHttp][16] 网络请求
- [Gson][17] Gson 解析
- [Glide Transformations][18] 图像转换
- [Eventbus][19] 事件总线
- [Permissionx][20] 动态请求权限封装
- [FlycoTabLayout][21] TabLayout封装
- [SmartRefreshLayout][22] 下拉刷新框架
- [BannerViewPager][23] Banner轮播图
- [Immersionbar][24] 状态栏管理
- [PhotoView][25] 支持手势缩放图片
- [Circleimageview][26] 圆形图像
- [GSYVideoPlayer][27] 视频播放器
- [VasSonic][28] 提升H5首屏加载速度
- [Leakcanary][29] 内存泄漏检测
- [Kotlinx Coroutines][30] 简化代码管理后台线程与回调


## License

**所有数据来源于开眼，仅供学习和交流使用，严禁用于任何商业用途，原作公司拥有所有权利。**

```
Copyright (c) 2020. vipyinzhiwei <vipyinzhiwei@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

[1]:https://kotlinlang.org
[2]:https://www.kaiyanapp.com
[3]:https://developer.android.com/jetpack
[4]:https://www.kotlincn.net/docs/reference/coding-conventions.html
[5]:https://github.com/Blankj/AndroidStandardDevelop
[6]:https://www.vipyinzhiwei.com/2020/06/19/pager05/#more
[7]:https://github.com/VIPyinzhiwei/Eyepetizer/raw/master/eyepetizer.apk
[8]:https://developer.android.com/studio/write/vector-asset-studio?hl=zh-cn
[9]:https://www.iconfont.cn
[10]:https://www.postman.com
[11]:https://www.charlesproxy.com
[12]:https://www.fancynode.com.cn/pxcook
[13]:https://github.com/Trinea/android-open-project/issues/314
[14]:https://github.com/square/retrofit
[15]:https://github.com/bumptech/glide
[16]:https://github.com/square/okhttp
[17]:https://github.com/google/gson
[18]:https://github.com/wasabeef/glide-transformations
[19]:https://github.com/greenrobot/EventBus
[20]:https://github.com/guolindev/PermissionX
[21]:https://github.com/H07000223/FlycoTabLayout
[22]:https://github.com/scwang90/SmartRefreshLayout
[23]:https://github.com/zhpanvip/BannerViewPager
[24]:https://github.com/gyf-dev/ImmersionBar
[25]:https://github.com/chrisbanes/PhotoView
[26]:https://github.com/hdodenhof/CircleImageView
[27]:https://github.com/CarGuo/GSYVideoPlayer
[28]:https://github.com/Tencent/VasSonic
[29]:https://github.com/square/leakcanary
[30]:https://github.com/Kotlin/kotlinx.coroutines


