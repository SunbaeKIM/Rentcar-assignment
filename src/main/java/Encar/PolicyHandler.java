package Encar;

import Encar.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    AssignmentRepository assignmentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentRequested_RentAccept(@Payload RentRequested rentRequested){

        if(rentRequested.isMe()){
            System.out.println("##### listener RentAccept : " + rentRequested.toJson());
            Assignment assignment = new Assignment();
            assignment.setRentId(rentRequested.getRentId());
            assignment.setAgencyName("대리점_" + (int)((Math.random()*10000)%10));
            assignment.setCarId(rentRequested.getCarId());
            assignmentRepository.save(assignment);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentCancelRequested_RentCancel(@Payload RentCancelRequested rentCancelRequested){

        if(rentCancelRequested.isMe()){
            System.out.println("##### listener RentCancel : " + rentCancelRequested.toJson());
            //Assignment assignment = new Assignment();
            //assignment.setRentId(rentCancelRequested.getRentId());
            Optional<Assignment> optionalAssignment = assignmentRepository.findByRentId(rentCancelRequested.getRentId());
            Assignment assignment = optionalAssignment.orElseGet(Assignment::new);
            assignmentRepository.delete(assignment);
        }
    }


}
