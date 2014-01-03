package pl.kemot.scrum.scrumteczki2.model;

/**
 * Created by Tomek on 22.11.13.
 */
public class Task extends BaseEntity {
    private String label;
    private String product;
    private String estimatedTime;
    private long sprintId;

    /**
     * @return nazwa produktu/zadania
     */
    public String getProduct() {
        return product;
    }

    /**
     * Ustawi nazwę produktu/zadania.
     * @param product nazwa produktu/zadania
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @return estymowany czas do ukończenia zadania
     */
    public String getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Ustawi estymowany czas do zakończenia zadania.
     * @param estimatedTime estymowany czas do ukończenia zadania
     */
    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    /**
     * @return etykieta zadania w postaci ciągu liczba rozdzielonych kropkami
     */
    public String getLabel() {
        return label;
    }

    /**
     * Ustawi etykietę dla zadania postaci ciągu liczba rozdzielonych kropkami.
     * @param label etykieta zadania w postaci ciągu liczba rozdzielonych kropkami
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return identyfikator sprinta, do którego należy zadanie
     */
    public long getSprintId() {
        return sprintId;
    }

    /**
     * Ustawi identyfikator sprinta, do którego należy zadanie.
     * @param sprintId identyfikator sprinta, do którego należy zadanie
     */
    public void setSprintId(long sprintId) {
        this.sprintId = sprintId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((this.label == null) ? 0 : this.label.hashCode());
        result = prime * result + ((this.product == null) ? 0 : this.product.hashCode());
        result = prime * result + ((this.estimatedTime == null) ? 0 : this.estimatedTime.hashCode());
        return result;
    }
}
