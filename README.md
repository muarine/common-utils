# common-utils

## 工具篇

* **Exception异常处理类**
* **JodaTime常用操作**
* **excel导出（XSSFWorkbook）工具**
* **微信Media Upload组件**
* **ZIP压缩**

## 导出百万记录excel的应用场景：
* 1. 查找数据库minId 和 maxId区间段 ， 切割id区间段 ， 并且让每个excel存放数据量在3W条以下
* 2. 建立多线程export excel机制，实时监测线程池的线程数，并延时开启新线程
* 3. 按照切割的minId和maxId 查库导出excel(超出1W条记录自动新建Sheet)，存在临时文件目录下
* 4. 对当前请求所有临时excel文件进行zip压缩
* 5. 响应给页面

## 性能一览
* tomcat的catalina.sh 配置JDK -Xmx5048m	
* 50W条数据，平均响应时间在：3-4分钟
* 100W条数据，平均响应时间在：7-8分钟
* 上100W条以上，响应时间在10分钟，甚至会出现内存溢出，建议检测数据量过大，直接不允许导出，或者对外提供导出API，异步导出excel，配置FTP定向到临时文件目录提供下载即可

