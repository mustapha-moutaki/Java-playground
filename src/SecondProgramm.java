import java.util.Scanner;

public class SecondProgramm {
    public static void main(String[] args){
        Conjugue();
    }

    public static void Conjugue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez un verbe du premier groupe: ");
        String word = sc.nextLine();
        sc.close();
        if(word.toLowerCase().endsWith("er")){
            String root = word.substring(0, word.length() -2);
            System.out.println("Je " + root + "e");
            System.out.println("Tu " + root + "es");
            System.out.println("Il " + root + "e");
            System.out.println("Nous " + root + "ons");
            System.out.println("Vous " + root + "ez");
            System.out.println("Ils " + root + "ent");

        }
    }

}
