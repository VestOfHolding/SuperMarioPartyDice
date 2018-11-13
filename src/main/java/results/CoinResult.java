package results;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CoinResult extends DieResult {
    public CoinResult(int result) {
        super(result);
    }

    @Override
    public String toString() {
        return result + " coins";
    }
}