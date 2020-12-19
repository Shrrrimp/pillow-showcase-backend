package com.pillows.springbootpillowsapi.repo;

import com.pillows.springbootpillowsapi.domain.Pillow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PillowRepo extends JpaRepository<Pillow, Integer>{
}
