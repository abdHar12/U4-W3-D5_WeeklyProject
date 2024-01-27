package harouane.Exceptions;

import java.util.UUID;

public class InexistentIsbn extends RuntimeException{
    public InexistentIsbn(UUID isbn){
        super(isbn+": ISBN non esistente");
    }
}
