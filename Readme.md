#  Java Core & Architecture Training

> **"Write Once, Run Anywhere"** – A deep dive into the fundamentals of Java, JVM internals, and system architecture.

##  Overview
This repository documents my training and exploration into Core Java and the internal workings of the Java Virtual Machine (JVM). It covers practical implementations of data structures (Arrays, Strings) and theoretical deep dives into memory management, class loading, and execution flows.

The goal is to move beyond syntax and understand *how* Java executes code under the hood, preparing for high-performance backend development.

---

##  Modules

### 1. Methods & Logic
* **Method Anatomy:** Declaration, parameters, and return types.
* **Scope & Lifetime:** Understanding local variables and stack frames.
* **Recursion:** solving problems via self-referential logic.
* **Pass-by-Value:** How primitives and object references behave when passed to methods.

### 2. Arrays & Memory
* **Declaration & Initialization:** 1D and Multi-dimensional arrays.
* **Memory Layout:** How array objects are stored in the Heap vs. references in the Stack.
* **Traversal:** Enhanced `for` loops and index-based iteration.
* **Common Algorithms:** Sorting, searching, and in-place manipulation.

### 3. Strings & Immutability
* **String Pool:** Understanding the Heap optimization for string literals.
* **Immutability:** Why Strings cannot be changed and the security/performance implications.
* **`StringBuilder` vs `StringBuffer`:** Mutable alternatives for high-performance concatenation.
* **Memory Management:** Literal vs. `new String()` object creation.

---

##  JVM Architecture & Internals
*A detailed study of the Java Runtime Environment.*

### 🛠 Class Loaders
* **Delegation Model:** Bootstrap → Platform → Application ClassLoaders.
* **Functions:** Loading (`.class` to memory), Linking (Verification), and Initialization.
* **Dynamic Loading:** How the JVM loads classes "lazily" on demand.

###  Runtime Data Areas (Memory)
| Area | Purpose | Shared? |
| :--- | :--- | :--- |
| **Method Area** | Stores class structures, static variables, and constants (Metaspace). | ✅ Yes |
| **Heap Area** | The "Object Factory" – stores all Objects and Arrays. | ✅ Yes |
| **Stack Area** | Stores method calls (Frames) and local variables. | ❌ No |
| **PC Register** | Tracks the current instruction address. | ❌ No |
| **Native Stack** | Handles C/C++ native method calls. | ❌ No |

###  Execution Engine
* **Interpreter:** Reads bytecode line-by-line.
* **JIT Compiler:** Optimizes "hot" code into native machine code for high performance.
* **Garbage Collection:** Automatic memory management (Mark-and-Sweep).

---

##  Key Learnings & Notes
* **Static vs. Instance:** `static` variables live in the Class Object (Heap) and are global; instance variables live in specific objects.
* **Bytecode:** The intermediate instruction set (`javap` output) that makes Java platform-independent.
* **Concurrency:** Introduction to Thread Pools (`ExecutorService`) and Asynchronous processing.

---

##  Author

**Amitabh Singh**
**Last Updated**
* 5th Feb 2026
---