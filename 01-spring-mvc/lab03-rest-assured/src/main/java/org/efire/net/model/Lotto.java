package org.efire.net.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tbl_lotto")
@ToString
public class Lotto {

    public Lotto() {
    }

    public Lotto(Integer lottoId, List<Integer> winningNumbers, Set<Winner> winners) {
        this.lottoId = lottoId;
        this.winningNumbers = winningNumbers;
        this.winners = winners;
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer lottoId;

    @ElementCollection
    private List<Integer> winningNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "lotto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Winner> winners;

    public Integer getLottoId() {
        return lottoId;
    }

    public Set<Winner> getWinners() {
        return winners;
    }

    public void setWinners(Set<Winner> winners) {
        this.winners = winners;
    }

    public List<Integer> getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(List<Integer> winningNumbers) {
        this.winningNumbers = winningNumbers;
    }
}
