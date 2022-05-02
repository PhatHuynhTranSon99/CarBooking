package com.transonphat.carbooking.dao.invoice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles(profiles = {"test"})
@Transactional
public class InvoiceDAOTest {
    @Test
    public void getOneReturnCorrectResult() {

    }

    @Test
    public void getOneNotFound() {

    }

    @Test
    public void getAllReturnCorrectResult() {

    }

    @Test
    public void deleteSuccessfully() {

    }

    @Test
    public void deleteNotFound() {

    }

    @Test
    public void saveSuccessfully() {

    }
}
