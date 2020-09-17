package Encar;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long assignmentId;
    private Long rentId;
    private Long carId;
    private String agencyName;

    @PostPersist
    public void onPostPersist(){
        RentAccepted rentAccepted = new RentAccepted();
        BeanUtils.copyProperties(this, rentAccepted);
        rentAccepted.setRentId(this.getRentId());
        rentAccepted.setAgencyname(this.getAgencyName());
        rentAccepted.setStatus("RentAccepted");
        rentAccepted.publishAfterCommit();
    }
    @PostRemove
    public void onPostRemove(){
        RentCanceled rentCanceled = new RentCanceled();
        BeanUtils.copyProperties(this, rentCanceled);
        rentCanceled.setStatus("RentCanceled");
        rentCanceled.publishAfterCommit();
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
    public Long getRentId() {
        return rentId;
    }

    public void setRentId(Long rentId) {
        this.rentId = rentId;
    }
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }


    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }





}
