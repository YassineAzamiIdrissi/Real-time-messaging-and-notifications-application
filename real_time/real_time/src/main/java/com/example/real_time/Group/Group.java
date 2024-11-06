package com.example.real_time.Group;

import com.example.real_time.GroupMessage.GroupMessage;
import com.example.real_time.User.User;
import com.example.real_time.UserGroups.UserGroups;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

import static jakarta.persistence.FetchType.EAGER;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_group")
public class Group {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "group")
    private List<UserGroups> groups;
    

    @OneToMany(mappedBy = "group")
    private List<GroupMessage> messages;
}
