#  基于区块链的工业建筑信息模型平台

# 一、介绍

基于区块链的工业建筑信息模型系统，顾名思义，采用国产自主知识产权的“长安链”，利用其技术完成信息的可信存储。前端使用Threejs技术，完成其3D可视化开发展示。后端采用SpringBoot框架完成数据业务逻辑处理。

# 二、技术栈

前端技术栈：Vue、ElementUI、Echarts

后端技术栈：Springboot、MybatisPlus、MariaDB

# 三、启动流程

## 1. 前端：

1. 安装Node.js环境（v16.17.0）
2. 执行npm install（相关错误请自行解决）
3. 执行npm run dev启动项目
4. 打包流程自行百度

## 2. 后端：

1. 从Gitee克隆项目
2. 修改application.yaml配置文件
3. 创建数据库（SQL文件见/SQL文件夹），启动数据库。
4. 更新Maven依赖
5. 启动服务器
6. 打包流程自行百度

## 3.区块链、Minio搭建

区块链相关，请参考长安链官网的进行搭建配置等操作。

Minio分布式存储，请参考Minio搭建教程进行搭建。

# 四、目录

+ BiBim-Java：项目后端代码

+ BiBim-Vue：项目前端代码

+ Blockchain：项目中区块链所需要的智能合约（根据长安链官网示例修改而来）

+ Data-Structure：项目数据库的数据结构

# 五、注意事项

1. 本项目开源可以随意使用，但禁止**简单修改**后作为毕业设计、毕业论文等方式使用。
2. 本项目为作者的毕业设计若后期出现学术抄袭、学术不端等情况，将根据自身损失对侵权人进行维权。
3. 本项目不对商业使用进行任何限制，但希望能保留相关作者信息。
3. 由于项目编码时间紧张，可能存在较多Bug，欢迎大家提交代码合并。

# 六、鸣谢

感谢在开发过程中长安链平台工程师对本项目使用长安链时出现的问题的帮助。

国产自主知识产权区块链平台：[长安链](https://chainmaker.org.cn/)
