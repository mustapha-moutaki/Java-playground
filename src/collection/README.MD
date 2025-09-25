# Java Data Structure Programming Exercises

## Exercise 1: Library Book Management
You are asked to create a library management system using **Java ArrayList**. The system should have two classes:

**1. Book Class**
**Attributes:**
* `bookId` (int)
* `title` (String)
* `author` (String)
* `pages` (int)

**2. Library Class**
**Methods to implement:**
1. Add Book
2. Display All Books
3. Show Total Books
4. Get Books with More than 200 Pages
5. Update Book by Title
6. Remove Book by ID
7. Search Book by Author

---

## Exercise 2: Employee Management System
You are asked to create an employee management system using **Java LinkedList**. The system should have two classes:

**1. Employee Class**
**Attributes:**
* `empId` (int)
* `name` (String)
* `department` (String)
* `salary` (double)

**2. Company Class**
**Attribute:**
* `employees`

**Methods to implement:**
1. Add Employee
2. Display All Employees
3. Show Total Employees
4. Get Employees by Department
5. Remove Employee by ID
6. Update Employee Salary
7. Get Employees with Salary Above 5000

---

## Exercise 3: Product Inventory System
You are asked to create a product inventory system using **Java HashMap**. The system should have two classes:

**1. Product Class**
**Attributes:**
* `productId` (int)
* `name` (String)
* `price` (double)
* `quantity` (int)

**2. Inventory Class**
**Attribute:**
* `products` (HashMap)

**Methods to implement:**
1. Add Product
2. Display All Products
3. Show Total Products
4. Update Product Price
5. Remove Product by ID
6. Search Product by Name
7. Get Products with Low Stock (quantity < 10)

---

## Exercise 4: Customer Order Queue
You are asked to create a customer order system using **Java Queue (LinkedList)**. The system should have two classes:

**1. Order Class**
**Attributes:**
* `orderId` (int)
* `customerName` (String)
* `items` (String)
* `totalAmount` (double)

**2. Restaurant Class**
**Attribute:**
* `orderQueue`

**Methods to implement:**
1. Add Order to Queue
2. Process Next Order (remove from front)
3. Display All Pending Orders
4. Show Total Orders in Queue
5. Search Order by Customer Name
6. Get Orders Above Certain Amount

---

## Exercise 5: Contact Management System
You are asked to create a contact management system using **Java TreeSet**. The system should have two classes:

**1. Contact Class**
**Attributes:**
* `phoneNumber` (String)
* `name` (String)
* `email` (String)

**2. PhoneBook Class**
**Attribute:**
* `contacts`

**Methods to implement:**
1. Add Contact
2. Display All Contacts (sorted by phone number)
3. Show Total Contacts
4. Remove Contact by Phone Number
5. Search Contact by Name
6. Update Contact Email
7. Get Contacts Starting with Specific Area Code

---

## Exercise 6: Movie Rating System
You are asked to create a movie rating system using **Java Stack**. The system should have two classes:

**1. Movie Class**
**Attributes:**
* `movieId` (int)
* `title` (String)
* `genre` (String)
* `rating` (double)

**2. MovieTheater Class**
**Attribute:**
* `recentlyViewed` (Stack)

**Methods to implement:**
1. Add Movie to Recently Viewed
2. Get Last Viewed Movie (peek)
3. Remove Last Viewed Movie (pop)
4. Display All Recently Viewed Movies
5. Show Total Movies in Stack
6. Clear All Recently Viewed
7. Check if Stack is Empty

---

## Exercise 7: Hospital Patient Management
You are asked to create a patient management system using **Java PriorityQueue**. The system should have two classes:

**1. Patient Class**
**Attributes:**
* `patientId` (int)
* `name` (String)
* `age` (int)
* `severity` (int) // 1 = critical, 5 = minor

**2. Hospital Class**
**Attribute:**
* `patientQueue`

**Methods to implement:**
1. Add Patient
2. Treat Next Patient (highest priority)
3. Display All Waiting Patients
4. Show Total Patients
5. Remove Patient by ID
6. Search Patient by Name
7. Get Patients by Severity Level

---

## Exercise 8: Car Dealership System
You are asked to create a car dealership system using **Java HashSet**. The system should have two classes:

**1. Car Class**
**Attributes:**
* `vinNumber` (String)
* `brand` (String)
* `model` (String)
* `year` (int)
* `price` (double)

**2. Dealership Class**
**Attribute:**
* `cars`

**Methods to implement:**
1. Add Car
2. Display All Cars
3. Show Total Cars
4. Remove Car by VIN Number
5. Search Cars by Brand
6. Get Cars by Year Range
7. Get Cars Under Certain Price

---

## Exercise 9: Course Registration System
You are asked to create a course registration system using **Java ArrayList and HashMap**. The system should have three classes:

**1. Course Class**
**Attributes:**
* `courseId` (String)
* `courseName` (String)
* `credits` (int)
* `maxStudents` (int)

**2. Student Class**
**Attributes:**
* `studentId` (int)
* `name` (String)
* `registeredCourses` (ArrayList)

**3. University Class**
**Attributes:**
* `courses` (HashMap)
* `students` (ArrayList)

**Methods to implement:**
1. Add Course
2. Add Student
3. Register Student for Course
4. Display All Courses
5. Display Students in a Course
6. Remove Student from Course
7. Get Available Courses (not full)

---

## Exercise 10: Social Media Post System
You are asked to create a social media post system using **Java LinkedList and TreeMap**. The system should have two classes:

**1. Post Class**
**Attributes:**
* `postId` (int)
* `username` (String)
* `content` (String)
* `likes` (int)
* `timestamp` (String)

**2. SocialMedia Class**
**Attributes:**
* `posts` (LinkedList)
* `userPosts` (TreeMap) // username -> list of posts

**Methods to implement:**
1. Create New Post
2. Display All Posts (chronological order)
3. Display Posts by User
4. Like a Post
5. Remove Post by ID
6. Search Posts by Content
7. Get Most Popular Posts (highest likes)
8. Show Total Posts by Each User