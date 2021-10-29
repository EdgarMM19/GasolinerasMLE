import matplotlib.pyplot as plt
import numpy as np 
from mpl_toolkits.mplot3d import Axes3D
import math

ls = []
lslog = []
ks = []
kslog = []
valors = []
iters = []
with open("annealing3.txt", "r") as o:
    lines = o.read().split("\n")[:-1]
    for line in lines:
        nums = line.split(" ")
        lamb = float(nums[0])
        k = float(nums[1])
        valor = float(nums[2])
        iteracions = [float(i) for i in nums[3:]]
        ls.append(lamb)
        lslog.append(math.log(lamb,10))
        ks.append(k)
        kslog.append(math.log(k,10))
        valors.append(valor)
        iters.append(iteracions)

for i in range(len(iters)):
    n = len(iters[i])
    for j in range(n):
        if iters[i][j] == 0:
            iters[i][j] = iters[i][j-1]

convergencia = []
for i in range(len(iters)):
    n = len(iters[i])
    for j in range(3,n):
        if iters[i][j] == iters[i][-1]:
            convergencia.append(j)
            break

ls = np.array(ls)
ks = np.array(ks)
lslog = np.array(lslog)
kslog = np.array(kslog)
valors = np.array(valors)
iters = np.array(iters)

# plt.plot(lslog, valors, label = "Lambda vs benefici", marker = "o", linestyle="none")
# plt.show()
# plt.plot(lslog, convergencia, label = "Lambda vs benefici", marker = "o", linestyle="none")
# plt.show()
# plt.plot(kslog, valors, label = "K vs benefici", marker = "o", linestyle="none")
# plt.show()
# plt.plot(kslog, convergencia, label = "K vs benefici", marker = "o", linestyle="none")
# plt.show()
# fig = plt.figure()
# ax = Axes3D(fig)
for i in range(20):
    n = len(iters[i])
    for j in range(n):
        if iters[i][j] == 0:
            iters[i][j] = iters[i][j-1]
    x = [kslog[i] for j in range(n)]
    y = [j for j in range(n)]
    plt.plot(y, iters[i])
    #ax.plot(x, y, iters[i], marker = 'o')
plt.show()

#ax.scatter(lslog, kslog, valors, marker = 'o')
#plt.show()
# ax.scatter(lslog, kslog, convergencia, marker = 'o')
# plt.show()