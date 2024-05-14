package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class Biljka {
    @Position(0)
    private String vrsta;
    @Position(1)
    private String nadvrsta;
    public Biljka(String vrsta, String nadvrsta) {
        this.vrsta = vrsta;
        this.nadvrsta = nadvrsta;
    }
    public String getVrsta() {
        return vrsta;
    }
    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }
    public String getNadvrsta() {
        return nadvrsta;
    }
    public void setNadvrsta(String nadvrsta) {
        this.nadvrsta = nadvrsta;
    }
    @Override
    public String toString() {
        return "Biljka [vrsta=" + vrsta + ", nadvrsta=" + nadvrsta + "]";
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((vrsta == null) ? 0 : vrsta.hashCode());
        result = prime * result + ((nadvrsta == null) ? 0 : nadvrsta.hashCode());
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
        Biljka other = (Biljka) obj;
        if (vrsta == null) {
            if (other.vrsta != null)
                return false;
        } else if (!vrsta.equals(other.vrsta))
            return false;
        if (nadvrsta == null) {
            if (other.nadvrsta != null)
                return false;
        } else if (!nadvrsta.equals(other.nadvrsta))
            return false;
        return true;
    }

    
}
