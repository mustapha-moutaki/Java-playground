package streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(5,3,2,6,8,90,1);
        List<Integer> doubled = numbers.stream().map(n -> n*2).
                collect(Collectors.toList());
        System.out.println(doubled);


    }


}
