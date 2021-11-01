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
with open("../../resultatsExperiments/experiment4/grandaria.txt", "r") as o:
    lines = o.read().split("\n")[:-1]
    for line in lines:
        nums = line.split(" ")
        centres = float(nums[0])
        tempsini = float(nums[1])
        tempshill = float(nums[2])
        tempsann = float(nums[3])
        benhill = float(nums[4])
        benann = float(nums[5])
        print(centres, tempsini/1000000, tempshill/1000000, tempsann/1000000,
            benhill, benann, (benhill-benann)/benhill*100)