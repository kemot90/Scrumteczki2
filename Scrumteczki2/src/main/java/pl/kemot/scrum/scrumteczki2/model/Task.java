package pl.kemot.scrum.scrumteczki2.model;

/**
 * Created by Tomek on 22.11.13.
 */
public class Task {
    private String id;
    private String product;
    private EstimatedTime estimatedTime;

    /**
     * @return identyfikator zadania
     */
    public String getId() {
        return id;
    }

    /**
     * Ustawi identyfikator zadania.
     * @param id identyfikator zadania
     */
    public void setId(String id) {
        this.id = id;
    }

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
    public EstimatedTime getEstimatedTime() {
        return estimatedTime;
    }

    /**
     * Ustawi estymowany czas do zakończenia zadania.
     * @param estimatedTime estymowany czas do ukończenia zadania
     */
    public void setEstimatedTime(EstimatedTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }
}
