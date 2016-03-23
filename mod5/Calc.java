/**
 * Калькулятор.
 * <pre>
 * Приложение получает 2 операнда и операцию в качестве аргументов командной строки,
 * например: java Calc 5 + 7
 *
 * Программа выводит сообщение об ошибке:
 * a) если число аргументов меньше 3,
 * b) если операнды – не числа,
 * c) если операция не +,-,*,/
 *
 * После проверки необходимого числа элементов массива String args[]
 * необходимо выполнить конвертацию 1 и 3 строк в числа, а 2 в символ операции.
 * Для этого использовать Double.parseDouble(String) и string.charAt(0) 
 * </pre>
 */
import java.util.Map;
import java.util.HashMap;

public class Calc {

    Map<String, ICalcOperation> operationsList = null;

    public Calc() {
        operationsList = new HashMap<String, ICalcOperation>();
        new calcPlus(operationsList);
        new calcMinus(operationsList);
        new calcMul(operationsList);
        new calcDiv(operationsList);
    }

    public static void main(String[] args) {

        String op = null;
        try {
            Calc c = new Calc();
            if (args.length < 3) {
                System.out.print("Usage: Calc x op y\n"
                               + "where:\tx, y\t- integers\n"
                               + "\top\t- operator. One of (");
                boolean first = true;
                for(ICalcOperation o : c.operationsList.values()) {
                        System.out.print((first ? "" : ", ") + o.getOp());
                        first = false;
                }
                System.out.println(")");
                // System.exit(1); // jRunner doesn't like it
            } else {
                op = args[1];
                double result = c.operationsList.get(op).calc(Double.parseDouble(args[0]),
                                                              Double.parseDouble(args[2]));
                System.out.println("Result: " + result);
            }
        } catch(NullPointerException e) {
            System.out.println("Operation \"" + op + "\" not yet implemented.");
        } catch(NumberFormatException e) {
            System.out.println("Wrong number: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        
    }
}

interface ICalcOperation {
        double calc(double p_1, double p_2);
        String getOp();
}

class calcPlus implements ICalcOperation{
        static final String op = "+";
        public String getOp() {return op;}

        private calcPlus(){}
        public calcPlus(Map<String, ICalcOperation> m){
                m.put(op, this);
        }
        @Override
        public int hashCode() {
                return op.hashCode();
        }
        @Override
        public boolean equals(final Object obj) {
                if (obj instanceof calcPlus) {
                    return op.equals(((calcPlus)obj).op);
                }
                return false;
        }

        public double calc(double p_1, double p_2) {
                return p_1 + p_2;
        }
}

class calcMinus implements ICalcOperation{
        static final String op = "-";
        public String getOp() {return op;}

        private calcMinus(){}
        public calcMinus(Map<String, ICalcOperation> m){
                m.put(op, this);
        }
        @Override
        public int hashCode() {
                return op.hashCode();
        }
        @Override
        public boolean equals(final Object obj) {
                if (obj instanceof calcMinus) {
                    return op.equals(((calcMinus)obj).op);
                }
                return false;
        }

        @Override
        public double calc(double p_1, double p_2) {
                return p_1 - p_2;
        }
}
class calcMul implements ICalcOperation{
        static final String op = "*";
        public String getOp() {return op;}

        private calcMul(){}
        public calcMul(Map<String, ICalcOperation> m){
                m.put(op, this);
        }
        @Override
        public int hashCode() {
                return op.hashCode();
        }
        @Override
        public boolean equals(final Object obj) {
                if (obj instanceof calcMul) {
                    return op.equals(((calcMul)obj).op);
                }
                return false;
        }

        @Override
        public double calc(double p_1, double p_2) {
                return p_1 * p_2;
        }
}
class calcDiv implements ICalcOperation{
        static final String op = "/";
        public String getOp() {return op;}

        private calcDiv(){}
        public calcDiv(Map<String, ICalcOperation> m){
                m.put(op, this);
        }
        @Override
        public int hashCode() {
                return op.hashCode();
        }
        @Override
        public boolean equals(final Object obj) {
                if (obj instanceof calcDiv) {
                    return op.equals(((calcDiv)obj).op);
                }
                return false;
        }

        @Override
        public double calc(double p_1, double p_2) {
                return p_1 / p_2;
        }
}
