package cgs;

import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

//how to set ID/PW 
//createAccount();
//serialization 

public class CGS {
    public static void main(String[] args) {
        //initialize the users list in the constructor for UserManager
        UserManager userManager = new UserManager(new ArrayList<User>());

        Scanner sc = new Scanner(System.in);

        System.out.print("Is it the first time running the program? (y/n): ");
        String firstTime = sc.nextLine();

        if (firstTime.equalsIgnoreCase("y")) {
            File csvFile = new File("MyUniversityCourses.csv");
            if (csvFile.exists() && !csvFile.isDirectory()) {
                Data data = new Data();
                data.storeDataFromCSV(csvFile);
                data.storeDataToSDR();
            } else {
                Data data = new Data();
                data.readDataFromSDR();
            }
        }

        while (true) {
            System.out.println("Are you a student or an admin (Enter 'S' for student and 'A' for admin) or 'C' to create a new account:");
            String userType = sc.nextLine();
            if (userType.equalsIgnoreCase("C")) {
                // createAccount();
                continue;
            }
            
            if (userType.equalsIgnoreCase("S")) {
            System.out.println("Enter your student ID:");
            String studentId = sc.nextLine();
            System.out.println("Enter your password:");
            String studentPassword = sc.nextLine();
            
            User student = userManager.verifyUser(studentId, studentPassword);
            
            if (student == null) {
                System.out.println("Invalid credentials. Access denied.");
            } 
            else if (student instanceof Student) {
                System.out.println("Access granted! Welcome, " + student.getFirstName() + " " + student.getLastName());
                // Perform operations for Student
            }
        } 
            else if (userType.equalsIgnoreCase("A")) {
                System.out.println("Enter your admin ID:");
                String adminId = sc.nextLine();
                System.out.println("Enter your password:");
                String adminPassword = sc.nextLine();
        
                User admin = userManager.verifyUser(adminId, adminPassword);
            
                if (admin == null) {
                    System.out.println("Invalid credentials. Access denied.");
                } 
                else if (admin instanceof Admin) {
                    System.out.println("Access granted! Welcome, Admin");
                        // Perform operations for Admin
                    }
            } 
            else {
                System.out.println("Invalid input.");
                continue;
            }
        }
    }
}

class UserManager {
    private List<User> users;

    public UserManager(List<User> users) {
        this.users = users;
    }

    public User verifyUser(String enteredUsername, String enteredPassword) {
        for (User user : users) {
            if (user.verifyCredentials(enteredUsername, enteredPassword)) {
                return user;
            }
        }
        return null;
    }
}

class Data implements Serializable {
    private static final long serialVersionUID = 1;
    private static final String COURSE_FILE = "MyUniversityCourses.csv";
    private static final String SERIALIZED_FILE = "MyUniversityCourses.sdr";
    private ArrayList<Course> courses; 
    private ArrayList<Student> students;

    public Data() {
        this.courses = new ArrayList<>();
        this.students = new ArrayList<>();
    }
    
    public void storeDataFromCSV(File csvfile) {
        List<Course> courses = new ArrayList<>();
        try (Scanner scanner = new Scanner(csvfile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(",");
                courses.add(new Course(
                    values[0], 
                    values[1], 
                    Integer.parseInt(values[2]), 
                    values[4], 
                    Integer.parseInt(values[5]), 
                    values[6]
                ));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
        this.courses = (ArrayList<Course>) courses;
    }

    public void storeDataToSDR() {
        try (FileOutputStream fileOut = new FileOutputStream("data.sdr");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(this);
        } catch (Exception e) {
            System.out.println("Error storing data to data.sdr: " + e.getMessage());
        }
    }

    public void readDataFromSDR() {
        try (FileInputStream fileIn = new FileInputStream("data.sdr");
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            Data data = (Data) in.readObject();
            this.students = data.students;
            this.courses = data.courses;
        } catch (Exception e) {
            System.out.println("Error reading data from data.sdr: " + e.getMessage());
        }
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void addStudent(String studentName) {
        students.add(studentName);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }
}

// Course class have fields for the course name, course ID, maximum number of students, current number of students, a list of student names, the instructor, section number, and location.`
class Course implements Serializable {

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
public String getCourseName() {return courseName;}
public String getCourseID() {return courseID;}
public int getMaxStudents() {return maxStudents;}
public int getCurrentStudents() {return currentStudents;}
public ArrayList<String> getStudentNames() {return studentNames;}
public String getInstructor() {return instructor;}
public int getSectionNumber() {return sectionNumber;}
public String getLocation() {return location;}

// setter methods
public void setCourseName(String courseName) {this.courseName = courseName;}
public void setCourseID(String courseID) {this.courseID = courseID;}
public void setMaxStudents(int maxStudents) {this.maxStudents = maxStudents;}
public void setCurrentStudents(int currentStudents) {this.currentStudents = currentStudents;}
public void setStudentNames(ArrayList<String> studentNames) {this.studentNames = studentNames;}
public void setInstructor(String instructor) {this.instructor = instructor;}
public void setSectionNumber(int sectionNumber) {this.sectionNumber = sectionNumber;}
public void setLocation(String location) {this.location = location;}

// boolean method to check if a course if full
public boolean isFull() {return currentStudents == maxStudents;}

// other class properties and methods 


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

public String getUsername() {return username;}
public String getPassword() {return password;}
public String getFirstName() {return firstName;}
public String getLastName() {return lastName;}

public void setUsername(String username) {this.username = username;}
public void setPassword(String password) {this.password = password;}
public void setFirstName(String firstName) {this.firstName = firstName;}
public void setLastName(String lastName) {this.lastName = lastName;}

public boolean verifyCredentials(String enteredUsername, String enteredPassword) {
    return enteredUsername.equals(this.username) && enteredPassword.equals(this.password);
}

private static void createAccount() {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter first name: ");
    String firstName = input.nextLine();
    System.out.println("Enter last name: ");
    String lastName = input.nextLine();
    System.out.println("Enter ID: ");
    String id = input.nextLine();
    System.out.println("Enter Password: ");
    String password = input.nextLine();
    User newUser = new Student(id, password, firstName, lastName);
    System.out.println("Account created successfully.");
    Data data = new Data();
    data.addstudents(newUser);
}
}

// Interface for admin class that has the methods' signatures that will used by the Admin
interface Adminstrator {
    void createCourse();
    void deleteCourse();
    void editCourse();
    void displayCourseInfo();
    void registerStudent();
}

// Interface for student class that has the methods' signatures that will used by the student
interface StudentI {
    void viewAllCourses();
    void viewNotFullCourse();
    void registerCourse();
    void withdrawCourse();
    void viewStudents(); //View all courses that the current student is being registered in

}

// Admin class inherits from the User class, and implement the methods for the tasks listed in the requirements (create a new course, delete a course, etc.)
class Admin extends User implements Adminstrator, Serializable {
private ArrayList<Course> courses = new ArrayList<Course>();
// constructor that calls the super class constructor
public Admin(String Admin, String Admin001, String Jae, String Lee) {
    super(Admin, Admin001, Jae, Lee);
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
input.close();

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
    input.close();


    Course course = new Course(name,id,maxStudents,instructor,sectionNumber,location);
    courses.add(course);
    System.out.println("Course created successfully.");
    }

    public void deleteCourse() {
    System.out.println("Enter the ID of the course to delete: ");
    Scanner input = new Scanner(System.in);
    String id = input.nextLine();
    input.close();
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
    input.close();

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
    Scanner input = new Scanner(System.in); //DO I HAVE TO CREATE SCANNER EVERY TIME??
    String id = input.nextLine();
    input.close();


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

class Student extends User implements StudentI, Serializable {
    private ArrayList<Course> courses = new ArrayList<Course>();
    public Student(String username, String password, String firstName, String lastName) {
        super(username, password, firstName, lastName);
    } //how to set id and pw?

    public void run() {
        while (true) {
        System.out.println("Courses Management");
        System.out.println("1. View all courses");
        System.out.println("2. View all courses that are not full");
        System.out.println("3. Register on a course");
        System.out.println("4. Withdraw from a course");
        System.out.println("5. View all courses that the current student is being registered in");
        System.out.println("6. Exit");
        
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        input.close();

        
        switch (choice) {
            case 1:
                viewAllCourses();
                break;
            case 2:
                viewNotFullCourse();
                break;
            case 3:
                registerCourse();
                break;
            case 4:
                withdrawCourse();
                break;
            case 5:
                viewStudents();
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

public void viewAllCourses() {
    for (Course c : courses) {
        System.out.println("ID: " + c.getCourseID() + " Name: " + c.getCourseName());}
}

public void viewNotFullCourse() {
    for (Course c: courses) {
        if(!c.isFull()) {
            System.out.println(c.getCourseName() + " " + c.getCourseID());
        }
    }
}

public void registerCourse() {
    // Get course info from student
    System.out.println("Enter the course name you want to register: ");
    Scanner input = new Scanner(System.in);
    String courseName = input.nextLine();
    System.out.println("Enter the section number: ");
    int section = input.nextInt();
    System.out.println("Enter your first name: ");
    String firstName = input.nextLine();
    System.out.println("Enter your last name: ");
    String lastName = input.nextLine();
    input.close();


    // Find the course in the ArrayList
    Course selectedCourse = null;
    for (Course c : courses) {
        if (c.getCourseName().equals(courseName) && c.getSectionNumber() == section) {
            selectedCourse = c;
            break;
        }
    }
    // Check if course was found and if it's not full
    if (selectedCourse != null && !selectedCourse.isFull()) {
        // Register the student
        selectedCourse.addStudent(firstName + " " + lastName);
        System.out.println("Successfully registered for " + selectedCourse.getCourseName() + " " + selectedCourse.getSectionNumber());
    } else {
        System.out.println("Could not register for course. It may be full or does not exist.");
    }
}

public void withdrawCourse(){
    Scanner input = new Scanner(System.in);
    System.out.println("Enter the course ID you want to withdraw: ");
    String selectedCourseID = input.nextLine();
    System.out.println("Enter your first name: ");
    String firstName = input.nextLine();
    System.out.println("Enter your last name: ");
    String lastName = input.nextLine();

    Course selectCourse = null;
    for (Course c : courses) {
        if (selectedCourseID != null) {
            selec
        }
    }
}

@Override
public void viewStudents() {
    // TODO Auto-generated method stub
    
}
}


