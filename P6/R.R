datos = read.delim('P_Santiago.csv',TRUE, sep='|')

Series<-ts(datos,frequency=7)
           
