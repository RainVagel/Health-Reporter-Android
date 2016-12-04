package rainvagel.healthreporter.TestClasses;

import android.content.Context;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;

import rainvagel.healthreporter.DBClasses.DBAppraisalTestsTransporter;
import rainvagel.healthreporter.DBClasses.DBAppraisalsTransporter;
import rainvagel.healthreporter.DBClasses.DBClientsTransporter;
import rainvagel.healthreporter.DBClasses.DBQueries;
import rainvagel.healthreporter.DBClasses.DBTestsTransporter;

/**
 * Created by rainvagel on 04.12.16.
 */

public class FormulaEvaluation {

    public String evaluate(Context context, String appraisalID, String testID) {

        DBQueries dbQueries = new DBQueries();
        DBAppraisalTestsTransporter dbAppraisalTestsTransporter = dbQueries.getAppraisalTestsFromDB(context);
        String trial1 = dbAppraisalTestsTransporter.getAppraisalIdToTrial1().get(appraisalID);
        String trial2 = dbAppraisalTestsTransporter.getAppraisalIdToTrial2().get(appraisalID);
        String trial3 = dbAppraisalTestsTransporter.getAppraisalIdToTrial3().get(appraisalID);

        DBTestsTransporter dbTestsTransporter = dbQueries.getTestsFromDB(context);
        String formulaF = dbTestsTransporter.getTestIdToFormulaF().get(testID);
        String formulaM = dbTestsTransporter.getTestIdToFormulaM().get(testID);
        String decimals = dbTestsTransporter.getTestIdToDecimals().get(testID);
        String units = dbTestsTransporter.getTestIdToUnits().get(testID);

        DBAppraisalsTransporter dbAppraisalsTransporter = dbQueries.getAppraisalsFromDB(context);
        String clientID = dbAppraisalsTransporter.getAppraisalIdToClientId().get(appraisalID);

        DBClientsTransporter dbClientsTransporter = dbQueries.getClientsFromDB(context);
        String gender = dbClientsTransporter.getClientIdToGender().get(clientID);

        double numericValue = 0.0;

        if (gender.equals("0")) {
            numericValue = stringToNumber(trial1, trial2, trial3, formulaF);
        } else {
            numericValue = stringToNumber(trial1, trial2, trial3, formulaM);
        }

        numericValue = round(numericValue, Integer.parseInt(decimals));
        return String.valueOf(numericValue) + units;
    }

    private double stringToNumber(String trial1, String trial2, String trial3, String formula) {
        double trial1Numeric = Double.parseDouble(trial1);
        double trial2Numeric = Double.parseDouble(trial2);
        double trial3Numeric = Double.parseDouble(trial3);
        Deque<Double> stack = new ArrayDeque<>();
        String[] tokenised = formula.split(" ");
        for (String token : tokenised) {
            switch (token) {
                case "trial1":
                    stack.push(trial1Numeric);
                    break;
                case "trial2":
                    stack.push(trial2Numeric);
                    break;
                case "trial3":
                    stack.push(trial3Numeric);
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    stack.push(stack.pop() / stack.pop());
                    break;
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(stack.pop() - stack.pop());
                    break;
                case "(":
                    break;
                case ")":
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }
        }
        return stack.pop();
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
