# netty-im
Netty 入门与实战：仿写微信 IM 即时通讯系统

**使用 Netty 不使用 JDK 原生 NIO 的原因**

`1.使用 JDK 自带的NIO需要了解太多的概念，编程复杂，一不小心 bug 横飞`

`2.Netty 底层 IO 模型随意切换，而这一切只需要做微小的改动，改改参数，Netty可以直接从 NIO 模型变身为 IO 模型`

`3.Netty 自带的拆包解包，异常检测等机制让你从NIO的繁重细节中脱离出来，让你只需要关心业务逻辑`

`4.Netty 解决了 JDK 的很多包括空轮询在内的 Bug`

`5.Netty 底层对线程，selector 做了很多细小的优化，精心设计的 reactor 线程模型做到非常高效的并发处理自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手`

`6.Netty 社区活跃，遇到问题随时邮件列表或者 issue`

`7.Netty 已经历各大 RPC 框架，消息中间件，分布式通信中间件线上的广泛验证，健壮性无比强大`

**拆包粘包**

`1. 固定长度的拆包器 FixedLengthFrameDecoder`

`2. 行拆包器 LineBasedFrameDecoder`

`3. 分隔符拆包器 DelimiterBasedFrameDecoder`

`4. 基于长度域拆包器 LengthFieldBasedFrameDecoder`