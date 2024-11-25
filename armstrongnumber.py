a=int(input("enter the number:"))
if a==sum(int(digit)**3 for digit in str(a)):
    print(f"{a}is an armstrong number")
else:
    print(f"{a}is not an armstrong number")