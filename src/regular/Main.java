package regular;

import java.util.Scanner;

public class Main {
    public static void main(String[] arg){
        System.out.println("------program started--------");
        menu();
    }

    public static void menu(){

        Scanner sc = new Scanner(System.in);
        int sum =0;
        int[] notes = new int[4];
        for (int i = 0; i < 4; i++) {
            System.out.print("enter note "+(i+1) + ":");
            notes[i] = sc.nextInt();
            sum += notes[i];

        }
        do{
            System.out.println("1-Triez et afficher la liste des notes");
            System.out.println("2-Affichez la note moyenne.");
            System.out.println("3-Affichez la note maximale et minimale..");
            System.out.println("4-Affichez le nombre d’étudiants ayant une note saisie par l’utilisateur..");

            System.out.println("shose a number: ");
            int choice = sc.nextInt();


        switch (choice){
            case 1: trier(notes);
                break;
            case 2: moyenne(sum);
                break;
            case 3: maxAndMin(notes);
                break;
            case 4: numberEtud(notes);
                break;
            default:
                System.out.println("pls try again");
        }
        }while(true);
    }

    public static void trier(int[] notes){
        // Bubble Sort
        for (int i = 0; i < notes.length - 1; i++) {
            for (int j = 0; j < notes.length - 1 - i; j++) {
                if (notes[j] > notes[j + 1]) {
                    // swap
                    int temp = notes[j];
                    notes[j] = notes[j + 1];
                    notes[j + 1] = temp;
                }
            }
        }

        System.out.print("Sorted notes: ");
        for (int i = 0; i < notes.length; i++) {
            System.out.print(notes[i] + " ");
        }
        System.out.println();
    }
    public static void moyenne(int sum){
        int moyenne = sum/5;
        System.out.println("avg: " + moyenne);
    }
    public static void maxAndMin(int[] notes){
        int max = notes[0];
        for(int i =0; i< notes.length; i++){
            if(notes[i] > max){
                max = notes[i];
            }
        }
        System.out.println("max:"+max);
        System.out.println("min:"+notes[0]);
    }
    public static void numberEtud(int[] notes){
        int count = 0;
        for (int i = 0; i < notes.length; i++) {
            for (int j =1; j < notes.length; j++){
                if(notes[0] == notes[j]){
                    count++;
                }
            }
        }
        System.out.println("number of the student that have the same notes: "+count);
    }


}
