import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

class PeriodeUtilTest {

    @Test
    void merge5Periodes() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(LocalDate.of(2010, 6, 30));

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 4, 1));
        p2.setEindDatum(LocalDate.of(2010, 9, 30));

        Periode p3 = new PeriodeImpl();
        p3.setBeginDatum(LocalDate.of(2010, 10, 1));
        p3.setEindDatum(LocalDate.of(2011, 2, 15));

        Periode p4 = new PeriodeImpl();
        p4.setBeginDatum(LocalDate.of(2011, 4, 1));
        p4.setEindDatum(LocalDate.of(2011, 7, 31));

        Periode p5 = new PeriodeImpl();
        p5.setBeginDatum(LocalDate.of(2009, 1, 1));
        p5.setEindDatum(LocalDate.of(2009, 6, 30));

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);
        inputPeriodes.add(p3);
        inputPeriodes.add(p4);
        inputPeriodes.add(p5);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(3));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2009, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(LocalDate.of(2009, 6, 30)));
        assertThat(outputPeriodes.get(1).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(1).getEindDatum(), is(LocalDate.of(2011, 2, 15)));
        assertThat(outputPeriodes.get(2).getBeginDatum(), is(LocalDate.of(2011, 4, 1)));
        assertThat(outputPeriodes.get(2).getEindDatum(), is(LocalDate.of(2011, 7, 31)));
    }

    @Test
    public void mergeLegeLijst() {
        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(Collections.emptyList());
        assertThat(outputPeriodes.size(), is(0));
    }

    @Test
    void mergeInsluitendePeriodes() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(LocalDate.of(2010, 6, 30));

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 2, 1));
        p2.setEindDatum(LocalDate.of(2010, 3, 30));

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(LocalDate.of(2010, 6, 30)));
    }

    @Test
    void mergeEnkelePeriode() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(LocalDate.of(2010, 6, 30));

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(LocalDate.of(2010, 6, 30)));
    }

    @Test
    void mergeEnkelePeriodeZonderEinddatum() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(null);

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(nullValue()));
    }


    @Test
    void mergeTweePeriodesEersteZonderEinddatum() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(null);

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 2, 1));
        p2.setEindDatum(LocalDate.of(2010, 6, 30));

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(nullValue()));
    }

    @Test
    void mergeTweePeriodesTweedeZonderEinddatum() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(LocalDate.of(2010, 6, 30));

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 2, 1));
        p2.setEindDatum(null);

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(nullValue()));
    }

    @Test
    void mergeTweePeriodesBeideZonderEinddatum() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(null);

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 2, 1));
        p2.setEindDatum(null);

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(nullValue()));
    }

    @Test
    void mergeTweePeriodesMet1DagOverlap() {
        Periode p1 = new PeriodeImpl();
        p1.setBeginDatum(LocalDate.of(2010, 1, 1));
        p1.setEindDatum(LocalDate.of(2010, 1, 15));

        Periode p2 = new PeriodeImpl();
        p2.setBeginDatum(LocalDate.of(2010, 1, 15));
        p2.setEindDatum(LocalDate.of(2010, 1, 31));

        List<Periode> inputPeriodes = new ArrayList<>();
        inputPeriodes.add(p1);
        inputPeriodes.add(p2);

        List<Periode> outputPeriodes = PeriodeUtil.mergeIntervals(inputPeriodes);
        assertThat(outputPeriodes.size(), is(1));
        assertThat(outputPeriodes.get(0).getBeginDatum(), is(LocalDate.of(2010, 1, 1)));
        assertThat(outputPeriodes.get(0).getEindDatum(), is(LocalDate.of(2010, 1, 31)));
    }


}
