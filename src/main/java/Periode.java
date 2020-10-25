import java.time.LocalDate;

public interface Periode {
    void setBeginDatum(LocalDate beginDatum);
    LocalDate getBeginDatum();
    void setEindDatum(LocalDate eindDatum);
    LocalDate getEindDatum();
}
