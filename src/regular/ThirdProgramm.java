package regular;

import java.util.ArrayList;
import java.util.Scanner;

public class ThirdProgramm {
    public static void main(String[] args){
        menu();
    }

    public static void menu(){
        String phrase = "";
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("\n=== MENU ===");
            System.out.println("1. Saisir une chaîne");
            System.out.println("2. Afficher la chaîne");
            System.out.println("3. Inverser la chaîne");
            System.out.println("4. Nombre de mots");
            System.out.println("5. Quitter");
            System.out.print("Votre choix: ");
            int choix = sc.nextInt();
            sc.nextLine();

            switch (choix){
                case 1: phrase = typing(sc);
                        break;
                case 2: display(phrase);
                    break;
                case 3:  inverse(phrase);
                    break;
                case 4: totalWords(phrase);
                    break;
                case 5: return;
                default:
                    System.out.println("pls try again !");
            }
        }


    }

    public static String typing(Scanner sc){
        System.out.print("enter the phrase: ");
        return sc.nextLine();
    }
    public static void display(String phrase){
        System.out.println("----------------");
        System.out.println(phrase);
        System.out.println("----------------");
    }

    public static void inverse(String phrase){
        String[] parts = phrase.trim().split(" ");
        ArrayList<String>reversedCollec = new ArrayList<>();
        for(String word: parts){
            String wordReversed = new StringBuilder(word).reverse().toString();
            reversedCollec.add(wordReversed);
        }
        System.out.println(reversedCollec);
    }

    public static void totalWords(String phrase){
        String[] words = phrase.split(" ");
        int count = words.length;
        System.out.println("number of words: "+ count);
    }


}
