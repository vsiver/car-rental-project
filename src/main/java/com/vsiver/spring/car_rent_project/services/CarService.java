package com.vsiver.spring.car_rent_project.services;


import com.vsiver.spring.car_rent_project.dtos.CarDto;
import com.vsiver.spring.car_rent_project.entities.Car;
import com.vsiver.spring.car_rent_project.exceptions.NoCarWithSuchIdException;
import com.vsiver.spring.car_rent_project.repositories.CarRepository;
import com.vsiver.spring.car_rent_project.utils.CustomMappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CarService {

    private CustomMappers customMappers;

    private CarRepository carRepository;

    private Logger logger = LoggerFactory.getLogger(CarService.class);

    public CarService(CustomMappers customMappers, CarRepository carRepository) {
        this.customMappers = customMappers;
        this.carRepository = carRepository;
    }

    public List<CarDto> getAllCars() {
        logger.info("Getting all cars from car repository");
        return carRepository.findAll().stream().map(car -> customMappers.mapCarToCarDto(car))
                .toList();
    }

    public CarDto saveOrUpdateCar(CarDto carDto) {
        Car car = customMappers.mapCarDtoToCar(carDto);
        carRepository.save(car);
        return customMappers.mapCarToCarDto(car);
    }

    public void deleteCarById(Integer carId) throws NoCarWithSuchIdException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoCarWithSuchIdException("No car with such id"));
        logger.info("deleting car with ID " + car.getCarId());
        carRepository.delete(car);
    }

    public CarDto findById(Integer carId) throws NoCarWithSuchIdException {
        Car car = carRepository.findById(carId).orElseThrow(() -> new NoCarWithSuchIdException("No car with such id"));
        logger.info("Getting car with ID " + carId);
        return customMappers.mapCarToCarDto(car);
    }
}
