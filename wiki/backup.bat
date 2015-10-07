::by liyixing
::2014/12/15
@@echo off
echo "start backup databases"

::解析年份
set year=%date:~0,4%
::解析月份，月份需要特殊处理，处理两次
set month=%date:~5,6%
set month=%month:~0,2%
::解析日期，日期需要特殊处理，处理三次
set day=%date:~5,6%
set day=%day:~3,3%
set day=%day:~0,2%
::时
set hh=%time:~0,2%
::时间小于10
if /i %hh% LSS 10 (set hh=0%time:~1,1%)
::解析分，分需要特殊处理，处理两次
set mm=%time:~3,5%
set mm=%mm:~0,2%
::解析秒，秒需要特殊处理，处理两次
set ss=%time:~3,5%
set ss=%ss:~3,5%
set fileName=%year%-%month%-%day%-%hh%_%mm%_%ss%.sql
echo %fileName%

Pause

::shop
echo "start backup shop"
::备份目录
if not exist "D:/database_backup/shop" md "D:/database_backup/shop"
::表结构
mysqldump --opt -R -d a0826103329 -h221.231.6.226 --default-character-set=utf8 -ua0826103329 -p91337934 > D:/database_backup/shop/table%fileName%
::表数据
echo "start backup data"
mysqldump -t a0826103329 -h221.231.6.226 --default-character-set=utf8 -ua0826103329  -p91337934 > D:/database_backup/shop/data%fileName%


echo "backup all"

Pause