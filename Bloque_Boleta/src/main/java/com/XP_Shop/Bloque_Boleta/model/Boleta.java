package com.XP_Shop.Bloque_Boleta.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boleta")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idBoleta;

    @Column(nullable = false)
    private LocalDate fechaCompra;

    @NotNull(message = "Es obligatorio llenar este apartado")
    @Column(nullable = false)
    private Double totalCompra;

    @ManyToOne
    @JoinColumn(name = "usuario_Id")
    private Integer id_usuario;

    @ManyToOne
    @JoinColumn(name = "metodoEnvio_Id")
    private MetodoEnvio metodoEnvio;
    
    @ManyToOne
    @JoinColumn(name = "metodoPago_Id")
    private MetodoPago metodoPago;

    @OneToOne
    @JoinColumn(name = "detalleBoleta_id")
    private DetalleBoleta detalleBoleta;
    
}
