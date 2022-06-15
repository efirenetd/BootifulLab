package org.efire.net.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tbl_lotto")
public class Lotto {

    public Lotto() {
    }

    public Lotto(Integer lottoId, Set<Winner> winners) {
        this.lottoId = lottoId;
        this.winners = winners;
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer lottoId;

/*    @ElementCollection
    private List<Integer> winningNumbers = new ArrayList<>();*/

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

    @Override
    public String toString() {
        return "Lotto{" +
                "lottoId=" + lottoId +
                ", winners=" + winners +
                '}';
    }
}
