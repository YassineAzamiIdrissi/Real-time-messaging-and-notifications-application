package com.example.real_time.User;

import com.example.real_time.ActivationCode.ActivationCode;
import com.example.real_time.Authority.Authority;
import com.example.real_time.FriendRequest.FriendRequest;
import com.example.real_time.Group.Group;
import com.example.real_time.GroupMessage.GroupMessage;
import com.example.real_time.Message.Message;
import com.example.real_time.RecoveryCode.RecoveryCode;
import com.example.real_time.GroupMembership.GroupMembership;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_user")
public class User implements Principal, UserDetails {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private boolean enabled;
    private boolean locked;
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = EAGER)
    @JoinTable(
            name = "user_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;

    @OneToMany(mappedBy = "user", fetch = EAGER)
    private List<ActivationCode> activationCodes;


    @OneToMany(mappedBy = "sender", fetch = EAGER)
    private List<FriendRequest> sentRequests;

    @OneToMany(mappedBy = "receiver", fetch = EAGER)
    private List<FriendRequest> receivedRequests;


    @OneToMany(mappedBy = "sender", fetch = EAGER)
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "receiver", fetch = EAGER)
    private List<Message> receivedMessages;

    @OneToMany(mappedBy = "user")
    private List<RecoveryCode> recoveryCodes;

    @OneToMany(mappedBy = "sender")
    private List<GroupMessage> sentGrpMessages;

    @OneToMany(mappedBy = "member")
    private List<GroupMembership> userGroups;

    @OneToMany(mappedBy = "grpCreator")
    private List<Group> createdGroups;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(
                auth -> new SimpleGrantedAuthority(auth.getAuthority())
        ).collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String fullName() {
        return firstName + " " + lastName;
    }
}
