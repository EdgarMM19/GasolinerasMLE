import numpy as np
import matplotlib.pyplot as plt


data = []
cost = [2,4,8,16,32,64,128]

filename = f"../../resultatsExperiments/experiment6/num_peticions.csv"
with open(filename, "r") as f:
    data = [float(x) for x in f.readlines()]

plt.xlabel('Cost de recòrrer un quilòmetre')
plt.ylabel('Número de peticions servides')
plt.plot(cost, data, label = "Cost quilòmetre vs peticions ateses", marker = "o")
plt.savefig("dotplot-exp6-peticions.png", dpi=200)
plt.show()