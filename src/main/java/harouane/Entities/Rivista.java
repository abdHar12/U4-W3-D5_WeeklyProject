package harouane.Entities;

import harouane.Archivio;
import harouane.DAO.BibliografiaDAO;
import harouane.Enum.Periodicita;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@Entity
public class Rivista extends Bibliografia{
    Periodicita periodicita;
    static List<Rivista> allMagazines=new ArrayList<>();

    public Rivista(String titolo, Integer yearOfPubblication, Integer numOfPage, Periodicita periodicita) {
        super(titolo, yearOfPubblication, numOfPage);
        this.periodicita = periodicita;
    }
    public static void addAllMagazines(Rivista magazine){
        allMagazines.add(magazine);
    }

    public static void creationMagazine(){
        Rivista magazine;
        Integer date=yearSupplier.get();;
        String title=faker.book().title();
        Periodicita periodicita=randomPeriodicity.get();
        BibliografiaDAO bibliografiaDAO=new BibliografiaDAO(Archivio.em);
        magazine = new Rivista(title, date, randomNumPage.get(), periodicita);
        bibliografiaDAO.saveNewBookOrMagazine(magazine);
    }
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
    @Override
    public String toString() {
        return "Rivista{" +
                "Isbn= "+isbn +
                ", periodicita=" + periodicita +
                ", titolo='" + titolo + '\'' +
                ", yearOfPubblication=" + yearOfPubblication +
                ", numOfPage=" + numOfPage +
                '}';
    }

}
