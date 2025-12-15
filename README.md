# PurePanel

<p>
  <a href="https://github.com/zyan1226/purepanel/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/zyan1226/purepanel" alt="License"/>
  </a>
  <a href="https://github.com/zyan1226/purepanel/releases">
    <img src="https://img.shields.io/github/v/release/zyan1226/purepanel" alt="Version"/>
  </a>
  <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/zyan1226/purepanel">
  <img alt="GitHub User's stars" src="https://img.shields.io/github/stars/zyan1226/purepanel">
  <a href="https://deepwiki.com/zyan1226/purepanel">
    <img src="https://deepwiki.com/badge.svg" alt="Ask DeepWiki">
  </a>
</p>

PurePanel 是一个极简、纯粹、易上手的中后台项目启动脚手架，让你快速搭建中后台项目。

包含前端 [TDesign](https://tdesign.tencent.com/) Vue3 项目和后端 SpringBoot3 项目。

<img alt="image" src="https://github.com/user-attachments/assets/015bbc29-0541-494b-bbab-f7615d00ce92" />

如果你也对该项目感兴趣请邮件至 **zyan1226@163.com** 与我取得联系！

> 注意！本项目基于 SpringBoot3，所以必须使用 Java17+。

## 快速开始

### 获取代码

#### 方式一

前往 [版本发布页](https://github.com/zyan1226/purepanel/releases) 获取相应版本代码。

#### 方式二

git 拉取仓库代码

```shell
git clone https://github.com/zyan1226/purepanel.git
```

### 初始化数据库

运行项目目录下 `server/sql/purepanel.sql` 文件初始化数据库。

### 修改项目配置

前往 `server/src/main/resources/application.yml` 修改 Mysql、Redis 配置。
