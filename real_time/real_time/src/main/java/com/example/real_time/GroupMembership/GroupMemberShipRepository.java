package com.example.real_time.GroupMembership;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupMemberShipRepository extends JpaRepository<GroupMembership, Integer> {
    @Query("""
                        SELECT m FROM GroupMembership m
                        WHERE m.group.id = :groupId 
            """)
    Page<GroupMembership> findGroupMemberships(Pageable pageable, int groupId);

    @Query("""
                        SELECT (COUNT(*)>0) FROM GroupMembership membership 
                        WHERE membership.group.id = :groupId 
                        AND membership.member.id = :userId
            """)
    boolean isUserAlreadyJoined(Integer userId, Integer groupId);
}
