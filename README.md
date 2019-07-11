# News_1
## 新闻头条（Android）
### Commits-1:Initial Commit<br>
内容：初始化<br>
### Commits-2:Create Peoject<br>
内容：创建项目<br>
### Commits-3:Advertisement Finish(广告页面初步完成)<br>
内容：初步完成广告页面,完成广告图片点击事件<br>
图一：广告页面截图<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/ad.png)<br>
图二：广告页面业务流程图<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/advertisement.png)<br>
图三：广告页面中图片显示流程图<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/image_show.png)<br>
### Commits-4:Circular Finish(圆形控件完成)<br>
内容：在MainActivity页面完成圆形自定义控件,使其①功能完整（倒计时）②能够交互③有一定自由度（刷新控件）<br>
图四：圆型控件截图<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/circular.png)<br>
### Commits-5:AD Finish(广告页面完成点击事件)<br>
内容：圆形控件与广告页面合并,并添加控件的点击事件,处理了Handler与Activity交互产生的内存泄漏问题,具体方法为:①使用静态内部类切断访问activity②使用弱引用持有对象<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/ad_finish.png)<br>
### Commits-6:Immersion Finish(引入沉浸式状态栏)<br>
内容：引入沉浸式状态栏:①View.SYSTEM_UI_FLAG_FULLSCREEN(状态栏隐藏)②View.SYSTEM_UI_FLAG_HIDE_NAVIGATION(隐藏导航栏)③View.SYSTEM_UI_FLAG_IMMERSIVE(沉浸模式,隐藏状态栏和导航栏,并且在第一次会弹泡提醒,并且在状态栏区域滑动可以呼出状态栏;要想使它生效,需要和View.SYSTEM_UI_FLAG_FULLSCREEN,View.SYSTEM_UI_FLAG_HIDE_NAVIGATION其中的一个或两个同时设置;)<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/immersion.png)<br>
### Commits-7:(Tab Finish)<br>
内容：完成tab标签UI样式,实现文字和图片的selector,主要操作包括:①找到FragmentTabHost②绑定Fragment容器③生成不同的标签<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/tab.bmp)<br>
### Commits-8:(HomePageUI Finish)<br>
内容:编写首页UI布局,,通过ViewPager与SmartTabLayout绑定的方式,在首页顶部引入滑动栏<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/hot_news.png)<br>
