/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.controllers;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ppitbull
 */
public class AbstractJpaController {
    protected EntityManagerFactory emf=Persistence.createEntityManagerFactory("MicroEvaluation");

}
