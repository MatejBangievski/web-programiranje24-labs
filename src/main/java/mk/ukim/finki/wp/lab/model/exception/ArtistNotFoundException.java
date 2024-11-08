package mk.ukim.finki.wp.lab.model.exception;

public class ArtistNotFoundException extends RuntimeException{
    public ArtistNotFoundException () {
        super("Artist not found exception");
    }
}
