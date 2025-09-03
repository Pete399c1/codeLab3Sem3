package app.daos;

import app.entities.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CourseDao implements IDAO<Course, Integer>{

    private EntityManagerFactory emf;

    public CourseDao(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Course create(Course course) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);
            return query.getResultList();
        }
    }

    @Override
    public Course getById(Integer id) {
        try(EntityManager em = emf.createEntityManager()) {
            return em.find(Course.class, id);
        }
    }

    @Override
    public Course update(Course course) {
        try(EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.merge(course);
            em.getTransaction().commit();
        }
        return course;
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            Course course = em.find(Course.class, id);
            if(course != null){
                em.getTransaction().begin();
                em.remove(course);
                em.getTransaction().commit();
                return true;
            }else{
                return false;
            }
        }catch (PersistenceException e){
            return false;
        }
    }
}
