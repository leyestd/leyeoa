#LeyeOA

Love your neighbor as yourself. 
要爱人如己。－－《旧·利》

文档会马上编写，有问题请加QQ群 搜leyeoa 或 117660032
希望能帮到大家，相互学习，如果你有生产环境使用，使用出现的问题请告诉我，我会帮忙解决。

介绍

本系统是一个使用servlet完成的简单签单程序，以角色流和用户流，并通过jquery实现点击生成1，254，64，65这样的流程，用一个标记表明是角色的还是用户的，并以角色层次方式完成部门概念。表单和流程是分离的，一个表单可以有多个流程，用户在使用过程中还可以随时生成自己的流程。

安装

安装tomcat 7 , jdk 1.7 ,Mysql 5.0+
项目导入tomcat ,把.sql导入数据库，ROOT初始密码为12345678
或者在WebContent/META-INF/context.xml 修改数据库的连接。
开发者请用eclipse导入此项目，使用者把LeyeOA.war放入Tomcat7\webapps
在浏览器输入http://localhost:8080/LeyeOA/ 
初始用户名密码 administrator 12345678
初始的用色和用户不要删掉，是系统初始信息。
