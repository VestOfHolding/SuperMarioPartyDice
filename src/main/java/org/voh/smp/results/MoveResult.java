package org.voh.smp.results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MoveResult extends DieResult {
    public MoveResult(int result) {
        super(result);
    }

    @Override
    public String toString() {
        return String.valueOf(result);
    }
}