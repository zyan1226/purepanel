# PurePanel 后端服务

<p>
  <a href="https://github.com/purepanel-projects/purepanel-backend/blob/main/LICENSE">
    <img src="https://img.shields.io/github/license/purepanel-projects/purepanel-backend" alt="License"/>
  </a>
  <a href="https://github.com/purepanel-projects/purepanel-backend/releases">
    <img src="https://img.shields.io/github/v/release/purepanel-projects/purepanel-backend" alt="Version"/>
  </a>
  <img alt="GitHub repo size" src="https://img.shields.io/github/repo-size/purepanel-projects/purepanel-backend">
  <img alt="GitHub User's stars" src="https://img.shields.io/github/stars/purepanel-projects/purepanel-backend">
  <a href="https://deepwiki.com/purepanel-projects/purepanel-backend">
    <img src="https://deepwiki.com/badge.svg" alt="Ask DeepWiki">
  </a>
</p>

> 注意！本项目基于 SpringBoot3，所以必须使用 Java17+。

## 快速开始

### 获取代码

#### 方式一

前往 [版本发布页](https://github.com/purepanel-projects/purepanel-backend/releases) 获取相应版本代码。

#### 方式二

git 拉取仓库代码

```shell
git clone https://github.com/purepanel-projects/purepanel-backend.git
```

### 初始化数据库

运行项目目录下 `sql/purepanel.sql` 文件初始化数据库。

### 修改项目配置

前往 `src/main/resources/application.yml` 修改 Mysql、Redis 配置。