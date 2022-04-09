package model.user;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.UUID;

public class UserSOAPEntity implements Cloneable {
    private UUID userID;
    private String login;
    private UserTypeSOAPEntity type;
    private boolean active;

    public UserSOAPEntity(UUID userID, String login, boolean isActive, UserTypeSOAPEntity type) {
        this.userID = userID;
        this.login = login;
        this.active = isActive;
        this.type = type;
    }

    public UUID getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public UserTypeSOAPEntity getType() {
        return type;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean getActive() {
        return active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserSOAPEntity userEntity = (UserSOAPEntity) o;

        return new EqualsBuilder()
                .append(userID, userEntity.userID)
                .isEquals();
    }

    @Override
    public UserSOAPEntity clone() throws CloneNotSupportedException {
        UserSOAPEntity u = (UserSOAPEntity)super.clone();
        return u;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(userID)
                .toHashCode();
    }

}
