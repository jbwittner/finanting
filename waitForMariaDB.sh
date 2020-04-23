#!/bin/sh
# wait until MariaDB is really available
maxcounter=45
 
counter=1
while ! mysql --protocol TCP -u"$SQL_USER" -p"$SQL_PASSWORD" -e "show databases;" > /dev/null 2>&1; do
    sleep 1
    counter=`expr $counter + 1`
    if [ $counter -gt $maxcounter ]; then
        >&2 echo "We have been waiting for MySQL too long already; failing."
        exit 1
    fi;
done