package com.ftn.sbnz.model.models;

import org.kie.api.definition.type.Position;

public class Parent {
    @Position(0)
    private String parent;
    @Position(1)
    private String child;
    public Parent(String parent, String child) {
        this.parent = parent;
        this.child = child;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getChild() {
        return child;
    }
    public void setChild(String child) {
        this.child = child;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
        result = prime * result + ((child == null) ? 0 : child.hashCode());
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
        Parent other = (Parent) obj;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        if (child == null) {
            if (other.child != null)
                return false;
        } else if (!child.equals(other.child))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Parent [parent=" + parent + ", child=" + child + "]";
    }
    
}
