package harouane.Exceptions;

public class ElementsNotFound extends RuntimeException{
    public ElementsNotFound() {
        super("Non è stato trovato alcun elemento non trovato");
    }
}
