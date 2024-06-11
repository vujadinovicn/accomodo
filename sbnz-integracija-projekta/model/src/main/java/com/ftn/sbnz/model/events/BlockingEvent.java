package com.ftn.sbnz.model.events;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;


@Role(Role.Type.EVENT)
@Expires("2m")
public class BlockingEvent {
    private String email;
    private boolean block;

    public BlockingEvent(String email, boolean block) {
        this.email = email;
        this.block = block;
    }
    
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isBlock() {
        return block;
    }
    public void setBlock(boolean block) {
        this.block = block;
    }

    
    
}
