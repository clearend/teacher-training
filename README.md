# 课程思政教师培训管理系统设计与实现

## 功能要求

### 用户管理
- 注册账号
- 登录
- 区分用户角色（管理员和普通用户）

### 培训内容管理
- 创建、编辑和删除培训内容
- 查看培训内容详情

### 培训完成情况管理
- 培训报名
- 记录培训完成情况
- 分析培训结果

### 素材库管理
- 素材上传
- 素材查看和下载
- 素材评论和审核

### 提醒功能
- 培训提醒
- 自定义提醒

### 其他功能
- 系统通知
- 数据共享

## 数据库设计

### 管理员表 (Admin)
- admin_id: INT (主键，自增)
- username: VARCHAR(50) (唯一)
- password: VARCHAR(50)
- email: VARCHAR(100)
- create_time: DATETIME

### 用户表 (User)
- user_id: INT (主键，自增)
- username: VARCHAR(50) (唯一)
- password: VARCHAR(50)
- email: VARCHAR(100)
- create_time: DATETIME
- role: ENUM('admin', 'user')

### 培训内容表 (TrainingContent)
- content_id: INT (主键，自增)
- title: VARCHAR(255)
- description: TEXT
- start_date: DATE
- end_date: DATE
- created_by: INT (外键关联管理员表)
- create_time: DATETIME

### 培训完成情况表 (TrainingCompletion)
- completion_id: INT (主键，自增)
- user_id: INT (外键关联用户表)
- content_id: INT (外键关联培训内容表)
- completion_date: DATE
- certificate_url: VARCHAR(255)
- completion_rate: DECIMAL(5,2)
- remarks: TEXT
- create_time: DATETIME

### 素材库表 (MaterialLibrary)
- material_id: INT (主键，自增)
- title: VARCHAR(255)
- type: ENUM('image', 'article', 'video', 'animation', 'ebook')
- content: TEXT
- teaching_content: TEXT
- ideological_elements: TEXT
- key_points: TEXT
- uploaded_by: INT (外键关联用户表)
- upload_time: DATETIME

### 提醒记录表 (Reminder)
- reminder_id: INT (主键，自增)
- user_id: INT (外键关联用户表)
- reminder_content: TEXT
- reminder_date: DATE
- create_time: DATETIME

## 前端界面设计

### 登录页面 (Login Page)
- 用户名和密码输入框
- 登录按钮

### 注册页面 (Registration Page)
- 用户名、密码和确认密码输入框
- 邮箱输入框
- 注册按钮

### 首页 (Home Page)
- 显示当前用户信息
- 菜单导航栏

### 培训内容管理页面 (Training Content Management Page)
- 列表展示已创建的培训内容
- 添加、编辑和删除培训内容功能

### 培训完成情况查看页面 (Training Completion Viewing Page)
- 根据用户查看已完成的培训情况
- 过滤和排序功能

### 素材库管理页面 (Material Library Management Page)
- 列表展示已上传的素材
- 添加、编辑和删除素材的功能

### 提醒设置与查看页面 (Reminder Setting and Viewing Page)
- 显示当前用户的提醒设置
- 设置和管理提醒

### 个人信息修改页面 (Profile Modification Page)
- 允许用户修改个人信息

## 技术要求
- Java
- MySQL
- Python
- Spring Boot

## 总结
该系统通过管理培训内容和完成情况，提供了一个全面的教师培训管理解决方案。同时，素材库管理和提醒功能的设计，使得教师可以更轻松地获取资源和管理个人时间。使用Java、MySQL和Spring Boot等技术，可以实现系统的设计与实现。
