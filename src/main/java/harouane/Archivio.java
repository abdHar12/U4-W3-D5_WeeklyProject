package harouane;

import com.github.javafaker.Faker;
import harouane.DAO.BibliografiaDAO;
import harouane.DAO.PrestititoDAO;
import harouane.DAO.UserDAO;
import harouane.Entities.*;
import harouane.Enum.Periodicita;
import harouane.Exceptions.*;
import harouane.Interfaces.MyFunction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

public class Archivio {
    static boolean continueCycle;
    static int choice=0;
    static File file = new File("/src/output.txt");
    static Faker faker= new Faker();
    static EntityManagerFactory emf= Persistence.createEntityManagerFactory("catalogo_bibliografico");
    static EntityManager em =  emf.createEntityManager();
    public static void main(String[] args) {
        creationUser();
        creationBook();
        creationLoan();
        initialChoice();
        em.close();
        emf.close();
    }
    public static void verifyChoice(int choice, int min, int max) throws Scelta{
        if (choice>max || choice<min) throw new Scelta(choice);
    }
    static MyFunction systemOutInitialChoice1=()->{
        System.out.println("\nCosa vuoi fare? ");
        System.out.println("1: Aggiungere un elemento");
        System.out.println("2: Rimuovere un elemento");
        System.out.println("3: Trovare un elemento con ISBN");
        System.out.println("4: Trovare un elemento o più elementi con l'anno di pubblicazione digitato");
        System.out.println("5: Trovare un elemento o più elementi con lo stesso autore");
        System.out.println("6: Trovare un elemento o più elementi cercando titolo parziala");
        System.out.println("7: Trovare un i prestiti non restituiti data una number card");
        System.out.println("8: Esci");
    };
    static MyFunction systemOutChoice1=()->{
        System.out.println("Che elemento vuoi aggiungere? ");
        System.out.println("1: Libro");
        System.out.println("2: Rivista");
        System.out.println("3: User");
        System.out.println("4: Prestito");
        System.out.println("0: Torna alla pagina principale");
    };

    static MyFunction systemOutRemoveElementOrExit=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Rimuovere un elemento");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutFindByDate=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare uno o più elemento usando la data");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutFindElementOrExit=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutFindByTitle=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare uno o più elemento usando il titolo");
        System.out.println("0: Uscire");
    };

    static MyFunction systemOutPutTheIsbnToRemove=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi rimuovere: ");
            System.out.printf("Inserisci l'ISBN: ");
            String isbn = sc.next();
            BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(em);
            bibliografiaDAO.removeElementFromCatalog(UUID.fromString(isbn));
        } catch (InexistentIsbn e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione all'isbn inserito");
            continueCycle = true;
        }
        //Bibliografia.removeElement(isbn);
    };
    static MyFunction systemOutPutTheIsbnToFind=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi trovare: ");
            System.out.printf("Inserisci l'ISBN: ");
            String isbn = sc.nextLine();
            BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(em);
            Bibliografia found= bibliografiaDAO.findCatalog(UUID.fromString(isbn));
            System.out.print("Ecco il tuo elemento: ");
            System.out.println(found);
        } catch (InexistentIsbn e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        } catch (ElementsNotFound e){
            System.out.println(e.getMessage());
            continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione all'isbn inserito");
            continueCycle = true;
        }
    };

    static MyFunction PutTheNumberCardToFindTheLoans=()->{
        try {
            Scanner sc= new Scanner(System.in);
            System.out.println("Che elemento vuoi trovare: ");
            System.out.printf("Inserisci la number card: ");
            String numberCard = sc.nextLine();
            PrestititoDAO prestititoDAO= new PrestititoDAO(em);
            List<Prestito> found= prestititoDAO.findLoansNotReturnedByUserCard(UUID.fromString(numberCard));
            System.out.print("Ecco i tuoi elementi: ");
            System.out.println(found);
        } catch (InexistentNumberCard e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        } catch (ElementsNotFound e){
            System.out.println(e.getMessage());
            continueCycle = true;
        }catch (IllegalArgumentException e){
            System.out.println("Attenzione alla number card inserita");
            continueCycle = true;
        }
    };

    static MyFunction systemOutFindByAuthor=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un elemento uno o più elementi cercando l'autore");
        System.out.println("0: Uscire");
    };
    static MyFunction systemOutFindByCardLoansNotReturned=()->{
        System.out.println("Cosa vuoi fare?");
        System.out.println("1: Trovare un i prestiti non restituiti data una number card");
        System.out.println("0: Uscire");
    };


    static MyFunction putTheAuthor=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci l'autore: ");
            String author= sc.nextLine();
            bibliografiaDAO.findElementsByAuthor(author).forEach(System.out::println);
        } catch (AuthorNotFound e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            continueCycle = true;
        }
    };

    static MyFunction putTheDate=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci l'anno di pubblicazione: ");
            Integer year = sc.nextInt();
            System.out.println("Gli elementi:");
            bibliografiaDAO.findElementsByDate(year).forEach(System.out::println);
        }catch (DateNotFound e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        }catch (InputMismatchException e){
            System.out.println("Attenzione devi inserire un anno!");
            continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            continueCycle = true;
        }
    };

    static MyFunction putTheTitle=()->{
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(em);
        Scanner sc= new Scanner(System.in);
        try {
            System.out.printf("Inserisci il titolo di pubblicazione: ");
            String title = sc.nextLine();
            List<Bibliografia> elements=bibliografiaDAO.findByTitle(title);
            System.out.println("Gli elementi:");
            elements.forEach(System.out::println);
        }catch (ElementsNotFound e) {
            System.out.println(e.getMessage());
            continueCycle = true;
        } catch (NoResultException e){
            System.out.println("Nessun elemento trovato");
            continueCycle = true;
        }
    };

    public static void tryCatchForChoices(MyFunction f, int min, int max){
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

    public static void initialChoice(){
        while (choice!=8){
        tryCatchForChoices(systemOutInitialChoice1,1, 8);
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
                System.exit(0);
                break;
        }
}
    }
    static Supplier<User> randomUser=()-> new User(faker.name().firstName(), faker.name().lastName(), faker.date().birthday(12, 80).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

    static Supplier<Prestito> randomLoan=()->{
        Date date=faker.date().between(Date.from(LocalDate.parse("2023-05-01").atStartOfDay(ZoneId.systemDefault()).toInstant()),
                (Date.from(LocalDate.parse("2024-01-26").atStartOfDay(ZoneId.systemDefault()).toInstant())));
        System.out.println(date);
        Random random=new Random();
        LocalDate dateLoan=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
        LocalDate dateReturn=dateLoan.plusDays(30);
        BibliografiaDAO bibliografiaDAO= new BibliografiaDAO(em);
        UserDAO userDAO=new UserDAO(em);
        List<User> allUser=userDAO.getAllUsers();
        List<Bibliografia> allCatalog=bibliografiaDAO.getAllCatalog();
        if(!allUser.isEmpty() && !allCatalog.isEmpty()){
            User randomUser = allUser.get(random.nextInt(0, allUser.size()));
            Bibliografia randomCatalog = allCatalog.get(random.nextInt(0, allCatalog.size()));
            return new Prestito(dateLoan, dateReturn, randomCatalog, randomUser);
        }
        return null;
    };

    static Supplier<Integer> randomNumPage=()->{
        Random random= new Random();
        return random.nextInt(80, 600);
    };
    static Supplier<Periodicita> randomPeriodicity=()->{
        Random random= new Random();
        switch (random.nextInt(1, 3)) {
            case 1:
                return Periodicita.MENSILE;
            case 2:
                return Periodicita.SEMESTRALE;
            case 3:
                return Periodicita.SETTIMANALE;
        }
        return null;
    };
    static Supplier<Integer> yearSupplier=()->{
        Random random= new Random();
        return random.nextInt(1990, 2023);
    };

    public static void creationLoan(){
        PrestititoDAO prestititoDAO= new PrestititoDAO(em);
        Prestito prestito= randomLoan.get();
        if(prestito!=null)
            prestititoDAO.createNewLoans(prestito);
        else System.out.println("Nessun prestito creato");
    }
    public static void creationBook(){
        Libro book;
        String author;
        String title;
        String genre;
        Integer date;
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(em);
        title = faker.book().title();
        author = faker.book().author();
        genre = faker.book().genre();
        date = yearSupplier.get();
        book = new Libro(title, date, randomNumPage.get(), author, genre);
        bibliografiaDAO.saveNewBookOrMagazine(book);
    }

    public static void creationMagazine(){
        Rivista magazine;
        Integer date;
        String title;
        Periodicita periodicita = null;
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(em);
        title = faker.book().title();
        date = yearSupplier.get();
        periodicita = randomPeriodicity.get();
        magazine = new Rivista(title, date, randomNumPage.get(), periodicita);
        bibliografiaDAO.saveNewBookOrMagazine(magazine);
    }
    public static void creationUser(){
        UserDAO userDAO=new UserDAO(em);
        userDAO.saveNewUser(randomUser.get());
    }
    public static void choice1(){
        do{
            tryCatchForChoices(systemOutChoice1, 0, 4);
            switch (choice) {
                case 0:
                    initialChoice();
                    break;
                case 1:
                    creationBook();
                    break;
                case 2:
                    creationMagazine();
                    break;
                case 3:
                    creationUser();
                    break;
                case 4:
                    creationLoan();
                    break;
            }
        }while (!(choice==0));

    }
    public static void choice2(){
        findByIsbnDateOrAuthorOrTitle(systemOutRemoveElementOrExit, systemOutPutTheIsbnToRemove);
    }
    public static void choice3(){
        findByIsbnDateOrAuthorOrTitle(systemOutFindElementOrExit, systemOutPutTheIsbnToFind);
    }
    public static void choice4() {
        findByIsbnDateOrAuthorOrTitle(systemOutFindByDate, putTheDate);
    }
    public static void choice5(){
        findByIsbnDateOrAuthorOrTitle(systemOutFindByAuthor, putTheAuthor);
    }

    public static void choice6(){
        findByIsbnDateOrAuthorOrTitle(systemOutFindByTitle, putTheTitle);
    }
    public static void choice7(){
        findByIsbnDateOrAuthorOrTitle(systemOutFindByCardLoansNotReturned, PutTheNumberCardToFindTheLoans);
    }
    public static void findByIsbnDateOrAuthorOrTitle(MyFunction f1, MyFunction f2){
        continueCycle=true;
        while (continueCycle){
            continueCycle=false;
            while (choice!=0){
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
}
