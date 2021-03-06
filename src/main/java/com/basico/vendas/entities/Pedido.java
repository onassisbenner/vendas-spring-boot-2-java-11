package com.basico.vendas.entities;

import com.basico.vendas.entities.enums.PedidoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Pedido implements Serializable {
    private static final long serialVersionUID = 5419043836492360363L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "GMT")
    private Instant momento;
    private Integer status;
    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_client"))
    private Usuario user;
    @OneToMany(mappedBy = "id.pedido")
    private Set<PedidoItem> items = new HashSet<>();
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL)
    private Pagamento pagamento;

    public Pedido() {
    }

    public Pedido(Long id, Instant momento, Usuario user, PedidoStatus status) {
        this.id = id;
        this.momento = momento;
        setStatus(status);
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMomento() {
        return momento;
    }

    public void setMomento(Instant momento) {
        this.momento = momento;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public PedidoStatus getStatus() {
        return PedidoStatus.valueOf(status);
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Set<PedidoItem> getItems() {
        return items;
    }

    public void setStatus(PedidoStatus status) {
        if (status != null) {
            this.status = status.getCode();
        }
    }

    public Double getTotal(){
        var total = 0.0;
        for (PedidoItem item: items ) {
            total += item.getSubTotal();
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
