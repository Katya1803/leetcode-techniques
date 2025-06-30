# Sorting Algorithms - CS Fundamentals

## Core Concepts and Mathematical Foundation

### Definition
**Sorting:** Rearranging elements of a collection in a specific order (typically ascending or descending) according to a comparison criterion.

**Formal definition:** Given a sequence of n elements a₁, a₂, ..., aₙ, produce a permutation a'₁, a'₂, ..., a'ₙ such that a'₁ ≤ a'₂ ≤ ... ≤ a'ₙ.

### Fundamental Properties

#### 1. Stability
**Definition:** A sorting algorithm is stable if it preserves the relative order of equal elements.

**Example:**
```
Input:  [(3,a), (1,b), (3,c), (2,d)]
Stable:   [(1,b), (2,d), (3,a), (3,c)]  // a before c maintained
Unstable: [(1,b), (2,d), (3,c), (3,a)]  // a, c order changed
```

**Why stability matters:**
- Multi-level sorting (sort by secondary key, then primary)
- Database applications where record order matters
- Maintaining object identity in equal elements

#### 2. In-place Property
**Definition:** An algorithm is in-place if it uses O(1) extra space (excluding input array).

**Examples:**
- **In-place:** Bubble sort, selection sort, insertion sort, heap sort
- **Not in-place:** Merge sort (needs O(n) auxiliary space), counting sort

#### 3. Adaptive Property
**Definition:** An algorithm is adaptive if it performs better on inputs that are already partially sorted.

**Example:** Insertion sort runs in O(n) time on nearly sorted arrays.

## Comparison-Based Sorting Theory

### Lower Bound for Comparison-Based Sorting
**Theorem:** Any comparison-based sorting algorithm requires Ω(n log n) comparisons in the worst case.

**Proof sketch:**
1. **Decision tree model:** Each comparison creates a binary decision tree
2. **Leaf nodes:** Each leaf represents one possible permutation
3. **Number of leaves:** n! possible permutations of n elements
4. **Tree height:** Minimum height of binary tree with n! leaves
5. **Calculation:** height ≥ log₂(n!) ≥ log₂((n/e)ⁿ) = n log₂(n/e) = Ω(n log n)

**Implication:** No comparison-based algorithm can sort faster than O(n log n) in worst case.

### Information-Theoretic Argument
**Information content:** Sorting requires determining which of n! permutations is correct.
**Information needed:** log₂(n!) bits
**Per comparison:** At most 1 bit of information
**Minimum comparisons:** log₂(n!) = Ω(n log n)

## Major Sorting Algorithms Analysis

### 1. Merge Sort (Divide and Conquer)

#### Algorithm
```java
public void mergeSort(int[] arr, int left, int right) {
    if (left >= right) return;
    
    int mid = left + (right - left) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, mid, right);
}

private void merge(int[] arr, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int i = left, j = mid + 1, k = 0;
    
    while (i <= mid && j <= right) {
        if (arr[i] <= arr[j]) {
            temp[k++] = arr[i++];
        } else {
            temp[k++] = arr[j++];
        }
    }
    
    while (i <= mid) temp[k++] = arr[i++];
    while (j <= right) temp[k++] = arr[j++];
    
    System.arraycopy(temp, 0, arr, left, temp.length);
}
```

#### Mathematical Analysis
**Recurrence relation:**
```
T(n) = 2T(n/2) + O(n)
```

**Master theorem application:**
- a = 2, b = 2, f(n) = O(n)
- log_b(a) = log₂(2) = 1
- f(n) = O(n¹) = O(n^log_b(a))
- **Result:** T(n) = O(n log n)

**Space complexity:** O(n) for auxiliary arrays

**Properties:**
- ✅ Stable
- ❌ Not in-place
- ❌ Not adaptive
- ✅ Consistent O(n log n) performance

### 2. Quick Sort (Divide and Conquer)

#### Algorithm
```java
public void quickSort(int[] arr, int low, int high) {
    if (low < high) {
        int pivotIndex = partition(arr, low, high);
        quickSort(arr, low, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, high);
    }
}

private int partition(int[] arr, int low, int high) {
    int pivot = arr[high];  // Choose last element as pivot
    int i = low - 1;
    
    for (int j = low; j < high; j++) {
        if (arr[j] <= pivot) {
            i++;
            swap(arr, i, j);
        }
    }
    
    swap(arr, i + 1, high);
    return i + 1;
}
```

#### Mathematical Analysis
**Best/Average case recurrence:**
```
T(n) = 2T(n/2) + O(n) = O(n log n)
```

**Worst case recurrence:**
```
T(n) = T(n-1) + O(n) = O(n²)
```

**Worst case occurs when:** Pivot is always smallest or largest element (already sorted array with poor pivot selection).

**Randomized quicksort:** Choose random pivot to make worst case probabilistically unlikely.

**Properties:**
- ❌ Not stable (basic version)
- ✅ In-place
- ❌ Not adaptive
- ⚠️ Average O(n log n), worst O(n²)

### 3. Heap Sort

#### Algorithm
```java
public void heapSort(int[] arr) {
    int n = arr.length;
    
    // Build max heap
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(arr, n, i);
    }
    
    // Extract elements from heap
    for (int i = n - 1; i > 0; i--) {
        swap(arr, 0, i);  // Move root to end
        heapify(arr, i, 0);  // Restore heap property
    }
}

private void heapify(int[] arr, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    
    if (left < n && arr[left] > arr[largest]) {
        largest = left;
    }
    
    if (right < n && arr[right] > arr[largest]) {
        largest = right;
    }
    
    if (largest != i) {
        swap(arr, i, largest);
        heapify(arr, n, largest);
    }
}
```

#### Mathematical Analysis
**Build heap phase:** O(n) - sum of heights ∑(h=0 to log n) h × 2^h / 2^(h+1) = O(n)
**Sorting phase:** O(n log n) - n extractions, each O(log n) heapify

**Total:** O(n log n)

**Properties:**
- ❌ Not stable
- ✅ In-place
- ❌ Not adaptive
- ✅ Consistent O(n log n) performance

## Simple Sorting Algorithms

### 1. Insertion Sort
```java
public void insertionSort(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
        int key = arr[i];
        int j = i - 1;
        
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        
        arr[j + 1] = key;
    }
}
```

**Analysis:**
- **Best case:** O(n) - already sorted
- **Worst case:** O(n²) - reverse sorted
- **Average case:** O(n²)
- **Properties:** Stable, in-place, adaptive

### 2. Selection Sort
```java
public void selectionSort(int[] arr) {
    for (int i = 0; i < arr.length - 1; i++) {
        int minIndex = i;
        
        for (int j = i + 1; j < arr.length; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        
        swap(arr, i, minIndex);
    }
}
```

**Analysis:**
- **All cases:** O(n²) - always does same number of comparisons
- **Properties:** Not stable (basic version), in-place, not adaptive

### 3. Bubble Sort
```java
public void bubbleSort(int[] arr) {
    boolean swapped;
    
    for (int i = 0; i < arr.length - 1; i++) {
        swapped = false;
        
        for (int j = 0; j < arr.length - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                swap(arr, j, j + 1);
                swapped = true;
            }
        }
        
        if (!swapped) break;  // Array is sorted
    }
}
```

**Analysis:**
- **Best case:** O(n) - already sorted (with optimization)
- **Worst case:** O(n²)
- **Properties:** Stable, in-place, adaptive

## Non-Comparison Based Sorting

### 1. Counting Sort
**Assumption:** Elements are integers in range [0, k] where k = O(n)

```java
public void countingSort(int[] arr, int k) {
    int[] count = new int[k + 1];
    int[] output = new int[arr.length];
    
    // Count occurrences
    for (int num : arr) {
        count[num]++;
    }
    
    // Calculate cumulative counts
    for (int i = 1; i <= k; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output array (from right to maintain stability)
    for (int i = arr.length - 1; i >= 0; i--) {
        output[count[arr[i]] - 1] = arr[i];
        count[arr[i]]--;
    }
    
    System.arraycopy(output, 0, arr, 0, arr.length);
}
```

**Analysis:**
- **Time:** O(n + k)
- **Space:** O(n + k)
- **Properties:** Stable, not in-place
- **When to use:** k = O(n), small range of integers

### 2. Radix Sort
**Idea:** Sort by individual digits/characters, from least to most significant

```java
public void radixSort(int[] arr) {
    int max = Arrays.stream(arr).max().getAsInt();
    
    // Do counting sort for every digit
    for (int exp = 1; max / exp > 0; exp *= 10) {
        countingSortByDigit(arr, exp);
    }
}

private void countingSortByDigit(int[] arr, int exp) {
    int[] count = new int[10];
    int[] output = new int[arr.length];
    
    // Count occurrences of digits
    for (int num : arr) {
        count[(num / exp) % 10]++;
    }
    
    // Calculate cumulative counts
    for (int i = 1; i < 10; i++) {
        count[i] += count[i - 1];
    }
    
    // Build output array
    for (int i = arr.length - 1; i >= 0; i--) {
        int digit = (arr[i] / exp) % 10;
        output[count[digit] - 1] = arr[i];
        count[digit]--;
    }
    
    System.arraycopy(output, 0, arr, 0, arr.length);
}
```

**Analysis:**
- **Time:** O(d × (n + k)) where d = number of digits, k = base
- **For integers:** O(d × n) where d = log_k(max_value)
- **Properties:** Stable, not in-place

### 3. Bucket Sort
**Idea:** Distribute elements into buckets, sort buckets individually

```java
public void bucketSort(float[] arr) {
    int n = arr.length;
    List<Float>[] buckets = new List[n];
    
    // Initialize buckets
    for (int i = 0; i < n; i++) {
        buckets[i] = new ArrayList<>();
    }
    
    // Put array elements into buckets
    for (float num : arr) {
        int bucketIndex = (int) (n * num);  // Assuming values in [0, 1)
        buckets[bucketIndex].add(num);
    }
    
    // Sort individual buckets
    for (List<Float> bucket : buckets) {
        Collections.sort(bucket);
    }
    
    // Concatenate all buckets
    int index = 0;
    for (List<Float> bucket : buckets) {
        for (float num : bucket) {
            arr[index++] = num;
        }
    }
}
```

**Analysis:**
- **Average case:** O(n + k) where k = number of buckets
- **Worst case:** O(n²) if all elements go to one bucket
- **Properties:** Depends on sorting algorithm used for buckets

## Sorting Algorithm Comparison

### Performance Summary
| Algorithm | Best | Average | Worst | Space | Stable | In-place | Adaptive |
|-----------|------|---------|-------|-------|--------|----------|----------|
| Bubble Sort | O(n) | O(n²) | O(n²) | O(1) | ✅ | ✅ | ✅ |
| Selection Sort | O(n²) | O(n²) | O(n²) | O(1) | ❌ | ✅ | ❌ |
| Insertion Sort | O(n) | O(n²) | O(n²) | O(1) | ✅ | ✅ | ✅ |
| Merge Sort | O(n log n) | O(n log n) | O(n log n) | O(n) | ✅ | ❌ | ❌ |
| Quick Sort | O(n log n) | O(n log n) | O(n²) | O(log n) | ❌ | ✅ | ❌ |
| Heap Sort | O(n log n) | O(n log n) | O(n log n) | O(1) | ❌ | ✅ | ❌ |
| Counting Sort | O(n + k) | O(n + k) | O(n + k) | O(k) | ✅ | ❌ | ❌ |
| Radix Sort | O(d(n + k)) | O(d(n + k)) | O(d(n + k)) | O(n + k) | ✅ | ❌ | ❌ |

### When to Use Each Algorithm

**Small arrays (n < 50):**
- **Insertion sort:** Simple, adaptive, good for nearly sorted data
- **Selection sort:** Minimizes number of swaps

**Large arrays:**
- **Merge sort:** Guaranteed O(n log n), stable, good for linked lists
- **Quick sort:** Average O(n log n), in-place, cache-friendly
- **Heap sort:** Guaranteed O(n log n), in-place, used in heapsort

**Special cases:**
- **Nearly sorted:** Insertion sort (adaptive)
- **Small range integers:** Counting sort
- **Integers with many digits:** Radix sort
- **Uniformly distributed:** Bucket sort

## Advanced Sorting Concepts

### 1. External Sorting
**Problem:** Sort data too large to fit in memory

**K-way merge approach:**
1. Divide data into chunks that fit in memory
2. Sort each chunk individually (internal sorting)
3. Merge k sorted chunks using priority queue
4. Write merged result to output

**Analysis:** O((n/k) × k log k × log(n/M)) where M = memory size

### 2. Parallel Sorting
**Merge sort parallelization:**
```java
public void parallelMergeSort(int[] arr, int left, int right) {
    if (right - left < THRESHOLD) {
        insertionSort(arr, left, right);  // Base case
        return;
    }
    
    int mid = left + (right - left) / 2;
    
    // Fork two subtasks
    ForkJoinTask<Void> leftTask = 
        ForkJoinTask.adapt(() -> parallelMergeSort(arr, left, mid));
    ForkJoinTask<Void> rightTask = 
        ForkJoinTask.adapt(() -> parallelMergeSort(arr, mid + 1, right));
    
    leftTask.fork();
    rightTask.compute();
    leftTask.join();
    
    merge(arr, left, mid, right);
}
```

### 3. Hybrid Sorting (Introsort)
**Idea:** Combine multiple algorithms for optimal performance

**Introsort algorithm:**
1. Start with quicksort
2. If recursion depth exceeds 2×log(n), switch to heapsort
3. For small subarrays (< 16), use insertion sort

**Benefits:** 
- Average case: Fast as quicksort
- Worst case: Guaranteed O(n log n) like heapsort
- Small arrays: Efficient as insertion sort

## Stability and Custom Comparators

### Making Unstable Sorts Stable
**Technique:** Attach original index to each element

```java
class IndexedValue {
    int value;
    int originalIndex;
    
    IndexedValue(int value, int index) {
        this.value = value;
        this.originalIndex = index;
    }
}

// Comparator that breaks ties using original index
Comparator<IndexedValue> stableComparator = (a, b) -> {
    int valueComparison = Integer.compare(a.value, b.value);
    return valueComparison != 0 ? valueComparison : 
           Integer.compare(a.originalIndex, b.originalIndex);
};
```

### Multi-key Sorting
**Example:** Sort students by grade (descending), then by name (ascending)

```java
class Student {
    String name;
    int grade;
}

// Method 1: Composite comparator
Comparator<Student> comparator = 
    Comparator.comparing(Student::getGrade).reversed()
             .thenComparing(Student::getName);

// Method 2: Multiple stable sorts (reverse order)
Collections.sort(students, Comparator.comparing(Student::getName));
Collections.sort(students, Comparator.comparing(Student::getGrade).reversed());
```

## Common Pitfalls and Interview Tips

### Common Mistakes
1. **Assuming stability:** Not all efficient algorithms are stable
2. **Ignoring space constraints:** Merge sort uses O(n) extra space
3. **Poor pivot selection:** Can make quicksort O(n²)
4. **Integer overflow:** In calculations like `(left + right) / 2`
5. **Not handling duplicates:** Some algorithms struggle with many duplicates

### Edge Cases to Consider
1. **Empty array:** Should return immediately
2. **Single element:** Already sorted
3. **All elements equal:** Test algorithm robustness
4. **Already sorted:** Test adaptive algorithms
5. **Reverse sorted:** Often worst case for some algorithms

### Problem Recognition Patterns
**Custom sorting needed when:**
- Complex comparison criteria
- Multi-key sorting requirements
- Stability requirements specified
- Space/time constraints given
- Large datasets (external sorting)

### Interview Problem-Solving Strategy
1. **Clarify requirements:** Stability, space constraints, data characteristics
2. **Choose appropriate algorithm:** Based on constraints and data properties
3. **Consider edge cases:** Empty, single element, duplicates
4. **Implement carefully:** Pay attention to boundary conditions
5. **Analyze complexity:** Verify time and space requirements

### Classic Interview Problems
- Implement merge sort/quicksort from scratch
- Sort array by frequency of elements
- Sort colors (Dutch National Flag)
- Merge k sorted arrays
- Find kth largest element
- Sort nearly sorted array
- Custom comparator problems

### Template for Comparison-Based Sorting
```java
public void customSort(T[] array, Comparator<T> comparator) {
    // Handle edge cases
    if (array == null || array.length <= 1) return;
    
    // Choose sorting algorithm based on requirements
    if (needsStability && spaceNotConcern) {
        mergeSort(array, comparator);
    } else if (averageCase && inPlaceRequired) {
        quickSort(array, comparator);
    } else if (guaranteedPerformance && inPlaceRequired) {
        heapSort(array, comparator);
    }
}
```