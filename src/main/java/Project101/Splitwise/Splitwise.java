package Project101.Splitwise;
import Project101.Splitwise.code.*;

import java.util.ArrayList;
import java.util.List;

public class Splitwise {
    ExpenseController expenseController;
    BalanceSheetController balanceSheetController;
    UserController userController ;

    GroupControllers groupControllers;
    public Splitwise(){
//        expenseController = new ExpenseController();
        balanceSheetController = new BalanceSheetController();
        userController = new UserController();
        groupControllers = new GroupControllers();
    }

    public void test (){
        setupUserAndGroup();

        Group group = groupControllers.getGroup(1001);
        group.addMember(userController.getUser(2001));
        group.addMember(userController.getUser(3001));

        //Step2. create an expense inside a group
        List<Split> splits = new ArrayList<>();
        Split split1 = new Split(userController.getUser(1001), 300);
        Split split2 = new Split(userController.getUser(2001), 300);
        Split split3 = new Split(userController.getUser(3001), 300);
        splits.add(split1);
        splits.add(split2);
        splits.add(split3);
        group.createExpense(1001, splits, SplitType.EQUAL, userController.getUser(1001));

        List<Split> splits2 = new ArrayList<>();
        Split splits2_1 = new Split(userController.getUser(1001), 400);
        Split splits2_2 = new Split(userController.getUser(2001), 100);
        splits2.add(splits2_1);
        splits2.add(splits2_2);
        group.createExpense(1001, splits2, SplitType.UNEQUAL, userController.getUser(2001));

        for(User user : userController.getUserList()) {
            balanceSheetController.displayBalance(user);
        }

    }


    public void setupUserAndGroup(){

        //onboard user to splitwise app
        addUsersToSplitwiseApp();

        //create a group by user1
        User user1 = userController.getUser(123213);
        groupControllers.createNewGroup("G1001", "Outing with Friends", user1);
    }

    private void addUsersToSplitwiseApp(){

        //adding User1
        User user1 = new User(102, "User1");

        //adding User2
        User user2 = new User (2001, "User2");

        //adding User3
        User user3 = new User (3001, "User3");

        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);
    }

}
