package org.efire.net.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.efire.net.model.Winner;

import java.util.List;

@Getter @Setter
@ToString
public class LottoDto {

    private Integer lottoId;
    private List<Integer> winningNumbers;
    private List<Winner> winners;
}
