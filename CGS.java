package cgs;

import java.util.ArrayList;

import java.util.Scanner;

// Course class have fields for the course name, course ID, maximum number of students, current number of students, a list of student names, the instructor, section number, and location.

class Course {

private String courseName;

private String courseID;

private int maxStudents;

private int currentStudents;

private ArrayList<String> studentNames;

private String instructor;

private int sectionNumber;

private String location;

// constructor

public Course(String courseName, String courseID, int maxStudents, String instructor, int sectionNumber, String location) {

this.courseName = courseName;

this.courseID = courseID;

this.maxStudents = maxStudents;

this.currentStudents = 0;

this.studentNames = new ArrayList<>();

this.instructor = instructor;

this.sectionNumber = sectionNumber;

this.location = location;

}

// getter methods

public String getCourseName() {

return courseName;

}

public String getCourseID() {

return courseID;

}

public int getMaxStudents() {

return maxStudents;

}

public int getCurrentStudents() {

return currentStudents;

}

public ArrayList<String> getStudentNames() {

return studentNames;

}

public String getInstructor() {

return instructor;

}

public int getSectionNumber() {

return sectionNumber;

}

public String getLocation() {

return location;

}

// setter methods

public void setCourseName(String courseName) {

this.courseName = courseName;

}

public void setCourseID(String courseID) {

this.courseID = courseID;

}

public void setMaxStudents(int maxStudents) {

this.maxStudents = maxStudents;

}

public void setCurrentStudents(int currentStudents) {

this.currentStudents = currentStudents;

}

public void setStudentNames(ArrayList<String> studentNames) {

this.studentNames = studentNames;

}

public void setInstructor(String instructor) {

this.instructor = instructor;

}

public void setSectionNumber(int sectionNumber) {

this.sectionNumber = sectionNumber;

}

public void setLocation(String location) {

this.location = location;

}

// boolean method to check if a course if full

public boolean isFull() {

return currentStudents == maxStudents;

}

}

// User class have fields for the username, password, first name, and last name.

abstract class User {

private String username;

private String password;

private String firstName;

private String lastName;

// constructor and getter/setter methods

public User(String username, String password, String firstName, String lastName) {

this.username = username;

this.password = password;

this.firstName = firstName;

this.lastName = lastName;

}

public String getUsername() {

return username;

}

public String getPassword() {

return password;

}

public String getFirstName() {

return firstName;

}

public String getLastName() {

return lastName;

}

public void setUsername(String username) {

this.username = username;

}

public void setPassword(String password) {

this.password = password;

}

public void setFirstName(String firstName) {

this.firstName = firstName;

}

public void setLastName(String lastName) {

this.lastName = lastName;

}

}

// Admin class inherits from the User class, and implement the methods for the tasks listed in the requirements (create a new course, delete a course, etc.)

class Admin extends User {

private ArrayList<Course> courses = new ArrayList<Course>();

// constructor that calls the super class constructor

public Admin(String username, String password, String firstName, String lastName) {

super(username, password, firstName, lastName);

} //how to set id and pw?

public void run() {

while (true) {

System.out.println("Courses Management");

System.out.println("1. Create a new course");

System.out.println("2. Delete a course");

System.out.println("3. Edit a course");

System.out.println("4. Display information for a given course");

System.out.println("5. Register a student");

System.out.println("6. Exit");

Scanner input = new Scanner(System.in);

int choice = input.nextInt();

input.nextLine();

switch (choice) {

case 1:

createCourse();

break;

case 2:

deleteCourse();

break;

case 3:

editCourse();

break;

case 4:

displayCourseInfo();

break;

case 5:

registerStudent();

break;

case 6:

System.exit(0);

break;

default:

System.out.println("Invalid option. Please try again.");

break;

}

}

}

// methods for creating, deleting, editing and displaying courses

public void createCourse() {

Scanner input = new Scanner(System.in);

System.out.println("Enter the course name: ");

String name = input.nextLine();

System.out.println("Enter the course ID: ");

String id = input.nextLine();

System.out.println("Enter the maximum number of students: ");

int maxStudents = input.nextInt();

System.out.println("Enter the instructor name: ");

String instructor = input.nextLine();

System.out.println("Enter the section number: ");

int sectionNumber = input.nextInt();

System.out.println("Enter the location: ");

String location = input.nextLine();

Course course = new Course(name,id,maxStudents,instructor,sectionNumber,location);

courses.add(course);

System.out.println("Course created successfully.");

}

public void deleteCourse() {

System.out.println("Enter the ID of the course to delete: ");

Scanner input = new Scanner(System.in);

String id = input.nextLine();

for (int i = 0; i < courses.size(); i++) {

if (courses.get(i).getCourseID() == id) {

courses.remove(i);

System.out.println("Course deleted successfully.");

return;

}}

System.out.println("Course not found.");

}

public void editCourse() {

System.out.println("Enter the ID of the course to edit: ");

Scanner input = new Scanner(System.in);

String id = input.nextLine();

for (Course c : courses) {

if (c.getCourseID().equals(id)) {

System.out.println("Edit: ");

System.out.println("1. Maximum number of students");

System.out.println("2. Instructor");

System.out.println("3. Section number");

System.out.println("4. Location");

System.out.println("5. Exit");

int choice = input.nextInt();

switch (choice) {

case 1:

System.out.println("Enter the new number of maximum students.");

int newMax = input.nextInt();

c.setMaxStudents(newMax);

return;

case 2:

System.out.println("Enter new instructor");

String newInstructor = input.nextLine();

c.setInstructor(newInstructor);

return;

case 3:

System.out.println("Enter the new section number");

int newSection = input.nextInt();

c.setSectionNumber(newSection);

case 4:

System.out.println("Enter the new location");

String newLocation = input.nextLine();

c.setLocation(newLocation);

case 5:

System.exit(0);

break;

default:

System.out.println("Invalid option. Please try again.");

break;}

}}}

public void displayCourseInfo() {

System.out.println("Enter the ID of the course to display: ");

Scanner input = new Scanner(System.in);

String id = input.nextLine();

for (Course c : courses) {

if (c.getCourseID().equals(id)) {

System.out.println("Course Name: " + c.getCourseName());

System.out.println("ID: " + c.getCourseID());

System.out.println("Maximum number of students: " + c.getMaxStudents());

System.out.println("Number of students enrolled: " + c.getCurrentStudents());

return;

}

}

System.out.println("Course not found.");

}

public void registerStudent() {

}

}

class Student extends User {

private ArrayList<Course> courses = new ArrayList<Course>();

public Student(String username, String password, String firstName, String lastName) {

super(username, password, firstName, lastName);

} //how to set id and pw?

public void viewAllCourses() {

for (Course c : courses) {

System.out.println("ID: " + c.getCourseID() + " Name: " + c.getCourseName());}

}

}