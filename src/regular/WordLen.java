package regular;

import java.util.ArrayList;
import java.util.Scanner;

public class WordLen {
    public static void main(String[] args) {

    }

    public static void CharCounter(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the word: ");
        String word = sc.nextLine();
        sc.close();
        int len = word.length();
        String[] parts = word.split("");
        ArrayList<Integer>Counter = new ArrayList<>();

        if(len > 3){
            int count = 0;
            for (int i = 0; i< parts.length; i++) {
                for(int j =1; j < parts.length-1; j++){
                    if(parts[i] == parts[j]){
                        count ++;
                    }
                }
                Counter.add(count);
            }
        }


    }

}
