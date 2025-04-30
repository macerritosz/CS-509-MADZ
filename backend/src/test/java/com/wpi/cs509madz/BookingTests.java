package com.wpi.cs509madz;

import com.wpi.cs509madz.repository.DeltasRepository;
import com.wpi.cs509madz.repository.SouthwestsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@Import(DeltasRepository.class)
public class BookingTests {

    @Autowired
    private DeltasRepository deltas;

    @Autowired
    private SouthwestsRepository southwests;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setupDatabase() {

    }

    @Test
    void test() {

    }
}
