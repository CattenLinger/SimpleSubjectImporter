# 简单的课表查询网站

为针对广东岭南职业技术学院所使用的教务系统（青果网教务系统）的课表爬取与整理网站

[TOC]

## 公共API

通过此类 API 与服务器通讯均使用 UTF-8 编码格式

### 课表查询 API

#### /api/terms - 所有学期

查询所有可用学期

- 方法 ： GET
- 无传参
- 返回：学期字符串数组

```
{
	"data":["2016-2017学年第二学期"]
}
```

#### /api/weeks - 某学期最大周数

查询学期最大周数

- 方法 ： GET
- 参数
 - termName ： 学期全称
- 返回 ： 最大周数（从 1 开始算起）

```
{
	"data":19
}
```

#### /api/classes - 某学期上课班级

查询学期内上课的班级

- 方法 ： GET
- 参数
 - termName ： 学期全称
- 返回 ： 学期内所有上课班级

```
{
	"data":["班级名称 1"，"班级名称 2", "班级名称 3", ....]
}
```

#### /api/types - 查询课程类型

查询所有课程类型

- 方法 ： GET
- 参数 : 
    - termName ： 指定学期，可选，不指定则返回所有学期的类型
- 返回 ： 课程类型

```
{
	"data":[
		"专业基础课/必修课",
		"专业课/必修课",
		"专业课/限选课",
		"公共课/任选课",
		"公共课/必修课"，
		.... ]
}
```

#### /api/lessons - 查询课表

- 方法 ： GET
- 参数 ：
 - termName ： 学期全称
 - week ： 周数（从 1 开始算起），可选，不填则返回整个学期课表
 - className ： 上课班级全称
 - ignoreType ： 忽略的课程类型，数组，可选，不填则返回全部类型的课程（包括因为教务录入问题而产生的重复课程）
- 返回 ： 指定条件的课表

```
{
	"data":[
		{
			"id":158758,
			"term":"2016-2017学年第二学期", // 学期
			"unit":"外语外贸学院",			// 学院
			"name":"公共外语（二）",		// 课程名称
			"period":63.0,					// 课时
			"credit":3.5,					// 学分
			"teacher":"XXX",				// 上课教师
			"category":"公共课/必修课",		// 课程类型
			"attendClass":"xx通信技术x班",	//上课班级
			"week":1,						// 周数
			"weekday":5,					// 星期几
			"turn":5,						// 第几节课
			"address":"C栋B609"				// 地点
		}, .....]
}
```

#### /api/lessons/timepoint - 查询有课时间

- 方法 ： GET
- 参数 ： 
 - termName ： 指定学期
 - week ： 周数，数组
 - className ： 班级列表，数组
 - ignoreType ： 忽略的课程类型，数组，可选
- 返回 ： 指定条件的有课的时间点

```
{
	"data":[
		{"week":9,"day":2,"turn":7},
		{"week":9,"day":2,"turn":8},
		....]
}

```

## 其他 API

管理 API 可以查阅 Controller 代码得知。这里不直接说明。

***
## 参考

- [Hibernate EHCache – Hibernate Second Level Cache](http://www.journaldev.com/2980/hibernate-ehcache-hibernate-second-level-cache)
- [解决spring3 mvc中乱码的问题。](http://blog.csdn.net/zhengzhb/article/details/6967975)
- [How to configure log4j.properties for SpringJUnit4ClassRunner?](http://stackoverflow.com/questions/4570072/how-to-configure-log4j-properties-for-springjunit4classrunner)
- [H2数据库攻略](http://www.cnblogs.com/gao241/p/3480472.html)
- [阿里开源数据库连接池Druid一览](http://www.kailing.pub/article/index/arcid/91.html)
- [Spring Data操作Redis时，发现key值出现 \xac\xed\x00\x05t\x00\tb](http://blog.csdn.net/yunhaibin/article/details/9001198)

