package controllers.dtos;

/**
 * @Author: Voicu Hodrea
 * @Date: 31.05.2017
 */
public class StatisticsDTO {

    private Integer numberOfNotes;
    private Integer numberOfLeaves;
    private Integer numberOfRoots;

    public Integer getNumberOfNotes() {
        return numberOfNotes;
    }

    public void setNumberOfNotes(Integer numberOfNotes) {
        this.numberOfNotes = numberOfNotes;
    }

    public Integer getNumberOfLeaves() {
        return numberOfLeaves;
    }

    public void setNumberOfLeaves(Integer numberOfLeaves) {
        this.numberOfLeaves = numberOfLeaves;
    }

    public Integer getNumberOfRoots() {
        return numberOfRoots;
    }

    public void setNumberOfRoots(Integer numberOfRoots) {
        this.numberOfRoots = numberOfRoots;
    }
}
