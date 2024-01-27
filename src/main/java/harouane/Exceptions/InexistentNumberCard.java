package harouane.Exceptions;

import java.util.UUID;

public class InexistentNumberCard extends RuntimeException{
    public InexistentNumberCard(UUID numberCard) {
        super("La number card "+ numberCard+ " non esiste");
    }
}
