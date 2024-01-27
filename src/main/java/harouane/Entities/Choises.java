package harouane.Entities;

import com.github.javafaker.Faker;
import harouane.Archivio;
import harouane.DAO.BibliografiaDAO;
import harouane.DAO.PrestititoDAO;
import harouane.Exceptions.*;
import harouane.Interfaces.MyFunction;

import javax.persistence.NoResultException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Choises {
    private static int choice=0;
    private static boolean continueCycle;
    private static final Faker faker= Archivio.getFaker();
    public static void initialChoice(){
        while (choice!=8){
            tryCatchForChoices(systemOutInitialChoice1,1, 9);
            switch (choice){
                case 1:
                    choice1();
                    break;
                case 2:
                    choice2();
                    break;
                case 3:
                    choice3();
                    break;
                case 4:
                    choice4();
                    break;
                case 5:
                    choice5();
                    break;
                case 6:
                    choice6();
                    break;
                case 7:
                    choice7();
                    break;
                case 8:
                    choice8();
                    break;
                case 9:
                    System.exit(0);
                    break;
            }
        }
    }
        private static void verifyChoice(int choice, int min, int max) throws Scelta{
            if (choice>max || choice<min) throw new Scelta(choice);
        }

    private static void tryCatchForChoices(MyFunction f, int min, int max){
            continueCycle = true;
            while (continueCycle){
                Scanner sc= new Scanner(System.in);
                try {
                    continueCycle= false;
                    f.apply();
                    System.out.printf("\nLa tua scelta: ");
                    choice = sc.nextInt();
                    verifyChoice(choice, min, max);
                } catch (Scelta e) {
                    continueCycle = true;
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e) {
                    continueCycle = true;
                    System.out.println("Devi inserire un numero");
                } catch (Exception e) {
                    System.err.println("C'e un problema!");
                }
            }
        }

    private static void choice1(){
        do{
            tryCatchForChoices(systemOutChoice1, 0, 4);
            switch (choice) {
                case 0:
                    initialChoice();
                    break;
                case 1:
                    Libro.creationBook();
                    break;
                case 2:
                    Rivista.creationMagazine();
                    break;
                case 3:
                    User.creationUser();
                    break;
                case 4:
                    Prestito.creationLoan();
                    break;
            }
        }while (!(choice==0));

    }


    private static void choice2(){
        forAllChoices(systemOutRemoveElementOrExit, systemOutPutTheIsbnToRemove);
    }
    private static void choice3(){
        forAllChoices(systemOutFindElementOrExit, systemOutPutTheIsbnToFind);
    }
    private static void choice4() {
        forAllChoices(systemOutFindByDate, putTheDate);
    }
    private static void choice5(){
        forAllChoices(systemOutFindByAuthor, putTheAuthor);
    }
    private static void choice6(){
        forAllChoices(systemOutFindByTitle, putTheTitle);
    }
    private static void choice7(){
        forAllChoices(systemOutFindByCardLoansNotReturned, PutTheNumberCardToFindTheLoans);
    }
    private static void choice8(){
        forAllChoices(systemOutFindExpiredLoansNotReturned, findExpiredLoansNotReturned);
    }

    private static void forAllChoices(MyFunction f1, MyFunction f2) {
        continueCycle = true;
        while (continueCycle) {
            continueCycle = false;
            while (choice != 0) {
                tryCatchForChoices(f1, 0, 1);
                switch (choice) {
                    case 1:
                        f2.apply();
                        break;
                    case 0:
                        initialChoice();
                        break;
                }
            }
        }
    }
    private static MyFunction systemOutInitialChoice1=()->{
        System.out.println("\nCosa vuoi fare? ");
        System.out.println("1: Aggiungere un elemento");
        System.out.println("2: Rimuovere un elemento");
        System.out.println("3: Trovare un elemento con ISBN");
        System.out.println("4: Trovare un elemento o più elementi con l'anno di pubblicazione digitato");
        System.out.println("5: Trovare un elemento o più elementi con lo stesso autore");
        System.out.println("6: Trovare un elemento o più elementi cercando titolo parziala");
        System.out.println("7: Trovare i prestiti non restituiti data una number card");
        System.out.println("8: Stampa i prestiti scaduti e non ancora restituiti");
        System.out.println("9: Esci");
    };
    private static MyFunction systemOutChoice1=()->{
        System.out.println("Che elemento vuoi aggiungere? ");
        System.out.println("1: Libro");
        System.out.println("2: Rivista");
        System.out.println("3: User");
        System.out.println("4: Prestito");
        System.out.println("0: Torna alla pagina principale");
    };

    private static MyFunction systemOutRemoveElementOrExit=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Rimuovere un elemento");
        System.out.println("0: Uscire");
    };
    private    static MyFunction systemOutFindByDate=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare uno o più elemento usando la data");
        System.out.println("0: Uscire");
    };
    private static MyFunction systemOutFindElementOrExit = () -> {
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento");
        System.out.println("0: Uscire");
    };
    private static MyFunction systemOutFindByTitle=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare uno o più elemento usando il titolo");
        System.out.println("0: Uscire");
    };

    private static MyFunction systemOutFindByAuthor=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento uno o più elementi cercando l'autore");
        System.out.println("0: Uscire");
    };
    private static MyFunction systemOutFindByCardLoansNotReturned=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un i prestiti non restituiti data una number card");
        System.out.println("0: Uscire");
    };
    private static MyFunction systemOutFindExpiredLoansNotReturned=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un i prestiti scaduti e non restituiti");
        System.out.println("0: Uscire");
    };

    private static MyFunction putTheAuthor=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(Archivio.em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci l'autore: ");
            String author= sc.nextLine();
            bibliografiaDAO.findElementsByAuthor(author).forEach(System.out::println);
        } catch (AuthorNotFound e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            Choises.continueCycle = true;
        }
    };

    private static MyFunction putTheDate=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(Archivio.em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci l'anno di pubblicazione: ");
            Integer year = sc.nextInt();
            System.out.println("Gli elementi:");
            bibliografiaDAO.findElementsByDate(year).forEach(System.out::println);
        }catch (DateNotFound e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        }catch (InputMismatchException e){
            System.out.println("Attenzione devi inserire un anno!");
            Choises.continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            Choises.continueCycle = true;
        }
    };

    private static MyFunction putTheTitle=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(Archivio.em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci il titolo di pubblicazione: ");
            String title = sc.nextLine();
            List<Bibliografia> elements=bibliografiaDAO.findByTitle(title);
            System.out.println("Gli elementi:");
            elements.forEach(System.out::println);
        }catch (ElementsNotFound e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            Choises.continueCycle = true;
        }
    };

    private  static MyFunction systemOutPutTheIsbnToRemove=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi rimuovere: ");
            System.out.printf("Inserisci l'ISBN: ");
            String isbn = sc.next();
            BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(Archivio.em);
            bibliografiaDAO.removeElementFromCatalog(UUID.fromString(isbn));
        } catch (InexistentIsbn e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            Choises.continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione all'isbn inserito");
            Choises.continueCycle = true;
        }
    };
    private static MyFunction systemOutPutTheIsbnToFind=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi trovare: ");
            System.out.printf("Inserisci l'ISBN: ");
            String isbn = sc.nextLine();
            BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(Archivio.em);
            Bibliografia found= bibliografiaDAO.findCatalog(UUID.fromString(isbn));
            System.out.print("Ecco il tuo elemento: ");
            System.out.println(found);
        } catch (InexistentIsbn e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun risultato trovato");
            Choises.continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione all'isbn inserito");
            Choises.continueCycle = true;
        }
    };

    private static MyFunction PutTheNumberCardToFindTheLoans=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi trovare: ");
            System.out.printf("Inserisci la number card: ");
            String numberCard = sc.nextLine();
            PrestititoDAO prestititoDAO= new PrestititoDAO(Archivio.em);
            List<Prestito> found= prestititoDAO.findLoansNotReturnedByUserCard(UUID.fromString(numberCard));
            System.out.print("Ecco i tuoi elementi: ");
            System.out.println(found);
        } catch (InexistentNumberCard e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        } catch (ElementsNotFound e){
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione alla number card inserita");
            Choises.continueCycle = true;
        }
    };

    private static MyFunction findExpiredLoansNotReturned=()->{
        try {
            PrestititoDAO prestititoDAO= new PrestititoDAO(Archivio.em);
            List<Prestito> found= prestititoDAO.findLoansNotReturnedAndExpired();
            System.out.print("Ecco i tuoi elementi: ");
            System.out.println(found);
        } catch (ElementsNotFound e) {
            System.out.println(e.getMessage());
            Choises.continueCycle = true;
        }
    };

}

