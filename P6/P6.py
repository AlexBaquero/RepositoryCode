import statsmodels.api as sm
import pandas as pnd
import seaborn as sns
import matplotlib.pyplot as mplt 
import numpy as np

#empezamos lendo o .csv
sns.set()

serieP = pnd.read_csv('P_Santiago_limpio.csv', parse_dates= True)
print(serieP.head(4));
print(serieP.tail(4));
print(serieP.shape);
print(serieP.describe())
serieP['Fecha'] = pnd.to_datetime(serieP['Fecha'])
serieP = serieP.set_index('Fecha')

P = serieP.loc[:,"P"]

descomposicion_P = sm.tsa.seasonal_decompose(serieP.P, model= 'additive',period=2965)
descomposicion_P.plot()
mplt.show()


descomposicion_P = sm.tsa.seasonal_decompose(serieP.P,model= 'additive',period=371)
descomposicion_P.plot()
mplt.show()

#Interpolacion linea da tendencia
tend  = np.array(descomposicion_P.trend)
indexes= np.isfinite(tend)
coefs = np.polyfit(np.array(pnd.to_datetime(descomposicion_P.trend.index).astype(int)/10**18)[indexes],tend[indexes],1)
func = np.poly1d(coefs)

#Inserción das columnas da tendencia e regresion do dataframe
serieP.insert(1,'Tendencia', descomposicion_P.trend.interpolate(method='linear'))
serieP.insert(2, 'Regresion', func(np.array(pnd.to_datetime(serieP.index).astype(int)/10**18)))

mplt.plot(serieP.index,serieP.Tendencia, serieP.Regresion)
print(coefs)
mplt.show()




"""
inter = tend.interpolate(method='linear') 
inter.plot()
mplt.show()

P = serieP.loc[:,"P"]
sm.graphics.tsa.plot_acf(P,title="P- Autocorrelacion")
mplt.show()

P_diff_1= np.diff(P)
sm.graphics.tsa.plot_acf(P_diff_1,title="Primera diferencia p-autocorrelacion")
mplt.show()

P_diff_2= np.diff(P_diff_1)
sm.graphics.tsa.plot_acf(P_diff_2,title="Segunda diferencia p-autocorrelacion")
mplt.show()
"""