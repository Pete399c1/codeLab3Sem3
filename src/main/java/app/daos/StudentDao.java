package app.daos;

import app.entities.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class StudentDao implements IDAO<Student, Integer> {
    private EntityManagerFactory emf;

    public StudentDao(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Student create(Student student) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        }
        return student;
    }

    @Override
    public List<Student> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Student> query = em.createQuery("select s from Student s", Student.class);
            return query.getResultList();
        }
    }

    @Override
    public Student getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Student.class,id);
        }
    }

    @Override
    public Student update(Student student) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(student);
            em.getTransaction().commit();
        }
        return student;
    }

    @Override
    public boolean delete(Integer id) {
        try(EntityManager em = emf.createEntityManager()){
            Student student = em.find(Student.class,id);
            if(student != null){
                em.getTransaction().begin();
                em.remove(student);
                em.getTransaction().commit();
                return true;
            }else{
                return false;
            }
        }catch (PersistenceException e){
            return false;
        }
    }

    // should lay here because it returns Lists of the student objects its just based on witch id from course
    public List<Student> getStudentsByCourseId(int courseId){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Student> query = em.createQuery("select s from Student s where s.course.id = :courseId", Student.class);
            query.setParameter("courseId",courseId);
            return query.getResultList();

        }
    }

    // student knows course by relation But it does not know teacher only course does that
    public List<Student> getStudentsByCourseIdAndTeacherId(int courseId, int teacherId){
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Student> query = em.createQuery("select s from Student s where s.course.id = :courseId and s.course.teacher.id = :teacherId", Student.class);
            query.setParameter("courseId",courseId);
            query.setParameter("teacherId", teacherId);
            return query.getResultList();

        }
    }

}
