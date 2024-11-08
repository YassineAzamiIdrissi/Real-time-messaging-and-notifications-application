package com.example.real_time.Group;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    @Query("""
               SELECT (COUNT(*)>0) FROM Group group 
               WHERE group.grpCreator.id = :connectedId 
               AND group.name = :groupName
            """)
    boolean isGroupAlreadyExists(Integer connectedId,
                                 String groupName);

    @Query("""
                     SELECT group From Group group 
                     WHERE group.grpCreator.id = :connectedId
            """)
    Page<Group> findGroupsOfConnectedUser(Pageable pageable, Integer connectedId);

    @Query("""
                        SELECT grp FROM Group grp 
                        WHERE EXISTS (
                            SELECT membership FROM GroupMembership membership 
                            WHERE membership.member.id = :userId
                            AND membership.group.id = grp.id 
                        ) 
                        AND grp.grpCreator.id != :userId
            """)
    Page<Group> findJoinedGroupsByThisUser(Pageable pageable, Integer userId);
}
