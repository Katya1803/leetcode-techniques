# 🚀 LeetCode Java Techniques

[![Java Version](https://img.shields.io/badge/Java-17+-brightgreen.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Build Status](https://img.shields.io/badge/Build-Passing-success.svg)]()

> 🎯 **A comprehensive Java repository for mastering data structures, algorithms, and coding interview patterns through systematic practice and theoretical understanding.**

---

## 📋 Table of Contents

- [🌟 Features](#-features)
- [🎯 Target Audience](#-target-audience)
- [📁 Project Structure](#-project-structure)
- [🚀 Getting Started](#-getting-started)
- [💡 Usage Examples](#-usage-examples)
- [📚 Learning Path](#-learning-path)
- [🧪 Testing](#-testing)
- [📖 Documentation](#-documentation)
- [🤝 Contributing](#-contributing)
- [📄 License](#-license)

---

## 🌟 Features

### 🎓 **Educational Excellence**
- **Comprehensive theory** - Deep CS fundamentals with mathematical foundations
- **Pattern-based learning** - Systematic approach to algorithmic problem-solving
- **Multiple solutions** - Brute force → Optimized implementations with trade-off analysis
- **Interview preparation** - Real-world interview patterns and tips

### 💻 **Code Quality**
- **Clean architecture** - Well-organized, scalable codebase
- **Comprehensive testing** - JUnit 5 with extensive test coverage
- **Documentation** - Detailed explanations and complexity analysis
- **Best practices** - Industry-standard Java development patterns

### 🔍 **Systematic Coverage**
- **Data Structures**: Arrays, Strings, Hash Maps, Linked Lists, Stacks, Queues, Trees, Graphs
- **Algorithms**: Sorting, Searching, Dynamic Programming, Greedy, Backtracking, Graph Algorithms
- **Advanced Techniques**: Bit Manipulation, Union-Find, Segment Trees, Mathematical Algorithms
- **Problem Patterns**: Two Pointers, Sliding Window, Fast-Slow Pointers, Merge Intervals

---

## 🎯 Target Audience

| Audience | Benefits | Use Cases |
|----------|----------|-----------|
| **🎓 CS Students** | Solid foundation + practical skills | Course support, exam preparation |
| **💼 Interview Candidates** | Systematic patterns + comprehensive coverage | FAANG interviews, job preparation |
| **🏆 Competitive Programmers** | Advanced techniques + optimization focus | Contest preparation, ranking improvement |
| **👨‍🏫 Educators** | Teaching materials + structured curriculum | Course development, student mentoring |
| **🔄 Self-learners** | Complete package + clear progression | Skill development, career transition |

---

## 📁 Project Structure

```
leetcode-techniques/
├── 📄 README.md                     # Project documentation
├── 📄 pom.xml                       # Maven configuration
├── 📁 src/
│   ├── 📁 main/java/com/leetcode/
│   │   ├── 📁 datastructures/       # Core data structure implementations
│   │   │   ├── 📁 arrays/           # Array techniques (Two Pointers, Sliding Window)
│   │   │   ├── 📁 strings/          # String manipulation algorithms
│   │   │   ├── 📁 hashmaps/         # Hash table patterns
│   │   │   ├── 📁 linkedlists/      # Linked list algorithms
│   │   │   └── 📁 stacks/           # Stack & Queue implementations
│   │   ├── 📁 algorithms/           # Advanced algorithmic techniques
│   │   │   ├── 📁 searching/        # Binary search variants
│   │   │   ├── 📁 sorting/          # Sorting algorithms
│   │   │   ├── 📁 dynamicprogramming/ # DP patterns and optimizations
│   │   │   ├── 📁 graphs/           # Graph algorithms (DFS, BFS, Shortest Path)
│   │   │   ├── 📁 trees/            # Tree traversal and operations
│   │   │   ├── 📁 backtracking/     # Backtracking patterns
│   │   │   ├── 📁 greedy/           # Greedy algorithms
│   │   │   └── 📁 advanced/         # Advanced techniques (Tries, Segment Trees)
│   │   ├── 📁 problems/             # LeetCode problems by difficulty
│   │   │   ├── 📁 easy/             # Easy problems (1-300)
│   │   │   ├── 📁 medium/           # Medium problems (301-600)
│   │   │   └── 📁 hard/             # Hard problems (601+)
│   │   └── 📁 utils/                # Helper classes (ListNode, TreeNode)
│   └── 📁 test/java/                # Comprehensive test suites
└── 📁 docs/                         # Detailed documentation
    ├── 📁 theory/                   # CS fundamentals and theory
    ├── 📁 patterns/                 # Algorithmic patterns guide
    └── 📁 practice/                 # Practice problems and solutions
```

---

## 🚀 Getting Started

### Prerequisites

```bash
# Required
☑️ Java 17 or higher
☑️ Maven 3.8 or higher
☑️ Git

# Recommended
☑️ IntelliJ IDEA / Eclipse
☑️ Basic understanding of Java and data structures
```

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/Katya1803/leetcode-techniques.git
cd leetcode-techniques

# 2. Build the project
mvn clean compile

# 3. Run tests
mvn test

# 4. Generate test coverage report
mvn jacoco:report
```

### IDE Setup

**IntelliJ IDEA:**
```bash
# Import as Maven project
File → Open → Select pom.xml → Open as Project
```

**Eclipse:**
```bash
# Import existing Maven project
File → Import → Maven → Existing Maven Projects
```

---

## 💡 How to Use This Project Effectively

### 🎯 **Quick Start Workflow**

```bash
# 1. Clone and setup
git clone https://github.com/Katya1803/leetcode-techniques.git
cd leetcode-techniques
mvn clean compile

# 2. Start with a pattern
# Read theory first
open docs/theory/patterns/common-patterns.md

# Then see implementation
open src/main/java/com/leetcode/problems/easy/TwoSum_1.java

# Run and understand tests
mvn test -Dtest=TwoSum_1_Test

# 3. Practice implementation
# Copy pattern and solve similar problems
```

### 📚 **Learning Strategies**

**🔍 Pattern-First Approach** (Recommended)
```bash
Step 1: docs/theory/patterns/ → Understand the pattern
Step 2: src/main/java/ → See implementation
Step 3: src/test/java/ → Verify with tests
Step 4: Practice on LeetCode → Apply knowledge
```

**📖 Theory-First Approach** (For CS Students)
```bash
Step 1: docs/theory/data-structures/ → Build foundation
Step 2: docs/theory/algorithms/ → Learn algorithms  
Step 3: src/main/java/ → See practical implementation
Step 4: Problems practice → Reinforce learning
```

**⚡ Problem-First Approach** (For Interview Prep)
```bash
Step 1: Try solving LeetCode problem
Step 2: Check src/main/java/problems/ → Compare solution
Step 3: Read docs/theory/ → Understand why it works
Step 4: Practice similar problems → Build confidence
```

### 🛠️ **Usage Scenarios**

**Before Technical Interview:**
```bash
# Week before interview
1. Review docs/theory/patterns/common-patterns.md
2. Practice 5-10 problems from src/main/java/problems/
3. Run tests: mvn test (ensure you understand edge cases)
4. Time yourself solving problems

# Day before interview  
1. Quick review of docs/theory/patterns/
2. Practice 2-3 easy problems for confidence
3. Review complexity analysis notes
```

**During Algorithm Course:**
```bash
# Each topic study session
1. Read theory: docs/theory/algorithms/[topic].md
2. Implement: Follow examples in src/main/java/
3. Test: mvn test -Dtest=*[Topic]*Test
4. Document: Write notes on complexity and use cases

# Before exams
1. Review all docs/theory/ materials
2. Run full test suite: mvn test
3. Practice coding without IDE
```

**For Skill Development:**
```bash
# Weekly routine
Monday: Data structures review (docs/theory/data-structures/)
Tuesday: Algorithm study (docs/theory/algorithms/)  
Wednesday: Pattern practice (docs/theory/patterns/)
Thursday: Implementation (src/main/java/)
Friday: Testing and validation (mvn test)
Weekend: Real problem solving on platforms
```

### 🧭 **Navigation Guide**

**Finding the Right Content:**

| I want to... | Go to... | Then... |
|--------------|----------|---------|
| **Learn a new pattern** | `docs/theory/patterns/` | Read theory → See examples → Practice |
| **Understand algorithm** | `docs/theory/algorithms/` | Study fundamentals → Check implementations |
| **Practice problems** | `src/main/java/problems/` | Code → Test → Compare approaches |
| **Quick reference** | `docs/theory/patterns/common-patterns.md` | Use as cheat sheet |
| **Test understanding** | `src/test/java/` | Run specific tests |

**Code Organization Logic:**
```bash
datastructures/ → Basic building blocks (Arrays, Lists, etc.)
algorithms/     → Advanced techniques (DP, Graph algorithms)  
problems/       → Real LeetCode problems with solutions
utils/          → Helper classes (TreeNode, ListNode)
```

### 🎲 **Study Methods**

**🎯 Focused Learning (1-2 hours sessions):**
1. Pick ONE pattern/algorithm
2. Read theory documentation thoroughly  
3. Understand 2-3 code examples
4. Implement 1 problem from scratch
5. Run tests and debug if needed

**🚀 Sprint Learning (30-minute sessions):**
1. Quick review of pattern documentation
2. Code one implementation
3. Run tests immediately
4. Note down key insights

**🔄 Review Sessions (15-minute sessions):**
1. Skim through docs/theory/patterns/
2. Check one implementation from src/main/java/
3. Mental walkthrough of algorithm steps
4. Quick complexity analysis review

---

## 📚 Learning Path

### 🎯 **Beginner Track (Week 1-4)**

**Week 1: Foundations**
- [ ] Arrays and Strings (`docs/theory/data-structures/arrays-strings.md`)
- [ ] Hash Maps (`docs/theory/data-structures/hash-maps.md`)
- [ ] Two Pointers Pattern (`src/main/java/com/leetcode/datastructures/arrays/`)

**Week 2: Basic Data Structures**
- [ ] Linked Lists (`docs/theory/data-structures/linked-lists.md`)
- [ ] Stacks and Queues (`docs/theory/data-structures/stacks-queues.md`)
- [ ] Fast-Slow Pointers Pattern

**Week 3: Searching and Sorting**
- [ ] Binary Search (`docs/theory/algorithms/binary-search.md`)
- [ ] Sorting Algorithms (`docs/theory/algorithms/sorting.md`)
- [ ] Modified Binary Search Pattern

**Week 4: Trees**
- [ ] Tree Traversal (`docs/theory/algorithms/tree-algorithms.md`)
- [ ] Binary Search Trees
- [ ] Tree DFS/BFS Patterns

### 🚀 **Intermediate Track (Week 5-8)**

**Week 5-6: Dynamic Programming**
- [ ] 1D DP (`docs/theory/algorithms/dynamic-programming.md`)
- [ ] 2D DP and Memoization
- [ ] Classic DP Patterns (Knapsack, LCS, etc.)

**Week 7-8: Graph Algorithms**
- [ ] Graph Traversal (`docs/theory/algorithms/graph-algorithms.md`)
- [ ] Topological Sort
- [ ] Union-Find Pattern

### 🏆 **Advanced Track (Week 9-12)**

**Week 9-10: Advanced Techniques**
- [ ] Backtracking (`docs/theory/algorithms/backtracking.md`)
- [ ] Greedy Algorithms (`docs/theory/algorithms/greedy.md`)
- [ ] Bit Manipulation

**Week 11-12: Expert Level**
- [ ] Advanced Data Structures (Tries, Segment Trees)
- [ ] Mathematical Algorithms
- [ ] System Design Considerations

---

## 🧪 Testing

### Test Structure

```bash
src/test/java/com/leetcode/
├── datastructures/          # Data structure tests
├── algorithms/              # Algorithm tests
└── problems/                # LeetCode problem tests
    ├── easy/
    ├── medium/
    └── hard/
```

### Testing Philosophy

- **Comprehensive Coverage**: Multiple test cases including edge cases
- **Performance Testing**: Time and space complexity verification
- **Parameterized Tests**: Multiple inputs with expected outputs
- **Error Handling**: Exception testing and boundary conditions

### Example Test

```java
@ParameterizedTest
@DisplayName("Two Sum - Hash Map Solution")
@CsvSource({
    "'[2,7,11,15]', 9, '[0,1]'",
    "'[3,2,4]', 6, '[1,2]'",
    "'[3,3]', 6, '[0,1]'"
})
void testTwoSumHashMap(String numsStr, int target, String expectedStr) {
    int[] nums = parseArray(numsStr);
    int[] expected = parseArray(expectedStr);
    
    int[] result = solution.twoSum(nums, target);
    
    assertEquals(target, nums[result[0]] + nums[result[1]]);
    assertTrue(Arrays.equals(result, expected) || 
               Arrays.equals(result, new int[]{expected[1], expected[0]}));
}
```

---

## 📖 Documentation

### 📚 Theory Documentation

| Topic | Location | Description |
|-------|----------|-------------|
| **Data Structures** | `docs/theory/data-structures/` | Core DS with implementation details |
| **Algorithms** | `docs/theory/algorithms/` | Algorithmic paradigms and techniques |
| **Patterns** | `docs/theory/patterns/` | Problem-solving patterns and templates |
| **Complexity Analysis** | `docs/theory/patterns/complexity-analysis.md` | Time/space complexity guide |

### 🎯 Problem Documentation

| Difficulty | Location | Coverage |
|------------|----------|----------|
| **Easy** | `docs/practice/easy/` | Fundamental patterns and basic algorithms |
| **Medium** | `docs/practice/medium/` | Intermediate techniques and optimizations |
| **Hard** | `docs/practice/hard/` | Advanced algorithms and complex patterns |

### 📋 Quick References

- **Pattern Recognition Guide**: `docs/patterns/common-patterns.md`
- **Complexity Cheat Sheet**: `docs/patterns/time-complexity-analysis.md`
- **Interview Tips**: `docs/patterns/interview-strategies.md`

---

## 🤝 Contributing

We welcome contributions from the community! Here's how you can help:

### 🎯 **Ways to Contribute**

- **🐛 Bug Reports**: Found an issue? Report it!
- **💡 Feature Requests**: Suggest new algorithms or improvements
- **📝 Documentation**: Improve explanations and examples
- **🧪 Tests**: Add more test cases and edge cases
- **💻 Implementation**: Add new algorithms or optimize existing ones

### 📋 **Contribution Guidelines**

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/amazing-algorithm`
3. **Follow coding standards**: Consistent formatting and documentation
4. **Add comprehensive tests**: Ensure your code is well-tested
5. **Update documentation**: Include theory and complexity analysis
6. **Submit a Pull Request**: Clear description of changes

### 🎨 **Code Style**

- **Java Code Style**: Follow Google Java Style Guide
- **Documentation**: Use JavaDoc for all public methods
- **Naming**: Clear, descriptive variable and method names
- **Testing**: JUnit 5 with comprehensive coverage

---

## 🎯 Effective Usage Guide

### 📚 **For Interview Preparation**

**Phase 1: Foundation Building (Week 1-2)**
```bash
# 1. Start with theory documents
docs/theory/data-structures/arrays-strings.md
docs/theory/data-structures/hash-maps.md

# 2. Practice basic patterns
src/main/java/com/leetcode/problems/easy/TwoSum_1.java
src/main/java/com/leetcode/datastructures/arrays/ArrayTechniques.java

# 3. Run and understand tests
mvn test -Dtest=TwoSum_1_Test
```

**Phase 2: Pattern Mastery (Week 3-4)**
```bash
# Focus on common patterns
docs/theory/patterns/common-patterns.md
docs/theory/algorithms/two-pointers.md
docs/theory/algorithms/sliding-window.md

# Practice implementation
src/main/java/com/leetcode/datastructures/arrays/TwoPointers.java
```

**Phase 3: Advanced Topics (Week 5-8)**
```bash
# Deep dive into algorithms
docs/theory/algorithms/dynamic-programming.md
docs/theory/algorithms/graph-algorithms.md

# Implement complex solutions
src/main/java/com/leetcode/algorithms/dynamicprogramming/
```

### 🎓 **For Academic Learning**

**Structured Curriculum Approach:**
```bash
# Semester Week 1-4: Data Structures
1. Read theory: docs/theory/data-structures/
2. Implement: src/main/java/com/leetcode/datastructures/
3. Test understanding: mvn test
4. Document learnings: Create personal notes

# Semester Week 5-8: Algorithms  
1. Study paradigms: docs/theory/algorithms/
2. Code solutions: src/main/java/com/leetcode/algorithms/
3. Analyze complexity: Use provided analysis
4. Compare approaches: Multiple implementations available
```

### 🏆 **For Competitive Programming**

**Contest Preparation Strategy:**
```bash
# 1. Master templates
docs/theory/patterns/common-patterns.md

# 2. Practice fast implementation
src/main/java/com/leetcode/algorithms/advanced/

# 3. Optimize solutions
# Focus on time/space complexity improvements

# 4. Build personal library
# Copy useful patterns for quick reference
```

### 🔄 **Daily Practice Routine**

**Morning Routine (30 minutes):**
1. **Read theory** (10 min): Pick one algorithm from `docs/theory/`
2. **Code practice** (15 min): Implement or review code in `src/main/`
3. **Test & verify** (5 min): Run tests to ensure understanding

**Evening Review (15 minutes):**
1. **Pattern recognition** (10 min): Review `docs/patterns/`
2. **Complexity analysis** (5 min): Understand time/space trade-offs

---

## 📈 Learning Resources

### 🎓 **Recommended Study Materials**

**Books:**
- *Introduction to Algorithms* by Cormen, Leiserson, Rivest, and Stein
- *Cracking the Coding Interview* by Gayle Laakmann McDowell
- *Elements of Programming Interviews in Java* by Aziz, Lee, and Prakash

**Online Platforms:**
- [LeetCode](https://leetcode.com/) - Practice problems
- [GeeksforGeeks](https://www.geeksforgeeks.org/) - Algorithm tutorials
- [Coursera Algorithms Specialization](https://www.coursera.org/specializations/algorithms) - Structured learning

**YouTube Channels:**
- Abdul Bari - Clear algorithm explanations
- Back to Back SWE - Interview-focused content
- Tushar Roy - Coding made simple

### 🏆 **Practice Strategy**

1. **Master Patterns**: Focus on understanding common patterns
2. **Progressive Difficulty**: Start easy, gradually increase complexity
3. **Time Management**: Practice with interview time constraints
4. **Code Review**: Analyze and optimize your solutions
5. **Mock Interviews**: Practice explaining your thought process

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 LeetCode Java Techniques

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 🌟 Acknowledgments

- **LeetCode Platform** - Inspiration and problem sets
- **Java Community** - Best practices and conventions
- **Open Source Contributors** - Ongoing improvements and feedback
- **CS Education Community** - Theoretical foundations and teaching methodologies

---

## 📞 Contact & Support

### 💬 **Get Help**

- **📧 Email**: [katyavu.work@gmail.com](mailto:katyavu.work@gmail.com)
- **💬 Discussions**: Use GitHub Discussions for questions
- **🐛 Issues**: Report bugs via GitHub Issues

### 🎯 **Quick Links**

- [🚀 Getting Started](#-getting-started)
- [📚 Documentation](#-documentation)
- [🧪 Examples](#-usage-examples)
- [🤝 Contributing](#-contributing)

---

<div align="center">

**⭐ Star this repository if it helped you! ⭐**

**🔥 Happy Coding and Best of Luck with Your Interviews! 🔥**

</div>