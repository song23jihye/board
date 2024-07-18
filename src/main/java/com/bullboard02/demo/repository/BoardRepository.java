package com.bullboard02.demo.repository;

import com.bullboard02.demo.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    Board getOne(Long aLong);




}
