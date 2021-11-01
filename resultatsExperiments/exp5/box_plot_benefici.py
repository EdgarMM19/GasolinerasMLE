import numpy as np
import matplotlib.pyplot as plt

leg = ["1", "2"]

data = []
legend = []

for i in range(2):
    filename = f"./cas{i}_boxplot_benefici.csv"
    legend.append(leg[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Camion(s)/centre')
plt.ylabel('Benefici (€)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-operadors-benefici.png", dpi=200)
plt.show()