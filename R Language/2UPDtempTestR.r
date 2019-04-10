#install.packages("DBI")
#install.packages("RMySQL")
#install.packages("ggplot2")
library(ggplot2)
library(DBI)
library(RMySQL)

#connection to database
#-----------------------
con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")
result=dbSendQuery (con,"select * from temp")

#-----------------------
#Taking columns from database
#-----------------------
data=fetch(result,n=-1)
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

#dbWriteTable(con,"temp_sum", temp.data,overwrite=FALSE,append=TRUE) #if overwrite false and append true add new to colum


dbDisconnect(con)
#----------------------
#line chart for temperature vs humidity
#----------------------
print(data)
png("myplot.png")
myplot=ggplot(data,aes(y = hum,x = temp)) + geom_line() +ggtitle("Temp VS Humidity")
print(myplot)
dev.off()
#------------------------------------

#connection for taking temp_sum data
con<-dbConnect(MySQL(),host="localhost",dbname="temp",user="root",password="")
result2=dbSendQuery (con,"select * from temp_sum")
data2=fetch(result2,n=-1)
temp2=subset(data2,select=c(t))


#-------------------------------------

#grp <- c(temp2)

#print(length(temp2$t))  #length of t variable
#tmp_length<-(1:length(temp2$t))
#print (tmp_length)
#print (grp)

#png("myplotTempResult.png")
#barplot(grp)
#dev.off()



