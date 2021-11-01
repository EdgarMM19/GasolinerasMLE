import numpy as np
import matplotlib.pyplot as plt


data = []
cost = [2,4,8,16,32,64,128]
dies = ["0","1","2","3"]

def func(pct, allvals):
    absolute = int(round(pct/100.*np.sum(allvals)))
    return "{:.1f}%".format(pct, absolute)

for i in range(6):
    fig, ax = plt.subplots(figsize=(6, 3), subplot_kw=dict(aspect="equal"))
    filename = f"../../resultatsExperiments/experiment6/num_peticions_cost{i+1}.csv"
    with open(filename, "r") as f:
        data = [float(x) for x in f.readlines()]
    plt.title(f"Cost per quilòmetre = {cost[i]} €")
    wedges, texts, autotexts = ax.pie(data, autopct=lambda pct: func(pct, data),textprops=dict(color="w"))
    ax.legend(wedges, dies,
              title="Dies de la petició",
              loc="center left",
              bbox_to_anchor=(1, 0, 0.5, 1))
    plt.setp(autotexts, size=8, weight="bold")
    p = plt.gcf()
    plt.savefig(f"donutplot-exp6-peticions-cost{i+1}.png", dpi=200)
    plt.show()