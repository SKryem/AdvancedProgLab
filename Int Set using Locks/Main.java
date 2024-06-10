//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

            Boolean res;
            IntegerSet test = new FineGrainedList();
            res = test.remove(1);
            res = test.add(3);
            res = test.remove(3);

            res = test.contains(1);
            res = test.add(1);
            res = test.add(2);
            res = test.add(2);
            int a = test.getSize();

            res = test.remove(1);
            res = test.contains(1);
            res = test.remove(3);


            res = test.remove(5);
            System.out.println(a);
    }
}