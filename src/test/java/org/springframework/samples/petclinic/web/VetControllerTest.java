package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/**
 * Tests the VetController methods using Mockito BDD methods
 */
@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    /**
     * Looking at VetController, we see that it defines a ClinicService
     * property which we will need to inject
     */
    @Mock
    ClinicService service;

    /**
     * Looking at VetController, we see that showVetList expects a
     * Map<String, Object> model so we will mock it as well
     */
    @Mock
    Map<String, Object> model;

    /**
     * This declares the VetController that we will use, and Mockito
     * injects the two mock objects above into it.
     */
    @InjectMocks
    VetController controller;

    /**
     * This is the return value of service.findVets()
     */
    List<Vet> vets = new ArrayList<>();

    /**
     * Both methods under test below call service.findVets() so
     * we code it up as our given in this method
     */
    @BeforeEach
    void setUp() {
        // given
        vets.add(new Vet());
        given(service.findVets()).willReturn(vets);
    }

    /**
     * When controller.showVetList(model) is called
     * then we expect the service to find vets
     * and the model to have one element. We also assert
     * that the return value (view) matches the return
     * value of showVetList().
     */
    @Test
    void showVetList() {
        // when
        String view = controller.showVetList(model);

        // then
        then(service).should().findVets();
        then(model).should().put(anyString(), any());
        assertThat("vets/vetList").isEqualTo(view);
    }

    /**
     * When controller.showResourcesVetList() is invoked
     * then service.findVets() should be invoked and
     * the return value (vets) should have one element.
     */
    @Test
    void showResourcesVetList() {
        // when
        Vets vets = controller.showResourcesVetList();

        // then
        then(service).should().findVets();
        assertThat(vets.getVetList()).hasSize(1);
    }
}
