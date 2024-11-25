numbers=[10, 20, 30, 40, 50]
def sum_of_array(arr):
    return sum(arr)
def max_of_array(arr):
    return max(arr)
def min_of_array(arr):
    return min(arr)
def print_array(arr):
    for num in arr:
        print(num, end='')
    print()
print("array elements:")
print_array(numbers)
print(f"sum of array elements:{sum_of_array(numbers)}")
print(f"max of array elements:{max_of_array(numbers)}")
print(f"min of array elements:{min_of_array(numbers)}")
