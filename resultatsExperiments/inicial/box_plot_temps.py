import numpy as np
import matplotlib.pyplot as plt

estrategia = ["buida", "voraç"]

data = []
legend = []

for i in range(2):
    filename = f"./{estrategia[i]}_boxplot_temps.csv"
    legend.append(estrategia[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Estratègia inicial')
plt.ylabel('Temps (ms)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-inicial-temps.png", dpi=200)
plt.show()