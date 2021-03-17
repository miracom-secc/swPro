import sys

sys.stdin=open("./2847.txt",'rt')


n = int(input())


a = []

for cnt in range(n) :
    a.append(int(input()))
    
    
    
maxLevelNum = a[n-1]

diffNum=0
cnt=0
for i in range(n-2,-1,-1) :
    
    if maxLevelNum <= a[i] :        
        diffNum = a[i] - maxLevelNum  + 1
        cnt+=diffNum
        a[i] = a[i]-diffNum
    
        maxLevelNum = a[i]
    else :
        maxLevelNum = a[i]


print(cnt)        
    




