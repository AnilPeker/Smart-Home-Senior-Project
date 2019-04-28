#install.packages("DBI")
#install.packages("RMySQL")
#install.packages("ggplot2")

library(ggplot2)
library(DBI)
library(RMySQL)
#Database Connection and Retrivieng Values
#======================================================================
#======================================================================

con<-dbConnect(MySQL(),host="localhost",dbname="anildata",user="root",password="")
result=dbSendQuery (con,"select * from temphum")

data=fetch(result,n=-1)
temperature=subset(data,select=c(temperature))
humidity=subset(data,select=c(humidity))

#print(temperature)
#print(humidity)
#print(data)

#Function to sum totalValues like (temperature,humidity)
#======================================================================
#======================================================================
calcSum<-function(x)
{
  resultCalc<-(sum(x))
  return(resultCalc)
}
#calcSum(temperature)
#calcSum(humidity)
tempVal=calcSum(temperature)
humVal=calcSum(humidity)
print(tempVal)
print(humVal)



#making data frames to write to database
#======================================================================
#======================================================================
temp.data<-data.frame(t=c(tempVal))
hum.data<-data.frame(t=c(humVal))

#dbWriteTable(con,"temp_sum", temp.data,overwrite=FALSE,append=TRUE)
#dbWriteTable(con,"hum_sum", temp.data,overwrite=FALSE,append=TRUE)


#plotingGraph humidityVsTemperature
#======================================================================
#======================================================================
png("tempVshumidity.png")
myplot=ggplot(data,aes(y = humidity,x = temperature)) + geom_line() +ggtitle("Temperature VS Humidity")
print(myplot)
dev.off()

#png("tempVstime.png")
#myplot2=ggplot(data,aes(y = 'created_date',x = temperature)) + geom_line() +ggtitle("Temperature VS Time")
#print(myplot2)
#dev.off()



#clear result set and closing connection with database
#======================================================================
#======================================================================
dbClearResult(result)
dbDisconnect(con)