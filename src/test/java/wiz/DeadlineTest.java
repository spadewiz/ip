package wiz;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toFileString_newDeadline_formattedCorrectly() {
        LocalDateTime by = LocalDateTime.of(2026, 9, 10, 23, 59);
        Deadline deadline = new Deadline("submit assignment", by);
        assertEquals("D | 0 | submit assignment | 2026-09-10 2359", deadline.toFileString());
    }

    @Test
    public void toString_doneDeadline_formattedCorrectly() {
        LocalDateTime by = LocalDateTime.of(2026, 9, 10, 23, 59);
        Deadline deadline = new Deadline("submit assignment", by);
        deadline.markAsDone();
        assertEquals("[D][X] submit assignment (by: Sep 10 2026 2359)", deadline.toString());
    }
}
