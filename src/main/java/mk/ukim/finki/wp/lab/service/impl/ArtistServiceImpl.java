package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.exception.ArtistNotFoundException;
import mk.ukim.finki.wp.lab.model.exception.InvalidArgumentsException;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> listArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist ArtistfindById(Long id) {
        if (id == null) {
            throw new InvalidArgumentsException();
        }

        return artistRepository.findById(id).orElseThrow(ArtistNotFoundException::new);
    }

    @Override
    public List<Artist> listByIds(List<Long> artistIds) {
        if (artistIds.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return artistRepository.findByIds(artistIds);
    }
}
