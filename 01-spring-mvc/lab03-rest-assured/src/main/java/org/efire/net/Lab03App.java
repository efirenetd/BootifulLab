package org.efire.net;

import org.efire.net.model.Lotto;
import org.efire.net.model.Winner;
import org.efire.net.repository.LottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class Lab03App implements CommandLineRunner {

    @Autowired
    private LottoRepository lottoRepository;

    public static void main(String[] args) {
        SpringApplication.run(Lab03App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var lotto = new Lotto();

        var winner1 = new Winner();
        winner1.setLotto(lotto);
        winner1.setNumbers(Arrays.asList(1,2,3));

        var winner2 = new Winner();
        winner2.setLotto(lotto);
        winner2.setNumbers(Arrays.asList(1,2,3,4,5));

        lotto.setWinningNumbers(Arrays.asList(1,2,3,4,5,6));
        Set<Winner> winnerSet = new HashSet<>();
        winnerSet.add(winner1);
        winnerSet.add(winner2);
        lotto.setWinners(winnerSet);
        lottoRepository.save(lotto);
    }
}
