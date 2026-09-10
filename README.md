# 黑马头条（heima-leadnews）

> 基于 **Spring Cloud Alibaba** 的微服务架构资讯平台，对标「今日头条」，涵盖图文资讯浏览、自媒体内容创作、评论互动、智能搜索、用户行为采集等完整业务闭环。

## 一、项目简介

黑马头条是一个面向移动端的资讯信息流应用，整体由 **App 端 + 自媒体端 + 后台管理端** 三端组成，并配套完整的微服务后端：

- **App 端**：用户登录注册、频道定制、图文信息流、文章详情、评论、点赞收藏、搜索等；
- **自媒体端（Wemedia）**：自媒体作者登录、稿件撰写/发布、素材库、稿件审核流转、粉丝/数据统计等；
- **后台管理端（Admin）**：频道/菜单/角色/权限管理、敏感词管理、稿件人工审核、文章统计等。

完整业务流：自媒体作者发稿 → 自动 + 人工审核 → 文章落库 → App 端信息流拉取 → 用户行为采集 → Kafka 异步入库 → Elasticsearch 全文检索。跨服务一致性使用 **Seata** 分布式事务保障。

## 二、技术栈

| 分层         | 技术选型                                                                 |
| ------------ | ------------------------------------------------------------------------ |
| 基础框架     | Spring Boot 2.3.8.RELEASE、Spring Cloud Hoxton.SR9                       |
| 微服务组件   | Spring Cloud Alibaba 2.2.5.RELEASE（Nacos、Sentinel、Seata）             |
| 服务通信     | OpenFeign、Spring Cloud Gateway                                          |
| 数据存储     | MySQL 8.x、Redis 6.x、Elasticsearch 7.6.2、FastDFS                      |
| 数据访问     | MyBatis-Plus 3.1.1、PageHelper、Druid                                    |
| 消息中间件   | Apache Kafka 2.4.1                                                       |
| 分布式事务   | Seata 1.3.0（AT 模式）                                                   |
| 任务调度     | XXL-Job 2.1.2                                                            |
| 鉴权         | JWT + RSA 非对称加密                                                     |
| 文本处理     | HanLP（中文分词、文章标签）                                              |
| 容器化       | Docker、dockerfile-maven-plugin                                          |
| 构建         | Maven 多模块                                                             |

## 三、系统架构

```
[App端]    [自媒体端]    [后台管理端]
    |          |             |
  app-gateway  wemedia-gateway  admin-gateway     ← 三个 Spring Cloud Gateway
    |          |             |
 ┌──┴──┬──┬──┬─┴──┬──┬─┴──┬──┬──┬──┐
 │     │  │  │    │  │   │  │  │  │
article user wemedia admin behavior comment search  ← 7 个业务微服务
 │
 ├── Feign API 模块（4 个子 jar）
 └── Seata 分布式事务协调

中间件：Nacos / MySQL / Redis / Elasticsearch / Kafka / FastDFS / XXL-Job
```

## 四、模块说明

### 基础库（4 个）

| 模块                     | 说明 |
| ------------------------ | ---- |
| `heima-leadnews-model`   | 全局 POJO / DTO / VO / BO |
| `heima-leadnews-common`  | 通用 Result 响应、ThreadLocal 上下文、JWT 工具 |
| `heima-leadnews-utils`   | 日期、加密、JWT、阿里云、FastDFS、Html 解析等工具 |
| `heima-leadnews-api`     | 聚合模块，含 4 个 Feign 客户端子模块 |

### 业务微服务（7 个）

| 模块                       | 端口 | 核心职责 |
| -------------------------- | ---- | -------- |
| `heima-leadnews-admin`     | 9001 | 后台管理：频道/敏感词/菜单/角色权限、稿件审核、统计 |
| `heima-leadnews-user`      | 9002 | App 用户：登录注册、关注/粉丝、实名认证 |
| `heima-leadnews-wemedia`   | 9003 | 自媒体：作者登录、稿件增删改查、素材库、粉丝统计 |
| `heima-leadnews-article`   | 9004 | 文章中心：加载、内容、标签、关联词、收藏、热词 |
| `heima-leadnews-behavior`  | 9005 | 行为采集：点赞/收藏/阅读/不喜欢，Kafka 异步落盘 |
| `heima-leadnews-comment`   | 9006 | 评论：发表、列表、点赞、回复 |
| `heima-leadnews-search`    | 9007 | 全文搜索：ES 检索、关联词联想、搜索历史 |

### 网关（3 个）

| 模块                            | 端口 | 路由前缀 |
| ------------------------------- | ---- | -------- |
| `heima-leadnews-admin-gateway`  | 6001 | `/admin/**`、`/user/**` |
| `heima-leadnews-wemedia-gateway` | 6002 | `/wemedia/**`、`/admin/**` |
| `heima-leadnews-app-gateway`    | 6003 | `/article`、`/user`、`/behavior`、`/comment`、`/search` |

### 分布式事务

| 模块                | 说明 |
| ------------------- | ---- |
| `heima-leadnews-seata` | Seata 数据源代理、`@GlobalTransactional` 使用样例 |

## 五、关键设计亮点

1. **三网关分流**：App / 自媒体 / 后台三个入口独立 Gateway，RSA 公私钥按域隔离
2. **OpenFeign 解耦**：`heima-leadnews-api` 拆为 4 个子 jar，避免循环依赖
3. **Kafka 异步行为采集**：高频行为落 Kafka 后主链路立即返回
4. **Elasticsearch 增量索引**：Kafka 推送审核通过的文章写入 ES，读写分离
5. **Seata 分布式事务**：跨 wemedia / article / behavior 三库写操作保证最终一致
6. **JWT + RSA**：公私钥分离，网关验签、业务服务透传
7. **HanLP 分词**：自动提取文章标签
8. **XXL-Job 调度**：定时拉取、热词计算
9. **Docker 镜像化**：各服务内置 Dockerfile

## 六、快速开始

### 6.1 环境依赖

| 软件            | 版本       |
| --------------- | ---------- |
| JDK             | 1.8        |
| Maven           | 3.6+       |
| MySQL           | 8.0+       |
| Redis           | 6.0+       |
| Nacos Server    | 2.x        |
| Elasticsearch   | 7.6.2      |
| Kafka           | 2.12-2.4.1 |
| Seata Server    | 1.3.0      |
| FastDFS         | 5.11+      |
| XXL-Job Admin   | 2.1.2      |

> JDK 17 可编译但需额外 MAVEN_OPTS（见下方），推荐使用 JDK 8。

### 6.2 启动步骤

1. 启动中间件：MySQL、Redis、Nacos、Elasticsearch、Kafka、Seata、FastDFS、XXL-Job Admin
2. 导入 SQL 脚本（位于课程 `03.资料和工具` 目录）
3. 修改各服务 `application.yml` 中的 IP（默认 `192.168.66.133`）和 RSA 私钥路径
4. 按顺序启动：**三个网关 → 七个业务服务 → seata**
5. 接口文档：各服务启动后访问 `http://localhost:{port}/doc.html`（Knife4j）

### 6.3 JDK 17 编译（临时绕过）

```bash
# Windows PowerShell
$env:MAVEN_OPTS='--add-opens jdk.compiler/com.sun.tools.javac.code=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.comp=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.model=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.processing=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-opens jdk.compiler/com.sun.tools.javac.jvm=ALL-UNNAMED'
mvn clean compile -DskipTests
```

### 6.4 Docker 构建

```bash
mvn -pl heima-leadnews-admin -am dockerfile:build
mvn -pl heima-leadnews-admin-gateway -am dockerfile:build
# 其余模块类推
```

## 七、项目结构

```
heima-leadnews/
├── pom.xml                              # 父 POM
├── README.md
├── LICENSE
├── .gitignore
├── heima-leadnews-model/                # 数据模型
├── heima-leadnews-common/              # 公共组件
├── heima-leadnews-utils/               # 工具类
├── heima-leadnews-api/                 # Feign 客户端聚合
│   ├── heima-leadnews-article-api/
│   ├── heima-leadnews-behavior-api/
│   ├── heima-leadnews-user-api/
│   └── heima-leadnews-wemedia-api/
├── heima-leadnews-admin/                # 后台管理 :9001
├── heima-leadnews-user/                 # 用户服务 :9002
├── heima-leadnews-wemedia/              # 自媒体服务 :9003
├── heima-leadnews-article/              # 文章服务 :9004
├── heima-leadnews-behavior/             # 行为服务 :9005
├── heima-leadnews-comment/              # 评论服务 :9006
├── heima-leadnews-search/               # 搜索服务 :9007
├── heima-leadnews-admin-gateway/        # 后台网关 :6001
├── heima-leadnews-wemedia-gateway/      # 自媒体网关 :6002
├── heima-leadnews-app-gateway/          # App 网关 :6003
└── heima-leadnews-seata/                # 分布式事务
```

## 八、License

本项目基于 MIT License 开源，仅供学习交流使用。

> 原始课程素材来源于黑马程序员，本仓库为学习整理后的工程化版本。
