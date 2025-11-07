package com.example.eco_hospedajes.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkin;
    private LocalDate checkout;
    private int personas;
    private double precioTotal;

    // Constructor vacío
    public Reserva() {}

    // Constructor con parámetros
    public Reserva(LocalDate checkin, LocalDate checkout, int personas, double precioTotal) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.personas = personas;
        this.precioTotal = precioTotal;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public LocalDate getCheckin() { return checkin; }
    public void setCheckin(LocalDate checkin) { this.checkin = checkin; }
    public LocalDate getCheckout() { return checkout; }
    public void setCheckout(LocalDate checkout) { this.checkout = checkout; }
    public int getPersonas() { return personas; }
    public void setPersonas(int personas) { this.personas = personas; }
    public double getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(double precioTotal) { this.precioTotal = precioTotal; }
}
