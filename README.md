# 随心音乐

[![](https://img.shields.io/badge/作者-jsyjst-blue.svg)](https://blog.csdn.net/qq_41979349)

### 随心音乐，是一款基于MVP+Retrofit+EventBus+Glide的应用，有兴趣的盆友欢迎Star,Fork!

## 前言

由于平日里自己是一个很喜欢听音乐的boy,所以在闲暇时间本着自己的兴趣去开发了这款音乐，中途遇到了很多的困难，但是都是通过自己不断探索下一步一步解决了问题。

## 实现功能

- [x] 本地音乐
- [x] 我的收藏
- [x] 网络歌曲下载
- [x] 最近播放
- [x] 在线搜索歌曲，专辑，在线歌曲播放
- [x] 专辑歌曲
- [x] 播放栏，播放进度条
- [x] 播放界面，歌手图片唱碟
- [x] 播放控制，暂停，上一首，下一首
- [x] 歌词显示
- [x] 播放模式，顺序播放，随机播放，单曲循环
- [ ] 听模块，包括歌单，排行榜等
- [ ] 看模块，包括推荐MV等

## 项目展示
### 动态图

<div align="left">
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/gif1.gif" height="650" width="350" >
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/gif2.gif" height="650" width="350" >
</div>


### 截图

<div align="left">
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg1.jpg" height="650" width="350" >
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg2.jpg" height="650" width="350" >
</div>


<div align="left">
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg3.jpg" height="650" width="350" >
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg4.jpg" height="650" width="350" >
</div>

<div align="left">
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg5.jpg" height="650" width="350" >
<img src="https://github.com/jsyjst/YuanMusicPlay/raw/master/screenshots/jpg6.jpg" height="650" width="350" >

</div>


## 下载（5.0以上）
#### [apk下载](https://github.com/jsyjst/YuanMusicPlay/releases/download/YuanMusicPlay-v1.0/suixin-music-v1.apk)


## 项目Api

> 下面的请求中如果参数有songmid，都是得经过搜索歌曲或歌手后才能获得songmid，然后进行请求。不能获取到qq音乐的vip或者付费歌曲的播放地址，Api是通过fiddler4爬取网页端qq音乐获取的，如失效，请提出[issue](https://github.com/jsyjst/YuanMusicPlay/issues)。

### 搜索

#### 1.搜索歌手/歌曲

请求地址：https://c.y.qq.com

请求示例：[https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=泡沫&format=json](https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=泡沫&format=json)

说明：关键字为歌手就返回该歌手的歌曲，如果为歌曲就返回该歌曲列表

| 参数   | 说明     | 是否必须 | 默认值                                            |
| ------ | -------- | -------- | ------------------------------------------------- |
| p      | 分页     | 否       | 默认为1                                           |
| n      | 请求数量 | 否       | 默认为10                                          |
| w      | 关键字   | 是       | 无                                                |
| format | 格式化   | 否       | 如果用Retrofit的Json解析的话记得一定要format=json |

#### 2.搜索专辑

请求地址：https://c.y.qq.com

请求示例：https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=2&w=邓紫棋&format=json&t=8

| 参数   | 说明     | 是否必须 | 默认值                                                  |
| ------ | -------- | -------- | ------------------------------------------------------- |
| p      | 分页     | 否       | 默认为1                                                 |
| n      | 请求数量 | 否       | 默认为10                                                |
| w      | 关键字   | 是       | 无                                                      |
| format | 格式化   | 否       | 如果用Retrofit的Json解析的话记得一定要format=json       |
| t      | 类别     | 是       | 没有默认值，如果为搜索专辑，则t为8。如果是搜索mv,t=12。 |

### 获取歌手图片

#### 1.根据歌手获取图片

请求地址：http://music.163.com

请求示例：[http://music.163.com/api/search/get/web?s=邓紫棋&type=100](http://music.163.com/api/search/get/web?s=邓紫棋&type=100)

说明：请求后将有歌手图片的字段，请求后的picUrl和img1v1Url就是图片地址

| 参数 | 说明 | 是否必须 | 默认值          |
| ---- | ---- | -------- | --------------- |
| s    | 歌手 | 是       | 无              |
| type | 类别 | 是       | 无，但必须为100 |
**！！！注意**：有可能在网上能够请求到数据，但在实际代码中请求时却不能返回的情况（自己在开发中碰到），这时候别着急，只需要在网络请求时加上请求头"**User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36**"就能解决问题！

#### 2. 根据albumMID获取专辑图片

请求地址：[http://y.gtimg.cn](http://y.gtimg.cn)

请求示例：[http://y.gtimg.cn/music/photo_new/T002R180x180M000003c616O2Zlswm.jpg](http://y.gtimg.cn/music/photo_new/T002R180x180M000003c616O2Zlswm.jpg)

说明:请求示例其实有两部分组成，即固定地址+albumMid。

| 组成     | 值                                                 | 说明                     |
| -------- | -------------------------------------------------- | ------------------------ |
| 固定地址 | http://y.gtimg.cn/music/photo_new/T002R180x180M000 | 固定值，不需要改变       |
| albumMid | 在搜索专辑后，或者搜索歌曲会有albumMid这个字段     | 需要先搜索后得到albumMid |

### 获取播放地址

请求地址：https://u.y.qq.com

请求示例：[https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22`songmid`%22%3A%5B%22`001X0PDf0W4lBq`%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D](https://u.y.qq.com/cgi-bin/musicu.fcg?format=json&data=%7B%22req_0%22%3A%7B%22module%22%3A%22vkey.GetVkeyServer%22%2C%22method%22%3A%22CgiGetVkey%22%2C%22param%22%3A%7B%22guid%22%3A%22358840384%22%2C%22songmid%22%3A%5B%22001X0PDf0W4lBq%22%5D%2C%22songtype%22%3A%5B0%5D%2C%22uin%22%3A%221443481947%22%2C%22loginflag%22%3A1%2C%22platform%22%3A%2220%22%7D%7D%2C%22comm%22%3A%7B%22uin%22%3A%2218585073516%22%2C%22format%22%3A%22json%22%2C%22ct%22%3A24%2C%22cv%22%3A0%7D%7D)

说明：这个是根据歌曲songmid来获得音乐播放地址的，请求示例很长，在经过多次尝试后，很遗憾的并不能缩减，要想获取播放地址，只需要更改上面高亮字体的songmid后的001X0PDf0W4lBq即可，至于更改方法很多，比如直接用Java的字符串拼接。请求成功后请注意两个字段purl和sip（为数组）,歌曲的播放地址就是sip数组里的其中一个加上pur，即sip[0]+purl（vip音乐或者版权音乐的purl为空）。

#### 1. 请求时的参数说明

| 参数    | 说明                                                         |
| ------- | ------------------------------------------------------------ |
| songmid | 歌曲的songmid怎么得到，必须通过上面搜索歌曲后得到，然后更改上面示例的001X0PDf0W4lBq（泡沫的songmid）即可，示例的其它地方都不能做更改，也不能删减 |

#### 2.请求后重要字段解释

播放地址：sip[0]+purl

示例：[http://ws.stream.qqmusic.qq.com/C400000HjG8v1DTWRO.m4a?guid=358840384&vkey=852D30CD2DEA9E0AFF9CF700977FAFB413A06486CFE8F72502918665277C5407D8D5AD42039F02329401300003A2853B10816B83C3145159&uin=0&fromtag=66](http://ws.stream.qqmusic.qq.com/C400000HjG8v1DTWRO.m4a?guid=358840384&vkey=852D30CD2DEA9E0AFF9CF700977FAFB413A06486CFE8F72502918665277C5407D8D5AD42039F02329401300003A2853B10816B83C3145159&uin=0&fromtag=66)(泡沫)

| 字段 | 说明                                                 | 示例                                                         |
| ---- | ---------------------------------------------------- | ------------------------------------------------------------ |
| sip  | 播放接口地址，目前来说有两个地址，但是很有可能会改变 | "sip": [<br/>        "http://ws.stream.qqmusic.qq.com/",<br/>        "http://isure.stream.qqmusic.qq.com/"<br/>         ] |
| purl | 里面最重要的就是vkey的值，这个值每次请求都不一样     | "purl":C400000HjG8v1DTWRO.m4a?guid=358840384&<br>vkey=852D30CD2DEA9E0AFF9CF700977FAFB413A06486CFE8F7250<br>2918665277C5407D8D5AD42039F02329401300003A2853B10816B83C3145159<br>&uin=0&fromtag=66 |

### 获取歌词

请求地址：https://c.y.qq.com

请求示例：[https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=000wocYU11tSzS&format=json&nobase64=1](https://c.y.qq.com/lyric/fcgi-bin/fcg_query_lyric_new.fcg?songmid=000wocYU11tSzS&format=json&nobase64=1)

说明：如果你点击了上面的链接的话，你会发现返回的是-1310的错误码，这时候请别着急，并不是这个请求Api是无效的，只是因为这个获取歌词的Api有点特殊，在请求时需要在请求的header上加上Referer地址就可以了

| 参数     | 说明                                                         |
| -------- | ------------------------------------------------------------ |
| songmid  | 歌曲songmid，需要在搜索歌曲后获取                            |
| format   | 格式，建议加上format=json                                    |
| nobase64 | 敲重点！！！，一定要等于1，默认是0，如果没加上的话，返回的歌词将会是乱码，加上后就可以得到歌曲的动态歌词，即带有时间的歌词 |

**！！！敲重点**:记得在请求头上加上Referer:https://y.qq.com/portal/player.html,不然请求会返回-1310



## 后续

本项目为个人闲暇开发项目，仅供学习借鉴使用，不用做商业用途


