num=int(input("enter the number:"))
for i in range(2,int(num/2)+1):
     if num%i==0:
        print("the number is not prime",num)
        break
else:
    print("the number is prime",num)
