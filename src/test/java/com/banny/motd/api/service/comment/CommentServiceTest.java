package com.banny.motd.api.service.comment;

import com.banny.motd.api.service.alarm.producer.AlarmProducer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @MockBean
    private AlarmProducer alarmProducer;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

}