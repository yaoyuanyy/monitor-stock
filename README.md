

股票实时提醒站点蹲坑


![](https://images.pexels.com/photos/3483098/pexels-photo-3483098.jpeg?cs=srgb&dl=pexels-john-guccione-wwwadvergroupcom-3483098.jpg&fm=jpg)

## 简介

组件使用Futu(富途)api获取目标股票信息，通过自定义预警涨跌幅阈值自动发送到微信公众号，便于你及时收关注的股票的涨跌幅信息，多多赚钱


## 目的
   对于我们上班族而言，虽然手机和电脑都不离手，但在早9晚9的工作模式中，一旦投入到紧张的工作中，自然不能时刻关注股市的变化，如何能在较高点卖掉手里的持有，如何能在较低点买进心仪的股票。
   基于实际需要，尤其最近的港股和A股，每天的涨跌幅都很大。抓住了高低点，就抓住了money。有了想法，就要有行动支持。空想不会产出结果。工作之便，开发了这个组件：使用Futu(富途)api，通过微信公众号。通过简单的配置，达到当关注的股票涨跌幅超过设定阈值时，通过微信公众号及时的被你看到，知道。从而及时操作你的股票。其实，富途也提供了提醒的功能，但微信公众号的使用时间无论是手机上还是电脑上，信息更容易通知到你。所以，这个组件的意义就有了


## 方法
组件已经使用云服务器7*24运行，同时代码也开源。所以你有两种方式使用：
1. 在 Issues 添加你的微信号，关注的股票代码及预警值，同时我使用的是微信测试公众号，你需要关注这个微信测试公众号，这样就能通过我的服务免费的提供功能支持，你就可以收到涨跌幅预警了。
微信测试公众号：
[测试公众号](https://github.com/yaoyuanyy/monitor-stock/blob/master/%E6%B5%8B%E8%AF%95%E5%85%AC%E4%BC%97%E5%8F%B7.jpeg)
2. 你也可以自己搭建，本代码是java8的，所以你需要配置下java环境，同时注册Futu账号，启动Futu的cmd客户端，对项目的配置文件进行简单的配置你的信息后，启动服务，也是可以的，相对第一种方式，麻烦一些

### 方式一 我帮你
点击 [Issues](https://github.com/yaoyuanyy/monitor-stock/issues)，提交你的关注的股票信息，如下：
账号：xxx
  股票：a
  提醒阈值：5%
  股票 b
  提醒阈值：2%

我收到Issues后，会将你提供的配置加到服务中，即刻生效


### 方式二 你自建

#### 安装富途客户端
我是linux系统，安装cmd版，24小时运行。
参考：https://openapi.futunn.com/futu-api-doc/opend/opend-cmd.html
按步骤操作就行，配置文件 FutuOpenD.xml中的login_account和login_pwd_md5换成你的账号和密码
$ cd $yourdir
## 为了关闭终端时程序仍然运行，需要使用nohup以及&
$ nohup ./FutuOpenD > futu.log 2>&1 & 


#### 下载源码
$ git clone git@github.com:yaoyuanyy/monitor-stock.git
$ cd monitor-stock
配置application.yml中的monitor-stock部分，例子如下
$ mvn clean package
$ java -jar monitor-stock-x.x.x.jar &

application.yml中添加
```
stock-account:
  defaultRate: 3
  account:
#    xyz:
      -
        market: 1
        code: "09988"
        rate: 5
      -
        market: 22
        code: "002603"
        rate: 6
```


## 结果
![](https://github.com/yaoyuanyy/monitor-stock/blob/master/111111.png)


## 声明
本组件仅仅个人研究使用，不得用于任何商业活动


