package Project101.ExpenseShare;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseShareTest {
    ExpenseService expenseService ;
    @BeforeEach
    void setUp(){
        expenseService = new ExpenseService();
        //showBalanceSheet
    }
    @Test
    void testAddExpenseEqualSplit(){
        int payerId = 1;
        expenseService.addExpense(payerId,12.0,"Add expense",SplitType.EQUAL, Map.of(1,1.0,2,1.0));
        double netBalance = expenseService.showBalanceSheet(1);
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
        assertEquals(90,expenseService.showBalanceSheet(1));
        assertEquals(-10,expenseService.showBalanceSheet(2));
        assertEquals(-10,expenseService.showBalanceSheet(3));
        assertEquals(-70,expenseService.showBalanceSheet(4));
    }
    @Test
    void testUpdateBalance(){
        Map<Integer,Double> split = Map.ofEntries(
                Map.entry(1,10.0),
                Map.entry(2,10.0),
                Map.entry(3,10.0),
                Map.entry(4,70.0)
        );
        expenseService.addExpense(1,100,"testUpdateBalance",SplitType.PERCENTAGE,split);
        assertEquals(90,expenseService.showBalanceSheet(1));
        assertEquals(-10,expenseService.showBalanceSheet(2));
        assertEquals(-10,expenseService.showBalanceSheet(3));
        assertEquals(-70,expenseService.showBalanceSheet(4));

        assertThrows(IllegalArgumentException.class,()->expenseService.updateExpense(2,1,10,"testUpdateBalance",SplitType.PERCENTAGE,split));

        expenseService.updateExpense(1,2,10,"testUpdateBalance",SplitType.PERCENTAGE,split);

        assertEquals(-1,expenseService.showBalanceSheet(1));
        assertEquals(9,expenseService.showBalanceSheet(2));
        assertEquals(-1,expenseService.showBalanceSheet(3));
        assertEquals(-7,expenseService.showBalanceSheet(4));
    }
}
