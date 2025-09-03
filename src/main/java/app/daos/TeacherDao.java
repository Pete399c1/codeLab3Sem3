package app.daos;

import app.entities.Teacher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TeacherDao implements IDAO<Teacher, Integer> {
    private EntityManagerFactory emf;

    @Override
    public Teacher create(Teacher teacher) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        }
        return teacher;
    }

    @Override
    public List<Teacher> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            TypedQuery<Teacher> query = em.createQuery("select t from Teacher t", Teacher.class);
            return query.getResultList();
        }
    }

    @Override
    public Teacher getById(Integer id) {
        try (EntityManager em = emf.createEntityManager()){
            return em.find(Teacher.class, id);
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.merge(teacher);
            em.getTransaction().commit();
        }
        return teacher;
    }

    @Override
    public boolean delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Teacher teacher = em.find(Teacher.class, id);
            if (teacher != null) {
                em.getTransaction().begin();
                em.remove(teacher);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch (PersistenceException e) {
            return false;
        }
    }
}
