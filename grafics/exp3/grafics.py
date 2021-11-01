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
estab = []
with open("../../resultatsExperiments/experiment3/annealing.txt", "r") as o:
    lines = o.read().split("\n")[:-1]
    for line in lines:
        nums = line.split(" ")
        lamb = float(nums[0])
        k = float(nums[1])
        valor = float(nums[2])
        it = float(nums[3])
        iteracions = [float(i) for i in nums[4:]]
        ls.append(lamb)
        lslog.append(math.log(lamb,10))
        ks.append(k)
        kslog.append(math.log(k,10))
        valors.append(valor)
        iters.append(iteracions)
        estab.append(it)

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

plt.plot(lslog, valors, label = "Lambda vs benefici", marker = "o", linestyle="none")
plt.xlabel(r"$\log\lambda$")
plt.ylabel("Benefici")
plt.ylim(94000, 95000)
plt.show()
plt.plot(lslog, estab, label = "Lambda vs benefici", marker = "o", linestyle="none")
plt.xlabel(r"$\log\lambda$")
plt.ylabel("Iteracions")
plt.ylim(0, 2500)
plt.show()
# plt.plot(ks, valors, label = "K vs benefici", marker = "o", linestyle="none")
# plt.xlabel(r"$k$")
# plt.ylabel("Benefici")
# plt.show()
# plt.plot(ks, estab, label = "K vs benefici", marker = "o", linestyle="none")
# plt.xlabel(r"$k$")
# plt.ylabel("Iteracions")
# plt.show()
fig = plt.figure()
ax = Axes3D(fig)
# for i in range(20):
#     n = len(iters[i])
#     for j in range(n):
#         if iters[i][j] == 0:
#             iters[i][j] = iters[i][j-1]
#     x = [kslog[i] for j in range(n)]
#     y = [j for j in range(n)]
#     plt.plot(y, iters[i])
#     #ax.plot(x, y, iters[i], marker = 'o')
# plt.show()

# ax.scatter(lslog, ks, valors, marker = 'o')
# ax.set_xlabel(r'$\log{ \lambda}$')
# ax.set_ylabel(r'$k$')
# ax.set_zlabel('Benefici')
# plt.show()
# fig2 = plt.figure()
# ax2 = Axes3D(fig2)
# ax2.scatter(lslog, ks, estab, marker = 'o')
# ax2.set_xlabel(r'$\log{ \lambda}$')
# ax2.set_ylabel(r'$k$')
# ax2.set_zlabel('Iteracions')
# plt.show()