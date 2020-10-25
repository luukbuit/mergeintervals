import java.time.LocalDate;

public class PeriodeImpl implements Periode{

    private LocalDate beginDatum;
    private LocalDate eindDatum;

    @Override
    public LocalDate getBeginDatum() {
        return beginDatum;
    }

    @Override
    public void setBeginDatum(LocalDate beginDatum) {
        this.beginDatum = beginDatum;
    }

    @Override
    public LocalDate getEindDatum() {
        return eindDatum;
    }

    @Override
    public void setEindDatum(LocalDate eindDatum) {
        this.eindDatum = eindDatum;
    }

    @Override
    public String toString() {
        return "PeriodeImpl{" +
                "beginDatum=" + beginDatum +
                ", eindDatum=" + eindDatum +
                '}';
    }
}
