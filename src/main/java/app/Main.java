package app;


import app.config.HibernateConfig;
import app.daos.CourseDao;
import app.daos.StudentDao;
import app.daos.TeacherDao;
import app.entities.Course;
import app.entities.CourseName;
import app.entities.Student;
import app.entities.Teacher;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
       EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

       CourseDao courseDao = new CourseDao(emf);
       TeacherDao teacherDao = new TeacherDao(emf);
       StudentDao studentDao = new StudentDao(emf);

        Teacher teacher = Teacher.builder()
                        .name("callie")
                        .zoom("a3")
                        .email("cal@gmail.com")
                        .build();

        teacherDao.create(teacher);

        List<Teacher> teachers = teacherDao.getAll();
        //System.out.println(teachers);

        Teacher teacher1 = teacherDao.getById(1);
        //System.out.println(teacher1);

        teacher1.setName("soren");
        teacherDao.update(teacher1);
        //System.out.println(teacher1);

        Teacher teacher2 = Teacher.builder()
                .name("anton")
                .zoom("a4")
                .email("ant@gmail.com")
                .build();

        teacherDao.create(teacher2);

        teacherDao.delete(2);


        Course course = Course.builder()
                .courseName(CourseName.HISTORY)
                .description("language")
                .startDate(LocalDate.of(2025, 7,1))
                .endDate(LocalDate.of(2025,11,5))
                .teacher(teacher)
                .build();

        courseDao.create(course);

        List<Course> courses = courseDao.getAll();
        //System.out.println(courses);

        Course course1 = courseDao.getById(1);
        //System.out.println(course1);

        course1.setCourseName(CourseName.MATH);
        courseDao.update(course1);
        //System.out.println(course1);

        // make sure to remove .teacher() keys
        Course course2 = Course.builder()
                .courseName(CourseName.ENGLISH)
                .description("language")
                .startDate(LocalDate.of(2025, 10,6))
                .endDate(LocalDate.of(2025,12,11))
                .build();

        courseDao.create(course2);

        courseDao.delete(course2.getId());


        Student student = Student.builder()
                .name("Peter")
                .email("pet@gmail.com")
                .createdAt(LocalDateTime.now())
                .course(course)
                .build();

        studentDao.create(student);

        List<Student> students = studentDao.getAll();
        //System.out.println(students);

        Student student1 = studentDao.getById(1);
        //System.out.println(student1);

        student1.setName("Ida");
        studentDao.update(student1);
        //System.out.println(student1);

        // make sure to remove .course() keys
        Student student2 = Student.builder()
                .name("annie")
                .email("ani@gmail.com")
                .createdAt(LocalDateTime.now())
                .build();

        studentDao.create(student2);

        studentDao.delete(student2.getId());


       emf.close();
    }
}