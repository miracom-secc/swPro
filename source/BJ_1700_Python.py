import sys

sys.stdin=open('./1700.txt')

#구멍갯수 N (1 ≤ N ≤ 100)
#사용횟수  K (1 ≤ K ≤ 100)
#전기용품의 이름이 K 이하
n, k = map(int,input().split())


a = list(map(int,input().split()))


elect = [0] * (k+1)




concentList=[]

cnt=0

for i in range(k) :
    if a[i] in concentList : #있으면 Pass
            continue       
    elif len(concentList) < n : #최초

        concentList.append(a[i])                     
        continue
         
    #없으면 콘센트리스트에서 확인  
    index, maxConcent=-1,-1
    for j in range(n):

        if concentList[j] not in a[i:] : #콘센트가 없으면 체인지

            concentList[j]=a[i]
            cnt+=1
            break
        elif maxConcent < a[i:].index(concentList[j]) : #콘센트가 가장 멀리 있는것 부터 제거

            maxConcent=a[i:].index(concentList[j])
            index = j                
        
    else :

        if index>-1 :
            concentList[index]=a[i]
            cnt+=1
    
    

print(cnt)
        



