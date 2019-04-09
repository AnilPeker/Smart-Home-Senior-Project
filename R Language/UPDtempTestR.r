/#install.packages("DBI")
/#install.packages("RMySQL")
library(DBI)
library(RMySQL)

#connection to database
#-----------------------
con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")
result=dbSendQuery (con,"select * from temp")

#-----------------------
#Taking columns from database
#-----------------------
data=fetch(result,n=3)
temp=subset(data,select=c(temp))

humidity=subset(data,select=c(hum))

tempSum<-(sum(temp))
#------------------------
print(data)
print(temp)
print(humidity)

print(tempSum)

temp.data<-data.frame(t=c(tempSum))

#closing and disconnetcting from database
#--------------------
dbClearResult(result)
dbDisconnect(con)

#writting back to database part
#---------------------
con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")


dbWriteTable(con, "temp_sum", temp.data,overwrite=TRUE)


dbDisconnect(con)
#----------------------


