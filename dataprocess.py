import pandas as pd
import numpy as np
data = pd.read_csv("verified_online.csv")
data = data['url']
dnp_X = data.to_numpy()
dnp_y = np.ones(dnp_X.shape)
print(dnp_X[0:10])
print(dnp_y[0:10])
data2 = pd.read_csv("file_list.txt")
data2 = data2.drop(index=data2[data2['label']=='d'].index[0])
data2.loc[data2['label']=='n','label'] = 0
data2.loc[data2['label']=='p','label'] = 1
dnp2_X = data2['url'].to_numpy()
dnp2_y = data2['label'].to_numpy()
print(dnp_X.shape)
print(dnp_y.shape)
print(dnp2_X.shape)
print(dnp2_y.shape)
dnp_X = np.concatenate((dnp_X,dnp2_X))
dnp_y = np.concatenate((dnp_y,dnp2_y))
print(dnp_X.shape)
print(dnp_y.shape)

np.random.seed(116)
np.random.shuffle(dnp_X)
np.random.seed(116)
np.random.shuffle(dnp_y)

print(dnp_X[0:30])
print(dnp_y[0:30])