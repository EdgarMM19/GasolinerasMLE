import numpy as np
import matplotlib.pyplot as plt

operadors = ["S", "AE", "C", "AEC", "AES", "CS", "AECS"]

data = []
legend = []

for i in [2,5]:
    filename = f"../../resultatsExperiments/experiment2/operadors{i+1}_boxplot_temps.csv"
    legend.append(operadors[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Conjunt d\'operadors')
plt.ylabel('Temps (ms)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-comparacio-temps.png", dpi=200)
plt.show()

data = []
legend = []

for i in [2,5]:
    filename = f"./operadors{i+1}_boxplot_nodes.csv"
    legend.append(operadors[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Conjunt d\'operadors')
plt.ylabel('Nombre de passos')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-comparacio-nodes.png", dpi=200)
plt.show()

data = []
legend = []

for i in [2,5]:
    filename = f"./operadors{i+1}_boxplot_benefici.csv"
    legend.append(operadors[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Conjunt d\'operadors')
plt.ylabel('Benefici (€)')
plt.boxplot(data, labels=legend, flierprops=dict(marker='.', markeredgecolor='b', markersize=1))
plt.tight_layout()
plt.savefig("boxplot-comparacio-benefici.png", dpi=200)
plt.show()