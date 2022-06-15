package org.efire.net.repository;

import org.efire.net.model.Lotto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LottoRepository extends JpaRepository<Lotto, Integer> {

    @Query("SELECT DISTINCT l from Lotto l JOIN l.winners w WHERE l.lottoId = :lottoId")
    public Optional<Lotto> findByLottoId(Integer lottoId);
}
