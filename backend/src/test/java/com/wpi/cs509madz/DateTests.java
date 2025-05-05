package com.wpi.cs509madz;

import com.wpi.cs509madz.service.utils.DateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DateTests {
    final DateTime dateTime1 = new DateTime("2023-01-03 16:14:00");
    final DateTime dateTime2 = new DateTime("2023-01-03 18:55:00");
    final DateTime dateTime3 = new DateTime("2023-01-04 18:55:00");
    final DateTime dateTime4 = new DateTime("2023-01-04 19:14:00");

    @Test
    void testDateTimeStringToClass() {
        assertEquals("2023-01-03 16:14:00", dateTime1.toString());
        assertEquals("2023-01-03 18:55:00", dateTime2.toString());
    }

    @Test
    void testDateTimeIsBefore() {
        assertTrue(dateTime1.isBefore(dateTime2));
        assertFalse(dateTime2.isBefore(dateTime1));

        assertTrue(dateTime2.isBefore(dateTime3));
    }

    @Test
    void testDateTimeDifference() {
        assertEquals(161, dateTime1.getDifference(dateTime2));
        assertEquals(161, dateTime2.getDifference(dateTime1));
        assertEquals(19, dateTime3.getDifference(dateTime4));
    }
}
