package com.ftn.sbnz.model.events;

import java.io.Serializable;

public class Customer implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;
    private Double accountBalance;
    public Customer(Long id, Double accountBalance) {
        this.id = id;
        this.accountBalance = accountBalance;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((accountBalance == null) ? 0 : accountBalance.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Customer other = (Customer) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (accountBalance == null) {
            if (other.accountBalance != null)
                return false;
        } else if (!accountBalance.equals(other.accountBalance))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Customer [id=" + id + ", accountBalance=" + accountBalance + "]";
    }

    
}
