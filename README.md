# blog

# 个人博客系统

## 一、简介

### 1.1 概述

blog是基于SpringBoot快速开发的框架，主要设计为个人博客后台管理系统，前台展示系统。

### 1.2 框架

- 1、语言：基于JDK11进行开发
- 2、Web框架：采用SpringBoot-2.4.5.RELEASE版本进行开发，thymeleaf
- 3、ORM框架：ORM使用Mybatis框架进行集成开发，mybatis plus 3.11
- 4、中间件：Redis、MySQ、elasticSearch 等

### 1.3 能力

- 1、基于SpringBoot，简化大量的XML文件配置和项目依赖
- 2、全局异常处理：全局异常被拦截后，发送到Redis消息队列，由Server运维模块处理异常请求日志
- 3、提供全文检索、数据统计功能：基于Elasticsearch7.x(最新版本)，并且兼容5.x-7.x之间所有的ES版本
- 4、全局告警：在产生异常请求日志或者触发其它策略时，提供告警接口：邮件告警、短信告警、钉钉告警数据推送等
- 5、使用Mybatis，使用JPA快速开发简单业务，使用传统XML开发核心业务，简单易扩展，包括多数据源方案等
- 6、使用elasticSearch7.8作为存储

## 二、更新记录

- 1、目前项目还在开发中、持续更新

## 三、文档与更新

- 暂定

## springboot官网地址

https://www.springcloud.cc/spring-boot.html

## V1.0开发记录(2021.05-2021.12)
- 1、集成Mysql、Redis、等
- 2、前台首页列表展示，详情展示功能，增加评论功能。
- 3、后台管理功能
- 4、配置多数据源
- 5、配置elastic，blog模块同时保持在mysql与elastic中
- 6、配置了邮件系统，可以发送附件图片。
