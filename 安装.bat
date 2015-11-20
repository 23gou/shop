
cd mysql
net stop spidermysql
bin\mysqld -remove spidermysql
bin\mysqld -install spidermysql
net start spidermysql

rem bin\mysql -h127.0.0.1 -f -P3301 --default-character-set=utf8 -uroot mysql  < ..\install.sql
cd ..
pause