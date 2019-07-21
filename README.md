# News_1
## 新闻头条（Android）<br>
内容详解：本款App主要分为X个模块，分别为：<br>
### 1、广告页面<br>
包含Commits-1→Commits-6，详细步骤如下：<br>
（1）创建一个工程，创建MainActivity并创建其对应的布局文件activity_main.xml，在splash.activity包下创建SplashActivity，并创建其对应的布局文件activity_splash.xml，在styles.xml文件中取消顶部的ActionBar（修改默认样式，将默认样式的父样式改为NoActionBar），在SplashActivity内设置全屏显示<br>
（2）明确广告页面需要实现的功能：①通过OkHttp的异步方式来请求过场动画的广告数据②为了节省用户流量，我们下载广告图片到本地③实现点击广告跳转至指定的H5页面④自定义倒计时控件<br>
（3）为应用增加网络权限，使用开源的OkHttp控件（在build.gradle中引入依赖→compile 'com.squareup.okhttp3:okhttp:3.4.1'），并在SplashActivity中编写一个异步请求方法：httpRequest()<br>
（4）获取到请求数据后，我们使用GSON(在build.gradle中引入依赖→compile 'com.google.code.gson:gson:2.7')对其进行解析，在Splash.bean包下编写Action、Ads、AdsDetail三个文件并进行对对象进行序列化操作（实现Serializable接口，将对象转化为数据流），编写json解析工具类（在util包下新建JsonUtil工具类来解析json，使用泛型来提供通用性）<br>
（5）在util包下新建常量类Constant，存储广告页面的url与缓存文件名，解析成功数据，由于Service不能处理耗时操作，我们使用IntentService（在service包下新建DownloadImageService文件）开始下载图片，为下一次的使用做准备<br>
（6）在下载的时候需要根据url判断图片是否下载，防止图片二次下载，浪费流量，在showImage()方法里对图片进行处理，我们使用一个MD5工具类（在util包下新建Md5Helper,使用MD5算法对传入的key进行加密并返回），将每一张图片的地址转化为名称<br>
（7）在下载图片的过程中（在DownloadImageService的onHandleIntent（）方法中），首先我们将一获取到的url生成一个bitmap，再编写saveToSD方法将它缓存到sd卡上（缓存目录采用“.xxx”是为了隐藏文件夹，防止一般用户误删除），为应用添加读写权限<br>
（8）在Util包下编写ImageUtil工具类文件，先判断图片是否存在再判断文件内容是否完整，如果存在则不下载<br>
（9）能够下载图片后，编写处理显示图片的逻辑，首先在util包下编写SharePrenceUtil工具类，为了考虑效率，不需要每次去调用接口，我们将接口返回的数据进行缓存，采用SharedPreference进行缓存<br>
（10）在SplashActivity中添加getAds()方法，修改请求http的逻辑，判断是否需要http请求：①如果没有缓存，请求http②如果超时了，我们也请求http，缓存中的超时时间是小时，转化成毫秒需要*60*60*1000，在计算时间时，防止溢出<br>
（11）完善广告页面的布局（activity_splash.xml），显示界面，并在SPlashActivity中编写showImage()方法，编写显示图片的逻辑：我们请求网络成功后，就会缓存接口的数据，我们需要显示的图片首先要将接口的信息读出，接着根据接口信息转化成Md5的文件名称，再根据文件名称加载图片，为了实现每次打开应用后显示图片不一样，我们实现轮播图效果，在显示图片前先去获取上次显示的图片的角标，默认是第一张，当显示成功后，我们将角标指向下一张图片，并缓存，但是这种实现容易越界，我们采用取余的方式处理越界<br>
（12）处理完越界后，已经实现了显示图片的功能，接着为图片增加点击事件（每一个广告页面点击后会跳转到下一个activity(h5页面)），为了避免跳转到系统的浏览器，我们重写的跳转的方法，在splash.activity包下新建一个WebViewActivity并创建其对应的布局文件activity_webview.xml，设置全屏显示并处理url重定向问题（使其不要抛到系统浏览器），并且在WebViewActivity中添加onBackPressed()方法，使webview能够回退到上一个页面<br>
（13）开始编写圆形控件，在splash包下新建TimeView类，初始化控件的画笔，计算内圈的宽度和控件的宽度，设置控件的大小，画出内圈，居中画出文字，我们在新建attrs.xml，在xml中使用自定义属性，在控件内部获取到自定义属性，编写onTouchEvent方法为控件增加点击反馈，编写setProgress（）方法为外部提供一个刷新ui的方法<br>
（14）增加“跳转按钮”的点击事件，在splash包下新建OnTimeClickListener接口（将点击事件抽取成接口），在TimeView类中编写setListener()方法，在SplashActivity的onCreate()方法中为TimeView添加点击事件，并添加gotoMain()，使其跳转至主界面<br>
（15）处理内存泄漏问题，由于Handler持有对Activity的引用，Activity即使销毁了，由于与Handler的引用关系，使其无法被回收，为了处理这个问题：①在SplalshActivity类下新建静态类MyHandler使其继承自Handler,使用静态内部类的目的是切断访问activity②使用弱引用持有对象，在使用之前先取出对象的弱引用，由于JVM无法保证弱引用的存活，我们对对象进行判断，如果对象被回收，则不做其他处理，否则，就正常处理自定义控件的任务<br>
（16）在SplashActivity类的onCreate()方法中引入沉浸式状态栏:①View.SYSTEM_UI_FLAG_FULLSCREEN(状态栏隐藏)②View.SYSTEM_UI_FLAG_HIDE_NAVIGATION(隐藏导航栏)③View.SYSTEM_UI_FLAG_IMMERSIVE(沉浸模式,隐藏状态栏和导航栏,并且在第一次会弹泡提醒,并且在状态栏区域滑动可以呼出状态栏;要想使它生效,需要和View.SYSTEM_UI_FLAG_FULLSCREEN,View.SYSTEM_UI_FLAG_HIDE_NAVIGATION其中的一个或两个同时设置<br>
### 2、主页的底部导航栏页面（新闻、阅读、视频、话题、我）<br>
包含Commits-7，详细步骤如下：<br>
（1）开发完广告页面后，我们来开发首页，首页分为三个部分：1、底部的导航栏；2、导航栏上部的可滑动区域；3、最顶部栏目；我们来实现主页底部的导航栏，我们使用原生的FragmentTabHost控件来实现，首先在activity_main.xml里使用FragmentTabHost控件，在其中添加TabWidget（表示切换栏，其id必须为Android的id）和FrameLayout（表示放置内容的容器，其id必须也为android固定的id）<br>
（2）在MainActivity中使用FragmentTabHost，主要有以下三个步骤：1、找到FragmentTabHost；2、绑定Fragment容器；3、生成不同的标签，Tag相当于标签的id；在使用的同时，我们在news包下新建fragment包，并创建六个Fragment：1、NewsFragment（主页导航页面——新闻）2、ReadingFragment（主页导航页面——阅读）3、VideoFragment（主页导航页面——视频）4、TopicFragment（主页导航页面——话题）5、MineFragment（主页导航页面——我）6、EmptyFragment（空碎片，暂时替代其他未创建的Fragment）<br>
（3）创建fragment_new.xml（导航页面——新闻，界面布局）与fragment_empth.xml（空布局，暂时替代其他未创建的布局），在MainActivity中将碎片与布局文件绑定<br>
（4）接下来我们将绑定显示的容器更改为我们自定义的顶部容器，修改activity_main.xml，接下来我们实现自定义标签，新建item_title.xml，实现主页底部导航栏的自定义标签（图像在上，文字在下），但是文字和图案均不能变色，需要自定义颜色的selector<br>
（5）在drawable文件夹下新建五个图标自定义颜色的selector（注意，默认的selector必须放在最下面），实现点击前后的图标状态，分别为：news_selector.xml、reading_selector.xml、video_selector.xml、topic_selector.xml、mine_selector.xml，此外，我们将主页底部标题栏的文字输入到String.xml中，写成配置文件，方便修改<br>
（6）在MainActivity中获取tab的标题和背景图，并通过getEveryView()方法将layout布局转化成一个View，为每个标签设置好标题和图片，接着我们使用setIndicator()方法，将我们自定义View设置给每一个TabSpec<br>
（7）至此，图片点击会变色，而文字颜色不能变化，需要自定义文字颜色的selector，我们在res目录下新建color文件夹（用于文字selector），新建tab_title_selector，用于主页底部导航栏文字的selector<br>
### 3、主页新闻界面（热点、头条、娱乐、体育、本地、网易号、财经、科技、汽车）<br>
包含Commits-8→Commits-9，详细步骤如下：<br>
（1）由于FragmentTabHost里有5个Fragment，我们先完善第一个Fragment，由于新闻界面标签栏以下的内容能够滑动切换，我们选择了ViewPager作为容器，通过ViewPager，Fragment又可以再装一系列的Fragment，编写fragment_new.xml布局文件<br>
（2）为了实现顶部可滑动的标题栏，我们引入第三方控件SmartTabLayout，在build.gradle中添加依赖（compile 'com.ogaclejapan.smarttablayout:library:1.6.1@aar'和compile 'com.ogaclejapan.smarttablayout:utils-v4:1.6.1@aar'），引入成功后，我们开始使用SmartTabLayout<br>
（3）新建一个include_tab.xml布局文件，包括自定义命名空间、引入每一个标题的布局（include_title_tab.xml）、标题的id以及标题的下划线颜色宽度等，此外再新建一个include_title_tab.xml布局文件，作为每一个标题的布局，然后在res目录下的color文件夹下新建tab_color.xml布局文件，为标题增加颜色selector<br>
（4）修改NewsFragment，绑定布局文件，将SmartTabLayout加入到布局中（SmartTabLayout不需要任何数据，只需要绑定ViewPager即可），通过SmartTabLayout的setViewPager()方法自动绑定数据，但需要向其传入ViewPager对象，因此我们在news.adapter包下新建一个NewAdapter类，使其继承自FragmentStatePagerAdapter类（FragmentStatePagerAdapter不会把所有的fragment都加载到内存中，只把需要显示的加载，不需要的移除，需要重新显示的重新加载），通过ViewPager的setAdapter()方法给ViewPager添加NewAdapter，由于在NewAdapter中我们既要传回去Fragment也要传回其对应的title，单一的Fragment无法完成该操作，因此我们在news.bean包下新建FragmentInfo类，其中包括Fragment及其对应的title<br>
（5）我们将新闻页面顶部标题栏的文字写入到String.xml中，写成配置文件，方便修改，并减少硬编码，在news.news_inner包（用于存放新闻页面的Fragment）中创建HotFragment文件（用于表示热点新闻的Fragment），在layout文件夹下添加fragment_hot.xml布局文件，并在HotFragment文件中将fragment_hot.xml布局文件转化为view，修改NewsFragment文件，获取标题字符串数组，设置if-else，先生成一个页面,其余的置为空<br>
（6）之后的操作中会遇到重复的网络请求代码，由于在SplashActivity的httpRequest()方法中我门编写过Http请求，因此我们抽取Http请求，使其在整个应用中只创建一次，使用单例设计模式，在util包下新建HttpUtil类和HttpRespon类（回调类——监控一个对象的事件变化），将其封装成一个工具类<br>
### 4、新闻界面——热点<br>
包含Commits-10→Commits-12，详细步骤如下：<br>
（1）在news.bean包下新建Hot（热点新闻）、HotDetail（热点新闻详细数据）以及Banner（轮播图）三个文件夹，用于解析热点新闻数据，修改HotFragment，用于请求热点新闻的数据，修改util包下的Constant文件，向其中添加热门页面的url<br>
（2）由于我们要通过ListView来显示新闻，需要借助适配器（adapter）将数据传递给ListView，因此在news.adapter包下新建HotAdapter（热点新闻的适配器），这个适配器继承自BaseAdapter，并将泛型类型指定为HotDetail类，在layout文件夹下新建item_hot.xml布局文件，作为ListView的布局，为了统一管理显示的字体大小及颜色，我们修改styles.xml文件并将其引入item_hot.xml布局文件中<br>
（3）修改HotAdapter文件，通过ViewHolder来提升ListView的效率，采用弱引用，处理了Handler与Activity交互产生的内存泄漏问题，由于OkHttp异步请求是在子线程中进行，所以我们不能在onActivityCreated()方法的回调中更新UI，必须搭配Handler，编写完HotAdapter文件，就可以基本显示新闻文本数据<br>
（4）为了显示图片，我们引入UniversalImageLoader来加载图片，为了使用UniversalImageLoader，我们必须先初始化（最好的初始化方法就是在Application中初始化），因此我们在service包下新建NetEaseApplication类使其继承自Application，需要注意的是，自定义的application需配置后才能生效，因此我们在AndroidManifest.xml文件中添加application的配置<br>
（5）ListView效果显示完成后，我们开始增加轮播图，在layout文件夹下新建include_banner.xml布局文件作为轮播图的布局，编辑布局文件的内容，修改news.news_inner包下的HotFragment文件，将轮播图控件加入ListView，在news.adapter包中新建BannerAdapter类，使其继承自PagerAdapter<br>
（6）在layout文件夹下创建item_banner.xml布局文件作为轮播图的子布局，并将其加载到HotFragment中，至此，已经可以初步显示出轮播图，但是没有标题与指示点，修改include_banner.xml与HotFragment文件，为了显示指示圆点，我们在drawable文件夹下新建gray_dot.xml和white_dot文件，用来表示不同的指示圆点，在读取数据时，将圆点动态的添加到布局内部，至此，我们可以完全显示热点新闻<br>
（7）完善轮播图，在drawable文件夹下新建title_back.xml的shape文件，使轮播图的背景自底向上是由黑到透明渐变的，并将其添加到layout文件夹下的include_banner.xml布局文件中，实现了轮播图背景的渐变效果，更好的显示新闻标题<br>
（8）修改news.adapter包下的HotFragment文件，让热点新闻页面显示更多，并修改util包下的Constant文件，修改热点新闻的URL，使其能够动态扩展，在HotAdapter类中添加addData()方法，为了实现“再按一次退出网易新闻”功能，我们在MainActivity中重写onBackPressed()方法，通过判断点击时间来实现Toast<br>
### 5、热点新闻界面——详情页面<br>
包含Commits-13，详细步骤如下：<br>
（1）在news包下新建activity文件夹，在文件夹中先新建一个DetailActivity类使其继承自Activity，在layout文件夹下新建activity_detail.xml布局文件，并添加布局，注意在AndroidManifest.xml中注册DetailActivity<br>
（2）修改HotFragment，为ListView增加一个点击事件，修改HotAdapter，添加getDateByIndex()方法，为了实现滑动进入详情页面的效果，在res目录下新建anim文件夹，在anim文件夹中创建activity_in.xml和activity_in.xml<br>
（3）修改Constant类，添加详情页面的地址，在news.bean包中新建DetailWeb类和DetailWebImage类，来封装详情页面的数据，为了添加详情页面的滑动退出功能，我们在build.gradle文件中引入了SwipeBackLayout的依赖库，在操作之前首先绑定swipeLayout，然后将需要右滑退出的界面（DetailActivity）继承SwipeBackActivity，滑动关闭当前页面的方向有四种形式：1、SwipeBackLayout.EDGE_RIGHT 右边关闭页面；2、SwipeBackLayout.EDGE_LEFT 左边关闭页面；3、SwipeBackLayout.EDGE_BOTTOM 底部关闭页面；4、SwipeBackLayout.EDGE_ALL 以上三种形式关闭页面；<br>
（4）修改AndroidManifest.xml与style.xml文件，给DetailActivity的滑动退出设置背景，使得滑动退出时可看见详情页面，在news.activity包下新建DetailImageActivity文件，使其继承自Activity，并在layout目录下创建其对应的布局文件activity_detail_image.xml，最后在AndroidManifest.xml中注册DetailImageActivity活动，为了实现点击新闻图片有缩放的效果，我们引入PhotoView这个开源框架，在build.gradle中添加依赖库，并将其引入activity_detail_image.xml布局文件中，在DetailActivity中为详情新闻的图片增加点击事件<br>
（5）在news.adapter包下新建DetailImageAdapter文件，使其继承自PagerAdapter，为了更高的展示点击图片后的图片详情页面，我们新建DetailImageAdapter文件的布局文件item_detail_img.xml，为了在进程间传递对象，我们将DetailWebImage类实现了Serializable接口<br>
（6）总结：我们通过拼装HTML代码的方法，然后再通过WebView显示，最后与Java代码进行交互<br>
### 6、热点新闻详情页面——新闻跟帖页面<br>
包含Commits-14→Commits-x，详细步骤如下：<br>
（1）在news.activity包下新建FeedBackActivity类，在layout文件夹下创建其对应的布局文件activity_feedback.xml，在FeedBackActivity中绑定布局文件，修改util包下的Constant类，添加回帖页面的地址及替换方法，再修改DetailActivity，为replayCountTextView添加点击事件，使得点击跟帖数可以跳转到跟帖页面，最后在AndroidManifest.xml文件中对FeedBackActivity进行注册<br>
（2）在news.bean包下新建FeedBack和FeedBacks两个类，FeedBack用于封装单条回帖的数据，而FeedBacks则包含FeedBack，每次解析完FeedBack，就会将FeedBack加到我们的数据中，由于遍历的过程是无序的，我们在FeedBacks类中添加sort()方法以及比较器，对于JSONObject中的key-value形式的数据进行排序（按照key），key越大表示回帖时间最新，对应的value就是我们想要显示的回帖数据<br>
（3）由于HttpUtil是运行在子线程的，所以不能来更新UI，我们在FeedBackActivity类中创建静态内部类InnerHander使其继承自Handler，此外，由于数据无法直接传递给ListView，我们需要借助适配器（Adapter）来完成，在news.adapter包下新建FeedBackAdapter类，使其继承自BaseAdapter，修改FeedBackAdapter类，我们采用两种不同的adapter将数据显示出来，因此会有两种不同的view，因此我们首先判断要返回什么类型的view<br>
（4）在layout文件夹下新建item_feedback.xml布局文件，用于表示跟帖的布局，编写布局文件，用户的头像我们采用CircleImageView圆形控件，为了显示用户头像下的vip，我们采用在RelativeLayout布局下叠加两张ImageView，<br>

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
内容：编写首页UI布局,通过ViewPager与SmartTabLayout绑定的方式,在首页顶部引入滑动栏<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/hot_news.png)<br>
### Commits-9:(ExtractHttp Finish)<br>
内容：采用单例模式，抽取Http请求，将其封装为一个工具类<br>
### Commits-10:(HotNewsData Finish)<br>
内容：请求并显示热点新闻的数据<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/hotnews_data.png)<br>
### Commits-11:(BannerData Finish)<br>
内容：请求并显示轮播图的数据（由于数据源的问题，轮播图的图片暂时无法获取）<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/banner_data.png)<br>
### Commits-12:(GetMoreData Finish)<br>
内容：完善轮播图背景，完成下滑热点新闻界面显示出更多的新闻，并实现“再按一次退出网易新闻”功能<br>
### Commits-13:(HotPic Finish)<br>
内容：完成热点新闻的详情界面，实现点击图片放大放小、新闻侧滑退出等功能<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/hot_pic.png)<br>
### Commits-14:(FeedBack Finish)<br>
内容：完成跟帖界面的布局以及数据展示<br>
![](https://github.com/ambition-hb/News_1/raw/master/Pic/feedback.png)<br>