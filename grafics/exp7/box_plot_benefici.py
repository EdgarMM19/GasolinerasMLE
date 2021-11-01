import numpy as np
import matplotlib.pyplot as plt

leg = ["560", "640", "720"]

data = []
legend = []

for i in range(3):
    filename = f"../../resultatsExperiments/experiment7/boxplot_benefici_{7+i}hores.csv"
    legend.append(leg[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Quilòmetres màxims per camio')
plt.ylabel('Benefici (€)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-operadors-benefici.png", dpi=200)
plt.show()