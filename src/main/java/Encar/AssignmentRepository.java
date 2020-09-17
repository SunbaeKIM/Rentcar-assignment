package Encar;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AssignmentRepository extends PagingAndSortingRepository<Assignment, Long >{
    Optional<Assignment> findByRentId(Long rentId);
}