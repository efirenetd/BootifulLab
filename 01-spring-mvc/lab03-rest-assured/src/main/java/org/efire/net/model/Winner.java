package org.efire.net.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_winner")
@ToString
public class Winner {

    public Winner() {
    }

    public Winner(Integer winnerId, List<Integer> numbers, Lotto lotto) {
        this.winnerId = winnerId;
        this.numbers = numbers;
        this.lotto = lotto;
    }

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private Integer winnerId;

    @ElementCollection
    private List<Integer> numbers = new java.util.ArrayList<>();

    @ManyToOne // FetchType.LAZY by default
    @JoinColumn(name = "lotto_id")
    @OnDelete(action = OnDeleteAction.CASCADE) // Use for cascade delete capabilities of the foreign-key
    @JsonIgnore  // Used to ignore the logical property used in serialization and deserialization
    private Lotto lotto;

    public Lotto getLotto() {
        return lotto;
    }

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setLotto(Lotto lotto) {
        this.lotto = lotto;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }
}
