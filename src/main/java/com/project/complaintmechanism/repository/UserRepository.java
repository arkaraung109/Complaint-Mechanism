package com.project.complaintmechanism.repository;

import com.project.complaintmechanism.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    boolean existsByUsername(String username);

    User findByVerificationToken(String token);

    @Query(value = "select * from user where (name like :keyword% or username like :keyword% or email like :keyword% or phone like :keyword%) order by name",
            countQuery = "select * from user where (name like :keyword% or username like :keyword% or email like :keyword% or phone like :keyword%) order by name",
            nativeQuery = true)
    Page<User> findByPageWithKeyword(@Param("keyword") String keyword, Pageable paging);

}
