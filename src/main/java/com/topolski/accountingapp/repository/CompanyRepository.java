package com.topolski.accountingapp.repository;

import com.topolski.accountingapp.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Override
    @Query("from Company c left join fetch c.users users")
    List<Company> findAll();
}
