library(DBI)
library(RMySQL)

con<-dbConnect(MySQL(),host="localhost",dbname="test",user="root",password="")
result=dbSendQuery (con,"select * from temp")

data=fetch(result,n=3)

temp=subset(data,select=c(temp))

humidity=subset(data,select=c(hum))

print(data)
print(temp)
print(humidity)


