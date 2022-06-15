package org.efire.net.controller;

import org.efire.net.dto.LottoDto;
import org.efire.net.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class Lab03Controller {

    @Autowired
    private LottoRepository repository;

    @GetMapping("/{lottoId}")
    public ResponseEntity<LottoDto> draw(@PathVariable("lottoId") Integer lottoId) {
        //var byId = repository.findById(lottoId);
        var byId = repository.findByLottoId(lottoId);

        var lotto = byId.orElseThrow(() -> new EntityNotFoundException("No record found"));
        var winners = lotto.getWinners().stream().collect(Collectors.toList());;

        var lottoDto = new LottoDto();
        lottoDto.setLottoId(lottoId);

        lottoDto.setWinners(winners);
        return ResponseEntity.ok(lottoDto);

    }
}
