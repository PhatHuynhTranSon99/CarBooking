package com.transonphat.carbooking.dao.car;

import com.transonphat.carbooking.dao.CustomSelectDAO;
import com.transonphat.carbooking.dao.DAO;
import com.transonphat.carbooking.dao.query.Query;
import com.transonphat.carbooking.dao.SearchableDAO;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.exceptions.CarNotFoundException;
import com.transonphat.carbooking.pagination.PaginationResult;
import com.transonphat.carbooking.repositories.CarRepository;
import com.transonphat.carbooking.search.SearchCriterion;
import com.transonphat.carbooking.search.SearchSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MySQLCarDAO implements DAO<Car>, SearchableDAO<Car>, CustomSelectDAO<Car> {
    private final CarRepository carRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MySQLCarDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car add(Car car) {
        return this.carRepository.save(car);
    }

    @Override
    public Car delete(long id) {
        Car car = this.getOne(id);
        this.carRepository.delete(car);
        return car;
    }

    @Override
    public PaginationResult<Car> getAll(int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Car> carPage = this.carRepository.findAll(pageable);

        //Map page to pagination result
        PaginationResult<Car> carPaginationResult = new PaginationResult<>(
                carPage.getTotalElements(),
                carPage.get().collect(Collectors.toList()),
                carPage.getNumber(),
                carPage.getTotalPages()
        );

        return carPaginationResult;
    }

    @Override
    public Car getOne(long id) {
        return this.carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    @Override
    public PaginationResult<Car> search(SearchCriterion<Car> criteria, int currentPage, int pageSize) {
        //Create page request
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        //Get result
        Page<Car> carPage = this.carRepository.findAll(
                new SearchSpecification<>(criteria),
                pageable
        );

        //Map page to pagination result
        PaginationResult<Car> carPaginationResult = new PaginationResult<>(
                carPage.getTotalElements(),
                carPage.get().collect(Collectors.toList()),
                carPage.getNumber(),
                carPage.getTotalPages()
        );

        return carPaginationResult;
    }

    @Override
    public List<Car> select(Query query) {
        //Create query
        TypedQuery<Car> carQuery = this.entityManager.createQuery(query.getQuery(), Car.class);

        //Add parameters
        for (String parameterName : query.getParameters().keySet()) {
            carQuery.setParameter(parameterName, query.getParameters().get(parameterName));
        }

        return carQuery.getResultList();
    }
}
