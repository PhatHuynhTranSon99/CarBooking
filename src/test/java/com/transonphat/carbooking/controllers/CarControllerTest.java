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
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void updateCarSuccessfully() throws Exception {
        //New attribute
        String newVIN = "0111-022";
        String newLicensePlate = "G5-5655";
        String newColor = "Green";
        String newMake = "MAZDA";
        String newModel = "CSS";
        Boolean newConvertible = false;
        Double newRating = 3.0;
        Double newRate = 9.0;

        //Return car when find
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

        Car newCar = new CarBuilder()
                .setId(1L)
                .setMake(newMake)
                .setModel(newModel)
                .setColor(newColor)
                .setConvertible(newConvertible)
                .setIdentificationNumber(newVIN)
                .setLicensePlate(newLicensePlate)
                .setRating(newRating)
                .setRate(newRate)
                .build();

        when(carService.getCarById(1L)).thenReturn(car);
        when(carService.saveCar(any())).thenReturn(newCar);

        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("identification", newVIN);
        requestParams.add("color", newColor);
        requestParams.add("make", newMake);
        requestParams.add("model", newModel);
        requestParams.add("convertible", String.valueOf(newConvertible));
        requestParams.add("rating", String.valueOf(newRating));
        requestParams.add("rate", String.valueOf(newRate));
        requestParams.add("licensePlate", newLicensePlate);


        mockMvc.perform(
                put("/cars/1").params(requestParams)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newCar.getId()))
                .andExpect(jsonPath("$.make").value(newCar.getMake()))
                .andExpect(jsonPath("$.model").value(newCar.getModel()))
                .andExpect(jsonPath("$.color").value(newCar.getColor()))
                .andExpect(jsonPath("$.isConvertible").value(newCar.getIsConvertible()))
                .andExpect(jsonPath("$.identificationNumber").value(newCar.getIdentificationNumber()))
                .andExpect(jsonPath("$.licensePlate").value(newCar.getLicensePlate()))
                .andExpect(jsonPath("$.rating").value(newCar.getRating()))
                .andExpect(jsonPath("$.rate").value(newCar.getRate()))
                .andDo(print());

    }

    @Test
    public void updateCarThrowExceptionWhenNotFound() throws Exception {
        when(carService.getCarById(1L)).thenThrow(new CarNotFoundException("Car does not exist"));

        //Update params
        String newVIN = "0111-022";
        String newLicensePlate = "G5-5655";
        String newColor = "Green";
        String newMake = "MAZDA";
        String newModel = "CSS";
        Boolean newConvertible = false;
        Double newRating = 3.0;
        Double newRate = 9.0;

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("identification", newVIN);
        requestParams.add("color", newColor);
        requestParams.add("make", newMake);
        requestParams.add("model", newModel);
        requestParams.add("convertible", String.valueOf(newConvertible));
        requestParams.add("rating", String.valueOf(newRating));
        requestParams.add("rate", String.valueOf(newRate));
        requestParams.add("licensePlate", newLicensePlate);

        mockMvc.perform(
                put("/cars/1").params(requestParams)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof CarNotFoundException));
    }

    @Test
    public void deleteCarByIdSuccessfully() throws Exception {
        //When call delete -> Return a car
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
        when(carService.deleteCar(1L)).thenReturn(car);

        //Call endpoint
        mockMvc.perform(delete("/cars/1"))
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

        verify(bookingService).deleteRelatedBookingsWithCar(1L);
    }

    @Test
    public void getAllCarSuccessfully() throws Exception {
        //Params
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("color", "Blue");

        //Assert that search car is called
        mockMvc.perform(get("/cars").params(requestParams))
                .andExpect(status().isOk());

        verify(carService).searchCar(any(), anyInt(), anyInt());
    }
}
