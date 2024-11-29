package mk.ukim.finki.wp.lab.model.exception;

public class ArtistNotFoundException extends RuntimeException{
    public ArtistNotFoundException (Long artistId) {
        super(String.format("Artist with id %d does not exist.", artistId));
    }
}
