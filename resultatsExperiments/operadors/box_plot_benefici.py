import numpy as np
import matplotlib.pyplot as plt

operadors = ["S", "AE", "C", "AEC", "AES", "CS", "AECS"]

data = []
legend = []

for i in range(7):
    filename = f"./operadors{i+1}_boxplot_benefici.csv"
    legend.append(operadors[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Conjunt d\'operadors')
plt.ylabel('Benefici (€)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-operadors-benefici.png", dpi=200)
plt.show()