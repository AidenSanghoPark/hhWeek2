package com.Week2.domain.repository;

import com.Week2.domain.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Application 엔티티 관련 메서드
    public Optional<Application> findApplicationById(Long id) {
        return Optional.ofNullable(entityManager.find(Application.class, id));
    }

    public List<Application> findAllApplications() {
        return entityManager.createQuery("SELECT a FROM Application a", Application.class).getResultList();
    }

    @Transactional
    public void save(Application application) {

        //  entityManager.persist(application);
        entityManager.merge(application); // merge 사용
    }
}
