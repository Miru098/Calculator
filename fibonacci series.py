def fibonacci(n):
    a,b=0,1
    print("fibonacci series:",a,b,end="")
    for i in range(2,n):
        next_term=a+b
        print(next_term)
        a,b=b,next_term
n=int(input("enter the number:"))
if n<=0:
    print("enter a positive number:")
elif n==1:
    print(0)
else:
    fibonacci(n)
