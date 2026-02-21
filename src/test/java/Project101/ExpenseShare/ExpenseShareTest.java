package Project101.ExpenseShare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpenseShareTest {
    ExpenseService expenseService ;
    @BeforeEach
    void setUp(){
        expenseService = new ExpenseService();
    }
    @Test
    void testAddExpenseEqualSplit(){
        int payerId = 1;
        expenseService.addExpense(payerId,12.0,"Add expense",SplitType.EQUAL, Map.of(1,1.0,2,1.0));
        double netBalance = expenseService.checkNetBalance(1);
        assertEquals(6,netBalance);
    }
    @Test
    void testAddPercentageSplit(){
        Map<Integer,Double> split = Map.ofEntries(
                Map.entry(1,10.0),
                Map.entry(2,10.0),
                Map.entry(3,10.0),
                Map.entry(4,70.0)
        );
        expenseService.addExpense(1,100,"testAddPercentageSplit",SplitType.PERCENTAGE,split);
        assertEquals(90,expenseService.checkNetBalance(1));
        assertEquals(-10,expenseService.checkNetBalance(2));
        assertEquals(-10,expenseService.checkNetBalance(3));
        assertEquals(-70,expenseService.checkNetBalance(4));
    }
}
