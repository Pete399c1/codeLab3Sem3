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
                em.remove(student);
                return true;
            }else{
                return false;
            }
        }catch (PersistenceException e){
            return false;
        }
    }
}
