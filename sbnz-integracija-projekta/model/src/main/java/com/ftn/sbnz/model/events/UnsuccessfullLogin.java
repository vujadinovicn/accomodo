package com.ftn.sbnz.model.events;

import java.io.Serializable;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;

@Role(Role.Type.EVENT)
@Expires("5m")
public class UnsuccessfullLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    private long ipAdress;
    private long userId;
    
    public UnsuccessfullLogin(long ipAdress, long userId) {
        this.ipAdress = ipAdress;
        this.userId = userId;
    }

    public long getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(long ipAdress) {
        this.ipAdress = ipAdress;
    }    

    @Override
    public String toString() {
        return "UnsuccessfullLogin [ipAdress=" + ipAdress + ", userId=" + userId + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (ipAdress ^ (ipAdress >>> 32));
        result = prime * result + (int) (userId ^ (userId >>> 32));
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
        UnsuccessfullLogin other = (UnsuccessfullLogin) obj;
        if (ipAdress != other.ipAdress)
            return false;
        if (userId != other.userId)
            return false;
        return true;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    

}
