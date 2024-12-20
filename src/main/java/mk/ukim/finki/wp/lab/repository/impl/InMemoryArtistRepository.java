package mk.ukim.finki.wp.lab.repository.impl;

import mk.ukim.finki.wp.lab.boostrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InMemoryArtistRepository {
    public List<Artist> findAll() {
        return DataHolder.artists.stream().collect(Collectors.toList());
    }

    public Optional<Artist> findById(Long id) {
        return DataHolder.artists.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public List<Artist> findByIds(List<Long> artistIds) {
        return DataHolder.artists.stream()
                .filter(artist -> artistIds.contains(artist.getId()))
                .collect(Collectors.toList());
    }
}
