library(DBI)
library(RMySQL)

con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")
result=dbSendQuery (con,"select * from temp")

data=fetch(result,n=3)

temp=subset(data,select=c(temp))

humidity=subset(data,select=c(hum))

tempSum<-(sum(temp))

print(data)
print(temp)
print(humidity)

print(tempSum)

temp.data<-data.frame(t=c(tempSum))
dbClearResult(result)
dbDisconnect(con)


con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")


dbWriteTable(con, "temp_sum", temp.data,overwrite=TRUE)


dbDisconnect(con)

