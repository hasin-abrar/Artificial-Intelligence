import javax.sound.sampled.Line;
import java.util.LinkedList;

/**
 * @author ANTU on 06-Jul-18.
 * @project TSP
 */
public class Test {
    public static void main(String[] args) {
        LinkedList<Integer> check = new LinkedList<>();
        check.add(2);
        check.add(3);
        check.add(2);
        for (Integer i :
                check) {
            System.out.println(i);
        }

    }
}
