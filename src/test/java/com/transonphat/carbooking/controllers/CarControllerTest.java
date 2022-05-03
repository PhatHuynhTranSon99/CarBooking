package com.transonphat.carbooking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transonphat.carbooking.domain.Car;
import com.transonphat.carbooking.domain.CarBuilder;
import com.transonphat.carbooking.exceptions.CarNotFoundException;
import com.transonphat.carbooking.services.BookingService;
import com.transonphat.carbooking.services.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @MockBean
    private BookingService bookingService;

    @Test
    public void getCarByIdShouldReturnCorrectResult() throws Exception {
        //When call -> Return a car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Mazda")
                .setModel("CSV")
                .setColor("Blue")
                .setConvertible(false)
                .setIdentificationNumber("0111-012")
                .setLicensePlate("G1-5655")
                .setRating(3.6)
                .setRate(5.5)
                .build();
        when(carService.getCarById(1L)).thenReturn(car);

        //Call endpoint
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.getId()))
                .andExpect(jsonPath("$.make").value(car.getMake()))
                .andExpect(jsonPath("$.model").value(car.getModel()))
                .andExpect(jsonPath("$.color").value(car.getColor()))
                .andExpect(jsonPath("$.isConvertible").value(car.getIsConvertible()))
                .andExpect(jsonPath("$.identificationNumber").value(car.getIdentificationNumber()))
                .andExpect(jsonPath("$.licensePlate").value(car.getLicensePlate()))
                .andExpect(jsonPath("$.rating").value(car.getRating()))
                .andExpect(jsonPath("$.rate").value(car.getRate()));
    }

    @Test
    public void getCarByIdThrowExceptionWhenNotFound() throws Exception {
        //When call -> Throw exception
        when(carService.getCarById(1L)).thenThrow(new CarNotFoundException("Car does not exist"));

        //Call
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CarNotFoundException));
    }

    @Test
    public void createNewCarSuccessfully() throws Exception {
        //When save -> Return car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Mazda")
                .setModel("CSV")
                .setColor("Blue")
                .setConvertible(false)
                .setIdentificationNumber("0111-012")
                .setLicensePlate("G1-5655")
                .setRating(3.6)
                .setRate(5.5)
                .build();
        when(carService.saveCar(car)).thenReturn(car);

        //Call
        mockMvc.perform(
                    post("/cars")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(car))
                )
                .andExpect(status().isOk());

        //Assert call
        verify(carService).saveCar(car);
    }

    @Test
    public void createNewCarShouldThrowExceptionWhenMissingAttributes() throws Exception {
        //When save -> Return car
        Car car = new CarBuilder()
                .setId(1L)
                .setMake("Mazda")
                .setModel("CSV")
                .setColor("Blue")
                .setConvertible(false)
                .setIdentificationNumber(null) //No identification
                .setLicensePlate("G1-5655")
                .setRating(3.6)
                .setRate(5.5)
                .build();
        when(carService.saveCar(car)).thenReturn(car);

        //Call
        mockMvc.perform(
                        post("/cars")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(car))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCarSuccessfully() {

    }

    @Test
    public void updateCarThrowExceptionWhenNotFound() {

    }

    @Test
    public void deleteCarByIdSuccessfully() {

    }

    @Test
    public void getAllCarSuccessfully() {

    }
}
