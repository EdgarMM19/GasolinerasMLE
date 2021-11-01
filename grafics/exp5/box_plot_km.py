import numpy as np
import matplotlib.pyplot as plt

leg = ["1", "2"]

data = []
legend = []

for i in range(2):
    filename = f"../../resultatsExperiments/experiment5/cas{i}_boxplot_quilometres.csv"
    legend.append(leg[i])
    with open(filename, "r") as f:
        curr_data = [float(x) for x in f.readlines()]
    data.append(curr_data)

plt.xlabel('Camion(s)/centre')
plt.ylabel('Quilòmetres recorreguts totals')
plt.plot(data, labels = legent, marker = "o", linestyle="none")
plt.tight_layout()
plt.savefig("boxplot-exp5-km.png", dpi=200)
plt.show()