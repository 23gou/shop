::by liyixing
::2014/12/15
@@echo off
echo "start backup databases"

::�������
set year=%date:~0,4%
::�����·ݣ��·���Ҫ���⴦����������
set month=%date:~5,6%
set month=%month:~0,2%
::�������ڣ�������Ҫ���⴦����������
set day=%date:~5,6%
set day=%day:~3,3%
set day=%day:~0,2%
::ʱ
set hh=%time:~0,2%
::ʱ��С��10
if /i %hh% LSS 10 (set hh=0%time:~1,1%)
::�����֣�����Ҫ���⴦����������
set mm=%time:~3,5%
set mm=%mm:~0,2%
::�����룬����Ҫ���⴦����������
set ss=%time:~3,5%
set ss=%ss:~3,5%
set fileName=%year%-%month%-%day%-%hh%_%mm%_%ss%.sql
echo %fileName%

Pause

::shop
echo "start backup shop"
::����Ŀ¼
if not exist "D:/database_backup/shop" md "D:/database_backup/shop"
::��ṹ
mysqldump --opt -R -d a0826103329 -h221.231.6.226 --default-character-set=utf8 -ua0826103329 -p91337934 > D:/database_backup/shop/table%fileName%
::������
echo "start backup data"
mysqldump -t a0826103329 -h221.231.6.226 --default-character-set=utf8 -ua0826103329  -p91337934 > D:/database_backup/shop/data%fileName%


echo "backup all"

Pause