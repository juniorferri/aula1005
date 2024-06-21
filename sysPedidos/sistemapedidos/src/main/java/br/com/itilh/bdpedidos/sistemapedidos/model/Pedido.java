package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "tb_pedidos")

public class Pedido {

    @Id
    @SequenceGenerator(name = "seqPedido", sequenceName = "tb_pedido_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqPedido")
    private BigInteger id;

    @Column(name = "int_numero")
    private BigInteger numero;

    @Column(name = "dt_compra")
    private Date compra;

    @Column(name = "dt_entrega")
    private Date entrega;

    @Column(name = "dt_pagamento")
    private Date boAtivo;

    

    @ManyToOne
@JoinColumn(name = "id_cliente")
private Cliente cliente;

@ManyToOne
@JoinColumn(name = "id_forma_pagamento")
private FormaPagamento formaPagamento;


    
}

